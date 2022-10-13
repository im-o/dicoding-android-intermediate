package com.rivaldy.id.commons.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.rivaldy.id.commons.view.LoadingProgressDialog

/** Created by github.com/im-o on 10/1/2022. */

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    private val progressDialog: Dialog by lazy { LoadingProgressDialog.setProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initData()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    abstract fun getViewBinding(): VB

    abstract fun initData()

    protected fun showLoading(isLoading: Boolean) {
        if (isLoading) showProgressDialog()
        else hideProgressDialog()
    }

    private fun showProgressDialog() {
        hideProgressDialog()
        progressDialog.show()
    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing) progressDialog.cancel()
    }
}