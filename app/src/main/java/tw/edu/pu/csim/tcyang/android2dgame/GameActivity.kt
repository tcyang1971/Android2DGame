package tw.edu.pu.csim.tcyang.android2dgame

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        SetFullScreen()  //設定全螢幕

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL)
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
        finish()  //如果中斷，下次再繼續則跳回主畫面重玩
        sm.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        mygv.thread.running = true
        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL)
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


}