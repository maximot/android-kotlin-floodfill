package com.maximot.floodfill.assemble

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.maximot.floodfill.FloodfillApplication
import com.maximot.floodfill.base.BaseActivity
import com.maximot.floodfill.base.BaseFragment
import com.maximot.floodfill.floodfill.ui.fragment.FloodfillFragment

class Injector(private val context: Context) {

    companion object {
        fun inject(app: FloodfillApplication) {
            app.injector = Injector(app)
        }
    }

    val dependencyProvider by lazy {
        DependencyProvider(context)
    }

    fun handleActivity(activity: Activity) {
        if (activity is BaseActivity) {
            inject(activity)
        }
    }

    fun handleFragment(fragment: Fragment) {
        if (fragment is BaseFragment) {
            inject(fragment)
            when (fragment) {
                is FloodfillFragment -> {
                    inject(fragment)
                }
            }
        }
    }

    private fun inject(activity: BaseActivity) {
        // NO-OP
    }

    private fun inject(fragment: BaseFragment) {
        // NO-OP
    }

    private fun inject(fragment: FloodfillFragment) {
        fragment.floodfillViewModelFactory = dependencyProvider.floodfillViewModelFactory
    }
}