package com.mirash.familiar.activity.pin.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.mirash.familiar.R

/**
 * @author Mirash
 */
class CredentialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var imageView: ImageView
    private var textView: TextView

    init {
        orientation = HORIZONTAL
        inflate(context, R.layout.credential_view, this)
        imageView = findViewById(R.id.credential_image)
        textView = findViewById(R.id.credential_text)
        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.CredentialView)
            val resId = attributes.getResourceId(R.styleable.CredentialView_icon, 0)
            if (resId != 0) {
                setImageRes(resId)
            }
            attributes.recycle()
        }
    }

    fun setText(text: CharSequence?) {
        textView.text = text
    }

    private fun setImageRes(@DrawableRes imageRes: Int) {
        imageView.setImageResource(imageRes)
    }
}
