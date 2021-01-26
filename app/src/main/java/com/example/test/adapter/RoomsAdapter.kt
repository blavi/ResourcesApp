package com.example.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.RoomDetails
import com.example.test.databinding.RoomLayoutBinding

class RoomsAdapter: RecyclerView.Adapter<RoomsAdapter.DataViewHolder>() {
    private var rooms: List<RoomDetails> = ArrayList<RoomDetails>(0)

    class DataViewHolder(private val binding: RoomLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: RoomDetails) {
            binding.roomStatus.setImageResource(  if (room.is_occupied) {
                android.R.drawable.presence_busy
            } else {
                android.R.drawable.presence_online
            })
            binding.roomName.text = room.name
            binding.roomSize.text = room.max_occupancy.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RoomLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = rooms.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(rooms[position])

    fun addData(list: List<RoomDetails>) {
        rooms = rooms.plus(list)
        notifyDataSetChanged()
    }
}