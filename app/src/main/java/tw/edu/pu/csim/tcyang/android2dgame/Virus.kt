package tw.edu.pu.csim.tcyang.android2dgame

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import java.util.*

class Virus (var context: Context?, var res: Resources, var image : Bitmap) {
    //x,y軸座標
    var x: Int = 0
    var y: Int = 0

    //圖形寬度高度
    var w: Int = 0
    var h: Int = 0

    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    //切換圖片
    var pictNo : Int = 1

    var rnd = Random()

    init {
        //原始圖形較大，縮小為原圖的1/2
        w = image.width/2
        h = image.height/2

        //設定初始位置，y軸隨機
        x =  screenWidth-w
        y = rnd.nextInt(screenHeight-h)
    }

    fun update() {
        pictNo++
        if (pictNo==5){pictNo=1}

        //每兩個數字才換一張圖
        image = BitmapFactory.decodeResource(res,
                res.getIdentifier("virus" + ((pictNo+1) / 2).toString(), "drawable",
                        context!!.getPackageName()))

        x -= rnd.nextInt(20)
        y = y - rnd.nextInt(40) + 20

    }

    fun draw(canvas: Canvas) {
        //原始圖形較大，縮小為原圖的1/2
        var SrcRect: Rect = Rect(0, 0, image.width, image.height)
        var DestRect: Rect = Rect(x, y, w + x, h + y)
        canvas.drawBitmap(image, SrcRect, DestRect, null)
    }

    fun getRect():Rect{
        //取得圖形範圍 (內縮10像素，比較不會太敏感)
        return Rect(x+10, y+10,x+w-10, y+h-10)
    }

    //判斷病毒是否到達右邊或上下邊界
    fun ReachEdge():Boolean{
        if (x <= 0 || y <= 0 || y >= screenHeight-h) {
            x =  screenWidth-w
            y = rnd.nextInt(screenHeight-h)
            return true
        }
        else{
            return false
        }
    }
}