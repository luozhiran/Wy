package com.lzr.wy.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lzr.wy.R

class OwnFloorFragment : Fragment() {

    companion object {
        fun newInstance() = OwnFloorFragment()
    }

    private lateinit var viewModel: OwnFloorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.own_floor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OwnFloorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
