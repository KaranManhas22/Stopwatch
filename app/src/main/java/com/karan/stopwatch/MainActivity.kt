package com.karan.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var tvTime: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button
    private var startTime = 0L
    private var elapsedTime = 0L
    private var running = false
    private val handler = Handler(Looper.getMainLooper())

    private val updateRunnable: Runnable = object : Runnable {
        override fun run() {
            elapsedTime = System.currentTimeMillis() - startTime
            val seconds = (elapsedTime / 1000) % 60
            val minutes = (elapsedTime / (1000 * 60)) % 60
            val hours = (elapsedTime / (1000 * 60 * 60))
            tvTime.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            handler.postDelayed(this, 1000)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTime = findViewById(R.id.tvTime)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnReset = findViewById(R.id.btnReset)

        btnStart.setOnClickListener {
            start()
        }

        btnStop.setOnClickListener {
            stop()
        }

        btnReset.setOnClickListener {
            reset()
        }
    }

    private fun start() {
        if (!running) {
            startTime = System.currentTimeMillis() - elapsedTime
            handler.post(updateRunnable)
            running = true
        }
    }

    private fun stop() {
        if (running) {
            handler.removeCallbacks(updateRunnable)
            elapsedTime = System.currentTimeMillis() - startTime
            running = false
        }
    }

    private fun reset() {
        handler.removeCallbacks(updateRunnable)
        elapsedTime = 0L
        tvTime.text = "00:00:00"
        running = false
    }
}