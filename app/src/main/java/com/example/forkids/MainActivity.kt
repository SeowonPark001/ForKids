package com.example.forkids

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var TAG = "kakaologin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var email = intent.getStringExtra("email")
        textView.text = email + "님 환영합니다 ****"

        // 다른 Activity로 이동, 연결.  Intent를 통해서 이동
        addzone.setOnClickListener {
            var intent = Intent(this@MainActivity, FirebaseMain::class.java) // 두번째 인자에 이동할 액티비티
            startActivity(intent)           // 실질적으로 이동하는 것, 인텐트는 정보를 담음, 한방향으로만 불리어질때 씀
        }
        searchzone.setOnClickListener {
            var intent = Intent(this@MainActivity, GmapMainActivity::class.java)
            startActivity(intent)
        }
        mydiary.setOnClickListener {
            var intent = Intent(this@MainActivity, DiaryMainActivity::class.java)
            startActivity(intent)
        }

        // 로그아웃 동작
        logOut.setOnClickListener {
            Toast.makeText(applicationContext, "정상적으로 로그아웃되었습니다.", Toast.LENGTH_LONG).show()

            // 로그아웃
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                }
            }
        }
    }
}