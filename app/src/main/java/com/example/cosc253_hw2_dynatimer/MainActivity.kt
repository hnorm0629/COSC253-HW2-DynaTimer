package com.example.cosc253_hw2_dynatimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.view.View
import android.media.MediaPlayer
import android.widget.ImageView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    lateinit var countDownTimer: CountDownTimer
    lateinit var textView: TextView
    lateinit var seekBar: SeekBar
    lateinit var button: Button
    lateinit var boom: ImageView
    lateinit var mediaPlayer: MediaPlayer

    var timerRunning = false
    var currentTime = 0
    var restart = false


    /* =================================================== *
     *                      onCreate                       *
     * =================================================== */
    /* initialize activity and listeners
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieve view elements
        textView = findViewById(R.id.timer)
        seekBar = findViewById(R.id.setTime)
        button = findViewById(R.id.button)
        boom = findViewById(R.id.boom)

        seekBar.max = 600
        seekBar.progress = 300

        // set up media player for explosion sound
        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.explosion)

        // initialize seekbar change listener
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                update(progress)
            } // onProgressChanged

            override fun onStartTrackingTouch(p0: SeekBar?) { } // onStartTrackingTouch

            override fun onStopTrackingTouch(p0: SeekBar?) { } // onStopTrackingTouch
        }) // seekBar
    } // onCreate


    /* =================================================== *
     *                       update                        *
     * =================================================== */
    /* update timer TextView
     */
    fun update(secLeft : Int) {
        val minutes = secLeft / 60
        val seconds = secLeft - minutes *60
        var updatedSeconds = seconds.toString()
        seekBar.progress = secLeft

        // to add place-holding 0 to timer printout if time < 10 sec:
        if (seconds <= 9) {
            updatedSeconds = "0$updatedSeconds"
        } // if
        textView.text = minutes.toString() + ":" + updatedSeconds
    } // update

    /* =================================================== *
     *                       start                         *
     * =================================================== */
    /* button listener for start/stop/continue/restart
     */
    fun start(view: View){
        // if timer is started/resumed
        if(!timerRunning && !restart){
            timerRunning = true
            button.text = "STOP"
            currentTime = seekBar.progress * 1000 + 100

            // stop user from changing time while timer is running:
            seekBar.isEnabled = false

            // create countdown timer object from defined class
            countDownTimer = object : CountDownTimer(currentTime.toLong(), 1000) {
                override fun onTick(p0: Long){
                    update((p0 / 1000).toInt())
                } // onTick

                override fun onFinish() {
                    // at finish, play boom sound and restart program
                    boom.isVisible = true
                    mediaPlayer.start()
                    restart()
                } // onFinish

            } // countDownTimer
            countDownTimer.start()
        } // if
        // else if timer has completed
        else if (!timerRunning && restart){
            boom.isVisible = false
            reset()
        } // else-if
        // otherwise, when timer is paused
        else {
            timerRunning = false
            button.text = "CONTINUE"
            countDownTimer.cancel()
        } //else
    } //start

    /* =================================================== *
     *                      restart                        *
     * =================================================== */
    /* update button and vars along w/ BOOM finish
     */
    fun restart() {
        button.text = "RESTART"     // restart screen for timer
        timerRunning = false
        restart = true
    } // restart


    /* =================================================== *
     *                       reset                         *
     * =================================================== */
    /* reset timer to initial state to run again
     */
    fun reset() {
        button.text = "START"       // once user chooses to restart, reset timer
        textView.text = "5:00"
        seekBar.progress = 300
        seekBar.isEnabled = true
        restart = false
    } // reset

} // MainActivity