package com.haohao.feng.commonlib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.LoadingPopupView
import rxhttp.wrapper.exception.ParseException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 * Email： dehaofeng@126.com
 * Phone： 18363851958
 * Author：haohao.feng
 * Date：  2022/3/16
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), IBaseMvvm {
    private var _bind: VB? = null
    val mViewBinding get() = _bind!!
    val TAG = javaClass.simpleName
    private var loadingPopupView: BasePopupView? = null

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            // val vmClass = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
            val type = findType(javaClass.genericSuperclass)
            val vmClass = if (type is ParameterizedType)
                type.actualTypeArguments[0] as Class<VB>
            else ViewBinding::class.java as Class<VB>
            val method: Method = vmClass.getMethod("inflate", LayoutInflater::class.java)
            _bind = method.invoke(null, layoutInflater) as VB
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return mViewBinding.root

    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        addListener()
    }

    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun addListener()


    override fun showNetErrorLayout() {
//        val errorLayout = layoutInflater.inflate(R.layout.layout_error, null)
//        (mViewBinding.root as ViewGroup).addView(errorLayout)
//        return


    }
    //  同showNetErrorLayout

    override fun showEmptyLayout() {
    }


    override fun onErrorCode(bean: ParseException) {


    }

    override fun showLoading() {
        if (loadingPopupView == null) {
            loadingPopupView = XPopup.Builder(context)
                .hasShadowBg(false)
                .dismissOnBackPressed(true)
                .dismissOnTouchOutside(false)
                .asCustom(LoadingPopupView(requireContext(),mViewBinding.root.id))
        }
        loadingPopupView?.show()
    }

    override fun dismissLoading() {
        if (loadingPopupView != null && loadingPopupView!!.isShow)
            loadingPopupView?.dismiss()

    }

}