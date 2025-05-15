package com.siri.myb2cecommerce.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.siri.myb2cecommerce.R
import com.siri.myb2cecommerce.data.viewmodel.UserViewModel
import com.siri.myb2cecommerce.databinding.ActivityLoginBinding
import com.siri.myb2cecommerce.databinding.ActivitySignUpBinding
import com.siri.myb2cecommerce.ui.home.dialog.LoadingDialog
import com.siri.myb2cecommerce.utils.Extensions.toast
import com.siri.myb2cecommerce.utils.FirebaseUtils.firebaseAuth
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInBtn: Button
    lateinit var emailEt: EditText
    lateinit var passEt: EditText

    lateinit var loadingDialog: LoadingDialog

    lateinit var emailError: TextView
    lateinit var passwordError: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpTv = findViewById<TextView>(R.id.signUpTv)
        signInBtn = findViewById(R.id.loginBtn)
        emailEt = findViewById(R.id.emailEt)
        passEt = findViewById(R.id.PassEt)
        emailError = findViewById(R.id.emailError)
        passwordError = findViewById(R.id.passwordError)



        textAutoCheck()

        loadingDialog = LoadingDialog(this)

        signUpTv.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            checkInput()


        }


    }

    private fun textAutoCheck() {



        emailEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (emailEt.text.isEmpty()){
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                    emailError.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                    emailError.visibility = View.GONE
                }
            }
        })

        passEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (passEt.text.isEmpty()){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (passEt.text.length > 4){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                passwordError.visibility = View.GONE
                if (count > 4){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)

                }
            }
        })



    }

    private fun checkInput() {

        if (emailEt.text.isEmpty()){
            emailError.visibility = View.VISIBLE
            emailError.text = "Email Can't be Empty"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.text).matches()) {
            emailError.visibility = View.VISIBLE
            emailError.text = "Enter Valid Email"
            return
        }
        if(passEt.text.isEmpty()){
            passwordError.visibility = View.VISIBLE
            passwordError.text = "Password Can't be Empty"
            return
        }

        if ( passEt.text.isNotEmpty() && emailEt.text.isNotEmpty()){
            emailError.visibility = View.GONE
            passwordError.visibility = View.GONE
            signInUser()
        }
    }


    private fun signInUser() {

        loadingDialog.startLoadingDialog()
        signInEmail = emailEt.text.toString().trim()
        signInPassword = passEt.text.toString().trim()

        // Todo
//        firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
//            .addOnCompleteListener { signIn ->
//                if (signIn.isSuccessful) {
//
//                    loadingDialog.dismissDialog()
//                    startActivity(Intent(this, HomeActivity::class.java))
//                    toast("signed in successfully")
//                    finish()
//
//                    /*
//                    if(FirebaseUtils.firebaseUser?.isEmailVerified == true){
//                        startActivity(Intent(this, HomeActivity::class.java))
//                        loadingDialog.dismissDialog()
//                        toast("signed in successfully")
//                        finish()
//                    }
//                    else {
//                        loadingDialog.dismissDialog()
//                        val intent = Intent(this, EmailVerifyActivity::class.java)
//                        intent.putExtra("EmailAddress", emailEt.text.toString().trim())
//                        intent.putExtra("loginPassword", passEt.text.toString().trim())
//                        startActivity(intent)
//                    }
//
//                    */
//
//                } else {
//                    toast("sign in failed")
//                    loadingDialog.dismissDialog()
//                }
//            }



        // Todo : MOCK
        if (signInEmail.isNotEmpty()) {
            userViewModel.getUserByEmail(signInEmail)

            userViewModel.user.observe(this, Observer { user ->
                if (user != null) {
                    toast("Login Successful")
                    loadingDialog.dismissDialog()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    toast("User not found")
                }
            })
        } else {
            loadingDialog.dismissDialog()
            toast("Please enter email")
        }
    }



}