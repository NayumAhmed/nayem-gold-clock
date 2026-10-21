package com.nayem.livewallpaper

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NayemWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine = ClockEngine()

    inner class ClockEngine : Engine() {
        private val handler = Handler(Looper.getMainLooper())
        private var visibleNow = false
        private val gold = 0xFFFFC96B.toInt()
        private val softGold = 0xFFE5B96A.toInt()
        private val ivory = 0xFFF7EAD0.toInt()
        private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        private val dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        private val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        private val tick = object : Runnable {
            override fun run() {
                drawFrame()
                if (visibleNow) handler.postDelayed(this, 1000L)
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            visibleNow = visible
            handler.removeCallbacks(tick)
            if (visible) handler.post(tick)
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            drawFrame()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            visibleNow = false
            handler.removeCallbacks(tick)
            super.onSurfaceDestroyed(holder)
        }

        override fun onDestroy() {
            visibleNow = false
            handler.removeCallbacks(tick)
            super.onDestroy()
        }

        private fun drawFrame() {
            val holder: SurfaceHolder = surfaceHolder
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                if (canvas != null) render(canvas)
            } catch (_: Exception) {
            } finally {
                if (canvas != null) holder.unlockCanvasAndPost(canvas)
            }
        }

        private fun render(c: Canvas) {
            val w = c.width.toFloat()
            val h = c.height.toFloat()
            c.drawColor(0xFF000000.toInt())

            val centerX = w / 2f
            fun textPaint(size: Float, color: Int, family: Typeface = Typeface.create("sans-serif-light", Typeface.NORMAL), tracking: Float = 0f): Paint =
                Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    this.textSize = size
                    this.color = color
                    typeface = family
                    textAlign = Paint.Align.CENTER
                }

            val daySize = w * 0.035f
            val dateSize = w * 0.052f
            val timeSize = w * 0.13f
            val dayY = h * 0.105f
            val dateY = h * 0.155f
            val timeY = h * 0.235f

            c.drawText(dayFormat.format(Date()).uppercase(Locale.getDefault()).toCharArray().joinToString(" "), centerX, dayY,
                textPaint(daySize, softGold, Typeface.create("sans-serif", Typeface.NORMAL)))
            c.drawText(dateFormat.format(Date()), centerX, dateY,
                textPaint(dateSize, ivory, Typeface.create("sans-serif-light", Typeface.NORMAL)))
            c.drawText(timeFormat.format(Date()), centerX, timeY,
                textPaint(timeSize, gold, Typeface.create("sans-serif-light", Typeface.NORMAL)))

            // Fine ornamental arch, intentionally low contrast to preserve black pixels.
            val archPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = 0x553D2A12
                style = Paint.Style.STROKE
                strokeWidth = w * 0.002f
            }
            val archTop = h * 0.31f
            val archBottom = h * 0.70f
            val archHalf = w * 0.34f
            val path = android.graphics.Path().apply {
                moveTo(centerX - archHalf, archBottom)
                lineTo(centerX - archHalf, archTop + h * 0.10f)
                quadTo(centerX, archTop - h * 0.025f, centerX + archHalf, archTop + h * 0.10f)
                lineTo(centerX + archHalf, archBottom)
            }
            c.drawPath(path, archPaint)

            // Arabic text uses Android's Arabic-capable fallback font for proper shaping.
            val arabicSize = w * 0.125f
            c.drawText("نايم أحمد", centerX, h * 0.505f,
                textPaint(arabicSize, gold, Typeface.create("serif", Typeface.BOLD)))

            val lineY = h * 0.545f
            val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = 0xFF9E702F.toInt()
                strokeWidth = w * 0.0018f
            }
            c.drawLine(w * 0.13f, lineY, w * 0.31f, lineY, linePaint)
            c.drawLine(w * 0.69f, lineY, w * 0.87f, lineY, linePaint)
            c.drawText("N A Y E M   A H M E D", centerX, h * 0.575f,
                textPaint(w * 0.029f, softGold, Typeface.create("serif", Typeface.NORMAL)))

            // Tiny geometric accent; no animated effects or full-screen glow.
            val diamond = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = 0xFF9E702F.toInt()
                style = Paint.Style.STROKE
                strokeWidth = w * 0.0018f
            }
            val d = w * 0.012f
            val p = android.graphics.Path().apply {
                moveTo(centerX, h * 0.625f - d)
                lineTo(centerX + d, h * 0.625f)
                lineTo(centerX, h * 0.625f + d)
                lineTo(centerX - d, h * 0.625f)
                close()
            }
            c.drawPath(p, diamond)
        }
    }
}
