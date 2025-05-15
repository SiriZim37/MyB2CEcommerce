package com.siri.myb2cecommerce

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class MyApplication @Inject constructor() : Application() {

}