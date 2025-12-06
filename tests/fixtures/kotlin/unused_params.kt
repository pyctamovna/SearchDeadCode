// Test fixture: Unused parameter patterns
package com.example.fixtures.params

// Case 1: Simple unused parameter
class SimpleUnusedParam {
    fun process(data: String, unusedFlag: Boolean): String {  // unusedFlag is DEAD
        return data.uppercase()
    }
}

// Case 2: Multiple unused parameters
class MultipleUnused {
    fun calculate(a: Int, b: Int, unused1: String, unused2: Double): Int {  // unused1, unused2 DEAD
        return a + b
    }
}

// Case 3: NOT unused - all parameters used
class AllUsed {
    fun combine(a: String, b: String, separator: String): String {
        return "$a$separator$b"
    }
}

// Case 4: Underscore prefix - intentionally unused, skip
class IntentionallyUnused {
    fun callback(_event: String, data: String): String {
        return data  // _event is intentionally unused
    }
}

// Case 5: Override method - may need to keep signature
open class BaseClass {
    open fun onEvent(eventType: String, eventData: Any?) {
        println("Event: $eventType")  // eventData unused but required by signature
    }
}

class DerivedClass : BaseClass() {
    override fun onEvent(eventType: String, eventData: Any?) {
        // eventData unused but must match signature
        println("Derived: $eventType")
    }
}

// Case 6: Interface implementation - must keep signature
interface EventHandler {
    fun handle(event: String, context: Any?, metadata: Map<String, Any>)
}

class MyHandler : EventHandler {
    override fun handle(event: String, context: Any?, metadata: Map<String, Any>) {
        // context and metadata unused but required by interface
        println("Handling: $event")
    }
}

// Case 7: Lambda parameter unused
class LambdaParams {
    fun processList(items: List<String>) {
        items.forEachIndexed { index, item ->  // index might be unused
            println(item)
        }
    }

    fun transform(items: List<Int>): List<String> {
        return items.mapIndexed { _, value ->  // _ is intentionally unused
            value.toString()
        }
    }
}

// Case 8: Constructor parameter unused
class ConstructorParam(
    val used: String,
    unused: Int  // DEAD: not stored, not used
) {
    fun getValue() = used
}

// Case 9: Android framework callbacks
class AndroidCallbacks {
    // Common Android patterns where params may not be used
    fun onClick(view: Any?) {  // view unused but standard signature
        println("Clicked!")
    }

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Only s is typically used
        println("Text: $s")
    }
}

// Case 10: Serialization/reflection usage
class SerializationParam(
    val id: String,
    val name: String,
    val unusedInCode: String  // Used by JSON serialization, not in code
) {
    override fun toString() = "$id: $name"
}

fun main() {
    val simple = SimpleUnusedParam()
    println(simple.process("hello", true))

    val multi = MultipleUnused()
    println(multi.calculate(1, 2, "unused", 3.14))

    val all = AllUsed()
    println(all.combine("a", "b", "-"))

    val intent = IntentionallyUnused()
    println(intent.callback("click", "data"))

    val derived = DerivedClass()
    derived.onEvent("test", null)

    val handler = MyHandler()
    handler.handle("event", null, emptyMap())

    val lambda = LambdaParams()
    lambda.processList(listOf("a", "b"))
    println(lambda.transform(listOf(1, 2, 3)))

    val ctor = ConstructorParam("used", 42)
    println(ctor.getValue())

    val android = AndroidCallbacks()
    android.onClick(null)
    android.onTextChanged("text", 0, 0, 4)
}
