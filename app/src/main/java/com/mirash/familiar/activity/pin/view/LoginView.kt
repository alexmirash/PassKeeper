package com.mirash.familiar.activity.pin.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.mirash.familiar.R

/**
 * @author Mirash
 */
class LoginView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var loginViews: Array<CredentialView>
    private var spaces: Array<View>

    init {
        orientation = VERTICAL
        inflate(context, R.layout.login_view, this)
        loginViews = arrayOf(
            findViewById(R.id.login_section_0),
            findViewById(R.id.login_section_1),
            findViewById(R.id.login_section_2)
        )
        spaces = arrayOf(
            findViewById(R.id.space_top),
            findViewById(R.id.space_bot)
        )
    }


    fun setItems(vararg values: String?) {
        var count = 0
        for (value in values) {
            if (!value.isNullOrEmpty()) {
                loginViews[count].setText(value)
                loginViews[count].visibility = VISIBLE
                count++
            }
        }
        for (i in count until values.size) {
            loginViews[i].visibility = GONE
        }
        spaces[0].visibility = if (count > 1) VISIBLE else GONE
        spaces[1].visibility = if (count > 2) VISIBLE else GONE
    }
}
