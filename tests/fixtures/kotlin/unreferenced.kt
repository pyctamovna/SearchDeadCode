// Test fixture: Unreferenced code patterns (DC001)
package com.example.fixtures.unreferenced

// Case 1: Completely unused class
class UnusedClass {
    fun doSomething() {
        println("Never called")
    }
}

// Case 2: Used class with unused members
class PartiallyUsedClass {
    val usedProperty = "used"
    val unusedProperty = "unused"  // DEAD

    fun usedMethod() {
        println("This is called")
    }

    fun unusedMethod() {  // DEAD
        println("This is never called")
    }

    private fun privateUnused() {  // DEAD
        println("Private and unused")
    }
}

// Case 3: Unused private members
class PrivateMembers {
    private val privateUnusedVal = "unused"  // DEAD
    private var privateUnusedVar = 0  // DEAD

    private fun privateUnusedFun() {  // DEAD
        println("unused")
    }

    fun publicMethod() {
        println("This is used")
    }
}

// Case 4: Unused companion object members
class CompanionMembers {
    companion object {
        const val USED_CONST = "used"
        const val UNUSED_CONST = "unused"  // DEAD

        fun usedFactory(): CompanionMembers = CompanionMembers()

        fun unusedFactory(): CompanionMembers {  // DEAD
            return CompanionMembers()
        }
    }
}

// Case 5: Unused nested class
class OuterClass {
    class UsedNested {
        fun action() = println("Used")
    }

    class UnusedNested {  // DEAD
        fun action() = println("Unused")
    }

    fun useNested() {
        UsedNested().action()
    }
}

// Case 6: Unused extension functions
fun String.usedExtension(): String = this.uppercase()

fun String.unusedExtension(): String {  // DEAD
    return this.lowercase()
}

// Case 7: Unused top-level functions
fun usedTopLevel() {
    println("Used")
}

fun unusedTopLevel() {  // DEAD
    println("Unused")
}

// Case 8: Unused type aliases
typealias UsedAlias = List<String>
typealias UnusedAlias = Map<String, Int>  // DEAD

// Case 9: Unused object declaration
object UsedSingleton {
    fun action() = println("Used singleton")
}

object UnusedSingleton {  // DEAD
    fun action() = println("Unused singleton")
}

// Case 10: Unused enum
enum class UsedEnum {
    VALUE_A,
    VALUE_B
}

enum class UnusedEnum {  // DEAD
    UNUSED_A,
    UNUSED_B
}

// Case 11: Interface with unused implementation
interface MyInterface {
    fun required()
    fun optional() {}  // Default impl
}

class UsedImplementation : MyInterface {
    override fun required() {
        println("Required")
    }
}

class UnusedImplementation : MyInterface {  // DEAD
    override fun required() {
        println("Unused impl")
    }
}

// Case 12: Unused data class
data class UsedData(val id: String, val name: String)

data class UnusedData(val value: Int)  // DEAD

// Case 13: Unused sealed class variant - covered in sealed_classes.kt

// Case 14: Callback interface never used
interface UnusedCallback {  // DEAD
    fun onSuccess()
    fun onError(error: Throwable)
}

// Case 15: Builder pattern with unused methods
class Builder {
    private var name: String = ""
    private var age: Int = 0
    private var unused: String = ""

    fun name(n: String) = apply { name = n }
    fun age(a: Int) = apply { age = a }
    fun unused(u: String) = apply { unused = u }  // DEAD method (but might show as used if chained)

    fun build() = "Name: $name, Age: $age"
}

fun main() {
    // Use some declarations
    val partial = PartiallyUsedClass()
    println(partial.usedProperty)
    partial.usedMethod()

    val priv = PrivateMembers()
    priv.publicMethod()

    println(CompanionMembers.USED_CONST)
    val comp = CompanionMembers.usedFactory()

    val outer = OuterClass()
    outer.useNested()

    println("test".usedExtension())

    usedTopLevel()

    val list: UsedAlias = listOf("a", "b")
    println(list)

    UsedSingleton.action()

    println(UsedEnum.VALUE_A)

    val impl = UsedImplementation()
    impl.required()

    val data = UsedData("1", "Test")
    println(data)

    val built = Builder()
        .name("John")
        .age(30)
        .build()
    println(built)
}
