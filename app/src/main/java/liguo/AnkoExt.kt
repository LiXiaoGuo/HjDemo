package liguo

/**
 *
 * Created by Extends on 2017/7/10 15:02
 */
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import org.jetbrains.anko.wrapContent

fun View.drawable(@DrawableRes resource: Int): Drawable = ContextCompat.getDrawable(context, resource)

fun View.color(@ColorRes resource: Int): Int = ContextCompat.getColor(context, resource)

fun Intent.defaultCategory(): Intent = apply { addCategory(Intent.CATEGORY_DEFAULT) }

fun ViewGroup.setMinimumListHeight() {
    val tv = TypedValue()
    if (context.theme.resolveAttribute(android.R.attr.listPreferredItemHeight, tv, true)) {
        minimumHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    }
}

fun <T : View> T.widthProcent(procent: Int): Int =
        getAppUseableScreenSize().x.toFloat()
                .times(procent.toFloat() / 100).toInt()

fun <T : View> T.heightProcent(procent: Int): Int = getAppUseableScreenSize().y.toFloat().times(procent.toFloat() / 100).toInt()

fun <T : View> T.getAppUseableScreenSize(): Point {
    val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}
