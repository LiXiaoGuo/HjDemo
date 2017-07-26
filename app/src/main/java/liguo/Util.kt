package liguo

import android.view.ViewManager
import com.liguo.hjdemo.LoginBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import liguo.application.BaseApplication
import liguo.application.ke
import okhttp3.OkHttpClient
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 *
 * Created by Extends on 2017/7/3 14:37
 */

object Util{
    var sid=""
    val HTTPURL = "http://pifaqq.com/"//网络连接的地址
    val api:API by lazy {
        val sClient = OkHttpClient.Builder()
                .connectTimeout(20L, TimeUnit.SECONDS)
                .build()
        var sc: SSLContext? = null

        try {
            sc = SSLContext.getInstance("SSL")
            sc!!.init(null, arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(java.security.cert.CertificateException::class)
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {

                }

                @Throws(java.security.cert.CertificateException::class)
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {

                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? {
                    return null
                }
            }), SecureRandom())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val hv1 = HostnameVerifier { hostname, session -> true }

        val workerClassName = "okhttp3.OkHttpClient"
        try {
            val workerClass = Class.forName(workerClassName)
            val hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier")
            hostnameVerifier.isAccessible = true
            hostnameVerifier.set(sClient, hv1)

            val sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory")
            sslSocketFactory.isAccessible = true
            sslSocketFactory.set(sClient, sc!!.socketFactory)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Retrofit.Builder()
                .baseUrl(Util.HTTPURL)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(sClient)
                .build()
                .create(API::class.java)
    }


    fun connection(map: Map<String, String>): Observable<String> {
        return api.getDefaultForGet(map)
                .subscribeOn(Schedulers.io())
                .retry(3)
                .doOnNext({ s -> ke(map.toString(), s) })
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
    }

    fun connection(map: Map<String, String>, onNext: Consumer<in String>, onComplete: Action = Functions.EMPTY_ACTION): Disposable {
        return connection(map)
                .subscribe(onNext, Consumer<Throwable> { e ->
                    if (e is HttpException) {
                        BaseApplication.context?.toast("服务暂不可用")
                    } else if (e is IOException) {
                        BaseApplication.context?.toast("连接服务器失败")
                    } else {
                        BaseApplication.context?.toast(e.message?:"message is null")
                    }
                    onComplete.run()
                }, onComplete)
    }

    fun dip2px(dpValue:Float)= BaseApplication.context?.dip(dpValue)?:0



}

inline fun ViewManager.flushRecyclerView(theme:Int=0,init:liguo.FlushRecyclerView.()->Unit)
        = ankoView({liguo.FlushRecyclerView(it)},theme,init)
inline fun ViewManager.aMaps(theme:Int=0,init:com.amap.api.maps.MapView.()->Unit)
        =ankoView({com.amap.api.maps.MapView(it)},theme){init()}
inline fun ViewManager.tes(theme:Int=0,init:Tes.()->Unit)
        =ankoView({Tes(it)},theme){init()}