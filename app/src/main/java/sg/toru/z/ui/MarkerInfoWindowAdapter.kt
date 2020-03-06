package sg.toru.z.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import sg.toru.z.R
import sg.toru.z.glide.GlideApp
import sg.toru.z.repository.model.CameraInfo

class MarkerInfoWindowAdapter(private val context: Context): GoogleMap.InfoWindowAdapter {
    private val view = LayoutInflater.from(context).inflate(R.layout.item_marker, null, false)
    private val cameraImage = view.findViewById<ImageView>(R.id.badge)

    override fun getInfoContents(marker: Marker?): View {
        marker?.let {
            val info = it.tag as CameraInfo
            GlideApp.with(view)
                .load(info.image)
                .error(R.mipmap.ic_launcher)
                .into(cameraImage)
        }
        return view
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }
}