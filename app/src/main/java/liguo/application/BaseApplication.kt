package liguo.application

import android.content.Context
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.huajiao.sdk.shell.HJSDK


class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        HJSDK.init(context,"14485","hkqDXcP8mzp9njpidQeU0TbDiTAtzNy7","7735516454644A36978374F494546663","haohaojiazu","hjDemo",null)

    }

    companion object {
        var context: Context? = null
            private set
        val statusBarHeight by lazy {
            val resourceId = context!!.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                try {
                    context!!.resources.getDimensionPixelSize(resourceId)
                } catch (e: Exception) {
                    0
                }
            }else{
                ke("1","2")
                0
            }
        }
    }
}

fun ke(vararg msg: String?,deep:Int=4){
    val targetElement = Thread.currentThread().stackTrace[deep]?:Thread.currentThread().stackTrace[Thread.currentThread().stackTrace.lastIndex]
    val s = msg?.reduce { a, b -> a+"\n"+b }?:"value is Null"
    Log.e("ke","("+targetElement.fileName+":"+targetElement.lineNumber+")\tMethodName:"+targetElement.methodName+"\nFileName:"+targetElement.fileName+"\nThread："+Thread.currentThread().name+"\n"+s)
}
