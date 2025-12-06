// Test fixture: Write-only variable patterns
package com.example.fixtures.writeonly

// Case 1: Simple write-only private variable
class SimpleWriteOnly {
    private var counter = 0  // DEAD: assigned but never read

    fun increment() {
        counter++
    }

    fun reset() {
        counter = 0
    }
}

// Case 2: Write-only with multiple assignments
class MultipleAssignments {
    private var state = "initial"  // DEAD: assigned multiple times but never read

    fun update(newState: String) {
        state = newState
    }

    fun clear() {
        state = ""
    }
}

// Case 3: NOT write-only - variable is read
class ReadAndWrite {
    private var value = 0

    fun increment(): Int {
        value++
        return value  // READ here
    }
}

// Case 4: NOT write-only - used in condition
class UsedInCondition {
    private var enabled = false

    fun enable() {
        enabled = true
    }

    fun doAction() {
        if (enabled) {  // READ here
            println("Action!")
        }
    }
}

// Case 5: Backing field pattern - should be skipped
class BackingField {
    private var _data: String? = null  // Backing field, skip

    val data: String
        get() = _data ?: "default"

    fun setData(value: String) {
        _data = value
    }
}

// Case 6: Constant naming - should be skipped
class Constants {
    private val MAX_SIZE = 100  // Constant naming, skip
    private var CURRENT_COUNT = 0  // ALL_CAPS but var, might be intentional

    fun process() {
        CURRENT_COUNT++
    }
}

// Case 7: LiveData/StateFlow pattern - intentional
class ViewModelPattern {
    private val _uiState = mutableListOf<String>()  // Backing field for StateFlow

    fun addItem(item: String) {
        _uiState.add(item)
    }
}

// Case 8: Lambda capture - complex case
class LambdaCapture {
    private var callback: (() -> Unit)? = null  // Assigned but invoked elsewhere

    fun setCallback(cb: () -> Unit) {
        callback = cb
    }

    fun triggerCallback() {
        callback?.invoke()  // READ (invoke)
    }
}

fun main() {
    // Use some classes to make them reachable
    val simple = SimpleWriteOnly()
    simple.increment()
    simple.reset()

    val multi = MultipleAssignments()
    multi.update("new")
    multi.clear()

    val rw = ReadAndWrite()
    println(rw.increment())

    val cond = UsedInCondition()
    cond.enable()
    cond.doAction()

    val backing = BackingField()
    backing.setData("test")
    println(backing.data)

    val lambda = LambdaCapture()
    lambda.setCallback { println("callback") }
    lambda.triggerCallback()
}
