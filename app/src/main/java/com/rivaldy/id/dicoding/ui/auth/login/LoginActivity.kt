package com.rivaldy.id.dicoding.ui.auth.login

import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.commons.util.FormatterUtils.isValidEmail
import com.rivaldy.id.commons.util.FormatterUtils.spannedHintFooterAuth
import com.rivaldy.id.core.data.model.local.pref.LoginInfo
import com.rivaldy.id.core.data.model.remote.login.LoginResult
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilExceptions.handleApiError
import com.rivaldy.id.core.utils.UtilExtensions.openActivity
import com.rivaldy.id.core.utils.UtilExtensions.showSnackBar
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.databinding.ActivityLoginBinding
import com.rivaldy.id.dicoding.ui.MainViewModel
import com.rivaldy.id.dicoding.ui.auth.register.RegisterActivity
import com.rivaldy.id.dicoding.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initData() {
        initView()
        initObservers()
        initListeners()
        initClick()
    }

    private fun initView() {
        binding.hintFooterTV.text = spannedHintFooterAuth(this, getString(R.string.no_have_account), getString(R.string.sign_up))
    }

    private fun initObservers() {
        viewModel.loginUser.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showLoginSuccess(it.value.loginResult)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun initListeners() {
        binding.emailET.doAfterTextChanged { validationForm() }
        binding.passwordET.doAfterTextChanged { validationForm() }
    }

    private fun initClick() {
        binding.loginMB.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            viewModel.loginUserApiCall(email, password)
        }

        binding.hintFooterTV.setOnClickListener {
            openActivity(RegisterActivity::class.java)
        }
    }

    private fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        binding.root.showSnackBar(handleApiError(failure))
    }

    private fun showLoginSuccess(loginResult: LoginResult?) {
        showLoading(false)
        viewModel.setLoginInfo(LoginInfo(loginResult?.userId, loginResult?.token, binding.emailET.text.toString(), loginResult?.name))
        openActivity(HomeActivity::class.java)
        finish()
    }

    private fun validationForm() {
        binding.apply {
            loginMB.isEnabled = isValidEmail(emailET.text.toString()) && passwordET.text.toString().length >= 6
        }
    }
}