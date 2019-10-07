package com.edwinacubillos.mapsejemplo

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener,
    GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private lateinit var marker: MarkerOptions

    override fun onMarkerDragEnd(marker: Marker?) {
        Log.d("latitudEnd", marker?.position?.latitude.toString())
        Log.d("longiutdEnd", marker?.position?.longitude.toString())

        val pos = LatLng(marker?.position?.latitude!!, marker?.position?.longitude!! )
        getAddress(pos)
    }

    private fun getAddress(latLng: LatLng) {
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""
var title = ""
        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
           title = addresses[0].getAddressLine(0)
            Log.d("address", addresses[0].getAddressLine(0))
            // 3
            if (null != addresses && !addresses.isEmpty()) {

                address = addresses[0]
              //  Log.d("Address", address.toString())
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        mMap.clear()

        mMap.addMarker(MarkerOptions().title(title).position(latLng).draggable(true))



    }

    override fun onMarkerDragStart(marker: Marker?) {
    /*    Log.d("latitudStart", marker?.position?.latitude.toString())
        Log.d("longiutdStart", marker?.position?.longitude.toString())*/

    }

    override fun onMarkerDrag(marker: Marker?) {
   /*     Log.d("latitudDrag", marker?.position?.latitude.toString())
        Log.d("longiutdDrag", marker?.position?.longitude.toString())*/

    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setUpMap()


        // Add a marker in Sydney and move the camera
     /*   val sydney = LatLng(6.2660803, -75.5734088)
        marker =  MarkerOptions().position(sydney).draggable(true)

        mMap.addMarker(marker)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15F))
*/
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        /*    val udea = LatLng(6.267854,-75.569022)
            mMap.addMarker(MarkerOptions().position(udea).title("Alma Mater"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(udea,15f))*/

        btnGo.setOnClickListener {
            var geocoder = Geocoder(this)
            var ubicacion = eUbicacion.text.toString()
            //  var ubicacion = "Cra 2B N 19-04 Campestre B Dosquebradas Risaralda"
            lateinit var list : MutableList<Address>

            try {
                list = geocoder.getFromLocationName(ubicacion, 1)
            }catch (e: IOException){

            }
            if (list.size !=0) Log.d("data", list.toString())
            if (list.size > 0) {

                var address: Address = list.get(0)
                var position = LatLng(address.latitude, address.longitude)
                var marker = MarkerOptions().title(ubicacion).position(position)
                mMap.addMarker(marker)
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(position, 15F)
                )
            } else
                Toast.makeText(this, "Direccion no encontrada", Toast.LENGTH_SHORT).show()
        }

        mMap.setOnMarkerDragListener(this)


    }



    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true


    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}



