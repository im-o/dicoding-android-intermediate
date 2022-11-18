package com.rivaldy.id.commons.widget.input

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.rivaldy.id.commons.R
import com.rivaldy.id.commons.util.FormatterUtils.isValidEmail


/** Created by github.com/im-o on 10/4/2022. */

class EmailInputEditText : AppCompatEditText, View.OnTouchListener {
    private var clearButtonIcon: Drawable? = null
    private var isValidEmail = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = ((clearButtonIcon?.intrinsicWidth ?: 0) + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - (clearButtonIcon?.intrinsicWidth ?: 0)).toFloat()
                when {
                    event.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            return if (isClearButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        if (isValidEmail) {
                            when {
                                text != null -> if (isValidEmail) text?.clear()
                            }
                        }
                        true
                    }
                    else -> false
                }
            } else false
        }
        return false
    }

    override fun setError(error: CharSequence?, icon: Drawable?) {
        val customErrorDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_error_outline_24) as Drawable
        customErrorDrawable.setTint(ContextCompat.getColor(context, android.R.color.holo_red_light))
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.intrinsicWidth, customErrorDrawable.intrinsicHeight)
        super.setError(error, customErrorDrawable)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textSize = 12f
        if (hint.isNullOrEmpty()) hint = context.getString(R.string.email)
    }

    private fun init() {
        clearButtonIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24) as Drawable
        checkedClearButton()
        setOnTouchListener(this)
        addTextChangedListener(onTextChanged = { p0, _, _, _ ->
            isValidEmail = isValidEmail(p0.toString())
            error = if (!isValidEmail) context.getString(R.string.message_not_valid_email) else null
            if (isValidEmail) {
                setButtonDrawables(endOfTheText = null)
                checkedClearButton()
            }
        })
    }

    private fun checkedClearButton() {
        clearButtonIcon = (if (isValidEmail) ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24) as Drawable else null)
        setButtonDrawables(endOfTheText = clearButtonIcon)
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            compoundDrawables[0] ?: startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }
}