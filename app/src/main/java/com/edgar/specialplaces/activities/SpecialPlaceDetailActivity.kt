package com.edgar.specialplaces.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edgar.specialplaces.databinding.ActivitySpecialPlaceDetailBinding
import com.edgar.specialplaces.models.SpecialPlaceModel

class SpecialPlaceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpecialPlaceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecialPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var specialPlaceDetailModel: SpecialPlaceModel? = null

        if(intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)){
            specialPlaceDetailModel = intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS) as SpecialPlaceModel?
        }

        if(specialPlaceDetailModel != null){
            setSupportActionBar(binding.toolbarSpecialPlaceDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = specialPlaceDetailModel.title

            binding.toolbarSpecialPlaceDetail.setNavigationOnClickListener {
                onBackPressed()
            }
            binding.ivPlaceImage.setImageURI(Uri.parse(specialPlaceDetailModel.image))
            binding.tvDescription.text = specialPlaceDetailModel.description
            binding.tvLocation.text = specialPlaceDetailModel.location

            binding.btnViewOnMap.setOnClickListener {
                val intent = Intent(this, MapActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, specialPlaceDetailModel)
                startActivity(intent)
            }
        }
    }
}