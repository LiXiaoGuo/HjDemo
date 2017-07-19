package com.liguo.hjdemo

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.liguo.hjdemo.TypeAdapter.ViewHodler
import com.sun.jna.StringArray
import liguo.BaseRecyclerAdapter
import liguo.Section.SectionRecyclerHeadersAdapter
import liguo.widthProcent
import org.jetbrains.anko.*

/**
 *
 * Created by Extends on 2017/7/10 18:08
 */
class TypeAdapter(val context:Context,val list:ArrayList<Array<String>?>) : RecyclerView.Adapter<ViewHodler>(),SectionRecyclerHeadersAdapter<TypeAdapter.HeadViewHodler> {
    override fun getHeaderId(position: Int): Long {
        for (i in position downTo 0) {
            if (list[i] != null) {
                return list[i]!![2].toLong()
            }
        }
        return -1
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup?): HeadViewHodler {
        return HeadViewHodler(
                context.verticalLayout {
                    gravity = Gravity.CENTER_VERTICAL
                    backgroundColor = Color.GRAY
                    textView { text ="品牌名称" }
                    lparams(-1,dip(30))
                }
        )
    }

    override fun onBindHeaderViewHolder(holder: HeadViewHodler?, position: Int) {
    }

    override fun isNull(position: Int): Boolean = list[position] ==null

    //    private val list = arrayListOf<Array<String>>()//数组{二级id，二级名称，一级id，一级名称}
//    private val context: Context
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHodler, position: Int) {
        if(list[position]!=null){
            (holder.itemView as ViewGroup).apply {
                (getChildAt(1) as TextView).text = list[position]!![1]
            }
        }else{
            holder.itemView.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHodler {
        return ViewHodler(
                context.verticalLayout {
                    gravity = Gravity.CENTER
                    backgroundColor = Color.WHITE
                    view { setBackgroundColor(Color.RED) }.lparams(dip(30),dip(30))
                    textView {
                        text ="11111111111111"
                        gravity = Gravity.CENTER
                    }
                    lparams(-1,widthProcent(70)/3)
                }
        )
    }

    class ViewHodler(itemView: View?) : RecyclerView.ViewHolder(itemView)

    class HeadViewHodler(itemView: View?) : RecyclerView.ViewHolder(itemView)
}