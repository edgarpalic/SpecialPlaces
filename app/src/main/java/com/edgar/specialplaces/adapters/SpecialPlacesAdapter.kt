package com.edgar.specialplaces.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.Transliterator
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edgar.specialplaces.R
import com.edgar.specialplaces.activities.AddSpecialPlaceActivity
import com.edgar.specialplaces.activities.MainActivity
import com.edgar.specialplaces.database.DatabaseHandler
import com.edgar.specialplaces.databinding.ItemSpecialPlaceBinding
import com.edgar.specialplaces.models.SpecialPlaceModel

open class SpecialPlacesAdapter (private val context: Context, private var list: ArrayList<SpecialPlaceModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_special_place,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = ItemSpecialPlaceBinding.bind(holder.itemView)
        val model = list[position]

        if (holder is MyViewHolder){
            binding.ivPlaceImage.setImageURI(Uri.parse(model.image))
            binding.tvTitle.text = model.title
            binding.tvDescription.text = model.description

            holder.itemView.setOnClickListener{
                if(onClickListener != null){
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeAt(position: Int){
        val dbHandler = DatabaseHandler(context)
        val isDeleted = dbHandler.deleteSpecialPlace(list[position])
        if(isDeleted > 0){
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun notifyEditItem(activity : Activity, position : Int, requestCode : Int){
        val intent = Intent(context, AddSpecialPlaceActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, list[position])
        activity.startActivityForResult(intent, requestCode)
        notifyItemChanged(position)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: SpecialPlaceModel)
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}