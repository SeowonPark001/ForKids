package com.example.forkids

import android.app.Application
import com.kakao.auth.KakaoSDK
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 카카오 sdk 초기화
        KakaoSdk.init(this, "71a501cb3195e94ff10ff42fb6115490")
    }
}