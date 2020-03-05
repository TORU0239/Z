package sg.toru.z.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import sg.toru.z.R
import sg.toru.z.util.Utils

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }

    private fun initializeMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}