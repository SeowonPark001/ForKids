package com.example.forkids

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.gmap_main.*
import org.w3c.dom.Text

class GmapMainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var videoMark : GroundOverlayOptions
    lateinit var tv : TextView
    lateinit var btn : Button
    lateinit var homebtn : Button

    var locationClient : FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gmap_main)

        tv = findViewById<TextView>(R.id.mypos)
        btn = findViewById<Button>(R.id.btnPos)
        homebtn = findViewById<Button>(R.id.gmap2Home)

        btn.setOnClickListener {
            requestLocation()
        }
        // permmission 처리
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted { permissions ->
                    Log.d("Main", "허용된 권한 갯수 : ${permissions.size}")
                }
                .onDenied { permissions ->
                    Log.d("Main", "허용된 권한 갯수 : ${permissions.size}")
                }
                .start()


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Home 메뉴로 돌아가기
        gmap2Home.setOnClickListener {
            var intent = Intent(this@GmapMainActivity, MainActivity::class.java) // 메인 홈으로!
            startActivity(intent)
        }
    }
    private fun requestLocation() { //내 위치 찾기 버튼 클릭리스너
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        try{
            locationClient?.lastLocation?.addOnSuccessListener { location ->
                if(location == null){
                    tv.setText("!! 최근 위치 확인 실패 !!")
                }
                else {
                    tv.setText("최근 위치 확인 성공 : ${location.latitude}, ${location.longitude}")
                }
            }
                    ?.addOnFailureListener{
                        tv.setText("최근 위치 확인 시 에러 : ${it.message}")
                        it.printStackTrace()
                    }
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60 * 1000
            }
            val locationCallback = object: LocationCallback(){
                override fun onLocationResult(p0: LocationResult?) {
                    p0?.let{
                        for((i, location) in it.locations.withIndex()){
                            tv.setText("내 위치: ${location.latitude}, ${location.longitude}")
                        }
                    }
                }
            }
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
        catch (e: SecurityException){
            e.printStackTrace()
        }
    }


    override fun onMapReady(p0: GoogleMap) {
        mMap =p0
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE // 위성 지도 타입
        mMap.uiSettings.isZoomControlsEnabled = true // 지도 확대/축소 컨트롤러
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.567, 126.897240), 15f))
        mMap.setOnMapClickListener { point ->
            videoMark = GroundOverlayOptions().image(
                    BitmapDescriptorFactory.fromResource(R.drawable.video_icon))
                    .position(point, 100f, 100f)
            mMap.addGroundOverlay(videoMark)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menu!!.add(0, 1, 0, "위성 사진")
        menu.add(0, 2, 0, "일반 사진")
        var smenu = menu.addSubMenu("노키즈존 찾기 >> ")
        smenu.add(0, 4, 0, "카페록록 하도리본점")
        smenu.add(0, 5, 0, "아줄레주")
        smenu.add(0, 6, 0, "카페 더 콘테나")
        smenu.add(0, 7, 0, "인그리드")
        smenu.add(0, 8, 0, "클랭블루")
        smenu.add(0, 9, 0, "골목카페옥수")
        smenu.add(0, 10, 0, "니모메 빈티지 라운지")
        smenu.add(0, 11, 0, "카페 동백")

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            2 -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            4 -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(33.52903174067461, 126.87892081092457), 15f))
                mMap.addMarker(MarkerOptions().position(LatLng(33.52903174067461, 126.87892081092457))
                        .title("카페록록 하도리본점"))
                return true
            }
            5 -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(33.36762188557234, 126.83943396831202), 15f))
                mMap.addMarker(MarkerOptions().position(LatLng(33.36762188557234, 126.83943396831202))
                        .title("아줄레주"))
                return true
            }
            6 -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(33.4983049032936, 126.67932173768253), 15f))
                mMap.addMarker(MarkerOptions().position(LatLng(33.4983049032936, 126.67932173768253))
                        .title("카페 더 콘테나"))
                return true
            }
            7 -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(33.50298250332427, 126.45687420699593), 15f))
                mMap.addMarker(MarkerOptions().position(LatLng(33.50298250332427, 126.45687420699593))
                        .title("인그리드"))
                return true
            }
            8 -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(33.34567655017198, 126.17752516089168), 15f))
                mMap.addMarker(MarkerOptions().position(LatLng(33.34567655017198, 126.17752516089168))
                        .title("클랭블루"))
                return true
            }
            9 -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(33.44055231795819, 126.38039271437817), 15f))
                mMap.addMarker(MarkerOptions().position(LatLng(33.44055231795819, 126.38039271437817))
                        .title("골목카페옥수"))
                return true
            }
            10 -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(33.49439985112121, 126.43116770699193), 15f))
                mMap.addMarker(MarkerOptions().position(LatLng(33.49439985112121, 126.43116770699193))
                        .title("니모메 빈티지 라운지"))
                return true
            }
            11 -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(33.509636012472775, 126.71447016889954), 15f))
                mMap.addMarker(MarkerOptions().position(LatLng(33.509636012472775, 126.71447016889954))
                        .title("카페 동백"))
                return true
            }

        }
        return false
    }
}
