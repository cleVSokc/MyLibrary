package com.haohao.feng.commonlib.http

import java.io.Serializable

/**
 * Email： dehaofeng@126.com
 * Phone： 18363851958
 * Author：haohao.feng
 * Date：  2022/3/17 11:29
 */
class Response<T>  : Serializable {
    fun setData(data: T) {
        this.data = data
    }

    constructor(status: Status) {
        this.status = status
    }

    constructor(status: Status, data: T) {
        this.status = status
        this.data = data
    }
    var status: Status
    var data: T? = null
        private set
}