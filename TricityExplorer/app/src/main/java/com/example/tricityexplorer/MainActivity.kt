package com.example.tricityexplorer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tricityexplorer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        //Uzyto przy pierwszym uruchomieniu do załadowania danych
        //dbHelper.insertAttraction(Attraction("Muzeum Narodowe", "Największe muzeum w Gdańsku", 54.34550,  18.64697))
        //dbHelper.insertAttraction(Attraction("Skwer Kościuszki", "Popularny park w centrum Gdyni", 54.51937, 18.54456))
        //dbHelper.insertAttraction(Attraction("Opera Leśna", "Przestronna scena pod gołym niebem w Sopocie", 54.44473, 18.54444))
        //dbHelper.insertAttraction(Attraction("Attraction 4", "Description 4", 54.438055, 18.564167))
        //dbHelper.insertAttraction(Attraction("Attraction 5", "Description 5", 54.352207, 18.646369))

        val attractions = dbHelper.getAllAttractions()

        binding.attractionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.attractionsRecyclerView.adapter = AttractionAdapter(attractions) { attraction ->
            val intent = Intent(this, AttractionDetailActivity::class.java).apply {
                putExtra("name", attraction.name)
                putExtra("description", attraction.description)
                putExtra("latitude", attraction.latitude)
                putExtra("longitude", attraction.longitude)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val attractions = dbHelper.getAllAttractions()
        binding.attractionsRecyclerView.adapter = AttractionAdapter(attractions) { attraction ->
            val intent = Intent(this, AttractionDetailActivity::class.java).apply {
                putExtra("name", attraction.name)
                putExtra("description", attraction.description)
                putExtra("latitude", attraction.latitude)
                putExtra("longitude", attraction.longitude)
            }
            startActivity(intent)
        }
    }

    private fun loadAttractions() {
        val attractions = dbHelper.getAttractions()
        binding.attractionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.attractionsRecyclerView.adapter = AttractionAdapter(attractions) { attraction ->
            val intent = Intent(this, AttractionDetailActivity::class.java).apply {
                putExtra("name", attraction.name)
                putExtra("description", attraction.description)
                putExtra("latitude", attraction.latitude)
                putExtra("longitude", attraction.longitude)
            }
            startActivity(intent)
        }
    }

    fun showDeleteAttractionDialog(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Usuń atrakcję")
        builder.setMessage("Wprowadź nazwę atrakcji do usunięcia:")

        val input = android.widget.EditText(this)
        builder.setView(input)

        builder.setPositiveButton("Usuń") { dialog, _ ->
            val attractionName = input.text.toString()
            dbHelper.deleteAttraction(attractionName)
            loadAttractions()
            dialog.dismiss()
        }
        builder.setNegativeButton("Anuluj") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
        onResume()
    }

    fun addAttraction(view: View) {
        val intent = Intent(this, AddAttractionActivity::class.java)
        startActivity(intent)
    }

}