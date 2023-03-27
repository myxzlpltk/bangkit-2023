package com.dicoding.ticketingapps

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.KeyEvent.ACTION_DOWN
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat

class SeatsView : View {
    // Constructors
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    // Define seat object
    var seat: Seat? = null
        private set

    data class Seat(
        val id: Int,
        var x: Float = 0F,
        var y: Float = 0F,
        var name: String,
        var isBooked: Boolean,
    )

    private val seats: ArrayList<Seat> = arrayListOf(
        Seat(id = 1, name = "A1", isBooked = false),
        Seat(id = 2, name = "A2", isBooked = false),
        Seat(id = 3, name = "B1", isBooked = false),
        Seat(id = 4, name = "A4", isBooked = false),
        Seat(id = 5, name = "C1", isBooked = false),
        Seat(id = 6, name = "C2", isBooked = false),
        Seat(id = 7, name = "D1", isBooked = false),
        Seat(id = 8, name = "D2", isBooked = false),
    )

    // On measure updating
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val halfWidth = width / 2
        val halfHeight = height / 2
        var value = -600F

        for (i in 0..7) {
            if (i % 2 == 0) {
                seats[i].apply {
                    x = halfWidth - 300F
                    y = halfHeight + value
                }
            } else {
                seats[i].apply {
                    x = halfWidth + 100F
                    y = halfHeight + value
                }
                value += 300F
            }
        }
    }

    // Rendering
    private val backgroundPaint = Paint()
    private val armrestPaint = Paint()
    private val bottomSeatPaint = Paint()
    private val mBounds = Rect()
    private val numberSeatPaint = Paint()
    private val titlePaint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (seat in seats) {
            drawSeat(canvas, seat)
        }

        val text = "Silahkan pilih kursi"
        titlePaint.apply {
            textAlign = Paint.Align.CENTER
            textSize = 50F
        }
        canvas?.drawText(text, (width / 2F), 100F, titlePaint)
    }

    private fun drawSeat(canvas: Canvas?, seat: Seat) {
        if (seat.isBooked) {
            backgroundPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            armrestPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            bottomSeatPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
            numberSeatPaint.color = ResourcesCompat.getColor(resources, R.color.black, null)
        } else {
            backgroundPaint.color = ResourcesCompat.getColor(resources, R.color.blue_500, null)
            armrestPaint.color = ResourcesCompat.getColor(resources, R.color.blue_700, null)
            bottomSeatPaint.color = ResourcesCompat.getColor(resources, R.color.blue_200, null)
            numberSeatPaint.color = ResourcesCompat.getColor(resources, R.color.grey_200, null)
        }

        // Save state
        canvas?.save()

        // Background
        Path().apply {
            canvas?.translate(seat.x, seat.y)
            addRect(0F, 0F, 200F, 200F, Path.Direction.CCW)
            addCircle(100F, 50F, 75F, Path.Direction.CCW)
            canvas?.drawPath(this, backgroundPaint)
        }

        // Armrest
        Path().apply {
            addRect(0F, 0F, 50F, 200F, Path.Direction.CCW)
            canvas?.drawPath(this, armrestPaint)
            canvas?.translate(150F, 0F)
            addRect(0F, 0F, 50F, 200F, Path.Direction.CCW)
            canvas?.drawPath(this, armrestPaint)
        }

        // Bottom Seat
        Path().apply {
            canvas?.translate(-150F, 175F)
            addRect(0F, 0F, 200F, 25F, Path.Direction.CCW)
            canvas?.drawPath(this, bottomSeatPaint)
        }

        // Number Seat
        numberSeatPaint.apply {
            canvas?.translate(0F, -175F)
            textSize = 50F
            getTextBounds(seat.name, 0, seat.name.length, mBounds)
            canvas?.drawText(seat.name, 100F - mBounds.centerX(), 100F, numberSeatPaint)
        }

        canvas?.restore()
    }

    // Touch event
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val halfHeight = height / 2
        val halfWidth = width / 2

        val widthColumnOne = (halfWidth - 300F)..(halfWidth - 100F)
        val widthColumnTwo = (halfWidth + 100F)..(halfWidth + 300F)
        val heightRowOne = (halfHeight - 600F)..(halfHeight - 400F)
        val heightRowTwo = (halfHeight - 300F)..(halfHeight - 100F)
        val heightRowTree = (halfHeight + 0F)..(halfHeight + 200F)
        val heightRowFour = (halfHeight + 300F)..(halfHeight + 500F)

        if (event?.action == ACTION_DOWN) {
            when {
                event.x in widthColumnOne && event.y in heightRowOne -> booking(0)
                event.x in widthColumnTwo && event.y in heightRowOne -> booking(1)
                event.x in widthColumnOne && event.y in heightRowTwo -> booking(2)
                event.x in widthColumnTwo && event.y in heightRowTwo -> booking(3)
                event.x in widthColumnOne && event.y in heightRowTree -> booking(4)
                event.x in widthColumnTwo && event.y in heightRowTree -> booking(5)
                event.x in widthColumnOne && event.y in heightRowFour -> booking(6)
                event.x in widthColumnTwo && event.y in heightRowFour -> booking(7)
            }
        }
        return true
    }

    private fun booking(position: Int) {
        seats.forEach { it.isBooked = false }
        seats[position].apply {
            seat = this
            isBooked = true
        }
        invalidate()
    }
}