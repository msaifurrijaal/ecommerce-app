package com.msaifurrijaal.tokoonline.presentation.login

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.data.hawkstorage.HawkStorage
import com.msaifurrijaal.tokoonline.data.model.Resource
import com.msaifurrijaal.tokoonline.data.model.auth.LoginRequest
import com.msaifurrijaal.tokoonline.databinding.ActivityLoginBinding
import com.msaifurrijaal.tokoonline.presentation.main.MainActivity
import com.msaifurrijaal.tokoonline.presentation.register.RegisterActivity
import com.msaifurrijaal.tokoonline.utils.showDialogError
import com.msaifurrijaal.tokoonline.utils.showDialogLoading
import com.msaifurrijaal.tokoonline.utils.showDialogNotification
import com.msaifurrijaal.tokoonline.utils.showDialogSuccess
import com.msaifurrijaal.tokoonline.utils.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dialogLoading: AlertDialog
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        dialogLoading = showDialogLoading(this)

        onAction()
    }

    private fun onAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString().trim()
            val pass = binding.etPasswordLogin.text.toString().trim()

            if (checkValid(email, pass)) {
                loginToServe(email, pass)
            }
        }

        binding.btnRegisterLogin.setOnClickListener { startActivity<RegisterActivity>() }
    }

    private fun loginToServe(email: String, pass: String) {
        val loginRequest = LoginRequest(
            email = email,
            password = pass
        )
        val body = Gson().toJson(loginRequest)

        loginViewModel.login(body).observe(this) { state ->
            when(state){
                is Resource.Empty -> {
                    dialogLoading.dismiss()
                    showDialogNotification(this, "EMPTY")
                }
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    val errorMessage = state.errorMessage
                    showDialogError(this, errorMessage)
                }
                is Resource.Loading -> {
                    dialogLoading.show()
                }
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    val dialogSuccess = showDialogSuccess(this, "Conratulations you are already login")
                    dialogSuccess.show()

                    val data = state.data
                    HawkStorage.instance(this).setUser(data)

                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            dialogSuccess.dismiss()
                            startActivity<MainActivity>()
                            finish()
                        }, 200)
                }
            }
        }
    }

    private fun checkValid(email: String, pass: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.textInputEmailLogin.error = getString(R.string.field_email)
                binding.textInputEmailLogin.requestFocus()
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.textInputEmailLogin.apply {
                    error = getString(R.string.valid_email)
                    requestFocus()
                }
                false
            }
            pass.isEmpty() -> {
                binding.textInputEmailLogin.error = null
                binding.textInputPasswordLogin.apply {
                    error = getString(R.string.field_password)
                    requestFocus()
                }
                false
            }
            else -> {
                binding.textInputEmailLogin.error = null
                binding.textInputPasswordLogin.error = null
                true
            }
        }
    }
}