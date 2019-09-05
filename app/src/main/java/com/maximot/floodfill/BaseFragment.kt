package com.maximot.floodfill

import androidx.fragment.app.Fragment
import com.maximot.floodfill.assemble.DependencyProvider

open class BaseFragment : Fragment(){
    lateinit var dependencyProvider: DependencyProvider
}