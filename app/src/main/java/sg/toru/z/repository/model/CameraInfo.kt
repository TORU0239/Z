package sg.toru.z.repository.model

data class CameraInformation(
    val items:List<CameraInfoItems>
)
data class CameraInfoItems(
    val timeStamp: String,
    val cameras: List<CameraInfo>
)
data class CameraInfo (
    val timeStamp: String,
    val image: String,
    val cameraId: String,
    val location: CameraLocation
)

data class CameraLocation(
    val latitude: Double,
    val longitude: Double
)

data class CameraImageMetaData(
    val height: String,
    val width: String,
    val md5: String
)