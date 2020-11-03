
package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer?=null
    private var restProgress=0

    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress=0

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech?=null
    private var player: MediaPlayer?=null

    private var exerciseAdapter: ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Navigate the activity on click on back button of action bar.
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        exerciseList=Constants.defaultExerciseList()

        setupRestView()
        tts= TextToSpeech(this,this)
        setupExerciseAdapterRecyclerView()

    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null)
        {
            restTimer!!.cancel()
            restProgress=0
        }
        if(exerciseTimer!=null)
        {
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }
        if(tts!=null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null)
        {
            player!!.stop()
        }
    }
    private fun setRestProgressBar(){
        progressBar.progress=restProgress
        restTimer=object: CountDownTimer(10000,1000)
        {
            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
            }

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress=10-restProgress
                tvTimer.text=(10-restProgress).toString()

            }

        }.start()
    }
    private fun setExerciseProgressBar(){
        progressBarExercise.progress=exerciseProgress
        exerciseTimer=object: CountDownTimer(30000,1000)
        {
            override fun onFinish() {
                if(currentExercisePosition< exerciseList?.size!!-1)
                {
                    setupRestView()
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                }
                else{
                    finish()
                    val intent =Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress=30-exerciseProgress
                tvExerciseTimer.text=(30-exerciseProgress).toString()

            }

        }.start()
    }
    private fun setupRestView(){

        try {
            player=MediaPlayer.create(applicationContext,R.raw.press_start)
            player!!.isLooping=false
            player!!.start()
        }
        catch(e: Exception)
        {
            e.printStackTrace()
        }




        llRestView.visibility=View.VISIBLE
        llExerciseView.visibility=View.GONE
        if(restTimer!=null)
        {
            restTimer!!.cancel()
            restProgress=0
        }

        setRestProgressBar()
        tvUpcomingExerciseName.text= exerciseList!![currentExercisePosition+1].getName()
    }
    private fun setupExerciseView(){
        llRestView.visibility=View.GONE
        llExerciseView.visibility=View.VISIBLE
        if(exerciseTimer!=null)
        {
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text=exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS)
        {
            val result=tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS","the language specified is not supported")
            }
        }
        else{
            Log.e("TTS","initilization failed")
        }

    }
    private fun speakOut(text : String)
    {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseAdapterRecyclerView(){
        rvExerciseStatus.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!,this)
        rvExerciseStatus.adapter=exerciseAdapter
    }

    private fun customDialogForBackButton()
    {
        val customDialog=Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }
}