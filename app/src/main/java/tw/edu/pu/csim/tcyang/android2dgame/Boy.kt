package tw.edu.pu.csim.tcyang.android2dgame

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect

class Boy(var context: Context?, var res: Resources, var image : Bitmap) {
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

    init {
        //原始圖形較大，縮小為原圖的1/3
        w = image.width/3
        h = image.height/3

        //設定初始位置
        x =  screenWidth / 10
        y = (screenHeight - h) * 9 / 10
    }

    fun update() {
        pictNo++
        if (pictNo==9){pictNo=1}

        image = BitmapFactory.decodeResource(res,
                res.getIdentifier("boy" + pictNo.toString(), "drawable",
                        context!!.getPackageName()))
    }

    fun draw(canvas: Canvas) {
        //針對圖片進行裁切
        var SrcRect: Rect = Rect(0, 0, image.width, image.height)
        var DestRect: Rect = Rect(x, y, w + x, h + y)
        canvas.drawBitmap(image, SrcRect, DestRect, null)
    }

}