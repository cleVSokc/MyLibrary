package com.haohao.feng.commonlib.http

import java.io.Serializable

class Status :Serializable {
    var code = 0
    var msg: String? = null
    var trace: String? = null
}