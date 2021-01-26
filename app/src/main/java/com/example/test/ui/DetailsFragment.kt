package com.example.test.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.domain.model.PersonDetails
import com.example.test.databinding.FragmentDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var person: PersonDetails
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var mapFragment: SupportMapFragment

    companion object {
        const val PERSON_DATA = "person"
        const val FRAGMENT_MAP_TAG = "fragment_map"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(PERSON_DATA)) {
                person = it.getParcelable<PersonDetails>(PERSON_DATA)!!
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        mapFragment = childFragmentManager.findFragmentByTag(FRAGMENT_MAP_TAG) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.avatar.context)
            .load(Uri.parse(person.avatar))
            .fitCenter()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(android.R.drawable.gallery_thumb)
            .into(binding.avatar)

        binding.name.text = person.firstName + " " + person.lastName
        binding.jobTitle.text = person.jobTitle
        binding.email.text = person.email
        binding.phone.text = person.phone

        binding.root.setBackgroundColor(Color.parseColor(person.favouriteColor))

        val colour = Color.parseColor(person.favouriteColor)
        val bmp = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
        val canvas = Canvas(bmp);
        canvas.drawColor(colour)

        val palette = Palette.from(bmp).generate()

        palette.dominantSwatch?.let {
            binding.name.setTextColor(it.titleTextColor)
            binding.jobTitle.setTextColor(it.titleTextColor)
            binding.email.setTextColor(it.titleTextColor)
            binding.phone.setTextColor(it.titleTextColor)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val location = LatLng(person.latitude, person.longitude)
        googleMap?.apply {
            clear()
            moveCamera(CameraUpdateFactory.newLatLng(location))
            addMarker(MarkerOptions().position(location))
        }
    }
}