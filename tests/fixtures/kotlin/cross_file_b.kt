// Test fixture: Cross-file references - File B
// Uses declarations from cross_file_a.kt
package com.example.fixtures.crossfile

// Uses SharedService from file A
class ServiceConsumer {
    private val service = SharedService()

    fun doWork(input: String): String {
        return service.process(input)
    }
}

// Implements DataProvider from file A
class LocalDataProvider : DataProvider {
    override fun getData(): List<String> = listOf("a", "b", "c")
    override fun getMetadata(): Map<String, Any> = mapOf("version" to 1)
}

// Extends BaseRepository from file A
class UserRepository : BaseRepository() {
    override fun save(item: Any) {
        println("UserRepository saving: $item")
        super.save(item)
    }

    // delete() is not overridden, uses base implementation
}

// Uses sealed class from file A
class NetworkHandler {
    fun handleResult(result: NetworkResult<String>) {
        when (result) {
            is NetworkResult.Success -> println("Success: ${result.data}")
            is NetworkResult.Error -> println("Error: ${result.message}")
            is NetworkResult.Loading -> println("Loading...")
            // Note: Idle is NOT handled - it's dead
        }
    }
}

// Uses Constants from file A
class ApiClient {
    val baseUrl = Constants.API_URL
    val timeout = Constants.TIMEOUT

    fun request(): String {
        return "Requesting $baseUrl with timeout $timeout"
    }
}

// Uses extension from file A
class SlugGenerator {
    fun generateSlug(title: String): String {
        return title.toSlug()
    }
}

// Uses type alias from file A
class UserManager {
    private val callbacks = mutableListOf<UserCallback>()

    fun registerCallback(callback: UserCallback) {
        callbacks.add(callback)
    }

    fun notifyUser(userId: UserId, message: String) {
        callbacks.forEach { it(userId, message) }
    }
}

// Uses enum from file A
class TaskPrioritizer {
    fun getPriorityOrder(priority: Priority): Int {
        return when (priority) {
            Priority.LOW -> 0
            Priority.MEDIUM -> 1
            Priority.HIGH -> 2
            Priority.CRITICAL -> 3
        }
    }
}

// Main function that uses everything
fun main() {
    val consumer = ServiceConsumer()
    println(consumer.doWork("hello"))

    val provider: DataProvider = LocalDataProvider()
    println(provider.getData())

    val repo = UserRepository()
    repo.save("user")
    repo.delete(1)

    val handler = NetworkHandler()
    handler.handleResult(NetworkResult.Success("data"))
    handler.handleResult(NetworkResult.Error("failed"))
    handler.handleResult(NetworkResult.Loading)

    val client = ApiClient()
    println(client.request())

    val slugger = SlugGenerator()
    println(slugger.generateSlug("Hello World"))

    val manager = UserManager()
    manager.registerCallback { id, msg -> println("User $id: $msg") }
    manager.notifyUser(123L, "Welcome!")

    val prioritizer = TaskPrioritizer()
    println(prioritizer.getPriorityOrder(Priority.HIGH))
}
