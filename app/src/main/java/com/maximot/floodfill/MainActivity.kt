package com.maximot.floodfill

import android.os.Bundle
import com.maximot.floodfill.base.BaseActivity
import com.maximot.floodfill.floodfill.ui.fragment.FloodfillFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupFloodfillFragment(savedInstanceState)
    }

    private fun setupFloodfillFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, FloodfillFragment.create())
                .commitNow()
        }
    }
}
