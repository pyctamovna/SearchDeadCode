// Test fixture: Edge cases and unusual patterns
package com.example.fixtures.edgecases

// ============================================================================
// Case 1: Unicode identifiers
// ============================================================================

class Ünïcödé {
    val 数据 = "Chinese"
    val данные = "Russian"
    val 🔥 = "emoji"  // May or may not be valid

    fun 処理(): String = 数据
}

// ============================================================================
// Case 2: Very long identifiers
// ============================================================================

class VeryLongClassNameThatExceedsTypicalLengthsAndMightCauseIssuesWithDisplayOrProcessing {
    val thisIsAnExtremelyLongVariableNameThatNoSanePersonWouldEverUseInProductionCode = 42

    fun thisIsAMethodWithAnIncrediblyLongNameThatTestsTheSystemsBehaviorWithExtremeLengths(): Int {
        return thisIsAnExtremelyLongVariableNameThatNoSanePersonWouldEverUseInProductionCode
    }
}

// ============================================================================
// Case 3: Keywords as backtick identifiers
// ============================================================================

class `class` {
    val `val` = "value"
    val `fun` = "function"
    val `when` = "when"
    val `is` = "is"
    val `in` = "in"
    val `object` = "object"

    fun `fun`(): String = `val`
}

// ============================================================================
// Case 4: Nested declarations (deeply nested)
// ============================================================================

class Level0 {
    class Level1 {
        class Level2 {
            class Level3 {
                class Level4 {
                    class Level5 {
                        val deepValue = "deep"
                        fun deepMethod() = deepValue
                    }
                }
            }
        }
    }

    inner class InnerLevel1 {
        inner class InnerLevel2 {
            fun accessOuter() = this@Level0.toString()
        }
    }
}

// ============================================================================
// Case 5: Anonymous classes and lambdas
// ============================================================================

interface Callback {
    fun onComplete()
}

class AnonymousExample {
    val callback = object : Callback {
        val localState = "state"

        override fun onComplete() {
            println(localState)
        }
    }

    val lambda: () -> Unit = {
        val captured = "captured"
        println(captured)
    }

    val complexLambda: (Int) -> (String) -> Boolean = { num ->
        { str ->
            num > 0 && str.isNotEmpty()
        }
    }
}

// ============================================================================
// Case 6: Generic types with complex bounds
// ============================================================================

class GenericComplexity<T : Comparable<T>, R : List<T>> where R : MutableList<T> {
    fun <S : T> process(item: S): T = item

    fun <A, B, C> multiGeneric(a: A, b: B, c: C): Triple<A, B, C> = Triple(a, b, c)
}

interface CovariantInterface<out T> {
    fun produce(): T
}

interface ContravariantInterface<in T> {
    fun consume(item: T)
}

// ============================================================================
// Case 7: Vararg and spread operator
// ============================================================================

class VarargExample {
    fun varargMethod(vararg items: String): List<String> = items.toList()

    fun spreadExample() {
        val array = arrayOf("a", "b", "c")
        varargMethod(*array)
    }
}

// ============================================================================
// Case 8: Infix, inline, tailrec, and other modifiers
// ============================================================================

class ModifierExample {
    infix fun String.shouldBe(expected: String): Boolean = this == expected

    inline fun <reified T> typeCheck(value: Any): Boolean = value is T

    tailrec fun factorial(n: Int, acc: Int = 1): Int =
        if (n <= 1) acc else factorial(n - 1, n * acc)
}

// ============================================================================
// Case 9: Property delegates
// ============================================================================

class DelegateExample {
    val lazyValue: String by lazy { "computed" }

    var observableValue: String = ""
        set(value) {
            println("Changed to $value")
            field = value
        }

    val customDelegate: String by object {
        operator fun getValue(thisRef: Any?, property: Any): String = "delegated"
    }
}

// ============================================================================
// Case 10: Multiple inheritance patterns
// ============================================================================

interface InterfaceA {
    fun methodA(): String = "A"
}

interface InterfaceB {
    fun methodA(): String = "B"
}

class MultipleInheritance : InterfaceA, InterfaceB {
    override fun methodA(): String = super<InterfaceA>.methodA() + super<InterfaceB>.methodA()
}

// ============================================================================
// Case 11: Contextual declarations
// ============================================================================

class ContextExample {
    context(String)
    fun contextualMethod(): Int = length

    fun regularMethod() = 42
}

// ============================================================================
// Case 12: Inline class / Value class
// ============================================================================

@JvmInline
value class Password(val value: String)

@JvmInline
value class UserId(val id: Long) {
    fun isValid(): Boolean = id > 0
}

// ============================================================================
// Case 13: Object expressions vs declarations
// ============================================================================

object SingletonObject {
    val value = "singleton"
    fun method() = value
}

class ObjectExpressionExample {
    val anonymousObject = object {
        val x = 10
        val y = 20
    }

    fun getSum() = anonymousObject.x + anonymousObject.y
}

// ============================================================================
// Case 14: Extension properties
// ============================================================================

val String.wordCount: Int
    get() = this.split(" ").size

var StringBuilder.lastChar: Char
    get() = this[length - 1]
    set(value) {
        this.setCharAt(length - 1, value)
    }

// ============================================================================
// Case 15: Type aliases with complex types
// ============================================================================

typealias StringMap = Map<String, String>
typealias Predicate<T> = (T) -> Boolean
typealias Handler = (Int, String, Boolean) -> Result<String>
typealias NestedAlias = Map<String, List<Pair<Int, String>>>

// ============================================================================
// Case 16: Destructuring declarations
// ============================================================================

data class DestructuringExample(
    val first: String,
    val second: Int,
    val third: Boolean
)

fun useDestructuring() {
    val (a, b, c) = DestructuringExample("x", 1, true)
    val (_, _, onlyThird) = DestructuringExample("y", 2, false)

    println("$a $b $c $onlyThird")
}

// ============================================================================
// Case 17: Local functions and classes
// ============================================================================

fun outerFunction(): Int {
    val outerVar = 10

    fun innerFunction(): Int {
        val innerVar = 20
        return outerVar + innerVar
    }

    class LocalClass {
        val localClassVar = outerVar

        fun localClassMethod() = innerFunction()
    }

    return LocalClass().localClassMethod()
}

// ============================================================================
// Case 18: Expect/Actual for multiplatform
// ============================================================================

// expect class PlatformClass {
//     fun platformMethod(): String
// }

// actual class PlatformClass {
//     actual fun platformMethod(): String = "JVM"
// }

// ============================================================================
// Case 19: Contract and opt-in annotations
// ============================================================================

@RequiresOptIn
annotation class ExperimentalApi

@ExperimentalApi
class ExperimentalFeature {
    fun experimentalMethod() = "experimental"
}

// ============================================================================
// Case 20: Labels and qualified returns
// ============================================================================

fun labelExample(): Int {
    outer@ for (i in 1..10) {
        inner@ for (j in 1..10) {
            if (i * j > 50) break@outer
            if (j > 5) continue@inner
        }
    }

    return listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return@forEach
        println(it)
    }.let { 42 }
}
