package sg.toru.z.repository.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import sg.toru.z.repository.model.Output

class ZRepository {

    private val service = ZNetwork.retrofit.create(ZNetworkService::class.java)

    fun getResponse() {
        CoroutineScope(Dispatchers.IO).launch {
            ZNetwork.retrofit.create(ZNetworkService::class.java).getCameraInformation()
        }
    }

    suspend fun getCameraInfo() = baseRequest { service.getCameraInformation() }

    private suspend fun<T : Any> baseRequest(apiCall: suspend ()-> Response<T>): Output<T> {
        val result: Response<T>
        try {
            result = apiCall.invoke()
        } catch (exception:Exception) {
            return Output.Exceptional(exception)
        }

        if(!result.isSuccessful){
            result.errorBody()?.let { error ->
                return Output.Failure(error.toString())
            }
        }

        if(result.body() == null){
            return Output.Exceptional(RuntimeException("Null body is not allowed here!"))
        }
        return Output.Success(result.body()!!)
    }
}