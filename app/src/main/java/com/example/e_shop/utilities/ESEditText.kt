package com.example.e_shop.utilities

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class ESEditText(context: Context,attrs:AttributeSet):AppCompatEditText(context, attrs) {

    init {
        // call the function to apply the front to the components
        applyFont()

    }

    private fun applyFont() {

        //this is used to get the file from assets and set it to the title text
        val typeface : Typeface =
            Typeface.createFromAsset(context.assets,"Montserrat-Regular.ttf")
        setTypeface(typeface)

    }
}