package com.qxy.siegelions.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 用于加载iconfont的TextView
 * @author MadSamurai
 */
class IconFontTextView : AppCompatTextView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    companion object {
        /** 所有IconFontTextView公用typeface  */
        private var typeface: Typeface? = null
    }

    init {
        Companion.typeface = Typeface.createFromAsset(context.assets, "iconfont.ttf")
        typeface = Companion.typeface
    }
}