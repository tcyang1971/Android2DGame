package tw.edu.pu.csim.tcyang.android2dgame

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs), SurfaceHolder.Callback{
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val screenWidth= Resources.getSystem().displayMetrics.widthPixels  //讀取螢幕寬度
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels  //讀取螢幕高度

    var background1:Background? = null

    init {
        holder.addCallback(this)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        paint.color = Color.WHITE
        paint.textSize = 50f
        canvas.drawText("螢幕解析度 : " +  screenWidth.toString()  + " * "
                + screenHeight.toString() , 400f,400f, paint)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        background1 = Background(BitmapFactory.decodeResource(resources, R.drawable.forest))
        var canvas:Canvas = holder.lockCanvas()
            //draw(canvas)
            background1!!.draw(canvas)
        holder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }
}