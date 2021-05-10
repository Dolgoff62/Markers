package ru.netology.markers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import ru.netology.markers.R


class MapsFragment : Fragment() {
    private lateinit var mapView: MapView
    private val TARGET_LOCATION: Point = Point(59.945933, 30.320045)
    private val MAPKIT_API_KEY = "3fcb8c8a-5fec-46d6-9d5c-446473cfa6ab"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(context)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mapView = mapView.findViewById(R.id.mapview)
//        mapView.map.move(
//            CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
//            Animation(Animation.Type.SMOOTH, 5F),
//            null
//        )

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapview) as MapsFragment

        lifecycle.coroutineScope.launchWhenCreated {

        }
    }
}