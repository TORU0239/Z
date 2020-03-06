package sg.toru.z.repository.model

sealed class ApiResponse<T> {
    class ApiSuccess<T>(val body:T):ApiResponse<T>()
    class ApiFailure<T>(val errorMessage: String):ApiResponse<T>()
}