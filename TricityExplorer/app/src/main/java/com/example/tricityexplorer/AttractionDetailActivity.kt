package com.example.tricityexplorer

import MapFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tricityexplorer.databinding.ActivityAttractionDetailBinding

class AttractionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAttractionDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttractionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pobierz dane przekazane z poprzedniej aktywności
        val attractionName = intent.getStringExtra("name")
        val attractionDescription = intent.getStringExtra("description")
        val attractionLatitude = intent.getDoubleExtra("latitude", 0.00000)
        val attractionLongitude = intent.getDoubleExtra("longitude", 0.00000)

        // Wyświetl dane atrakcji w interfejsie użytkownika
        binding.nameTextView.text = attractionName
        binding.descriptionTextView.text = attractionDescription


        val bundle = Bundle().apply {
            putDouble("latitude", attractionLatitude)
            putDouble("longitude", attractionLongitude)
            putString("name", attractionName)
        }

        val mapFragment = MapFragment().apply {
            arguments = bundle
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.mapContainer, mapFragment)
            .commit()
    }

    fun toMain(view: View) {
        val fragment = supportFragmentManager.findFragmentById(R.id.mapContainer)
        fragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        finish()
    }
}
