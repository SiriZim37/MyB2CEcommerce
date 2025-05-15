package com.siri.myb2cecommerce.manager.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.siri.myb2cecommerce.data.dao.TokenDao
import com.siri.myb2cecommerce.data.entitys.TokenEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var tokenDao: TokenDao

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "FCM Token: $token")

        // บันทึกลง Room Database
        CoroutineScope(Dispatchers.IO).launch {
            tokenDao.insertToken(TokenEntity(token = token))
        }
    }
}