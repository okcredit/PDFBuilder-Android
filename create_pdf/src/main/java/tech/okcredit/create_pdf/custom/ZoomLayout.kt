package tech.okcredit.create_pdf.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.widget.FrameLayout

/**
 * Layout that provides pinch-zooming of content. This view should have exactly one child
 * view containing the content.
 */
class ZoomLayout : FrameLayout, OnScaleGestureListener {
    private var mode = Mode.NONE
    private var scale = 1.0f
    private var lastScaleFactor = 0f

    // Where the finger first  touches the screen
    private var startX = 0f
    private var startY = 0f

    // How much to translate the canvas
    private var dx = 0f
    private var dy = 0f
    private var prevDx = 0f
    private var prevDy = 0f

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val scaleDetector = ScaleGestureDetector(context, this)
        setOnTouchListener { view, motionEvent ->
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i(TAG, "DOWN")
                    if (scale > MIN_ZOOM) {
                        mode = Mode.DRAG
                        startX = motionEvent.x - prevDx
                        startY = motionEvent.y - prevDy
                    }
                }

                MotionEvent.ACTION_MOVE -> if (mode == Mode.DRAG) {
                    dx = motionEvent.x - startX
                    dy = motionEvent.y - startY
                }

                MotionEvent.ACTION_POINTER_DOWN -> mode = Mode.ZOOM
                MotionEvent.ACTION_POINTER_UP -> mode =
                    Mode.NONE // changed from DRAG, was messing up zoom
                MotionEvent.ACTION_UP -> {
                    Log.i(TAG, "UP")
                    mode = Mode.NONE
                    prevDx = dx
                    prevDy = dy
                }

                else -> performClick()
            }
            scaleDetector.onTouchEvent(motionEvent)
            if (mode == Mode.DRAG && scale >= MIN_ZOOM || mode == Mode.ZOOM) {
                parent.requestDisallowInterceptTouchEvent(true)
                val maxDx = child().width * (scale - 1) // adjusted for zero pivot
                val maxDy = child().height * (scale - 1) // adjusted for zero pivot
                dx = Math.min(Math.max(dx, -maxDx), 0f) // adjusted for zero pivot
                dy = Math.min(Math.max(dy, -maxDy), 0f) // adjusted for zero pivot
                Log.i(
                    TAG, "Width: " + child().width + ", scale " + scale + ", dx " + dx
                            + ", max " + maxDx
                )
                applyScaleAndTranslation()
            }
            true
        }
    }

    override fun performClick(): Boolean {
        return false
    }

    override fun onScaleBegin(scaleDetector: ScaleGestureDetector): Boolean {
        Log.i(TAG, "onScaleBegin")
        return true
    }

    // ScaleGestureDetector
    override fun onScale(scaleDetector: ScaleGestureDetector): Boolean {
        val scaleFactor = scaleDetector.scaleFactor
        Log.i(TAG, "onScale(), scaleFactor = $scaleFactor")
        if (lastScaleFactor == 0f || Math.signum(scaleFactor) == Math.signum(lastScaleFactor)) {
            val prevScale = scale
            scale *= scaleFactor
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM))
            lastScaleFactor = scaleFactor
            val adjustedScaleFactor = scale / prevScale
            // added logic to adjust dx and dy for pinch/zoom pivot point
            Log.d(TAG, "onScale, adjustedScaleFactor = $adjustedScaleFactor")
            Log.d(TAG, "onScale, BEFORE dx/dy = $dx/$dy")
            val focusX = scaleDetector.focusX
            val focusY = scaleDetector.focusY
            Log.d(TAG, "onScale, focusX/focusy = $focusX/$focusY")
            dx += (dx - focusX) * (adjustedScaleFactor - 1)
            dy += (dy - focusY) * (adjustedScaleFactor - 1)
            Log.d(TAG, "onScale, dx/dy = $dx/$dy")
        } else {
            lastScaleFactor = 0f
        }
        return true
    }

    override fun onScaleEnd(scaleDetector: ScaleGestureDetector) {
        Log.i(TAG, "onScaleEnd")
    }

    private fun applyScaleAndTranslation() {
        child().scaleX = scale
        child().scaleY = scale
        child().pivotX = 0f // default is to pivot at view center
        child().pivotY = 0f // default is to pivot at view center
        child().translationX = dx
        child().translationY = dy
    }

    private fun child(): View {
        return getChildAt(0)
    }

    private enum class Mode {
        NONE, DRAG, ZOOM
    }

    companion object {
        private const val TAG = "ZoomLayout"
        private const val MIN_ZOOM = 1.0f
        private const val MAX_ZOOM = 4.0f
    }
}