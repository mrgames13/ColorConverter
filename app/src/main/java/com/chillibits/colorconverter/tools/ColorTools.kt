/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.colorconverter.tools

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.mrgames13.jimdo.colorconverter.R


class ColorTools(private var context: Context) {
    fun getVibrantColor(image: Bitmap): Int {
        val palette = Palette.from(image).generate()
        return palette.getVibrantColor(ContextCompat.getColor(context, R.color.gray))
    }

    fun getLightVibrantColor(image: Bitmap): Int {
        val palette = Palette.from(image).generate()
        return palette.getLightVibrantColor(ContextCompat.getColor(context, R.color.gray))
    }

    fun getDarkVibrantColor(image: Bitmap): Int {
        val palette = Palette.from(image).generate()
        return palette.getDarkVibrantColor(ContextCompat.getColor(context, R.color.gray))
    }

    fun getMutedColor(image: Bitmap): Int {
        val palette = Palette.from(image).generate()
        return palette.getMutedColor(ContextCompat.getColor(context, R.color.gray))
    }

    fun getLightMutedColor(image: Bitmap): Int {
        val palette = Palette.from(image).generate()
        return palette.getLightMutedColor(ContextCompat.getColor(context, R.color.gray))
    }

    fun getDarkMutedColor(image: Bitmap): Int {
        val palette = Palette.from(image).generate()
        return palette.getDarkMutedColor(ContextCompat.getColor(context, R.color.gray))
    }

    fun getTextColor(activity: Activity, color: Int): Int {
        return if(Color.alpha(color) > 127) {
            val sum = Color.red(color) + Color.green(color) + Color.blue(color)
            if (sum > 384) Color.BLACK else Color.WHITE
        } else {
            return when (activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> Color.BLACK
                Configuration.UI_MODE_NIGHT_YES -> Color.WHITE
                else -> Color.BLACK
            }
        }
    }
}