package ru.netology.markers.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.R.drawable.*
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color.BLUE
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider.fromResource
import ru.netology.markers.R
import ru.netology.markers.databinding.FragmentMapsBinding


class MapsFragment : Fragment(), UserLocationObjectListener, CameraListener {
    private lateinit var mapView: MapView
    private lateinit var userLocationLayer: UserLocationLayer
    private var routeStartLocation = Point(0.0, 0.0)
    private var permissionLocation = false
    private var followUserLocation = false
    private var markerTapListener: MapObjectTapListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey(mapKitApiKey)
        MapKitFactory.initialize(this.context)

        val binding = FragmentMapsBinding.inflate(
            inflater,
            container,
            false
        )
        mapView = binding.mapview

        checkPermission()

        val mapLogoAlignment = Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
        mapView.map.logo.setAlignment(mapLogoAlignment)
        mapView.map.isModelsEnabled = true


        binding.userLocationFab.setOnClickListener {
            if (permissionLocation) {
                cameraUserPosition()

                followUserLocation = true
            } else {
                checkPermission()
            }
        }

        return binding.root
    }

//    private fun addMarkers(dataList: List<Data>) {
//        for (data in dataList) {
//            val marker = yandexMap.addMarker(
//                latitude = data.latitude,
//                longitude = data.longitude,
//                imageRes = R.drawable.ic_marker,
//                userData = data.tag
//            )
//            //Вот здесь я как раз и сохраняю каждый маркер в свою мапу
//            markerDataList[data] = marker
//        }
    // }

    private fun checkPermission() {
        val permissionLocation = checkSelfPermission(this.requireContext(), ACCESS_FINE_LOCATION)
        if (permissionLocation != PERMISSION_GRANTED) {
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), requestPermissionLocation)
        } else {
            onMapReady()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            requestPermissionLocation -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    onMapReady()
                }

                return
            }
        }
    }
    
    private fun onMapReady() {
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)

        mapView.map.addCameraListener(this)

        cameraUserPosition()

        permissionLocation = true
    }

    private fun cameraUserPosition() {
        if (userLocationLayer.cameraPosition() != null) {
            routeStartLocation = userLocationLayer.cameraPosition()!!.target
            mapView.map.move(
                CameraPosition(
                    routeStartLocation,
                    16f,
                    0f,
                    0f
                ), Animation(Animation.Type.SMOOTH, 1f), null
            )
        } else {
            mapView.map.move(CameraPosition(Point(0.0, 0.0), 16f, 0f, 0f))
        }
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {

        userLocationView.pin.setIcon(fromResource(this.requireContext(), R.drawable.user_arrow))
        userLocationView.arrow.setIcon(fromResource(this.requireContext(), R.drawable.user_arrow))
        userLocationView.accuracyCircle.fillColor = BLUE
    }

    override fun onCameraPositionChanged(
        p0: Map, p1: CameraPosition, p2: CameraUpdateReason, finish: Boolean
    ) {}

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {}

    override fun onObjectRemoved(p0: UserLocationView) {}

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    companion object {
        const val mapKitApiKey = "3fcb8c8a-5fec-46d6-9d5c-446473cfa6ab"
        const val requestPermissionLocation = 1
    }
}
