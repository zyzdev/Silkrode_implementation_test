package com.example.silkrode_implementation_test.util

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.BindingAdapter

object DataBindingUtil {
    @BindingAdapter(value = ["avatar", ], requireAll = true)
    @JvmStatic
    fun setImageBitmapSrc(view: ImageView, url: String?) {
        if(url != null)
        BitmapDownloadUtil.downloadPic(url, listener= object:BitmapDownloadUtil.OnPicReady {
            override fun ready(bitmap: Bitmap) {
                val res = view.context.resources
                val avatarIcon =
                    RoundedBitmapDrawableFactory.create(res, bitmap).apply {
                        isCircular = true
                    }
                view.post {
                    view.setImageDrawable(avatarIcon)
                }
            }

            override fun fail(e: Exception) {

            }
        })
    }

    @BindingAdapter(value = ["fadeIn", ], requireAll = true)
    @JvmStatic
    fun fadeIn(view: View, fadeIn: Boolean) {
        view.animate().apply {
            alpha(if(fadeIn) 1f else 0f)
            start()
        }
    }
}