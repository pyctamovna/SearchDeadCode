// Test fixture: Sealed class variant patterns
package com.example.fixtures.sealed

// Case 1: Basic sealed class with unused variant
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String) : UiState()
    object Empty : UiState()  // DEAD: never instantiated
}

fun handleUiState(state: UiState) {
    when (state) {
        is UiState.Loading -> println("Loading...")
        is UiState.Success -> println("Data: ${state.data}")
        is UiState.Error -> println("Error: ${state.message}")
        is UiState.Empty -> {}  // Handled in when but never created
    }
}

// Case 2: Sealed interface with unused implementation
sealed interface Action {
    data class Click(val id: String) : Action
    data class LongClick(val id: String) : Action
    object Swipe : Action
    object DoubleTap : Action  // DEAD: never instantiated
}

fun processAction(action: Action) {
    when (action) {
        is Action.Click -> println("Click: ${action.id}")
        is Action.LongClick -> println("LongClick: ${action.id}")
        Action.Swipe -> println("Swipe")
        Action.DoubleTap -> println("DoubleTap")
    }
}

// Case 3: All variants used
sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure(val error: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

fun <T> handleResult(result: Result<T>) {
    when (result) {
        is Result.Success -> println("Success: ${result.value}")
        is Result.Failure -> println("Error: ${result.error}")
        Result.Loading -> println("Loading...")
    }
}

// Case 4: Nested sealed class
sealed class NavigationEvent {
    sealed class Screen : NavigationEvent() {
        object Home : Screen()
        object Profile : Screen()
        object Settings : Screen()
        object Admin : Screen()  // DEAD: never used
    }

    sealed class Dialog : NavigationEvent() {
        data class Alert(val message: String) : Dialog()
        object Confirmation : Dialog()
        object Loading : Dialog()  // DEAD: never used
    }

    object Back : NavigationEvent()
}

fun navigate(event: NavigationEvent) {
    when (event) {
        is NavigationEvent.Screen.Home -> println("Go Home")
        is NavigationEvent.Screen.Profile -> println("Go Profile")
        is NavigationEvent.Screen.Settings -> println("Go Settings")
        is NavigationEvent.Screen.Admin -> println("Go Admin")  // Handled but never created
        is NavigationEvent.Dialog.Alert -> println("Show Alert: ${event.message}")
        is NavigationEvent.Dialog.Confirmation -> println("Show Confirmation")
        is NavigationEvent.Dialog.Loading -> println("Show Loading")  // Handled but never created
        NavigationEvent.Back -> println("Go Back")
    }
}

// Case 5: Sealed class with factory pattern (should NOT flag)
sealed class ApiResponse {
    data class Success(val data: String) : ApiResponse()
    data class Error(val code: Int) : ApiResponse()

    companion object {
        fun fromJson(json: String): ApiResponse {
            return if (json.contains("error")) {
                Error(500)
            } else {
                Success(json)
            }
        }
    }
}

// Case 6: Sealed class with enum-like usage
sealed class PaymentMethod {
    object CreditCard : PaymentMethod()
    object DebitCard : PaymentMethod()
    object PayPal : PaymentMethod()
    object Crypto : PaymentMethod()  // DEAD: not supported yet
}

fun processPayment(method: PaymentMethod, amount: Double) {
    when (method) {
        PaymentMethod.CreditCard -> println("Charge credit: $amount")
        PaymentMethod.DebitCard -> println("Charge debit: $amount")
        PaymentMethod.PayPal -> println("PayPal: $amount")
        PaymentMethod.Crypto -> println("Crypto not supported")  // Handled but never created
    }
}

// Case 7: Moshi/Gson deserialization pattern (should NOT flag)
// These are instantiated via reflection by JSON libraries
sealed class NotificationPayload {
    @com.google.gson.annotations.SerializedName("message")
    data class Message(val text: String) : NotificationPayload()

    @com.google.gson.annotations.SerializedName("alert")
    data class Alert(val title: String, val body: String) : NotificationPayload()
}

fun main() {
    // Use sealed classes
    handleUiState(UiState.Loading)
    handleUiState(UiState.Success("data"))
    handleUiState(UiState.Error("failed"))
    // Note: UiState.Empty is never instantiated

    processAction(Action.Click("btn1"))
    processAction(Action.LongClick("btn2"))
    processAction(Action.Swipe)
    // Note: Action.DoubleTap is never instantiated

    handleResult(Result.Success("value"))
    handleResult(Result.Failure(Exception("error")))
    handleResult(Result.Loading)

    navigate(NavigationEvent.Screen.Home)
    navigate(NavigationEvent.Screen.Profile)
    navigate(NavigationEvent.Screen.Settings)
    navigate(NavigationEvent.Dialog.Alert("Hello"))
    navigate(NavigationEvent.Dialog.Confirmation)
    navigate(NavigationEvent.Back)
    // Note: Admin and Dialog.Loading are never instantiated

    val response = ApiResponse.fromJson("{\"data\": \"test\"}")
    println(response)

    processPayment(PaymentMethod.CreditCard, 100.0)
    processPayment(PaymentMethod.DebitCard, 50.0)
    processPayment(PaymentMethod.PayPal, 25.0)
    // Note: Crypto is never instantiated
}
