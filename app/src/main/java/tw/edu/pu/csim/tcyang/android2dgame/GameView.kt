package tw.edu.pu.csim.tcyang.android2dgame

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs), SurfaceHolder.Callback{
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val screenWidth= Resources.getSystem().displayMetrics.widthPixels  //讀取螢幕寬度
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels  //讀取螢幕高度

    var background1:Background? = null
    var background2:Background? = null
    var backgroundMoveX = 5

    //var countDown:Int = 200
    var thread: GameThread

    var boy:Boy? = null

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        background1 = Background(BitmapFactory.decodeResource(resources, R.drawable.forest))
        background2 = Background(BitmapFactory.decodeResource(resources, R.drawable.forest))
        background2!!.x = background1!!.x + screenWidth


        boy = Boy(context, resources,
                BitmapFactory.decodeResource(resources, R.drawable.boy1))
        /*
        var canvas:Canvas = holder.lockCanvas()
            draw(canvas)
        holder.unlockCanvasAndPost(canvas)
         */

        thread.running = true
        thread.start()  //開始Thread
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    fun update() {
        /*
        countDown--
        if (countDown<=0) {
            countDown = 0
            thread.running = false  //停止Thread
        }
        */

        //捲動背景處理
        background1!!.x -= backgroundMoveX
        background2!!.x -= backgroundMoveX

        if (background1!!.x + background1!!.image.getWidth() < 0) {
            background1!!.x = background2!!.x + screenWidth
        }

        if (background2!!.x + background2!!.image.getWidth() < 0) {
            background2!!.x = background1!!.x + screenWidth
        }

        boy!!.update()
    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        background1!!.draw(canvas)
        background2!!.draw(canvas)

        boy!!.draw(canvas)

        paint.color = Color.WHITE
        paint.textSize = 50f
        /*
        canvas.drawText("螢幕解析度 : " +  screenWidth.toString()  + " * "
                + screenHeight.toString() , 400f,400f, paint)
        canvas.drawText("倒數計時:" + countDown.toString(), 200f,200f, paint)
        */
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var boyRect: Rect = boy!!.getRect()  //讀取男孩圖形區域

        if (boyRect.contains(event.getX().toInt(), event.getY().toInt())) {
            boy!!.Jump("UP",false)  //按到小男孩，往上跳躍30像素，並往右移動20像素
        }
        else{
            if (event.action == MotionEvent.ACTION_DOWN) {
                boy!!.Jump("DOWN",false)  //按到其他區域，小男孩往下跳躍30像素，並往右移動20像素
            }
        }
        return true
    }
}