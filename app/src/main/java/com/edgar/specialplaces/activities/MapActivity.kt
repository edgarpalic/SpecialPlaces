package com.edgar.specialplaces.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edgar.specialplaces.R
import com.edgar.specialplaces.databinding.ActivityMapBinding
import com.edgar.specialplaces.models.SpecialPlaceModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private var mSpecialPlaceDetail: SpecialPlaceModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)){
            mSpecialPlaceDetail = intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS) as SpecialPlaceModel?
        }

        if(mSpecialPlaceDetail != null){
            setSupportActionBar(binding.toolbarMap)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = mSpecialPlaceDetail!!.title

            binding.toolbarMap.setNavigationOnClickListener {
                onBackPressed()
            }
            val supportMapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            supportMapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val position = LatLng(mSpecialPlaceDetail!!.latitude, mSpecialPlaceDetail!!.longitude)
        googleMap.addMarker(MarkerOptions().position(position).title(mSpecialPlaceDetail!!.location))
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position, 15f)
        googleMap.animateCamera(newLatLngZoom)
    }
}