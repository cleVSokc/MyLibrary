package com.haohao.feng.commonlib.http

import rxhttp.wrapper.annotation.DefaultDomain

/**
 * Email： dehaofeng@126.com
 * Phone： 18363851958
 * Author：haohao.feng
 * Date：  2022/3/17 11:30
 */
object Url {

    @JvmField
    @DefaultDomain //设置为默认域名
    var BASE_URL = "https://app.chime.me"   //线上

    const val REQUEST_SUCCESS = 0 //调用成功


}