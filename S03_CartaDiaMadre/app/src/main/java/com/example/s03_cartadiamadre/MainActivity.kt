package com.example.s03_cartadiamadre

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.*
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var heartLayer: FrameLayout
    private lateinit var btnMensaje: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializa vistas
        heartLayer = findViewById(R.id.heartAnimationLayer)
        btnMensaje = findViewById(R.id.btnMensaje)

        // Configura la acción del botón
        btnMensaje.setOnClickListener {
            showLoveToast()
            vibrateDevice()
            generateFloatingHearts(10)
        }
    }

    private fun showLoveToast() {
        val toast = Toast.makeText(this, "¡Te quiero mucho, mamá!", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 200)
        toast.show()
    }

    private fun vibrateDevice() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(400)
        }
    }

    private fun generateFloatingHearts(count: Int) {
        val handler = Handler(Looper.getMainLooper())

        repeat(count) { index ->
            handler.postDelayed({
                val heart = createHeartView()
                heartLayer.addView(heart)
                animateHeartFloating(heart)
            }, index * 150L)
        }
    }

    private fun createHeartView(): ImageView {
        val size = resources.getDimensionPixelSize(R.dimen.heart_size)
        val heart = ImageView(this).apply {
            setImageResource(R.drawable.heart)
            layoutParams = FrameLayout.LayoutParams(size, size).apply {
                val screenWidth = resources.displayMetrics.widthPixels
                leftMargin = Random.nextInt(0, screenWidth - size)
                topMargin = resources.displayMetrics.heightPixels + size
            }
            alpha = 0.8f
            imageTintList = ContextCompat.getColorStateList(context, R.color.purple_accent)
        }
        return heart
    }

    private fun animateHeartFloating(view: ImageView) {
        ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, -1200f),
            PropertyValuesHolder.ofFloat(View.ALPHA, 0.8f, 0f),
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.4f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.4f)
        ).apply {
            duration = 2500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }
}
