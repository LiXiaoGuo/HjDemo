package com.liguo.hjdemo

import android.os.Bundle
import liguo.BaseActivity
import org.jetbrains.anko.*

class MainActivity : BaseActivity() {
    val userId = "9999"
    val userToken="NogVOgH3kIRfcysskMpHocGA4yPuF7D0"
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
                            startActivity(intentFor<TypeActivity>())
                        }

                    }
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

    override fun initData() {
    }
}
