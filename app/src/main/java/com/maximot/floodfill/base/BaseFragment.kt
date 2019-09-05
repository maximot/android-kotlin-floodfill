package com.maximot.floodfill.base

import androidx.fragment.app.Fragment
import com.maximot.floodfill.assemble.DependencyProvider

open class BaseFragment : Fragment(){
    lateinit var dependencyProvider: DependencyProvider
}