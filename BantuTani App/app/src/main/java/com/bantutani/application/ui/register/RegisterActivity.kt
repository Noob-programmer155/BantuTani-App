package com.bantutani.application.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bantutani.application.R
import com.bantutani.application.customText.EmailTextField
import com.bantutani.application.customText.NameTextField
import com.bantutani.application.customText.PasswordTextField
import com.bantutani.application.customText.UsernameTextField
import com.bantutani.application.databinding.ActivityRegisterBinding
import com.bantutani.application.ui.login.LoginActivity
import com.bantutani.application.ui.main.HomeFactory
import com.bantutani.application.ui.main.HomeViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var customNama : NameTextField
    private lateinit var customEmail : EmailTextField
    private lateinit var customPassword : PasswordTextField
    private lateinit var customUsername : UsernameTextField
    private lateinit var customConfirmPass : PasswordTextField
    private val registerViewModel: RegisterViewModel by viewModels {RegisterFactory(this)}
    private val viewModel: HomeViewModel by viewModels { HomeFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val timer: Long = 2000
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)


        customNama = registerBinding.nama
        customEmail = registerBinding.email
        customPassword = registerBinding.password
        customUsername = registerBinding.username
        customConfirmPass = registerBinding.confirmPassword
        playAnimation()



        registerBinding.back.setOnClickListener {
            onBackPressed()
        }
        registerBinding.daftar.setOnClickListener {
            val name = customNama.text.toString()
            val email = customEmail.text.toString()
            val password = customPassword.text.toString()
            val username = customUsername.text.toString()
            val confirmPass = customConfirmPass.text.toString()
            if (name.isEmpty()) {
                customNama.error = resources.getString(R.string.text_empty)
            }
            if (email.isEmpty()) {
                customEmail.error = resources.getString(R.string.text_empty)
            }
            if (password.isEmpty()) {
                customPassword.error = resources.getString(R.string.text_empty)
            }
            if (username.isEmpty()) {
                customUsername.error = resources.getString(R.string.text_empty)
            }
            if (confirmPass.isEmpty()) {
                customConfirmPass.error = resources.getString(R.string.text_empty)
            }
            if (password != confirmPass) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.text_password_not_match),
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() && confirmPass.isNotEmpty() && password == confirmPass) {
                registerBinding.daftar.setOnClickListener {
                    registerBinding.daftar.isEnabled = true
                    registerViewModel.registerUser(name, username, password, email)
                    with(registerViewModel) {
                        getRegisterData().observe(this@RegisterActivity) {
                            info(it)
                        }
                        isLoading.observe(this@RegisterActivity) { load ->
                            showLoading(load)
                        }
                    }
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
    }

    private fun playAnimation() {
        val nama = ObjectAnimator.ofFloat(registerBinding.nama, View.ALPHA, 1f).setDuration(500)
        val username = ObjectAnimator.ofFloat(registerBinding.username, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(registerBinding.email, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(registerBinding.password, View.ALPHA, 1f).setDuration(500)
        val confirmPassword = ObjectAnimator.ofFloat(registerBinding.confirmPassword, View.ALPHA, 1f).setDuration(500)
        val daftar = ObjectAnimator.ofFloat(registerBinding.daftar, View.ALPHA, 1f).setDuration(500)
        val kembali = ObjectAnimator.ofFloat(registerBinding.back, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(daftar,kembali)
        }

        AnimatorSet().apply {
            playSequentially(nama, username, email, password, confirmPassword, together)
            start()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            registerBinding.progressBar.visibility = View.VISIBLE
        } else {
            registerBinding.progressBar.visibility = View.GONE
        }
    }
    private fun info(msg : String){
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}