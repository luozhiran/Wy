package com.lzr.wy.mvvm

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.plugin.itg_util.Utils

object MvvmBindadapter {

    @BindingAdapter(value = ["bindNetImg", "defaultIcon"], requireAll = false)
    @JvmStatic
    fun bindNetImg(
        img: ImageView,
        imgName: String?,
        drawableId: Int
    ) {
        if (TextUtils.isEmpty(imgName)) {

            Glide.with(img.context).load(drawableId).into(img)
        } else {
            val id = img.context.resources.getIdentifier(
                imgName,
                "mipmap",
                img.context.packageName
            )
            Glide.with(img.context).load(id).into(img)
        }
    }
}