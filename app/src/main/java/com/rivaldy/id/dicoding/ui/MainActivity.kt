package com.rivaldy.id.dicoding.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilFunctions.logE
import com.rivaldy.id.dicoding.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityLoginBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
        viewModel.testing()
        viewModel.loginUserApiCall("rival@gmail.com", "testing bro")
    }

    private fun initObservers() {
        viewModel.loginUser.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(false)
                is DataResource.Success -> logE("Login Success : ${it.value}")
                is DataResource.Failure -> logE("Login Failure : $it")
            }
        }
    }
}