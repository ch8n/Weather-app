package dev.ch8n.weather.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation

fun View.isVisible(show: Boolean) = if (show) {
    this.visibility = View.VISIBLE
} else {
    this.visibility = View.GONE
}

fun View.startRotation() {
    val anim = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = 700
        repeatCount = -1
        fillAfter = true
    }
    this.startAnimation(anim)
}

fun View.stopRotation() {
    this.clearAnimation()
}