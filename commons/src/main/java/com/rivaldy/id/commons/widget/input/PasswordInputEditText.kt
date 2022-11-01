package com.rivaldy.id.commons.widget.input

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.rivaldy.id.commons.R


/** Created by github.com/im-o on 10/4/2022. */

class PasswordInputEditText : AppCompatEditText, View.OnTouchListener {
    private lateinit var togglePasswordIcon: Drawable
    private var isVisiblePassword = false
    private var isValidPassword = false

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
            val toggleButtonStart: Float
            val toggleButtonEnd: Float
            var isTogglePasswordTouched = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                toggleButtonEnd = (togglePasswordIcon.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < toggleButtonEnd -> isTogglePasswordTouched = true
                }
            } else {
                toggleButtonStart = (width - paddingEnd - togglePasswordIcon.intrinsicWidth).toFloat()
                when {
                    event.x > toggleButtonStart -> isTogglePasswordTouched = true
                }
            }
            return if (isTogglePasswordTouched) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isVisiblePassword = !isVisiblePassword
                        if (error.isNullOrEmpty()) checkedPasswordIcon()
                        true
                    }
                    MotionEvent.ACTION_UP -> {
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
        if (hint.isNullOrEmpty()) hint = context.getString(R.string.password)
    }

    private fun init() {
        togglePasswordIcon = ContextCompat.getDrawable(context, R.drawable.ic_eye_visible_hide_hidden_show) as Drawable
        checkedPasswordIcon()
        setOnTouchListener(this)
        addTextChangedListener(onTextChanged = { p0, _, _, _ ->
            isValidPassword = p0.toString().length >= 6
            error = if (!isValidPassword) context.getString(R.string.message_not_valid_password) else null
        })
    }

    private fun checkedPasswordIcon() {
        setButtonDrawables()
        if (isVisiblePassword) {
            togglePasswordIcon = ContextCompat.getDrawable(context, R.drawable.ic_eye_visible_hide_hidden_show) as Drawable
            transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            togglePasswordIcon = ContextCompat.getDrawable(context, R.drawable.ic_eye_slash_visible_hide_hidden) as Drawable
            transformationMethod = PasswordTransformationMethod.getInstance()
        }
        setButtonDrawables(endOfTheText = togglePasswordIcon)
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