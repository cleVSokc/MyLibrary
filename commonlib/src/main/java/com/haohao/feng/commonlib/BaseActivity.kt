package com.haohao.feng.commonlib

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.LoadingPopupView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), IBaseMvvm {
    val TITLE = "TITLE"
    private var loadingPopupView: BasePopupView? = null
    private var _bind: VB? = null
    val mViewBinding get() = _bind!!
    private var hasTitleBar = false
    val TAG = javaClass.simpleName
    private var rightTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            // val vmClass = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
            val type = findType(javaClass.genericSuperclass)
            val vmClass = if (type is ParameterizedType)
                type.actualTypeArguments[0] as Class<VB>
            else ViewBinding::class.java as Class<VB>
            val method: Method = vmClass.getMethod("inflate", LayoutInflater::class.java)
            _bind = method.invoke(null, layoutInflater) as VB
            setContentView(mViewBinding.root)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        initView()
        addListener()
        initData()
    }

    protected open fun onBackClick() {}
    protected open fun onTitleBarRightClick() {


    }

    protected open fun onRightImgClick() {


    }

    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun addListener()


    override fun showNetErrorLayout() {
        //两种方法获取跟布局  mViewBinding.root==childAt== view.getChildAt(1)
        //val view = window.decorView.findViewById<ViewGroup>(android.R.id.content)
//        val view = mViewBinding.root as ViewGroup
//        val errorLayout = layoutInflater.inflate(R.layout.layout_error, null)
        return
    }

    override fun showEmptyLayout() {


    }

    override fun showLoading() {
        if (loadingPopupView == null) {
            loadingPopupView = XPopup.Builder(this)
                .hasShadowBg(false)
                .dismissOnBackPressed(true)
                .dismissOnTouchOutside(false)
                .asCustom(LoadingPopupView(this,mViewBinding.root.id))

        }
        loadingPopupView?.show()
    }

    override fun dismissLoading() {
        if (loadingPopupView != null && loadingPopupView!!.isShow)
            loadingPopupView?.dismiss()

    }


    override fun onErrorCode(bean: ParseException) {


    }


    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }


    open fun getRightView() = rightTV


}