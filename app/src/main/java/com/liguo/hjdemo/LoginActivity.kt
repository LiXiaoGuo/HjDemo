package com.liguo.hjdemo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import com.google.gson.Gson
import io.reactivex.functions.Consumer
import liguo.BaseActivity
import liguo.Util
import liguo.application.ke
import liguo.fromJson
import org.java_websocket.handshake.ServerHandshake
import org.jetbrains.anko.*
import org.json.JSONObject
import java.net.URI

class LoginActivity : BaseActivity() {
    override fun config(savedInstanceState: Bundle?) {
        setContentView(
        verticalLayout {
            var name : EditText?= null
            var password : EditText?= null
            padding = dip(30)
            backgroundColor = Color.parseColor("#323641")
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            textView {
                text = "welcome"
                textColor = Color.WHITE
                textSize = 30f
            }.lparams(-2,-2){
                bottomMargin = dip(80)
            }
            linearLayout {
                setLinearLayoutLoginStyle(this)
                imageView {
                    imageResource = R.drawable.ic_account
                }.lparams(dip(20),dip(20))
                name = editText {
                    hint = "Name"
                    setEditTextStyle(this)
                }.lparams(-1,-2){
                    leftMargin = dip(10)
                }
            }
            linearLayout {
                setLinearLayoutLoginStyle(this)
                imageView {
                    imageResource = R.drawable.ic_password
                }.lparams(dip(20),dip(20))
                password = editText {
                    hint = "Password"
                    setEditTextStyle(this)
                }.lparams(-1,-2){
                    leftMargin = dip(10)
                }
            }.lparams(-1,-2){
                topMargin = dip(10)
            }
            button("Login") {
                textSize = 16f
                backgroundResource = R.drawable.sp_green_round
                textColor = Color.WHITE
                onClick {
                    val strName = "xiaoxiao" //name!!.text.toString()
                    val strPass = "mm1357456" //password!!.text.toString()
                    if(strName.isEmpty()){
                        toast("Name cannot be empty")
                    }else if(strPass.isEmpty()){
                        toast("Password cannot be empty")
                    }else{
                        Util.connection(hashMapOf(
                                "option" to "com_users",
                                "task" to "user.axAuth",
                                "uname" to strName,
                                "pw" to strPass
                        ), Consumer<String>{
                            if(it != "null"){
                                Util.sid = it.substring(1,it.length-1)
                                toast("验证通过,服务器返回:\n"+Util.sid)
                                handlers.sendEmptyMessage(2)
                            }else{
                                toast("验证失败,服务器返回:\n"+it)
                            }
                        })
                    }
                }
            }.lparams(-1,-2){
                topMargin = dip(10)
                bottomMargin = dip(100)
            }
        })
    }

    val handlers = object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                2->{
                    connectServer()
                }
                1->{
                    toast("断开连接")
                }
            }
        }
    }
    var client:Client?=null

    fun connectServer(){
        toast("连接服务器中。。。")
        client = object : Client(URI("http://pifaqq.com:7272")){
            override fun onOpen(serverHandshake: ServerHandshake) {
                super.onOpen(serverHandshake)
                val jo = with(JSONObject()){
                    put("type","login")
                    put("sid",Util.sid)
                    put("users",0)
                    put("groups",0)
                    this
                }
                client!!.send(jo.toString())
                handlers.post { toast("已连接") }
            }

            override fun onClose(i: Int, s: String, b: Boolean) {
                super.onClose(i, s, b)
                handlers.sendEmptyMessage(1)
                handlers.sendEmptyMessageDelayed(2,1000)
            }

            override fun onError(e: Exception) {
                super.onError(e)
                ke(e.toString())
                handlers.sendEmptyMessageDelayed(2,1000)
                handlers.sendEmptyMessage(1)
            }

            override fun onMessage(s: String) {
                super.onMessage(s)
                ke(s)
                try {
                    val jo = JSONObject(s)
                    when(jo["type"]){
                        "login"->{
                            val login = Gson().fromJson<LoginBean>(s)
                            ke(login.client_id)
                        }
                        "ping"->{
                            //心跳包
                        }
                    }
                }catch (e: Throwable){

                }


            }
        }
        client!!.connect()
    }

    fun setLinearLayoutLoginStyle(ll:LinearLayout){
        ll.apply {
            orientation = LinearLayout.HORIZONTAL
            backgroundResource = R.drawable.sp_back_round
            padding = dip(2.5f)
            horizontalPadding = dip(20)
            gravity = Gravity.CENTER_VERTICAL
        }
    }

    fun setEditTextStyle(et:EditText){
        et.apply {
            textSize = 16f
            background = null
            lines = 1
            textColor = Color.WHITE
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }


}

data class LoginBean(
        val client_id:String,
        val userid:Int,
        val name:String,
        val thumb:String,
        val time:String,
        val groups_list:List<GroupsBean>,
        val unreaduser_list:List<String>,
        val insertusers:List<InsertusersBean>,
        val insertcats:List<InsertcatsBean>
)

data class GroupsBean(
        val id:Int,
        val name:String,
        val thumb:String,
        val description:String,
        val ownerid:Int
)

data class InsertusersBean(
        val userid:Int,
        val alias:String,
        val thumb:String,
        val latitude:Float,
        val longitude:Float,
        val iffriend:Int
)

data class InsertcatsBean(
        val cat_id:Int,
       val cat_name:String,
       val cat_image:String,
       val cat_parent:Int,
       val CN_1:Int,
       val CN_2:Int,
       val CN_N:Int,
       val cat_approved:Int,
       val cat_created:String
)

data class UnreaduserBean(
        val from:Int,
        val to:Int,
        val message:String,
        val sent:Long//10位,精确到秒
)
