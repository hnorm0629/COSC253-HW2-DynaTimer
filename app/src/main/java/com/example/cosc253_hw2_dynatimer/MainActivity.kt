package com.example.cosc253_hw2_dynatimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.view.View
import android.media.MediaPlayer
import android.provider.MediaStore

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var countDownTimer: CountDownTimer
    lateinit var textView: TextView
    lateinit var seekBar: SeekBar
    lateinit var mediaPlayer: MediaPlayer

    var timerRunning = false
    var currentTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.timer)
        seekBar = findViewById(R.id.setTime)
        button = findViewById(R.id.button)
        seekBar.max = 600
        seekBar.progress = 300

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                update(progress)
            }
            //I have to have function headers for these two to be able to compile the program
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })//seekBar
    }//onCreate


    fun update(secLeft : Int) {
        val minutes = secLeft / 60
        val seconds = secLeft - minutes *60
        var updatedSeconds = seconds.toString()
        seekBar.progress = secLeft

        //to add placeholding 0 to timer printout if time < 10 sec:
        if (seconds <= 9) {
            updatedSeconds = "0$updatedSeconds"
        }
        textView.text = minutes.toString() + ":" +updatedSeconds
    }//update

    fun reset() {
        timerRunning = false
        button.text = "START"
        textView.text = "5:00"
        seekBar.progress = 300
        seekBar.isEnabled = true
    }//reset

    fun start(view: View){
        //if timer not yet running:
        if(!timerRunning){
            timerRunning = true
            button.text = "STOP"
            currentTime = seekBar.progress*1000+100

            //stop user from changing time while timer is running:
            seekBar.isEnabled = false

            //create countdown timer object from defined class
            countDownTimer = object : CountDownTimer(currentTime.toLong(), 1000) {
                override fun onTick(p0: Long){
                    update((p0 / 1000).toInt())
                }//onTick

                override fun onFinish() {
                    //at finish, play boom sound and restart program
                    mediaPlayer.start()
                    reset()
                }//onFinish

            }//countDownTimer
        }
        //otherwise, cancel and restart program
        else {
            countDownTimer.cancel()
        }
    }//start

}//MainActivity