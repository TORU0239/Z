package sg.toru.z.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import retrofit2.http.GET
import sg.toru.z.R
import sg.toru.z.repository.network.ZRepository
import sg.toru.z.util.Utils
import sg.toru.z.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeMap()
        initializeData()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMinZoomPreference(10F)
        map.setMaxZoomPreference(16F)
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true
        map.setLatLngBoundsForCameraTarget(LatLngBounds(LatLng(Utils.SOUTHMOST_LAT, Utils.WESTMOST_LNG), LatLng(Utils.NORTHMOST_LAT, Utils.EASTMOST_LNG)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(Utils.MARINA_BAY_SANDS_LAT, Utils.MARINA_BAY_SANDS_LNG),11F))
    }

    private fun initializeMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initializeData() {
        viewModel.getCameraInfo().observe(this, Observer {
            it.cameras
        })

        viewModel.exceptionalLiveData.observe(this, Observer {

        })
    }
}