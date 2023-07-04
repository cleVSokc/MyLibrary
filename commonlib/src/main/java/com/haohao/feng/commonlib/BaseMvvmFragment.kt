package com.haohao.feng.commonlib

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * Email： dehaofeng@126.com
 * Phone： 18363851958
 * Author：haohao.feng
 * Date：  2022/3/16
 */
abstract class BaseMvvmFragment<VB: ViewBinding,VM : BaseViewModel> : BaseFragment<VB>() {

    lateinit var viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createViewModel()
        super.onViewCreated(view, savedInstanceState)
        observeDataChanged()

    }
    protected abstract fun observeDataChanged()

    private fun createViewModel() {
        val type = findType(javaClass.genericSuperclass)
        val modelClass = if (type is ParameterizedType)
            type.actualTypeArguments[1] as Class<VM>
        else BaseViewModel::class.java as Class<VM>
        viewModel = ViewModelProvider(this).get(modelClass, this)
    }

    /**
     * 为ViewModel获取activity或者fragment的context实例
     *
     * */

    fun <T : ViewModelProvider, V : BaseViewModel> T.get(
        modelClass: Class<V>,
        fragment: BaseFragment<*>
    ): V {
        val model = get(modelClass)
        model.addContext(fragment)
        return model
    }


}