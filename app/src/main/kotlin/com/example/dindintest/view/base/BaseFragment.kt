package com.example.dindintest.view.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.dindintest.R
import com.example.dindintest.data.context.IViewContext
import com.example.dindintest.view.exts.goBack
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_error.view.*
import java.io.File

abstract class BaseFragment : Fragment(), IViewContext {
    abstract val layoutResId: Int
    private lateinit var unBinder: Unbinder
    private var loadingDialog: MaterialDialog? = null
    private val disposables = CompositeDisposable()

    var onVideoSelected: (file: File) -> Unit = {}

    open fun onAsyncViewCreated(view: View) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = LayoutInflater
            .from(context)
            .inflate(layoutResId, container, false)
        unBinder = ButterKnife.bind(this, contentView)
        return contentView
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    open fun onVisible() {
        //This function will be call when view pager selected
    }

    open fun onInvisible() {
        //Call when page unselected
    }

    fun fitStatusBar(view: View) {
        val topMargin = getStatusBarHeight()
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin = topMargin
    }

    fun fitNavigationBar(view: View) {
        val bottomMargin = getBottomNavHeight()
        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin = bottomMargin
    }

    private fun showLoading(context: Context): MaterialDialog {
        return context.run {
            MaterialDialog(this).show {
                customView(viewRes = R.layout.dialog_progress)
                cancelable(false)
                view.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    fun showVideoPicker(onSelected: (video: File) -> Unit) {
        onVideoSelected = onSelected
        (activity as? BaseActivity)?.showVideoPicker()
    }

    override fun getDataDir(): File {
        return File(requireContext().applicationInfo.dataDir)
    }

    override fun showErrorDialog(
        message: String,
        cancelable: Boolean,
        onYes: () -> Unit,
        onDismiss: () -> Unit
    ): MaterialDialog? {
        return context?.run {
            MaterialDialog(this).show {
                customView(viewRes = R.layout.dialog_error, scrollable = false)
                view.btnDialogPositive.setOnClickListener {
                    onYes()
                    dismiss()
                    onDismiss()
                }
                view.setBackgroundColor(Color.TRANSPARENT)
                view.btnDialogPositive.setText(R.string.btn_ok)
                view.tvDialogMessage.text = message
                cancelable(cancelable)
            }
        }
    }

    override fun showLoadingDialog() {
        loadingDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        } ?: run {
            loadingDialog = showLoading(requireContext())
        }
    }

    override fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        disposables.clear()
        unBinder.unbind()
        hideLoadingDialog()
    }

    fun getBottomNavHeight(): Int {
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun onBackPressed() {
        goBack()
    }
}