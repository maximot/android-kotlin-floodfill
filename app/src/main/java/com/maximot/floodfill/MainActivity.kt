package com.maximot.floodfill

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maximot.floodfill.floodfill.ui.FloodfillFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FloodfillFragment.create())
                .commitNow()
        }
    }

}
