package com.example.tricityexplorer

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tricityexplorer.databinding.ActivityAddAttractionBinding
import com.example.tricityexplorer.models.Attraction
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AddAttractionActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityAddAttractionBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var googleMap: GoogleMap
    private var attractionLatitude: Double = 0.0
    private var attractionLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAttractionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(binding.mapContainer.id, mapFragment)
            .commit()
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Ustawienia mapy
        val triCity = LatLng(54.4347860, 18.5638580) // Przykładowe współrzędne Trójmiasta
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(triCity, 10.3f))

        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(latLng).title("Nowa atrakcja!"))
            attractionLatitude = latLng.latitude
            attractionLongitude = latLng.longitude
        }
    }

    private fun isEditTextEmpty(editText: EditText): Boolean {
        return TextUtils.isEmpty(editText.text.toString().trim())
    }

    fun save(view: View) {
        if (isEditTextEmpty(binding.nameEditText) || isEditTextEmpty(binding.descriptionEditText) || attractionLatitude == 0.00) {
            Toast.makeText(this, "Wypełnij wszystkie pola i zaznacz lokalizację na mapie!", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.insertAttraction(Attraction(
                binding.nameEditText.text.toString(),
                binding.descriptionEditText.text.toString(),
                attractionLatitude,
                attractionLongitude))
            finish()
        }
    }

    fun finish(view: View) {
        super.finish()
    }
}
