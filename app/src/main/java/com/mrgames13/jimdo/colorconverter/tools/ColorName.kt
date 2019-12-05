/*
 * Copyright © 2019 Marc Auberer. All rights reserved.
 */

package com.mrgames13.jimdo.colorconverter.tools

class ColorName(private var name: String, private var r: Int, private var g: Int, private var b: Int) {
    fun computeMSE(pixR: Int, pixG: Int, pixB: Int): Int {
        return ((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + (pixB - b) * (pixB - b)) / 3
    }

    fun getName(): String = name
}