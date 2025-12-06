// Test fixture: All code is used (no dead code)
package com.example.fixtures.used

class DataProcessor {
    private val cache = mutableMapOf<String, String>()

    fun process(input: String): String {
        if (cache.containsKey(input)) {
            return cache[input]!!
        }
        val result = input.uppercase()
        cache[input] = result
        return result
    }

    fun clearCache() {
        cache.clear()
    }
}

class Calculator {
    fun add(a: Int, b: Int) = a + b
    fun subtract(a: Int, b: Int) = a - b
    fun multiply(a: Int, b: Int) = a * b
    fun divide(a: Int, b: Int) = if (b != 0) a / b else 0
}

enum class Operation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE
}

fun calculate(a: Int, b: Int, op: Operation): Int {
    val calc = Calculator()
    return when (op) {
        Operation.ADD -> calc.add(a, b)
        Operation.SUBTRACT -> calc.subtract(a, b)
        Operation.MULTIPLY -> calc.multiply(a, b)
        Operation.DIVIDE -> calc.divide(a, b)
    }
}

sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure(val error: String) : Result<Nothing>()
}

fun processWithResult(input: String): Result<String> {
    return if (input.isNotEmpty()) {
        Result.Success(input.uppercase())
    } else {
        Result.Failure("Empty input")
    }
}

fun main() {
    val processor = DataProcessor()
    println(processor.process("hello"))
    println(processor.process("hello"))  // From cache
    processor.clearCache()

    println(calculate(10, 5, Operation.ADD))
    println(calculate(10, 5, Operation.SUBTRACT))
    println(calculate(10, 5, Operation.MULTIPLY))
    println(calculate(10, 5, Operation.DIVIDE))

    when (val result = processWithResult("test")) {
        is Result.Success -> println("Got: ${result.value}")
        is Result.Failure -> println("Error: ${result.error}")
    }
}
