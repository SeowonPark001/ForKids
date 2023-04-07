package com.example.forkids

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.diary_main.*
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class DiaryMainActivity : AppCompatActivity() {
    lateinit var dp : DatePicker
    lateinit var edtDiary : EditText
    lateinit var btnWrite : Button

    lateinit var fileName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diary_main)

        title = "나만의 여행 일지"

        dp = findViewById<DatePicker>(R.id.datePicker1)
        edtDiary = findViewById<EditText>(R.id.edtDiary)
        btnWrite = findViewById<Button>(R.id.btnWrite)

        val cal = Calendar.getInstance() // 현재 시간 확보
        var cYear = cal.get(Calendar.YEAR)
        var cMonth = cal.get(Calendar.MONTH) // 1~12월(x) 0~11월!! => +1 더해줘야!
        var cDay = cal.get(Calendar.DAY_OF_MONTH)

        // date picker을 오늘 날짜로 초기화
        fileName = (Integer.toString(cYear) + "_"
                + Integer.toString(cMonth + 1) +  "_"
                + Integer.toString(cDay) + ".txt")
        var str = readDiary(fileName) // 일기 있는지 처음부터 체크
        edtDiary.setText(str)

        // date picker 날짜마다 다르게
        dp.init(cYear, cMonth, cDay) { view, year, monthOfYear, dayOfMonth ->
            fileName = (Integer.toString(year) + "_"
                    + Integer.toString(monthOfYear + 1) +  "_"
                    + Integer.toString(dayOfMonth) + ".txt")
            var str = readDiary(fileName)
            edtDiary.setText(str)
            //btnWrite.isEnabled = true
        }

        btnWrite.setOnClickListener {
            // 파일 쓰기
            var outFs = openFileOutput(fileName, Context.MODE_PRIVATE)
            var str = edtDiary.text.toString()
            outFs.write(str.toByteArray())
            outFs.close()
            Toast.makeText(applicationContext, "$fileName 이 저장됨", Toast.LENGTH_SHORT).show()
        }

        // Home 메뉴로 돌아가기
        diary2Home.setOnClickListener {
            var intent = Intent(this@DiaryMainActivity, MainActivity::class.java) // 메인 홈으로!
            startActivity(intent)
        }
    }
    fun readDiary(fname:String) : String? { // 어떨 땐 null값 반환 => ? 표시
        var diaryStr : String? = null
        var inFs : FileInputStream
        try{
            inFs = openFileInput(fname)
            var txt = ByteArray(500)
            inFs.read(txt)
            inFs.close()

            diaryStr = txt.toString(Charsets.UTF_8).trim() //trim: 앞뒤 공백
            btnWrite.text = "수정하기"
        }
        catch (e: IOException) {
            edtDiary.hint = "일기 없음"
            btnWrite.text = "새로 저장"
        }
        return diaryStr
    }
}