package com.liguo.hjdemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.amap.api.maps.CameraUpdateFactory
import com.huajiao.sdk.hjbase.env.PartnerLoginCallback
import com.huajiao.sdk.hjbase.env.PartnerResultCallback
import com.huajiao.sdk.shell.HJSDK
import liguo.BaseActivity
import liguo.Util
import liguo.application.ke
import liguo.tes
import org.jetbrains.anko.*

class MainActivity : BaseActivity() {
    val userId = "9999"
    val userToken="NogVOgH3kIRfcysskMpHocGA4yPuF7D0"
    var te : Tes?=null
    override fun config(savedInstanceState: Bundle?) {
//        setContentView(R.layout.activity_main)

        setContentView(
                verticalLayout {
                    padding = dip(30)
                    button("登陆界面") {
                        onClick { startActivity(intentFor<LoginActivity>()) }
                    }
                    button("地图界面"){
                        onClick {
//                            if(Util.sid.isEmpty()){
//                                toast("请先进入登陆界面,登陆成功后进入地图界面")
//                            }else{
                                startActivity(intentFor<MapActivity>())
//                            }
                        }
                    }
                    button("分类界面") {
                        onClick {
//                            startActivity(intentFor<TypeActivity>())
                            sartAn()
                        }

                    }
                    te = tes {  }.lparams(dip(100),dip(100))
                }
        )
    }

    override fun initView(savedInstanceState: Bundle?) {
//        //主动登陆
//        HJSDK.Login.login(this,userId,userToken,object :PartnerResultCallback<Boolean>{
//            override fun onSuccess(p0: Boolean?) {
//                toast("登陆成功")
//                ke("登陆成功")
//
//            }
//
//            override fun onFailure(p0: Int, p1: String?) {
//                toast("登陆失败")
//                ke("登陆失败",p0.toString(),p1)
//            }
//        })
//        //被动登陆时设置回调
//        HJSDK.Login.setPartnerLoginCallback(object :PartnerLoginCallback {
//            //当合作方提供的ID和Token成功登录花椒时调用
//            override fun onPartnerLoginSuccess(p0: String?, p1: String?, p2: String?) {
//                ke("被动登陆成功")
//            }
//
//            //获取第三方的用户id
//            override fun getPartnerId(): String {
//                return userId
//            }
//
//            //获取第三方的用户的token(未经urlencode的)
//            override fun getPartnerToken(): String {
//                return userToken
//            }
//
//            //当合作方提供的ID和Token校验失败时回调合作方应用
//            // 由合作方应用判断是否应该重新登录客户端
//            override fun onPartnerLoginFail(p0: String?, p1: String?) {
//                ke("被动登陆失败")
//            }
//
//            //当合作方未提供ID和Token时调用，此时合作方需要登陆客户端
//            override fun onPartnerLoginRequest(p0: Activity?, p1: PartnerResultCallback<Boolean>?) {
//                ke("未提供ID和Token")
//            }
//        })


    }

    fun sartAn(){
        val sy = PropertyValuesHolder.ofFloat("jindu",0f,1f,0f,1f,0f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(te,sy)
        var arr = 0
        animator.interpolator = object : AccelerateDecelerateInterpolator(){
            override fun getInterpolation(input: Float): Float {
                Log.e("----------","--------------"+input)
                if(0<=input && input<0.25){
                    te!!.setCeng(0)
                }else if(0<=input && input<0.25){
                    te!!.setCeng(1)
                }else if(0<=input && input<0.25){
                    te!!.setCeng(2)
                }else{
                    te!!.setCeng(3)
                }
//                when(arr){
//                    0,2->{
//                     if(input==1f){
//                         arr++
//                         te!!.setCeng(arr)
//                     }
//                    }
//                    1,3->{
//                        if(input==0f){
//                            arr++
//                            te!!.setCeng(arr)
//                            if(arr==4)arr=0
//                        }
//                    }
//                }
                return super.getInterpolation(input)
            }
        }
        animator.duration = 10000
        animator.start()
        animator.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {

            }
        })
    }

    override fun initData() {
    }
}
