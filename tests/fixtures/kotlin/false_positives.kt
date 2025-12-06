// Test fixture: False positives - code that LOOKS dead but is NOT
// These should NOT be reported as dead code
package com.example.fixtures.falsepositives

import android.os.Bundle
import android.view.View
import java.io.Serializable

// ============================================================================
// Case 1: Reflection - classes instantiated via reflection
// ============================================================================

// Used by Retrofit/Moshi for JSON deserialization
class ApiResponse(
    val status: String,
    val data: List<Item>
)

data class Item(
    val id: Long,
    val name: String
)

// Used by Room database @Entity
data class UserEntity(
    val id: Long,
    val email: String,
    val name: String
)

// ============================================================================
// Case 2: Android lifecycle callbacks - called by the framework
// ============================================================================

class MyActivity {
    // Called by Android framework
    fun onCreate(savedInstanceState: Bundle?) {
        println("Activity created")
    }

    fun onResume() {
        println("Activity resumed")
    }

    fun onPause() {
        println("Activity paused")
    }

    fun onDestroy() {
        println("Activity destroyed")
    }

    // Called by XML onClick
    fun onButtonClick(view: View) {
        println("Button clicked")
    }
}

// ============================================================================
// Case 3: Serialization - fields accessed during serialization
// ============================================================================

class SerializableData : Serializable {
    val id: Long = 0
    val hiddenField: String = ""  // Accessed by serialization
    private val privateSerializedField: Int = 0
}

// Parcelable pattern
class ParcelableData {
    val value: String = ""

    companion object {
        @JvmField
        val CREATOR = object : Any() {
            fun createFromParcel(): ParcelableData = ParcelableData()
            fun newArray(size: Int): Array<ParcelableData?> = arrayOfNulls(size)
        }
    }
}

// ============================================================================
// Case 4: Dependency Injection - provided/injected classes
// ============================================================================

// Dagger/Hilt module
class NetworkModule {
    fun provideRetrofit(): Any {
        return Object()
    }

    fun provideOkHttpClient(): Any {
        return Object()
    }
}

// Injected class - may appear unused but is instantiated by DI
class UserRepository {
    fun getUsers(): List<String> = emptyList()
}

class AuthInterceptor {
    fun intercept(request: Any): Any = request
}

// ============================================================================
// Case 5: Interface implementations for callbacks
// ============================================================================

interface ClickListener {
    fun onClick()
    fun onLongClick(): Boolean
}

// Passed as lambda/anonymous class
class ButtonClickHandler : ClickListener {
    override fun onClick() {
        println("Clicked")
    }

    override fun onLongClick(): Boolean {
        println("Long clicked")
        return true
    }
}

// ============================================================================
// Case 6: Test classes and fixtures
// ============================================================================

class TestFixture {
    fun setUp() {
        println("Setup")
    }

    fun tearDown() {
        println("Teardown")
    }

    fun testSomething() {
        println("Test")
    }
}

// Mock implementation
class MockRepository {
    val mockData = listOf("a", "b", "c")

    fun getData(): List<String> = mockData
}

// ============================================================================
// Case 7: Library API - public surface for external consumers
// ============================================================================

// Part of library's public API
class LibraryApi {
    fun publicMethod() {
        println("Public API")
    }

    fun anotherPublicMethod(): String = "result"
}

// ============================================================================
// Case 8: Kotlin conventions - special named methods
// ============================================================================

class Conventions {
    // operator functions
    operator fun plus(other: Conventions): Conventions = this
    operator fun get(index: Int): Any = index
    operator fun invoke(): Unit = println("Invoked")
    operator fun contains(element: Any): Boolean = false

    // Component functions for destructuring
    operator fun component1(): String = "first"
    operator fun component2(): Int = 2

    // Property delegates
    operator fun getValue(thisRef: Any?, property: Any): String = "delegated"
    operator fun setValue(thisRef: Any?, property: Any, value: String) {}
}

// ============================================================================
// Case 9: Companion object factory - called via ClassName.create()
// ============================================================================

class FactoryPattern private constructor(val value: String) {
    companion object {
        fun create(value: String): FactoryPattern = FactoryPattern(value)
        fun default(): FactoryPattern = FactoryPattern("default")

        // Constant used externally
        const val MAX_VALUE = 100
        const val API_VERSION = "v1"
    }
}

// ============================================================================
// Case 10: Enum values - all used via valueOf() or entries
// ============================================================================

enum class Status {
    PENDING,
    ACTIVE,
    COMPLETED,
    FAILED;

    companion object {
        fun fromString(s: String): Status = valueOf(s.uppercase())
    }
}

enum class HttpStatus(val code: Int) {
    OK(200),
    NOT_FOUND(404),
    SERVER_ERROR(500)
}

// ============================================================================
// Case 11: Extension functions used from other modules
// ============================================================================

fun String.toTitleCase(): String = this.split(" ").joinToString(" ") {
    it.replaceFirstChar { c -> c.uppercase() }
}

fun List<String>.joinWithComma(): String = this.joinToString(", ")

// ============================================================================
// Case 12: Annotation processors - generated code
// ============================================================================

// BindingAdapter for DataBinding
fun setImageUrl(view: View, url: String?) {
    println("Setting image: $url")
}

// BindingConversion
fun convertStringToInt(value: String): Int = value.toIntOrNull() ?: 0

// ============================================================================
// Case 13: JNI/Native methods
// ============================================================================

class NativeLib {
    external fun nativeMethod(): Long
    external fun processData(data: ByteArray): ByteArray

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}

// ============================================================================
// Case 14: Coroutine entry points
// ============================================================================

class CoroutineExample {
    // Called from coroutine scope
    suspend fun fetchData(): String {
        return "data"
    }

    suspend fun processAsync(): Result<String> {
        return Result.success("processed")
    }
}

// ============================================================================
// Case 15: Sealed class variants used in when expressions elsewhere
// ============================================================================

sealed class UiEvent {
    object Loading : UiEvent()
    data class Success(val data: String) : UiEvent()
    data class Error(val message: String) : UiEvent()
    object Retry : UiEvent()
}

// ============================================================================
// Case 16: Builder pattern methods
// ============================================================================

class RequestBuilder {
    private var url: String = ""
    private var method: String = "GET"
    private var headers: Map<String, String> = emptyMap()

    fun url(url: String) = apply { this.url = url }
    fun method(method: String) = apply { this.method = method }
    fun headers(headers: Map<String, String>) = apply { this.headers = headers }
    fun build(): String = "$method $url"
}

// ============================================================================
// Case 17: DSL functions
// ============================================================================

class HtmlBuilder {
    fun div(init: HtmlBuilder.() -> Unit) {}
    fun span(init: HtmlBuilder.() -> Unit) {}
    fun p(text: String) {}
}

fun html(init: HtmlBuilder.() -> Unit): HtmlBuilder {
    val builder = HtmlBuilder()
    builder.init()
    return builder
}
