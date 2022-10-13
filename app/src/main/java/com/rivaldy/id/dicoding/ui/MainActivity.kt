package com.rivaldy.id.dicoding.ui

import androidx.activity.viewModels
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.dicoding.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initData() {

    }
}