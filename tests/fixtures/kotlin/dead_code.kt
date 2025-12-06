// Test fixture: Various dead code patterns in Kotlin
package com.example.fixtures

// DC001: Unreferenced class
class UnusedClass {
    fun doSomething() {}
}

// DC001: Unreferenced method in used class
class UsedClassWithDeadMethod {
    fun usedMethod() {
        println("used")
    }

    // This method is never called
    private fun unusedPrivateMethod() {
        println("never called")
    }
}

// DC001: Unreferenced property
class ClassWithDeadProperty {
    private val unusedProperty = "dead"  // Never read
    val usedProperty = "alive"
}

// DC002: Write-only variable
class WriteOnlyExample {
    private var writeOnlyCounter = 0  // Assigned but never read

    fun increment() {
        writeOnlyCounter++
    }
}

// DC003: Unused parameter
class UnusedParamExample {
    fun processData(data: String, unusedParam: Int): String {
        return data.uppercase()  // unusedParam is never used
    }
}

// DC004: Unused import (would need actual imports)

// DC005: Unused enum case
enum class Status {
    ACTIVE,
    INACTIVE,
    DEPRECATED,  // Never referenced
    ARCHIVED     // Never referenced
}

fun checkStatus(status: Status): Boolean {
    return status == Status.ACTIVE || status == Status.INACTIVE
}

// DC008: Unused sealed variant
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    object Error : UiState()
    object Empty : UiState()  // Never instantiated
}

fun handleState(state: UiState) {
    when (state) {
        is UiState.Loading -> println("Loading")
        is UiState.Success -> println("Success: ${state.data}")
        is UiState.Error -> println("Error")
        is UiState.Empty -> {} // Handled but never created
    }
}

// Entry point - call some used code
fun main() {
    val instance = UsedClassWithDeadMethod()
    instance.usedMethod()

    val prop = ClassWithDeadProperty()
    println(prop.usedProperty)

    val write = WriteOnlyExample()
    write.increment()

    val param = UnusedParamExample()
    param.processData("test", 42)

    checkStatus(Status.ACTIVE)

    handleState(UiState.Loading)
    handleState(UiState.Success("data"))
    handleState(UiState.Error)
}
