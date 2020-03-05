package sg.toru.z.repository.network

class ZRepository {

    fun getResponse() {
        ZNetwork.provideNetworkService().getCameraInformation()
    }
}