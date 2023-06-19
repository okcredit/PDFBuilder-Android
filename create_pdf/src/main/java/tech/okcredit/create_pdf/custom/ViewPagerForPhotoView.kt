package tech.okcredit.create_pdf.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ViewPagerForPhotoView : ViewPager {
    var isLocked = false

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return !isLocked && super.onTouchEvent(event)
    }

    fun toggleLock() {
        isLocked = !isLocked
    }
}