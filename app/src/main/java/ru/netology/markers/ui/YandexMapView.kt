package ru.netology.markers.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Runtime.init
import com.yandex.runtime.image.ImageProvider
import ru.netology.markers.R
import ru.netology.markers.databinding.FragmentMapsBinding

//class YandexMapView : Fragment(), UserLocationObjectListener, CameraListener {
//
//    private lateinit var yandexMap: MapView
//    private lateinit var mapObjectCollection: MapObjectCollection
//    private var markerTapListener: MapObjectTapListener? = null
//    private val userLocation by lazy { getUserLocationLayer() }
//    private lateinit var userLocationLayer: UserLocationLayer
//    private var routeStartLocation = Point(0.0, 0.0)
//    private var permissionLocation = false
//    private var followUserLocation = false
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        MapKitFactory.setApiKey(MapsFragment.mapKitApiKey)
//        MapKitFactory.initialize(this.context)
//
//        val binding = FragmentMapsBinding.inflate(
//            inflater,
//            container,
//            false
//        )
//
//        init(requireContext(), "3fcb8c8a-5fec-46d6-9d5c-446473cfa6ab")
//
//        yandexMap = binding.mapview
//
//        // Тут получаем координату выбранного элемента
//
//        val point = Point(
//            yandexMap.map.cameraPosition.target.latitude,
//            yandexMap.map.cameraPosition.target.longitude
//        )
//
//        checkPermission()
//        val mapLogoAlignment = Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
//        yandexMap.map.logo.setAlignment(mapLogoAlignment)
//        yandexMap.map.isModelsEnabled = true
//
//
//        showUserLocation()
//
//        binding.userLocationFab.setOnClickListener {
//            if (permissionLocation) {
//                cameraUserPosition()
//                followUserLocation = true
//            } else {
//                checkPermission()
//            }
//        }
//
//
//        return binding.root
//    }
//
//
//    private fun checkPermission() {
//        val permissionLocation = ContextCompat.checkSelfPermission(
//            this.requireContext(),
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                MapsFragment.requestPermissionLocation
//            )
//        } else {
//            retainInstance
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        yandexMap.onStart()
//        MapKitFactory.getInstance().onStart()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        yandexMap.onStop()
//        MapKitFactory.getInstance().onStop()
//    }
//
//    fun release() {
//        mapObjectCollection.clear()
//        markerTapListener = null
//    }
//
//    fun setTapListener(listener: MapObjectTapListener) {
//        markerTapListener = listener
//    }
//
//    fun showUserLocation() {
//        userLocation.apply {
//            isVisible = true
//            isHeadingEnabled = false
//
//            setAnchor(
//                PointF((yandexMap.width * 0.5f), (yandexMap.height * 0.5f)),
//                PointF((yandexMap.width * 0.5f), (yandexMap.height * 0.83f))
//            )
//            resetAnchor()
//        }
//    }
//
//    fun moveAnimatedTo(
//        latitude: Double,
//        longitude: Double,
//        zoom: Float = DEFAULT_ZOOM,
//        azimuth: Float = 0F,
//        tilt: Float = 0F,
//        animation: Animation,
//        callback: Map.CameraCallback? = null
//
//    ) {
//        yandexMap.map.move(
//            CameraPosition(Point(latitude, longitude), zoom, azimuth, tilt),
//            animation,
//            callback
//        )
//    }
//
//    fun addMarker(
//        latitude: Double,
//        longitude: Double,
//        @DrawableRes imageRes: Int,
//        userData: Any? = null
//    ): PlacemarkMapObject {
//        val marker = mapObjectCollection.addPlacemark(
//            Point(latitude, longitude),
//            ImageProvider.fromResource(context, imageRes)
//        )
//        marker.userData = userData
//        markerTapListener?.let { marker.addTapListener(it) }
//        return marker
//    }
//
//    fun getZoom() = yandexMap.map.cameraPosition.zoom
//
//    private fun cameraUserPosition() {
//        if (userLocationLayer.cameraPosition() != null) {
//            routeStartLocation = userLocationLayer.cameraPosition()!!.target
//            yandexMap.map.move(
//                CameraPosition(
//                    routeStartLocation,
//                    16f,
//                    0f,
//                    0f
//                ), Animation(Animation.Type.SMOOTH, 1f), null
//            )
//        } else {
//            yandexMap.map.move(CameraPosition(Point(0.0, 0.0), 16f, 0f, 0f))
//        }
//    }
//
//    private fun getUserLocationLayer() =
//        MapKitFactory.getInstance().createUserLocationLayer(yandexMap.mapWindow)
//
//    override fun onCameraPositionChanged(
//        p0: Map, p1: CameraPosition, p2: CameraUpdateReason, finish: Boolean
//    ) {
//    }
//
//    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {}
//
//    override fun onObjectAdded(userLocationView: UserLocationView) {
//
//        userLocationView.pin.setIcon(
//            ImageProvider.fromResource(
//                this.requireContext(),
//                R.drawable.user_arrow
//            )
//        )
//        userLocationView.arrow.setIcon(
//            ImageProvider.fromResource(
//                this.requireContext(),
//                R.drawable.user_arrow
//            )
//        )
//        userLocationView.accuracyCircle.fillColor = Color.BLUE
//    }
//
//    override fun onObjectRemoved(p0: UserLocationView) {}
//
//    companion object {
//
//        const val DEFAULT_ZOOM = 10F
//
//    }
//}