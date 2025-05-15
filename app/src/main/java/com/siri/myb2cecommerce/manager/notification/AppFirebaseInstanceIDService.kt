package com.siri.myb2cecommerce.manager.notification
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseTokenManager {

    fun fetchToken(onTokenReceived: (String) -> Unit, onError: (Exception?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FirebaseTokenManager", "FCM Token: $token")
                    onTokenReceived(token)
                } else {
                    Log.w("FirebaseTokenManager", "Fetching FCM token failed", task.exception)
                    onError(task.exception)
                }
            }
    }
}