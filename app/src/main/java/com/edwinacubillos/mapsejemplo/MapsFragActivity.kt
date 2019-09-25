package com.edwinacubillos.mapsejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MapsFragActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_frag)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        val mapFragment = MapFragment()
        transaction.add(R.id.framelayout, mapFragment).commit()

    }
}
