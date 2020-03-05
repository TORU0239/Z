package sg.toru.z.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import sg.toru.z.repository.model.CameraInfoItems
import sg.toru.z.repository.model.Output
import sg.toru.z.repository.network.ZRepository

class MainViewModel:ViewModel() {
    private val repo:ZRepository = ZRepository()

    internal fun getCameraInfo(): LiveData<CameraInfoItems> = liveData{
        when(val post = repo.getCameraInfo()){
            is Output.Success<CameraInfoItems> ->{
                emit(post.output)
            }
            is Output.Failure -> {
                exceptionalLiveData.value = ""
            }
            is Output.Exceptional -> {
                exceptionalLiveData.value = ""
            }
        }
    }

    val exceptionalLiveData:MutableLiveData<String> = MutableLiveData()
}