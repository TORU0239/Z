package sg.toru.z.repository.network

import retrofit2.Response
import sg.toru.z.repository.model.ApiResponse
import sg.toru.z.repository.model.CameraInformation

class ZRepository {

    private val service = ZNetwork.retrofit.create(ZNetworkService::class.java)

    suspend fun getCameraInfo(): ApiResponse<CameraInformation> {
        return baseResponse {
            service.getCameraInformation()
        }
    }

    private suspend fun<T> baseResponse(apiCall:suspend ()->Response<T>): ApiResponse<T> {
        val response: Response<T>
        try {
            response = apiCall.invoke()
        } catch (exception:Exception){
            exception.printStackTrace()
            // Exception
            return ApiResponse.ApiFailure(exception.message!!)
        }

        return if(response.isSuccessful) {
            // Success
            ApiResponse.ApiSuccess(response.body()!!)
        } else {
            // Failure
            ApiResponse.ApiFailure(response.message())
        }
    }
}