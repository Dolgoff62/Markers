package ru.netology.markers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.markers.R
import ru.netology.markers.databinding.MarkerCardBinding
import ru.netology.markers.dto.Marker


interface OnItemClickListener {
    fun onMarker(marker: Marker) {}
    fun onEdit(marker: Marker) {}
    fun onDelete(marker: Marker) {}
}

class MarkersAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Marker, MarkersAdapter.MarkerViewHolder>(MarkerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        val binding = MarkerCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkerViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        val marker = getItem(position)
        holder.bind(marker)
    }

    class MarkerViewHolder(
        private val binding: MarkerCardBinding,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(marker: Marker) {
            binding.apply {
                markerDescription.text = marker.markerDescription
                published.text = marker.publishedDate

                markerMenu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.menu_options)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.menuItemDelete -> {
                                    onItemClickListener.onDelete(marker)
                                    true
                                }
                                R.id.menuItemEdit -> {
                                    onItemClickListener.onEdit(marker)
                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()
                }
                markerDescription.setOnClickListener {
                    onItemClickListener.onMarker(marker)
                }
            }
        }
    }
}

class MarkerDiffCallback : DiffUtil.ItemCallback<Marker>() {
    override fun areItemsTheSame(oldItem: Marker, newItem: Marker): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Marker, newItem: Marker): Boolean {
        return oldItem == newItem
    }
}
