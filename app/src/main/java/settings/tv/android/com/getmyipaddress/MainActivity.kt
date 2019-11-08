package settings.tv.android.com.getmyipaddress

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.widget.Toast
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {

     lateinit var ipadress:TextView
    lateinit var getipbutton:TextView
    var mHandler = Handler()
    var isRunning = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ipadress =  findViewById(R.id.ipadress)
        getipbutton = findViewById(R.id.getipadress)
        Thread(Runnable {

            while (isRunning) {
                try {
                    Thread.sleep(1000)
                    mHandler.post {

                        displayData()
                    }
                } catch (e: Exception) {

                }

            }
        }).start()

    }


    fun getmyipadress (view: View) {

        ipadress.setText(getIpAddress2())

    }



    fun getIpAddress2(): String? {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()


                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }

        return null
    }

    private fun displayData() {
        val cn = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nf = cn.activeNetworkInfo
        if (nf != null && nf.isConnected == true) {
         //   Toast.makeText(this, "Network Available", Toast.LENGTH_SHORT).show()
            ipadress.visibility = View.VISIBLE
            getipbutton.visibility = View.VISIBLE

        } else {
            Toast.makeText(this, "Network Not Available, check your internet connection", Toast.LENGTH_SHORT).show()
            ipadress.visibility = View.INVISIBLE
            getipbutton.visibility = View.INVISIBLE

        }
    }


}
