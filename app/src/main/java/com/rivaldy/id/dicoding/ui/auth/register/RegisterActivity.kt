package com.rivaldy.id.dicoding.ui.auth.register

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.commons.util.FormatterUtils
import com.rivaldy.id.commons.util.FormatterUtils.isValidEmail
import com.rivaldy.id.core.data.model.remote.login.LoginResult
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilExceptions.handleApiError
import com.rivaldy.id.core.utils.UtilExtensions.myToast
import com.rivaldy.id.core.utils.UtilExtensions.showSnackBar
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.databinding.ActivityRegisterBinding
import com.rivaldy.id.dicoding.ui.MainViewModel
import com.rivaldy.id.dicoding.ui.auth.AuthViewModel
import com.rivaldy.id.dicoding.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private val viewModel by viewModels<AuthViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()

    override fun getViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initData() {
        initView()
        initObservers()
        initListeners()
        initClick()
    }

    private fun initView() {
        binding.hintFooterTV.text = FormatterUtils.spannedHintFooterAuth(this, getString(R.string.already_have_account), getString(R.string.sign_in))
    }

    private fun initObservers() {
        viewModel.loginUser.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true, isCancelable = false)
                is DataResource.Success -> showLoginSuccess(it.value.loginResult)
                is DataResource.Failure -> showFailure(it)
            }
        }

        viewModel.registerUser.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true)
                is DataResource.Success -> showRegisterSuccess(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun initListeners() {
        binding.nameET.doAfterTextChanged { validationForm() }
        binding.emailET.doAfterTextChanged { validationForm() }
        binding.passwordET.doAfterTextChanged { validationForm() }
    }

    private fun initClick() {
        binding.registerMB.setOnClickListener {
            val name = binding.nameET.text.toString()
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            viewModel.registerUserApiCall(name, email, password)
        }

        binding.hintFooterTV.setOnClickListener { finish() }
    }

    private fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        binding.root.showSnackBar(handleApiError(failure))
    }

    private fun showLoginSuccess(loginResult: LoginResult?) {
        showLoading(false)
        mainViewModel.setUserTokenPref(loginResult?.token ?: "")

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun showRegisterSuccess(response: RegisterResponse) {
        showLoading(false)
        myToast(response.message.toString())
        viewModel.loginUserApiCall(binding.emailET.text.toString(), binding.passwordET.text.toString())
    }

    private fun validationForm() {
        binding.apply {
            registerMB.isEnabled = nameET.text.toString().trim().isNotEmpty() && isValidEmail(emailET.text.toString().trim()) && passwordET.text.toString().length >= 6
        }
    }
}