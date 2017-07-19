package com.liguo.hjdemo

//import kotlinx.android.synthetic.main.activity_map.*
import android.animation.*
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.liguo.hjdemo.databinding.ItemMapBinding
import liguo.*
import liguo.application.BaseApplication
import liguo.application.ke
import org.java_websocket.handshake.ServerHandshake
import org.jetbrains.anko.*
import java.lang.Exception
import java.net.URI
import java.security.acl.Group
import kotlin.concurrent.thread

class MapActivity : BaseActivity() {
    val adapter by lazy { BaseAdapter<UserBean, ItemMapBinding>(R.layout.item_map, arrayListOf(
            UserBean("1","测试1", LatLng(39.962511,116.559496)),
            UserBean("1","测试2",LatLng(40.004528,116.234094)),
            UserBean("1","测试3",LatLng(39.9948,116.287561)),
            UserBean("1","测试4",LatLng(39.9948,116.287561)),
            UserBean("1","测试5",LatLng(40.000106,116.658956)),
            UserBean("1","测试6",LatLng(39.705425,116.414042)),
            UserBean("1","测试7",LatLng(39.766238,116.25824)),
            UserBean("1","测试8",LatLng(39.879729,116.648033)),
            UserBean("1","测试9",LatLng(39.709865,116.281237)),
            UserBean("1","测试10", LatLng(39.962511,116.559496)),
            UserBean("1","测试11", LatLng(39.962511,116.559496)),
            UserBean("1","测试12",LatLng(40.004528,116.234094)),
            UserBean("1","测试13",LatLng(39.9948,116.287561)),
            UserBean("1","测试14",LatLng(39.9948,116.287561)),
            UserBean("1","测试15",LatLng(40.000106,116.658956)),
            UserBean("1","测试16",LatLng(39.705425,116.414042)),
            UserBean("1","测试17",LatLng(39.766238,116.25824)),
            UserBean("1","测试18",LatLng(39.879729,116.648033)),
            UserBean("1","测试19",LatLng(39.709865,116.281237))
    ))}
    private val aMap by lazy { fn_map!!.map }//地图控制器
    private val myLocationStyle by lazy { MyLocationStyle() }//初始化定位蓝点样式类'
    var fn_map : MapView?=null
    var map_recycler:FlushRecyclerView?=null
    override fun config(savedInstanceState: Bundle?) {
//        setContentView(R.layout.activity_map)
        setContentView(
                relativeLayout {
                    map_recycler = flushRecyclerView {}.lparams(-1,-1)
                    fn_map = aMaps { visibility=View.GONE }.lparams(dip(250),dip(160))
                }
        )
    }

    var selsece = -1
    var fangda = false
    val listMarkers = arrayListOf<Marker>()

    override fun initView(savedInstanceState: Bundle?) {

        fn_map!!.onCreate(savedInstanceState)
        //初始化定位
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)//定位一次，且将视角移动到地图中心点。
        aMap.myLocationStyle = myLocationStyle//设置定位蓝点的Style
        aMap.uiSettings.isMyLocationButtonEnabled = false//设置默认定位按钮是否显示，非必需设置。
        aMap.uiSettings.isZoomControlsEnabled = false//设置缩放按钮是否显示
        aMap.uiSettings.isScaleControlsEnabled = false//设置比例尺是否显示
        aMap.isMyLocationEnabled = false// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        adapter.datas.forEach {
            val m = aMap.addMarker(MarkerOptions().position(it.jwd).title(it.name).snippet("sdfasdf"))
            listMarkers.add(m)
        }
        ke(listMarkers.size.toString())

        map_recycler!!.layoutManager = LinearLayoutManager(this)
        map_recycler!!.adapter = adapter
        adapter.onBind { itemBingding, position, data ->
            val lp = itemBingding.root.layoutParams
            lp.height = if(!data.xz) dip(40) else if(fangda) dip(360) else dip(180)
            itemBingding.root.layoutParams = lp
            itemBingding.root.onClick {
                if(selsece==position){
                    if(fangda){
                        fangda = false
                        selsece=-1
                        data.xz=false
                        fn_map!!.visibility = View.GONE
                    }else{
                        fangda = true
                        fangdaAnimator(data.jwd,position)
                    }
                }else{
                    if(selsece != -1){
                        adapter.datas[selsece].xz = false
                        fangda = false
                    }
                    data.xz = true

                    val location = intArrayOf(0,0)
                    itemBingding.itemSpace.getLocationInWindow(location)
                    ke(BaseApplication.statusBarHeight.toString()+","+location[0].toString()+","+location[1].toString())
                    var h = location[1].toFloat()-BaseApplication.statusBarHeight-dip(10)
                    h = if(selsece in 0..position){
                        h-dip(180-40)
                    }else{
                        h
                    }
                    if(fn_map!!.y<0){
                        h = h+dip(180-40)
                    }
                    startAnimator(location[0].toFloat(),h,data.jwd,position)

                    selsece=position
                }
                adapter.notifyDataSetChanged()
            }
        }
        map_recycler!!.setOnFlushListener(object :FlushRecyclerView.FlushListener(){
            override fun onScroll(recyclerView: RecyclerView?, dx: Int, dy: Int, scollY: Int) {
                Log.e("------",scollY.toString()+",,,"+dy.toString())
                fn_map!!.y = fn_map!!.y-dy
            }
        })

//        connectServer()
    }
    var fmy=0f
    fun startAnimator(x:Float,y:Float,jwd: LatLng,position:Int){
        fn_map!!.visibility= View.VISIBLE
        fn_map!!.layoutParams.height = dip(160)
        fn_map?.requestLayout()
        val x = PropertyValuesHolder.ofFloat("x",700f,x,x)
        val y = PropertyValuesHolder.ofFloat("y",-100f,y,y)
        val sx = PropertyValuesHolder.ofFloat("scaleX",0.4f,0f,1f)
        val sy = PropertyValuesHolder.ofFloat("scaleY",0.4f,0f,1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(fn_map,x,y,sx,sy)
        animator.interpolator = LinearInterpolator()
        animator.duration = 1000
        animator.start()
        animator.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                listMarkers[position].showInfoWindow()
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(jwd))
                fmy = fn_map!!.y
            }
        })
    }


    fun fangdaAnimator(jwd: LatLng,position:Int){
        fn_map!!.layoutParams.height = dip(340)
        fn_map?.requestLayout()
        val sy = PropertyValuesHolder.ofFloat("scaleY",0.5f,1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(fn_map,sy)
//        val animator = ValueAnimator.ofInt(dip(160),dip(340))
//        animator.addUpdateListener {
//            fn_map!!.layoutParams.height = it.animatedValue.toString().toInt()
//            fn_map?.requestLayout()
//        }
        animator.interpolator = LinearInterpolator()
        animator.duration = 300
        animator.start()
        animator.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                listMarkers[position].showInfoWindow()
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(jwd))
                fmy = fn_map!!.y
            }
        })
    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        fn_map!!.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        fn_map!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        fn_map!!.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        fn_map!!.onSaveInstanceState(outState)
    }

}

data class UserBean(
        val id : String,
        val name:String,
        val jwd:LatLng,
        var xz:Boolean = false
)
