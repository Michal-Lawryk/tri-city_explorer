import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tricityexplorer.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private var latitude: Double = 0.00000
    private var longitude: Double = 0.00000
    private var name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            latitude = it.getDouble("latitude",52.829640861320925)
            longitude = it.getDouble("longitude",18.78179742553916)
            name = it.getString("name")
        }

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Pobierz lokalizację atrakcji (np. za pomocą geokodowania)
        val attractionLocation = LatLng(latitude,  longitude) // Przykładowa lokalizacja w Trójmieście

        // Dodaj marker na mapie
        googleMap.addMarker(MarkerOptions().position(attractionLocation).title(name))

        // Przesuń kamerę na lokalizację atrakcji
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(attractionLocation, 12f))

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
