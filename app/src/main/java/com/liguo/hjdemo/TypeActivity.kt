package com.liguo.hjdemo

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import com.liguo.hjdemo.databinding.ItemTypeBinding
import liguo.*
import liguo.Section.SectionDecoration
import liguo.application.ke
import org.jetbrains.anko.*

class TypeActivity : BaseActivity() {
    var type_recycler: FlushRecyclerView?=null
    var context_recycler: FlushRecyclerView?=null
    val type_adapter by lazy { BaseAdapter<String, ItemTypeBinding>(R.layout.item_type, arrayListOf(
            "家用电器","手机数码","电脑办公","家具家装","男装女装","美妆个户","汽车用品","母婴玩具","食品酒类","礼品鲜花","医药保健","图书影像","机票酒店","理财众筹","农资绿植","钟表珠宝"
    ))
    }
    override fun config(savedInstanceState: Bundle?) {
        setContentView(
                frameLayout {

//                    button("sdfsdfasd").lparams(widthProcent(30),-1)
                    context_recycler = flushRecyclerView {
                        backgroundColor = Color.WHITE
                    }.lparams(widthProcent(70),-1){
                        leftMargin = widthProcent(30)
                    }
                    type_recycler = flushRecyclerView {
                        backgroundColor = Color.WHITE
                    }.lparams(widthProcent(30),-1)
                }
        )
    }
    var select = -1
    override fun initView(savedInstanceState: Bundle?) {
        type_recycler!!.apply {
            layoutManager = LinearLayoutManager(this@TypeActivity)
            adapter = type_adapter
            addItemDecoration(DividerGridItemDecoration(this@TypeActivity))
        }
        type_adapter.onBind { itemBingding, position, data ->
            val lp = itemBingding.itemTypeTv.layoutParams
            if(select == position){
                lp.width = -2
                itemBingding.itemTypeTv.layoutParams = lp
                itemBingding.itemTypeTv.setEms(1)
                itemBingding.itemTypeTv.textColor = Color.RED
            }else{
                lp.width = -1
                itemBingding.itemTypeTv.layoutParams = lp
                itemBingding.itemTypeTv.textColor = Color.GRAY
//                itemBingding.itemTypeTv.layoutParams.width = -1
//                itemBingding.itemTypeTv.requestLayout()
            }
//            ke(position.toString(),lp.width.toString())

            itemBingding.root.onClick {
                if(select == position){
//                    select = -1
                }else{
                    select = position
                    jinruAnimator()
                }
                type_adapter.notifyDataSetChanged()
            }
        }


        context_recycler!!.apply {
            layoutManager = GridLayoutManager(this@TypeActivity,3)
            val ap = TypeAdapter(this@TypeActivity, arrayListOf(
                    arrayOf("0","二级名称0","0","一级名称"),
                    arrayOf("0","二级名称1","0","一级名称"),
                    arrayOf("0","二级名称2","0","一级名称"),

                    arrayOf("0","二级名称3","0","一级名称"),
                    arrayOf("0","二级名称4","0","一级名称"),
                    null,

                    arrayOf("0","二级名称5","1","一级名称"),
                    arrayOf("0","二级名称6","1","一级名称"),
                    arrayOf("0","二级名称7","1","一级名称"),

                    arrayOf("0","二级名称8","1","一级名称"),
                    arrayOf("0","二级名称9","1","一级名称"),
                    arrayOf("0","二级名称10","1","一级名称"),

                    arrayOf("0","二级名称11","1","一级名称"),
                    null,null
            ))
            adapter = ap
            addItemDecoration(SectionDecoration(3,ap,false))
        }

        jinruAnimator()
    }

    override fun initData() {
    }

    fun jinruAnimator(){
        val x = PropertyValuesHolder.ofFloat("x",0f-context_recycler!!.widthProcent(40).toFloat(),context_recycler!!.widthProcent(30).toFloat())
        val animator = ObjectAnimator.ofPropertyValuesHolder(context_recycler,x)
        animator.interpolator = OvershootInterpolator()
        animator.duration = 1000
        animator.start()
    }
}
