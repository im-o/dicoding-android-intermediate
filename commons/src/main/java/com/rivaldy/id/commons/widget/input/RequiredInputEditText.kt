package com.rivaldy.id.commons.widget.input

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.rivaldy.id.commons.R


/** Created by github.com/im-o on 10/4/2022. */

class RequiredInputEditText : AppCompatEditText, View.OnTouchListener {
    private var clearButtonIcon: Drawable? = null
    private var isNotEmptyText = false

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
                        if (isNotEmptyText) {
                            when {
                                text != null -> if (isNotEmptyText) text?.clear()
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

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                isNotEmptyText = s.toString().trim().isNotEmpty()
                val errorMessage = if (!hint.isNullOrEmpty()) context.getString(R.string._cant_empty, hint) else context.getString(R.string.form_cant_empty)
                error = if (!isNotEmptyText) errorMessage else null
                if (isNotEmptyText) {
                    setButtonDrawables(endOfTheText = null)
                    checkedClearButton()
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun checkedClearButton() {
        clearButtonIcon = (if (isNotEmptyText) ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24) as Drawable else null)
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