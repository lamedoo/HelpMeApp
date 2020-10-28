package com.lukakordzaia.helpmeapp.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.applyBundle(block: Bundle.() -> Unit): T {
    val bundle = Bundle()
    bundle.block()
    arguments = bundle
    return this
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun View.setVisibleOrGone(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}