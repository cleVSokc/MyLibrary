package com.haohao.feng.commonlib.http

import android.annotation.SuppressLint
import android.app.Application
import okhttp3.*
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cache.CacheMode
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.Param
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


/**
 * Email： dehaofeng@126.com
 * Phone： 18363851958
 * Author：haohao.feng
 * Date：  2022/3/17 11:30
 */
object HttpManager {
    private val TAG = HttpManager::class.java.simpleName
    @SuppressLint("SuspiciousIndentation")
    fun init(context: Application?) {
//        val url = SpUtil.getString(SAVE_BASE_URL)
//        if (!TextUtils.isEmpty(url)) {
//            Url.BASE_URL = url
//        } else {
//            Url.BASE_URL =  NetworkConfig.getBaseUrl()
//        }
        val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            ).build()
        val certificatePinner = CertificatePinner.Builder()
            .add("*.chime.me", "sha256/CXwGbHsCyR85Ap20MtrvsUUDLrGBkXoI6FtRADwU1H0=")
            .add("*.chime.me", "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
            .add("*.chime.me", "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
            .build()
        val file = File(context!!.externalCacheDir, "RxHttpCookie")
        val sslParams = HttpsUtils.getSslSocketFactory()
        val client: OkHttpClient = OkHttpClient.Builder()
            .cookieJar(CookieStore(file))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .sslSocketFactory(sslParams!!.sSLSocketFactory, sslParams.trustManager) //添加信任证书
            .hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true }) //忽略host验证
            .build()
        RxHttpPlugins.init(client) //自定义OkHttpClient对象
            .setDebug(true,true) //是否开启调试模式，开启后，logcat过滤RxHttp，即可看到整个请求流程日志
            .setCache(
                File(context.externalCacheDir, "RxHttpCache"),
                10 * 1024 * 1024,
                CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
            )
            .setOnParamAssembly { p: Param<*>? -> //设置公共参数，非必须
                val method = p!!.method
//                if (method!!.isGet) {
//                    p.addQuery("client_info", Gson().toJson(ClientInfo.getInstance()))
//                    if (ModuleProvider.getInstance().sessionStore.get().isNotEmpty())
//                        p.addQuery("sessionKey", ModuleProvider.getInstance().sessionStore.get())
//                } else if (method.isPost) { //P
//                    p.add("client_info", Gson().toJson(ClientInfo.getInstance()))
//                    if (ModuleProvider.getInstance().sessionStore.get().isNotEmpty())
//                        p.add("sessionKey", ModuleProvider.getInstance().sessionStore.get())
//                }
                p
            }
    }


}