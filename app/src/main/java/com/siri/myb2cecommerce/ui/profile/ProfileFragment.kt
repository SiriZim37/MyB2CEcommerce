package com.siri.myb2cecommerce.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.UploadTask
import com.siri.myb2cecommerce.R
import com.siri.myb2cecommerce.data.viewmodel.CardViewModel
import com.siri.myb2cecommerce.data.viewmodel.CartViewModel
import com.siri.myb2cecommerce.data.viewmodel.UserViewModel
import com.siri.myb2cecommerce.databinding.FragmentProfileBinding
import com.siri.myb2cecommerce.manager.notification.FirebaseTokenManager
import com.siri.myb2cecommerce.ui.payment.PaymentMethodActivity
import com.siri.myb2cecommerce.ui.setting.SettingsActivity
import com.siri.myb2cecommerce.ui.shipping.ShipingAddressActivity
import com.siri.myb2cecommerce.utils.FirebaseUtils.storageReference
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()
    private val cardViewModel: CardViewModel by viewModels()
    lateinit var animationView: LottieAnimationView
    lateinit var profileImage_profileFrag: CircleImageView
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    lateinit var uploadImage_profileFrag: Button
    lateinit var profileName_profileFrag: TextView
    lateinit var profileEmail_profileFrag: TextView

    private val userCollectionRef = Firebase.firestore.collection("Users")
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var cards: Int = 0
    lateinit var linearLayout2: LinearLayout
    lateinit var linearLayout3: LinearLayout
    lateinit var linearLayout4: LinearLayout


//    private lateinit var binding: FragmentProfileBinding  // เปลี่ยนเป็นชื่อ binding ที่ตรงกับชื่อ layout XML


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        profileImage_profileFrag = view.findViewById(R.id.profileImage_profileFrag)
        val settingCd_profileFrag = view.findViewById<CardView>(R.id.settingCd_profileFrag)
        uploadImage_profileFrag = view.findViewById(R.id.uploadImage_profileFrag)
        profileName_profileFrag = view.findViewById(R.id.profileName_profileFrag)
        profileEmail_profileFrag = view.findViewById(R.id.profileEmail_profileFrag)
        animationView = view.findViewById(R.id.animationView)
        linearLayout2 = view.findViewById(R.id.linearLayout2)
        linearLayout3 = view.findViewById(R.id.linearLayout3)
        linearLayout4 = view.findViewById(R.id.linearLayout4)
        val shippingAddressCard_ProfilePage = view.findViewById<CardView>(R.id.shippingAddressCard_ProfilePage)
        val paymentMethod_ProfilePage = view.findViewById<CardView>(R.id.paymentMethod_ProfilePage)
        val cardsNumber_profileFrag: TextView = view.findViewById(R.id.cardsNumber_profileFrag)

        cardViewModel.allCards.observe(viewLifecycleOwner, Observer {
            cards = it.size
        })

        if(cards == 0){
            cardsNumber_profileFrag.text = "You Have no Cards."
        }
        else{
            cardsNumber_profileFrag.text = "You Have "+ cards.toString() + " Cards."
        }

        shippingAddressCard_ProfilePage.setOnClickListener {
            startActivity(Intent(context, ShipingAddressActivity::class.java))
        }

        paymentMethod_ProfilePage.setOnClickListener {
            startActivity(Intent(context, PaymentMethodActivity::class.java))
        }

        hideLayout()



        uploadImage_profileFrag.visibility = View.GONE


        // Call data from Room database
        getUserData()

        uploadImage_profileFrag.setOnClickListener {
            uploadImage()
        }

        settingCd_profileFrag.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }



        profileImage_profileFrag.setOnClickListener {

            val popupMenu: PopupMenu = PopupMenu(context,profileImage_profileFrag)

            popupMenu.menuInflater.inflate(R.menu.profile_photo_storage,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.galleryMenu ->
                        launchGallery()
                    R.id.cameraMenu ->
                        uploadImage()

                }
                true
            })
            popupMenu.show()

        }

        return view
    }

    private fun hideLayout(){
        animationView.playAnimation()
        animationView.loop(true)
        linearLayout2.visibility = View.GONE
        linearLayout3.visibility = View.GONE
        linearLayout4.visibility = View.GONE
        animationView.visibility = View.VISIBLE
    }
    private fun showLayout(){
        animationView.pauseAnimation()
        animationView.visibility = View.GONE
        linearLayout2.visibility = View.VISIBLE
        linearLayout3.visibility = View.VISIBLE
        linearLayout4.visibility = View.VISIBLE
    }

    private fun getUserData() = CoroutineScope(Dispatchers.IO).launch {
        // Todo : MOCK
            val userImage:String = "Anna"
            val userName:String = "Anna Test"
            val userEmail:String = "Anna@mail.com"

            withContext(Dispatchers.Main){

                profileName_profileFrag.text = userName
                profileEmail_profileFrag.text = userEmail
                Glide.with(this@ProfileFragment)
                    .load(userImage)
                    .placeholder(R.drawable.ic_profile)
                    .into(profileImage_profileFrag)

                showLayout()
            }

//        เรียกข้อมูลจาก ViewModel ใน coroutine
//        lifecycleScope.launch {
//            try {
//                // รอให้ข้อมูลจาก ViewModel ที่ได้ถูกโหลดแล้ว
//                userViewModel.getUserData(getToken())
//
//                // ใช้ LiveData observer เมื่อข้อมูลอัปเดต
//                userViewModel.user.observe(viewLifecycleOwner, Observer { user ->
//                    user?.let {
//                        // อัปเดต UI เมื่อข้อมูลพร้อม
//                        profileName_profileFrag.text = it.userName
//                        profileEmail_profileFrag.text = it.userEmail
//                        Glide.with(this@ProfileFragment)
//                            .load(it.userImage)
//                            .placeholder(R.drawable.ic_profile)
//                            .into(profileImage_profileFrag)
//                    }
//                })
//            } catch (e: Exception) {
//                // Handle exception
//            }
//        }
    }


    //  Todo : Do it later with Firebase > Cause  change to use Room

