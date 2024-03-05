package com.mirash.familiar.tool

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs

abstract class AppBarCollapseStateChangeListener : OnOffsetChangedListener {
    private var currentState = State.IDLE

    enum class State { EXPANDED, COLLAPSED, IDLE }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (verticalOffset == 0) {
            applyState(State.EXPANDED)
        } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
            applyState(State.COLLAPSED)
        } else {
            applyState(State.IDLE)
        }
    }

    private fun applyState(state: State) {
        if (currentState != state) {
            currentState = state
            onStateChanged(state)
        }
    }

    abstract fun onStateChanged(state: State)
}