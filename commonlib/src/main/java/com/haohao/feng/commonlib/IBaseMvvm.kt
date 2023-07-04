package com.haohao.feng.commonlib

import rxhttp.wrapper.exception.ParseException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

interface IBaseMvvm {

    fun findType(type: Type): Type? = when (type) {
        is ParameterizedType -> type
        is Class<*> -> findType(type.genericSuperclass)
        else -> null
    }

    fun showNetErrorLayout()

    fun showEmptyLayout()

    fun showLoading()

    fun dismissLoading()

    fun onErrorCode(bean: ParseException)

}