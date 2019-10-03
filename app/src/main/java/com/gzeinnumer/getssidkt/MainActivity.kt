package com.gzeinnumer.getssidkt

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.net.wifi.WifiInfo
import android.content.Context.WIFI_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.pm.PackageManager
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.wifi.SupplicantState
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {

    private var TAG:String? ="MainAxtivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private val LOCATION = 1
    override fun onStart() {
        super.onStart()
        //Assume you want to read the SSID when the activity is started
        tryToReadSSID()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == LOCATION) {
            //User allowed the location and you can read it now
            tryToReadSSID()
        }
    }

    private fun tryToReadSSID() {
        //If requested permission isn't Granted yet
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Request permission from user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION
            )
        } else {//Permission already granted
            val wifiManager =
                applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
                val ssid = wifiInfo.ssid//Here you can access your SSID
                println(ssid)
                Log.d(TAG, "onCreate: $ssid")
            }
        }
    }
}
