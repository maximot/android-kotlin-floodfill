package com.maximot.floodfill

import androidx.appcompat.app.AppCompatActivity
import com.maximot.floodfill.assemble.DependencyProvider

open class BaseActivity : AppCompatActivity(){
    lateinit var dependencyProvider: DependencyProvider
}