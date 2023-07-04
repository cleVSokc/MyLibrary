package com.haohao.feng.commonlib.http

import android.util.Log
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.GsonUtil
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type

/**
 * Email： dehaofeng@126.com
 * Phone： 18363851958
 * Author：haohao.feng
 * Date：  2022/3/17 11:29
 */
@Parser(name = "Response")
open class ResponseParser<T> : TypeParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    // 正常情况，data是一个对象，而异常情况下，服务端返回data为""或者[]，这样如果我们直接用Gson一次性解析，肯定会报JsonSyntaxException异常，此时，我们只能分两步解析，如下：
    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): T {
        //第一步，解析code、msg字段，把data当成String对象
        // if (response.code()==404)  throw ParseException(java.lang.String.valueOf(404), "not found", response)

        val data: Response<String> = response.convertTo(Response::class.java, String::class.java)

        var t: T? = null
        //第二步，取出data字段，转换成我们想要的对象
        if (data.status.code == Url.REQUEST_SUCCESS) t = GsonUtil.getObject(data.data, types[0])

        //判断我们传入的泛型是String对象，就给t赋值""字符串，确保t不为null、
        if (t == null && types[0] == String::class.java) t = "" as T

        //抛出异常 统一处理
        if (data.status.code != Url.REQUEST_SUCCESS || t==null) throw ParseException(java.lang.String.valueOf(data.status.code), data.status.msg, response)

        return t!!
    }


}
