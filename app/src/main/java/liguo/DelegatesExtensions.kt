package liguo

import android.content.Context
import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *委托扩展
 * Created by Extends on 2017/3/17.
 */
/**
 * 使用委托，可以保存基本类型到sp
 */
class Preference<T>(val context: Context, val name: String, val default: T, val title:String="kotlinDefault") : ReadWriteProperty<Any?, T> {
    val prefs by lazy {
        context.getSharedPreferences(title, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?,property: KProperty<*>) :T{
        return findPreference(name,default)
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }
    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        res as T
    }

    private fun <U> putPreference(name: String, value: U)= with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }
}

/**
 * 返回颜色
 */
fun Context.color(id:Int):Int = resources.getColor(id)

/**
 * 给ImageView设置图片
 */
fun ImageView.setImageUrl(url:String?){
//    Util.showImg(url,this)
}
inline fun <reified T> Gson.fromJson(json: String): T
        = Gson().fromJson<T>(json, object: TypeToken<T>(){}.type)


object DelegatesExt{
    /**
     * 保存基本类型到SP
     */
    fun <T:Any> preference(context: Context, name:String, default:T)= Preference(context,name,default)
}