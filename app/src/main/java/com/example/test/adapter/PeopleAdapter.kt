package com.example.test.adapter

import android.R
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.test.databinding.PersonLayoutBinding
import com.example.test.model.Person
import com.example.test.mvi.action.PeopleViewAction


class PeopleAdapter(private val clickListener: (PeopleViewAction.LoadPersonDetails) -> Unit): RecyclerView.Adapter<PeopleAdapter.DataViewHolder>() {
    private var people: List<Person> = ArrayList<Person>(0)

    class DataViewHolder(private val binding: PersonLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person, clickListener: (PeopleViewAction.LoadPersonDetails) -> Unit) {
            Glide.with(binding.avatar.context)
                    .load(Uri.parse(person.avatar))
                    .fitCenter()
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .placeholder(android.R.drawable.gallery_thumb)
                    .into(binding.avatar)

            binding.name.text = person.firstName + " " + person.lastName
            binding.jobTitle.text = person.jobTitle

            val colour = Color.parseColor(person.favouriteColor)

            val bmp = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
            val canvas = Canvas(bmp);
            canvas.drawColor(colour)

            binding.root.setCardBackgroundColor(ColorStateList.valueOf(colour))

            val palette = Palette.from(bmp).generate()

            palette.dominantSwatch?.let {
                binding.name.setTextColor(it.titleTextColor)
                binding.jobTitle.setTextColor(it.titleTextColor)
            }

            binding.root.setOnClickListener() {
                clickListener(PeopleViewAction.LoadPersonDetails(person))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = PersonLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = people.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(people[position], clickListener)

    fun addData(list: List<Person>) {
        people = people.plus(list)
        notifyDataSetChanged()
    }
}