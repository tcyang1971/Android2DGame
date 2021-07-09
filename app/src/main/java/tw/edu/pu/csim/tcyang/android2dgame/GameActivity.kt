package tw.edu.pu.csim.tcyang.android2dgame

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sm : SensorManager
    lateinit var sr : Sensor
    lateinit var mygv : GameView

    //音效
    //lateinit var mper: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        SetFullScreen()  //設定全螢幕

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL)

        mygv = GameView(this)
        mylayout.addView(mygv)

        /*
        //遊戲背景音效
        mper = MediaPlayer()
        mper = MediaPlayer.create(this, R.raw.background)
        mper.setLooping(true)
        mper.start()

         */
    }

    fun SetFullScreen(){
        //隱藏狀態列
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        // 隱藏動作列
        val actionBar = supportActionBar
        actionBar!!.hide()

        //不要自動休眠
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }


    override fun onPause() {
        super.onPause()
        mygv.thread.running = false

        //finish()  //如果中斷，下次再繼續則跳回主畫面重玩
        mylayout.removeView(mygv)

        sm.unregisterListener(this)

        /*
        if(mper != null && mper.isPlaying()){
            mper.pause()
        }
        else{
            mper.reset()
        }
         */
    }

    override fun onResume() {
        super.onResume()
        mygv = GameView(this)
        mylayout.addView(mygv)
        //mygv.thread.running = true

        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL)

        /*
        if(mper != null){
            mper.start()
        }

         */
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x: Float = event.values.get(0)
        if (x>1){  //比較明確往下
            mygv.boy!!.Jump("DOWN",true)
        }
        else if (x<-1){    //比較明確往上
            mygv.boy!!.Jump("UP",true)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    fun GameOver() {
        val intent = Intent(this, OverActivity::class.java)
        intent.putExtra("分數", mygv.Score)
        startActivity(intent)
        finish()
    }

}