package com.example.dindintest.view.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.dindintest.R
import com.example.dindintest.data.apimgr.exceptions.InvalidAccessTokenException
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

abstract class BaseActivity : AppCompatActivity() {
    private val layoutId: Int = R.layout.activity_base
    protected abstract var rootFragment: BaseFragment
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    fun showVideoPicker() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_PICK
        activityResultLauncher.launch(Intent.createChooser(intent, "Select video"))
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        RxJavaPlugins.setErrorHandler { throwable ->
            throwable.printStackTrace()
            var cause: Throwable? = null
            if (throwable is CompositeException) {
                cause = throwable.exceptions[0]
            }
            if (throwable is UndeliverableException) {
                cause = throwable.cause
            }
            cause?.let { errorCause ->
                if (errorCause is InvalidAccessTokenException) {
                    //Should be logout when token invalid
                }
            }
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT

        val view = LayoutInflater.from(this).inflate(layoutId, null, false)
        setContentView(view)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, rootFragment)
            .commitAllowingStateLoss()


    }

    override fun onBackPressed() {
        rootFragment.onBackPressed()
    }
}

