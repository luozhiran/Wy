package com.lzr.wy

import androidx.fragment.app.Fragment
import com.plugin.mvvm.viewpagerModel.PageFragmentModel

class MainViewModel: PageFragmentModel() {



    fun getFragment(index:Int):Fragment{
        return fragments[index]
    }
}