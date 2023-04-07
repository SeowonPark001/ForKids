package com.example.forkids

import com.google.firebase.database.Exclude

data class Nokidszonelist (
        var objectId: String,
        var cafename: String,
        var date: String,
        var rating: Long,
        var cafeaddress: String,
        //var cafelocation1: String,
        //var cafelocation2: String,
        var timestamp: Long=0
        ) {
    @Exclude
    fun toMap() : HashMap<String, Any>{
        var result: HashMap<String, Any> = HashMap()
        result["objectID"] = objectId
        result["cafe name"] = cafename
        result["date"] = date
        result["rating"] = rating
        result["address"] = cafeaddress
        //result["location (logitude)"] = cafelocation1
        //result["location (latitude)"] = cafelocation2
        result["timestamp"] = timestamp
        return result
    }
}




