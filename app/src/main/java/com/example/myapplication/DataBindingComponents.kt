package com.example.myapplication

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

object DataBindingComponents {
    @JvmStatic
    @BindingAdapter("setResource")
    fun setImage(view: CircleImageView, imageUrl : Any?){
        if(imageUrl ==null){
            view.setImageResource(R.drawable.ic_person_orange_80dp)
        }else{
            Picasso.get().load(imageUrl.toString()).into(view)
        }

    }
}