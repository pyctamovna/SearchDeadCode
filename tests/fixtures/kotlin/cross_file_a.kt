// Test fixture: Cross-file references - File A
// Tests that references across files are properly tracked
package com.example.fixtures.crossfile

// Public class used by cross_file_b.kt
class SharedService {
    fun process(data: String): String {
        return data.uppercase()
    }

    fun unusedServiceMethod() {  // DEAD - not used in any file
        println("Unused")
    }
}

// Interface implemented in cross_file_b.kt
interface DataProvider {
    fun getData(): List<String>
    fun getMetadata(): Map<String, Any>
}

// Base class extended in cross_file_b.kt
open class BaseRepository {
    open fun save(item: Any) {
        println("Saving: $item")
    }

    open fun delete(id: Long) {
        println("Deleting: $id")
    }

    fun unusedBaseMethod() {  // DEAD - not used or overridden
        println("Unused base method")
    }
}

// Sealed class with variants used in cross_file_b.kt
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
    object Idle : NetworkResult<Nothing>()  // DEAD - never used in when expressions
}

// Object used from cross_file_b.kt
object Constants {
    const val API_URL = "https://api.example.com"
    const val TIMEOUT = 30_000L
    const val UNUSED_CONSTANT = "unused"  // DEAD
}

// Extension function used in cross_file_b.kt
fun String.toSlug(): String = this.lowercase().replace(" ", "-")

// Extension function not used anywhere
fun String.unusedExtension(): String = this.reversed()  // DEAD

// Type alias used in cross_file_b.kt
typealias UserId = Long
typealias UserCallback = (UserId, String) -> Unit

// Unused type alias
typealias UnusedAlias = Map<String, List<Int>>  // DEAD

// Enum used in cross_file_b.kt
enum class Priority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL  // Might be DEAD if not used in when expressions
}

// Unused class - only in this file, never referenced
class OrphanClass {  // DEAD
    val orphanProperty = "orphan"
    fun orphanMethod() = orphanProperty
}
