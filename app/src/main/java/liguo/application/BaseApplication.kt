package liguo.application

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.huajiao.sdk.shell.HJSDK
import com.liguo.hjdemo.R
import liguo.getCrash.CrashLog


class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        CrashLog.getInstance().init(BaseApplication.context, "1043274460@qq.com", "gkfbkpfltrnlbfeh", BaseApplication.context?.getString(R.string.app_name), "2563892038@qq.com")
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
