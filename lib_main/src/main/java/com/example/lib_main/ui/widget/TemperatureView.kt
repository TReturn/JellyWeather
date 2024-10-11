package com.example.lib_main.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.lib_main.R
import com.example.lib_main.utils.TempUtil

/**
 * @CreateDate: 2022/7/8 10:51
 * @Author: 青柠
 * @Description: 温度折线图
 */
class TemperatureView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var minValue = 0
    private var maxValue = 0
    private var currentValue = 0
    private var lastValue = 0
    private var nextValue = 0
    private var mPaint: Paint = Paint()
    private var viewHeight = 0
    private var viewWidth = 0
    private var pointX = 0
    private var pointY = 0
    private var isDrawLeftLine = false
    private var isDrawRightLine = false
    private val pointTopY = (40 * TempUtil.getDensity(context)).toInt()
    private val pointBottomY = (60 * TempUtil.getDensity(context)).toInt()
    private var mMiddleValue = 0
    private val defaultLineColor = Color.parseColor("#AAD1FF")

    //温度线颜色
    private var lineColor = defaultLineColor

    //温度点颜色
    private var circleColor = defaultLineColor

    //温度文字位置。1温度线上方。2温度线下方
    private var tempTextPoint = 1

    @SuppressLint("RestrictedApi")
    private fun initAttrs(attrs: AttributeSet?) {
        val typeArray =
            context?.obtainStyledAttributes(attrs, R.styleable.TemperatureView)
        typeArray?.let {
            lineColor =
                it.getColor(R.styleable.TemperatureView_lineColor, defaultLineColor)
            circleColor = it.getColor(R.styleable.TemperatureView_circleColor, defaultLineColor)
            tempTextPoint = it.getInt(R.styleable.TemperatureView_tempTextPoint, 1)
            it.recycle()
        }
    }

    //设置最小值
    fun setMinValue(minValue: Int) {
        this.minValue = minValue
    }

    //设置最大值
    fun setMaxValue(maxValue: Int) {
        this.maxValue = maxValue
    }

    //设置目前的值
    fun setCurrentValue(currentValue: Int) {
        this.currentValue = currentValue
    }

    //设置是否画左边线段(只有第一个View是false)
    fun setDrawLeftLine(isDrawLeftLine: Boolean) {
        this.isDrawLeftLine = isDrawLeftLine
    }

    //设置是否画右边线段(只有最后一个View是false)
    fun setDrawRightLine(isDrawRightLine: Boolean) {
        this.isDrawRightLine = isDrawRightLine
    }

    //设置之前温度点的值
    fun setLastValue(lastValue: Int) {
        this.lastValue = lastValue
    }

    //设置下一个温度点的值
    fun setNextValue(nextValue: Int) {
        this.nextValue = nextValue
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //给一个初始长、宽
        val mDefaultWidth = 180
        val mDefaultHeight = (80 * TempUtil.getDensity(context)).toInt()
        setMeasuredDimension(
            resolveSize(mDefaultWidth, widthMeasureSpec),
            resolveSize(mDefaultHeight, heightMeasureSpec)
        )
        viewHeight = measuredHeight
        viewWidth = measuredWidth
        pointX = viewWidth / 2
        Log.d(TAG, "onMeasure: $viewWidth")
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mMiddleValue = (pointTopY + pointBottomY) / 2
        pointY =
            mMiddleValue + ((pointBottomY - pointTopY) * 1f / (maxValue - minValue) * ((maxValue + minValue) / 2 - currentValue)).toInt()
        Log.d(TAG, "onDraw: $pointY")
        drawGraph(canvas)
        drawValue(canvas)
        drawPoint(canvas)
    }

    //绘制数值
    private fun drawValue(canvas: Canvas) {
        mPaint.run {
            textSize = 36f
            setTextColor()
            isFakeBoldText = true
            strokeWidth = 0f
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER

            val tempPointY = if (tempTextPoint == 1) {
                (pointY - 20).toFloat()
            } else {
                (pointY + 50).toFloat()
            }
            canvas.drawText("$currentValue°", pointX.toFloat(), tempPointY, this)
        }

    }

    //设置字体颜色
    fun setTextColor() {
        mPaint.color = Color.WHITE
    }

    //绘制温度点
    private fun drawPoint(canvas: Canvas) {
        mPaint.run {
            color = circleColor
            strokeWidth = 8f
            style = Paint.Style.STROKE
            canvas.drawCircle(pointX.toFloat(), pointY.toFloat(), 4f, this)

        }
    }

    //绘制线段（线段组成折线）
    private fun drawGraph(canvas: Canvas) {
        mPaint.color = lineColor
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 4f
        mPaint.isAntiAlias = true //设置抗锯齿

        //判断是否画左线段（第一个View不用，其他全要）
        if (isDrawLeftLine) {
            val middleValue = currentValue - (currentValue - lastValue) / 2f
            val middleY =
                (mMiddleValue + ((pointBottomY - pointTopY) * 1f / (maxValue - minValue) * ((maxValue + minValue) / 2 - middleValue)).toInt()).toFloat()
            canvas.drawLine(0f, middleY, pointX.toFloat(), pointY.toFloat(), mPaint)
        }

        //判断是否画右线段（最后View不用，其他全要）
        if (isDrawRightLine) {
            val middleValue = currentValue - (currentValue - nextValue) / 2f
            val middleY =
                (mMiddleValue + ((pointBottomY - pointTopY) * 1f / (maxValue - minValue) * ((maxValue + minValue) / 2 - middleValue)).toInt()).toFloat()
            canvas.drawLine(
                pointX.toFloat(),
                pointY.toFloat(),
                viewWidth.toFloat(),
                middleY,
                mPaint
            )
        }
    }

    init {
        initAttrs(attrs)
    }

    companion object {
        private const val TAG = "TemperatureView"
    }
}