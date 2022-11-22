package com.udacity

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var done = 0.0
    private var valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }

    private val rect = RectF()
    private val textBoundRect = Rect()

    private val updateListener = ValueAnimator.AnimatorUpdateListener {
        done = (it.animatedValue as Float).toDouble()
        invalidate()
        if (done == 100.0) {
            onAnimateDone()
        }
    }

    init {
        isClickable = true
        valueAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.animator
        ) as ValueAnimator
        valueAnimator.addUpdateListener(updateListener)
    }

    private fun onAnimateDone() {
        valueAnimator.cancel()
        buttonState = ButtonState.Completed
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val buttonText =
            if (buttonState == ButtonState.Loading) resources.getString(R.string.button_loading)
            else resources.getString(R.string.button_download)

        //drawing the base button
        canvas?.drawRect(
            0.0f,
            0.0f,
            width.toFloat(),
            height.toFloat(),
            paint)
        
        if (buttonState == ButtonState.Loading) {
            paint.color = resources.getColor(R.color.cardview_dark_background)
            canvas?.drawRect(
                0f, 0f,
                (widthSize * (done / 100)).toFloat(),
                height.toFloat(),
                paint
            )

            paint.getTextBounds(buttonText,0,buttonText.length,textBoundRect)
            val centerX = measuredWidth.toFloat() / 2
            paint.color = resources.getColor(R.color.cardview_dark_background)
            rect.set(
                centerX+textBoundRect.right/2+40.0f,
                30.0f,
                centerX+textBoundRect.right/2+80.0f,
                measuredHeight.toFloat() -25.0f )
        }
        else if (buttonState == ButtonState.Completed) {
            paint.color = resources.getColor(R.color.cardview_dark_background)
            canvas?.drawRect(
                0f, 0f,
                (widthSize * (done / 100)).toFloat(), heightSize.toFloat(), paint
            )
        }
        paint.color = Color.WHITE
        canvas?.drawText(buttonText, (width / 2).toFloat(), ((height + 30) / 2).toFloat(), paint)
        paint.color = resources.getColor(R.color.colorPrimary)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 35.0f
        typeface = Typeface.create("", Typeface.BOLD)
        color = resources.getColor(R.color.colorPrimary)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val width: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val height: Int = resolveSizeAndState(
            MeasureSpec.getSize(width),
            heightMeasureSpec,
            0
        )
        widthSize = width
        heightSize = height
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(width: Int, hight: Int, oldWidth: Int, oldHight: Int) {
        widthSize = width
        heightSize = hight
    }

    override fun performClick(): Boolean {
        super.performClick()
        if (buttonState == ButtonState.Completed) buttonState = ButtonState.Loading
        valueAnimator.start()
        return true
    }


}