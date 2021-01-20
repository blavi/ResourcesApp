package com.example.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R

class DetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }

    override fun onResume() {
        super.onResume()

        val fragment = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DetailsFragment.PERSON_DATA, intent.getParcelableExtra(DetailsFragment.PERSON_DATA))
            }
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.detailsContainer, fragment)
            .commit()
    }
}