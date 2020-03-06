package sg.toru.z.repository.network

import retrofit2.Response
import retrofit2.http.GET
import sg.toru.z.repository.model.CameraInformation
import sg.toru.z.util.Utils.URLFORIMAGE

interface ZNetworkService {

    @GET(URLFORIMAGE)
    suspend fun getCameraInformation():Response<CameraInformation>
}