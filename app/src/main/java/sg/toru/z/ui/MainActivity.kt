package sg.toru.z.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import sg.toru.z.R
import sg.toru.z.glide.GlideApp
import sg.toru.z.repository.model.CameraInfo
import sg.toru.z.util.Utils
import sg.toru.z.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    private lateinit var containerCamera: ConstraintLayout
    private lateinit var imgTrafficCamera: ImageView
    private var isCameraShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        containerCamera = findViewById(R.id.containerTrafficCamera)
        imgTrafficCamera = findViewById(R.id.imgTrafficCamera)
        initializeMap()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMinZoomPreference(10F)
        map.setMaxZoomPreference(16F)
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isZoomGesturesEnabled = true
        map.setLatLngBoundsForCameraTarget(LatLngBounds(LatLng(Utils.SOUTHMOST_LAT, Utils.WESTMOST_LNG), LatLng(Utils.NORTHMOST_LAT, Utils.EASTMOST_LNG)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(Utils.MARINA_BAY_SANDS_LAT, Utils.MARINA_BAY_SANDS_LNG),11F))

        initializeData()
    }

    private fun initializeMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initializeUi() {
        containerCamera.setOnTouchListener { v, event ->
            true
        }
    }

    private fun initializeData() {
        viewModel.getCameraInfo().observe(this, Observer {
            addPin(it.items[0].cameras)
        })

        viewModel.exceptionalLiveData.observe(this, Observer {

        })
    }

    private fun addPin(list:List<CameraInfo>) {
        Log.e("Toru", "size:: ${list.size}")
        for(eachItem in list){
            val options = MarkerOptions()
                .position(LatLng(eachItem.location.latitude, eachItem.location.longitude))
                .title("${eachItem.location.latitude}, ${eachItem.location.longitude}")
            val marker = map.addMarker(options)
            marker.tag = eachItem
        }

        map.setOnMarkerClickListener {
            val info = it.tag as CameraInfo
            map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(info.location.latitude, info.location.longitude)))
            it.showInfoWindow()

            if(!isCameraShown) {
                isCameraShown = true
                containerCamera.visibility = View.VISIBLE
            }
            GlideApp.with(imgTrafficCamera)
                .load(info.image)
                .error(R.mipmap.ic_launcher)
                .into(imgTrafficCamera)

            true
        }
    }

    override fun onBackPressed() {
        if(isCameraShown) {
            containerCamera.visibility = View.GONE
            isCameraShown = false
        } else {
            super.onBackPressed()
        }
    }
}