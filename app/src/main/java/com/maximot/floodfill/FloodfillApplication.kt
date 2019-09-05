package com.maximot.floodfill

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.maximot.floodfill.assemble.DependencyProvider
import com.maximot.floodfill.base.BaseActivity
import com.maximot.floodfill.base.BaseFragment


class FloodfillApplication : Application() {

    val dependencyProvider: DependencyProvider by lazy {
        DependencyProvider(this)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                if (p0 is BaseActivity) {
                    p0.dependencyProvider = dependencyProvider
                    handleFragments(p0)
                }
            }

            override fun onActivityPaused(p0: Activity) = Unit

            override fun onActivityStarted(p0: Activity) = Unit

            override fun onActivityDestroyed(p0: Activity) = Unit

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) = Unit

            override fun onActivityStopped(p0: Activity) = Unit

            override fun onActivityResumed(p0: Activity) = Unit
        })
    }

    private fun handleFragments(p0: BaseActivity) {
        p0.supportFragmentManager.registerFragmentLifecycleCallbacks(object:
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentPreCreated(
                fm: FragmentManager,
                f: Fragment,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentPreCreated(fm, f, savedInstanceState)
                if(f is BaseFragment){
                    f.dependencyProvider = dependencyProvider
                }
            }
        },true)
    }
}