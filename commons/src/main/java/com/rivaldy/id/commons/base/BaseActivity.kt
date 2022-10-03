package com.rivaldy.id.commons.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.rivaldy.id.commons.view.LoadingProgressDialog

/** Created by github.com/im-o on 10/1/2022. */

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private lateinit var binding: VB
    private val progressDialog: Dialog by lazy { LoadingProgressDialog.setProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    abstract fun getViewBinding(): VB

    protected fun showLoading(isShown: Boolean) {
        if (isShown) showProgressDialog()
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