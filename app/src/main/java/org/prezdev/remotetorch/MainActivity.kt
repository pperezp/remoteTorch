package org.prezdev.remotetorch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.prezdev.remotetorch.util.IP

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ip = IP()
        val lanIp = ip.getLanIp()

        ipTextView.text = lanIp
    }
}
