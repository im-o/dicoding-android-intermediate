package com.rivaldy.id.dicoding.ui.home

import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.dicoding.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initData() {
    }
}