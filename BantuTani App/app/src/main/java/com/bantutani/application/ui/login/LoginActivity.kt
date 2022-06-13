package com.bantutani.application.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bantutani.application.customText.PasswordTextField
import com.bantutani.application.customText.UsernameTextField
import com.bantutani.application.databinding.ActivityLoginBinding
import com.bantutani.application.ui.main.MainActivity
import com.bantutani.application.ui.register.RegisterActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var customUsername: UsernameTextField
    private lateinit var customPassword: PasswordTextField
    private val vm: LoginViewModel by viewModels() { LoginFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        playAnimation()
        customUsername = loginBinding.username
        customPassword = loginBinding.password
        customPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButtonEnabled()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        loginBinding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        loginBinding.login.setOnClickListener {
            if (customUsername.text.toString().isNotEmpty() && customPassword.text.toString().isNotEmpty()) {
                vm.loginUser(customUsername.text.toString(), customPassword.text.toString())
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            }
        }
        vm.getSession()
        with(vm) {
            fetchData().observe(this@LoginActivity, {
                showLoading(true)
                if (it.token != "") {
                    vm.saveSession(it.id, it.fullName, it.username, it.email, it.image, it.status, it.token)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    showLoading(false)
                }
            })
            fetchSession().observe(this@LoginActivity){
                if ( it.token != ""){
                    showLoading(false)
                    Toast.makeText(this@LoginActivity, "Selamat Datang Kembali ${it.fullName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }
        }
    }

    private fun setButtonEnabled() {
        val resultPassword = customPassword.text
        val resultUsername = customUsername.text
        loginBinding.login.isEnabled =
            resultPassword != null && resultPassword.toString().isNotEmpty() &&
                    resultUsername != null && resultUsername.toString().isNotEmpty()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loginBinding.progressBar.visibility = View.VISIBLE
        } else {
            loginBinding.progressBar.visibility = View.GONE
        }
    }

    private fun playAnimation() {
        val username =
            ObjectAnimator.ofFloat(loginBinding.username, View.ALPHA, 1f).setDuration(500)
        val password =
            ObjectAnimator.ofFloat(loginBinding.password, View.ALPHA, 1f).setDuration(500)
        val forget =
            ObjectAnimator.ofFloat(loginBinding.forgetPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(loginBinding.login, View.ALPHA, 1f).setDuration(500)
        val register =
            ObjectAnimator.ofFloat(loginBinding.register, View.ALPHA, 1f).setDuration(500)
        val atau = ObjectAnimator.ofFloat(loginBinding.atau, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login, register, atau)
        }

        AnimatorSet().apply {
            playSequentially(username, password, forget, together)
            start()
        }
    }
}