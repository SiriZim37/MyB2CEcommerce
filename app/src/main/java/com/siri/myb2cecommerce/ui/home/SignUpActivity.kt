package com.siri.myb2cecommerce.ui.home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.siri.myb2cecommerce.databinding.ActivitySignUpBinding
import com.siri.myb2cecommerce.R
import com.siri.myb2cecommerce.data.dao.UserDao
import com.siri.myb2cecommerce.data.model.User
import com.siri.myb2cecommerce.data.viewmodel.UserViewModel
import com.siri.myb2cecommerce.manager.notification.FirebaseTokenManager
import com.siri.myb2cecommerce.utils.Extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var fullName: EditText
    private lateinit var emailEt: EditText
    private lateinit var passEt: EditText
    private lateinit var CpassEt: EditText



    private val userCollectionRef = Firebase.firestore.collection("Users")
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()



    lateinit var progressDialog: ProgressDialog

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signUpBtn = findViewById<Button>(R.id.signUpBtn_signUpPage)
        fullName = findViewById(R.id.nameEt_signUpPage)
        emailEt = findViewById(R.id.emailEt_signUpPage)
        passEt = findViewById(R.id.PassEt_signUpPage)
        CpassEt = findViewById(R.id.cPassEt_signUpPage)
        val signInTv = findViewById<TextView>(R.id.signInTv_signUpPage)



        progressDialog = ProgressDialog(this)

        textAutoCheck()

        signInTv.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        signUpBtn.setOnClickListener {
            checkInput()

        }
    }

    private fun textAutoCheck() {

        fullName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (fullName.text.isEmpty()){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (fullName.text.length >= 4){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (count >= 4){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }
        })

        emailEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (emailEt.text.isEmpty()){
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (emailEt.text.matches(emailPattern.toRegex())) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (emailEt.text.matches(emailPattern.toRegex())) {
                    emailEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }
        })

        passEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (passEt.text.isEmpty()){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (passEt.text.length > 5){
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
                if (count > 5){
                    passEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }
        })

        CpassEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (CpassEt.text.isEmpty()){
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (CpassEt.text.toString() == passEt.text.toString()){
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (CpassEt.text.toString() == passEt.text.toString()){
                    CpassEt.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }
        })

    }

    private fun checkInput() {
        if (fullName.text.isEmpty()){
            toast("Name can't empty!")
            return
        }
        if (emailEt.text.isEmpty()){
            toast("Email can't empty!")
            return
        }

        if (!emailEt.text.matches(emailPattern.toRegex())) {
            toast("Enter Valid Email")
            return
        }
        if(passEt.text.isEmpty()){
            toast("Password can't empty!")
            return
        }
        if (passEt.text.toString() != CpassEt.text.toString()){
            toast("Password not Match")
            return
        }

        signIn()


    }



    private fun signIn() {

        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account")
        progressDialog.show()

        val emailV: String = emailEt.text.toString()
        val passV: String = passEt.text.toString()
        val fullname : String = fullName.text.toString()


        // Todo : MOCK
        val user = User(fullname,"",getToken(),emailV,"","")

        storeUserData(user)

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()

        /* create a user through Firebase */
        // Todo
//        firebaseAuth.createUserWithEmailAndPassword(emailV,passV)
//
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    progressDialog.setMessage("Save User Data")
//
//
//                    val user = User(fullname,"",firebaseAuth.uid.toString(),emailV,"","")
//
//                    storeUserData(user)
//
//                    val intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    progressDialog.dismiss()
//                    toast("failed to Authenticate !")
//                }
//            }



    }
// Todo : replacec room statt Firebase

//    private fun storeUserData(user: User) = CoroutineScope(Dispatchers.IO).launch {
//        try {
//
//            userCollectionRef.document(firebaseAuth.uid.toString()).set(user).await()
//            withContext(Dispatchers.Main){
//                toast("Data Saved")
//                progressDialog.dismiss()
//            }
//
//        }catch (e:Exception){
//            withContext(Dispatchers.Main){
//                toast(""+ e.message.toString())
//                progressDialog.dismiss()
//            }
//        }
//    }

    // Todo : MOCK
    private fun storeUserData(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            userViewModel.saveUserToDatabase(user)

            withContext(Dispatchers.Main) {
                toast("Data Saved in Room")
                progressDialog.dismiss()
            }

        } catch (e: Exception) {
            // ถ้ามีข้อผิดพลาด
            withContext(Dispatchers.Main) {
                toast("Error: ${e.message}")
                progressDialog.dismiss()
            }
        }
    }





//    private fun sendEmailVerification() {
//        progressDialog.setMessage("Send Verification")
//        firebaseUser?.let {
//            it.sendEmailVerification().addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    progressDialog.dismiss()
//                    val intent = Intent(this, EmailVerifyActivity::class.java)
//                    intent.putExtra("EmailAddress", emailEt.text.toString().trim())
//                    intent.putExtra("loginPassword", passEt.text.toString().trim())
//                    startActivity(intent)
//                    finish()
//                }
//            }
//                .addOnFailureListener {
//                    progressDialog.dismiss()
//                    toast("Verification Link Send failed")
//                }
//        }
//    }


    fun getToken(): String {
       var uid: String = ""
        FirebaseTokenManager.fetchToken(
            onTokenReceived = { token ->
                uid =  token
                Log.d("TAG", "Token received: $token")
            },
            onError = { exception ->
                Log.e("TAG", "Error fetching token", exception)
            }
        )
        return uid
    }
}

