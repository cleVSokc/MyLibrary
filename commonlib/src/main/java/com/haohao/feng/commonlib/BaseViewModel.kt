package com.haohao.feng.commonlib

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import rxhttp.wrapper.exception.ParseException

/**
 * Email： dehaofeng@126.com
 * Phone： 18363851958
 * Author：haohao.feng
 * Date：  2022/3/16
 */
abstract class BaseViewModel : ViewModel() {

    protected lateinit var iBaseMvvm: IBaseMvvm


    /**
     * 带loading的请求 并统一处理异常情况
     * @params  isShow: Boolean
     */
    fun <T> Flow<T>.withLoading(isShow: Boolean = true, callBack: ((Throwable) -> Unit) = {}) =
        onStart {
            if (isShow) iBaseMvvm.showLoading()
        }.onCompletion {
            iBaseMvvm.dismissLoading()
            // iBaseMvvm.finishRefresh()
        }.catch {
            //统一处理错误code
            if (it is ParseException) iBaseMvvm.onErrorCode(it)
            else iBaseMvvm.showNetErrorLayout()

        }


    /**
     * 为ViewModel获取activity或者fragment的context实例
     *
     * */

    open fun addContext(iBaseMvvm:IBaseMvvm) {
        this.iBaseMvvm = iBaseMvvm

    }
}