//    private fun getUserData() = CoroutineScope(Dispatchers.IO).launch {
//        try {
//
//            val querySnapshot = userCollectionRef
//                .document(firebaseAuth.uid.toString())
//                .get().await()
//
//            val userImage:String = querySnapshot.data?.get("userImage").toString()
//            val userName:String = querySnapshot.data?.get("userName").toString()
//            val userEmail:String = querySnapshot.data?.get("userEmail").toString()
//

//            // Todo : MOCK
//            val userImage:String = "Me"
//            val userName:String = "Mustermann"
//            val userEmail:String = "Me.Mustermann@mail.com"
//
//            withContext(Dispatchers.Main){
//
//                profileName_profileFrag.text = userName
//                profileEmail_profileFrag.text = userEmail
//                Glide.with(this@ProfileFragment)
//                    .load(userImage)
//                    .placeholder(R.drawable.ic_profile)
//                    .into(profileImage_profileFrag)
//
//                showLayout()
//            }
//
//
//        }catch (e:Exception){
//
//        }
//    }



    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage(){
        if(filePath != null){
            val ref = storageReference.child("profile_Image/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())

                    // show save...


                } else {
                    // Handle failures
                }
            }?.addOnFailureListener{

            }
        }else{

            Toast.makeText(context, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }


        //Todo :  MOCK
        //Toast.makeText(context, "Please Upload an Image", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, filePath)
                profileImage_profileFrag.setImageBitmap(bitmap)
                uploadImage_profileFrag.visibility = View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun addUploadRecordToDb(uri: String) = CoroutineScope(Dispatchers.IO).launch {

        try {

            userCollectionRef.document(firebaseAuth.uid.toString())
                .update("userImage" , uri ).await()

            withContext(Dispatchers.Main){
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context, ""+e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
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