package ru.netology.markers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.markers.R
import ru.netology.markers.adapter.MarkersAdapter
import ru.netology.markers.adapter.OnItemClickListener
import ru.netology.markers.databinding.FragmentMainBinding
import ru.netology.markers.dto.Marker
import ru.netology.markers.viewmodel.MarkerViewModel

class MainFragment : Fragment() {

    private val viewModel: MarkerViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )

        binding.swiperefresh.setOnRefreshListener {
                viewModel.data
            binding.swiperefresh.isRefreshing
        }

        val adapter = MarkersAdapter(object : OnItemClickListener {
            override fun onEdit(marker: Marker) {
                viewModel.editMarker(marker.description)
                viewModel.saveMarker()
            }

            override fun onDelete(marker: Marker) {
                viewModel.deleteMarker(marker.id)
            }

            override fun onMarker(marker: Marker) {
//                findNavController().navigate(R.id.action_mainFragment_to_mapsFragment)
                findNavController().navigate(R.id.action_mainFragment_to_yandexMapView)
            }
        }
        )
        binding.fabAddNewPost.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_mapsFragment)
            findNavController().navigate(R.id.action_mainFragment_to_yandexMapView)
        }

        viewModel.dataState.observe(viewLifecycleOwner, { state ->
            binding.swiperefresh.isRefreshing = state.refreshing
        })

        viewModel.data.observe(viewLifecycleOwner, { state ->
            adapter.submitList(state.markers)
            binding.emptyList.isVisible = state.empty
        })

        return binding.root
    }
}