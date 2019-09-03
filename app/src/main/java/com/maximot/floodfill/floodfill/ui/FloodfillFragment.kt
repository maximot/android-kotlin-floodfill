package com.maximot.floodfill.floodfill.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.maximot.floodfill.R
import com.maximot.floodfill.floodfill.viewmodel.FloodfillViewModel

class FloodfillFragment : Fragment() {

    companion object {
        fun create() = FloodfillFragment()
    }

    private lateinit var viewModel: FloodfillViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FloodfillViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
