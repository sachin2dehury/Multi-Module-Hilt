package com.test.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.myapplication.databinding.MyItemBinding

class MyItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    val binding = MyItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        // default value
        binding.textView.text = ""
    }

    fun setText(value: String) {
        binding.textView.text = value
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(
            400f,
            400f,
            50f,
            Paint().apply {
                color = Color.GREEN
            }
        )
        super.onDraw(canvas)
    }
}
