package sg.toru.z.repository.network

import retrofit2.Response
import retrofit2.http.GET
import sg.toru.z.repository.model.CameraInfoItems

interface ZNetworkService {

    @GET
    fun getCameraInformation():Response<CameraInfoItems>
}