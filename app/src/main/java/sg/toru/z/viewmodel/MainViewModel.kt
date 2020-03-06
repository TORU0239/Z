package sg.toru.z.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import sg.toru.z.repository.model.ApiResponse
import sg.toru.z.repository.model.CameraInfoItems
import sg.toru.z.repository.model.CameraInformation
import sg.toru.z.repository.network.ZRepository

class MainViewModel:ViewModel() {
    private val repo:ZRepository = ZRepository()

    internal fun getCameraInfo(): LiveData<CameraInformation> = liveData{
        when(val post = repo.getCameraInfo()){
            is ApiResponse.ApiSuccess ->{
                emit(post.body)
            }
            is ApiResponse.ApiFailure -> {
                Log.e("Toru", "fail")
                exceptionalLiveData.value = ""
            }
        }
    }

    val exceptionalLiveData:MutableLiveData<String> = MutableLiveData()
}