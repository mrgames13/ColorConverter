/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.colorconverter.tools

import android.content.Context
import com.chillibits.colorconverter.model.Color
import com.chillibits.colorconverter.shared.Constants
import com.chillibits.colorconverter.shared.copyTextToClipboard
import com.chillibits.colorconverter.shared.round
import com.chillibits.colorconverter.ui.dialog.showArgbExportDialog
import com.chillibits.colorconverter.ui.dialog.showCmykExportDialog
import com.mrgames13.jimdo.colorconverter.R
import java.util.*

class ClipboardTools(
    private val context: Context,
    private val st: StorageTools,
    private val ct: ColorTools
) {

    fun copyNameToClipboard(name: String) = context.copyTextToClipboard(context.getString(R.string.color_name), name)

    fun copyArgbToClipboard(color: Color) = context.run {
        if (st.getBoolean(Constants.DISABLE_ALPHA)) {
            copyTextToClipboard(
                getString(R.string.rgb_code), String.format(
                    getString(R.string.rgb_clipboard),
                    color.red, color.green, color.blue
                )
            )
        } else {
            // Show multiple choice dialog
            if (!st.getBoolean(Constants.ARGB_REMEMBER, false)) {
                showArgbExportDialog(st, color.alpha, color.red, color.green, color.blue)
            } else if (st.getBoolean(Constants.ARGB_REMEMBER_SELECTION, false)) {
                copyTextToClipboard(
                    getString(R.string.argb_code), String.format(
                        getString(R.string.argb_clipboard),
                        color.alpha, color.red, color.green, color.blue
                    )
                )
            } else {
                copyTextToClipboard(
                    getString(R.string.argb_code), String.format(
                        getString(R.string.rgba_clipboard_css),
                        color.red, color.green, color.blue, (color.alpha / 255.0).round(3)
                    )
                )
            }
        }
    }

    fun copyHexToClipboard(color: Color) = context.run {
        copyTextToClipboard(
            getString(R.string.hex_code),
            if (st.getBoolean(Constants.DISABLE_ALPHA))
                "#%06X".format(0xFFFFFF and color.color).toUpperCase(Locale.getDefault())
            else
                "#%08X".format(color.color).toUpperCase(Locale.getDefault())
        )
    }

    fun copyHsvToClipboard(color: Color) = context.run {
        val hsv = FloatArray(3)
        android.graphics.Color.RGBToHSV(color.red, color.green, color.blue, hsv)
        val hsvString = String.format(getString(R.string.hsv_),
            String.format(Constants.HSV_FORMAT_STRING, hsv[0]),
            String.format(Constants.HSV_FORMAT_STRING, hsv[1]),
            String.format(Constants.HSV_FORMAT_STRING, hsv[2]))
        copyTextToClipboard(getString(R.string.hsv_clipboard), hsvString)
    }

    fun copyCmykToClipboard(color: Color) = context.run {
        // Show multiple choice dialog
        val cmyk = ct.getCmykFromRgb(color.red, color.green, color.blue)
        if (!st.getBoolean(Constants.CMYK_REMEMBER, false)) {
            showCmykExportDialog(st, cmyk[0], cmyk[1], cmyk[2], cmyk[3])
        } else if (st.getBoolean(Constants.CMYK_REMEMBER_SELECTION, false)) {
            copyTextToClipboard(
                getString(R.string.cmyk_code), String.format(
                    getString(R.string.cmyk_clipboard),
                    cmyk[0] / 100.0, cmyk[1] / 100.0, cmyk[2] / 100.0, cmyk[3] / 100.0
                )
            )
        } else {
            copyTextToClipboard(
                getString(R.string.cmyk_code), String.format(
                    getString(R.string.cmyk_clipboard_css),
                    cmyk[0], cmyk[1], cmyk[2], cmyk[3]
                )
            )
        }
    }
}