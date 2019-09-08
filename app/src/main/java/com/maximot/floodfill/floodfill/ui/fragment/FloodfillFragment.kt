package com.maximot.floodfill.floodfill.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.maximot.floodfill.R
import com.maximot.floodfill.base.BaseFragment
import com.maximot.floodfill.floodfill.ui.view.BitmapView
import com.maximot.floodfill.floodfill.viewmodel.FloodfillViewModel
import com.maximot.floodfill.utils.FloodfillAlgorithm
import kotlinx.android.synthetic.main.floodfill_fragment.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class FloodfillFragment : BaseFragment() {

    companion object {
        fun create() = FloodfillFragment()
    }

    private lateinit var viewModel: FloodfillViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.floodfill_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBitmapView()
        setupGenerateButton()
        setupSpeedChooser()
        setupAlgorithmChooser()
    }

    private fun setupAlgorithmChooser() {
        floodfill_algorithm_chooser.adapter =
            ArrayAdapter.createFromResource(context!!, R.array.algorithms, R.layout.spinner_item)
        floodfill_algorithm_chooser.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) = Unit

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.onNewAlgorithm(FloodfillAlgorithm.values()[p2])
                }
            }
    }

    private fun setupSpeedChooser() {
        floodfill_algorithm_speed_chooser.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                viewModel.onNewFps(min(60,max(1,(p1/1000.0f * 60).roundToInt())))
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit

            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.progress = (((viewModel.fps.value?:0) / 60.0f) * 1000).roundToInt()
            }
        })
    }

    private fun setupGenerateButton() {
        floodfill_bitmap_generate_button.setOnClickListener {
            val widthString = floodfill_bitmap_width.text?.toString()
            val heightString = floodfill_bitmap_height.text?.toString()
            if (widthString.isNullOrBlank() || heightString.isNullOrBlank())
                return@setOnClickListener
            viewModel.onGenerateImage(widthString.toInt(), heightString.toInt())
        }
    }

    private fun setupBitmapView() {
        floodfill_bitmap_view?.onBitmapClickListener = object : BitmapView.OnBitmapClickListener {
            override fun onBitmapClicked(x: Int, y: Int) {
                viewModel.onBitmapClicked(x, y)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, dependencyProvider.floodfillViewModelFactory)
            .get(FloodfillViewModel::class.java)
        viewModel.fps.observe(this, Observer { fps ->
            floodfill_fps?.text = "$fps FPS"
        })
        viewModel.algorithm.observe(this, Observer { algorithm ->
            floodfill_algorithm_chooser.setSelection(algorithm.ordinal)
        })
        viewModel.image.observe(this,Observer { image ->
            floodfill_bitmap_view.image = image
            floodfill_bitmap_width.setText(image.width.toString())
            floodfill_bitmap_height.setText(image.height.toString())
        })
        viewModel.isBusy.observe(this, Observer { busy ->
            if(busy)
                blockUi()
            else
                unblockUi()
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private fun unblockUi() {
        floodfill_bitmap_generate_button.isEnabled = true
    }

    private fun blockUi() {
        floodfill_bitmap_generate_button.isEnabled = false
    }

}
