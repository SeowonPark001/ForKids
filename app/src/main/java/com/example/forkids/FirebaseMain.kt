package com.example.forkids

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fb_main.*
import java.util.*
import kotlin.collections.HashMap

class FirebaseMain : AppCompatActivity() {
    private lateinit var adapter:NokidszoneAdapter
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fb_main)

        //recyclerView.layoutManager = LinearLayoutManager(this)
        val layoutManager = LinearLayoutManager(this)

        // 저장을 역순으로 => 최근 글부터 보기 가능
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recyclerView.layoutManager =layoutManager

        //recyclerView.adapter = MovieCommentAdapter()
        adapter = NokidszoneAdapter()
        //adapter.items.add(MovieComment("0", "카페 3", "1분전", 5, "남원동", 10, 23))
        //adapter.items.add(MovieComment("1", "카페 2", "4분전", 4, "서귀포시", 3, 23))
        //adapter.items.add(MovieComment("2", "카페 1", "10분전", 5, "제주시", 13,12))

        recyclerView.adapter =adapter

        // 파이어베이스 가져오기
        databaseRef = FirebaseDatabase.getInstance().reference

        saveButton.setOnClickListener {
            val storename = inputstore.text.toString()
            val rating = ratingBar.rating.toLong()
            val storeaddress = inputaddress.text.toString()
            //val longitude = inputlong.text.toString()
            //val latitude = inputlat.text.toString()

            //saveStore(storename, rating, storeaddress, longitude, latitude)
            saveStore(storename, rating, storeaddress)
        }

        databaseRef.orderByKey().limitToFirst(10).addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                loadStoreList(snapshot)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("test", "loadItem:onCancelled : ${error.toException()}")
            }
        })

        // Home 메뉴로 돌아가기
        lists2Home.setOnClickListener {
            var intent = Intent(this@FirebaseMain, MainActivity::class.java) // 메인 홈으로!
            startActivity(intent)
        }
    }

    fun loadStoreList(dataSnapshot: DataSnapshot) {
        val collectionIterator = dataSnapshot!!.children.iterator()
        if(collectionIterator.hasNext()) {
            adapter.items.clear()
            val comments = collectionIterator.next()
            val itemsIterator = comments.children.iterator()
            while (itemsIterator.hasNext()) {
                val currentItem = itemsIterator.next()
                val map = currentItem.value as HashMap<String, Any>
                val objectId = map["objectID"].toString()
                val names = map["cafe name"].toString()
                var commentTime = Date(map["timestamp"] as Long).toString()
                val rating = map["rating"] as Long
                val address1 = map["address"] as String
                //val longitude1 = map["location (longitude)"] as String
                //val latitude1 = map["location (latitude)"] as String


                //adapter.items.add(Nokidszonelist(objectId, names, commentTime, rating, address1, longitude1, latitude1))
                adapter.items.add(Nokidszonelist(objectId, names, commentTime, rating, address1))
            }
            adapter.notifyDataSetChanged()
        }
    }


    //fun saveStore(storename:String, rating:Long, address:String, longitude:String, latitude:String){
    fun saveStore(storename:String, rating:Long, address:String){
        var key : String? = databaseRef.child("lists").push().getKey()
        //val list1 = Nokidszonelist(key!!, storename, "", rating, address, longitude, latitude)
        val list1 = Nokidszonelist(key!!, storename, "", rating, address)
        val listValues : HashMap<String, Any> = list1.toMap()
        listValues["timestamp"] = ServerValue.TIMESTAMP
        val childUpdates: MutableMap<String, Any> = HashMap()
        childUpdates["/lists/$key"] = listValues
        databaseRef.updateChildren(childUpdates)
    }
}