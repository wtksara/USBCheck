package com.example.witeksara

import android.content.Context
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var sprawdzUrzadzenie: Button
    lateinit var wynik: TextView
    var state : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val p = getPreferences(Context.MODE_PRIVATE)
        var tekst = p.getString("text", "Nie zostało podłaczone urządzenie USB!").toString()

        sprawdzUrzadzenie = findViewById(R.id.przycisk)
        wynik = findViewById(R.id.tekst)
        wynik.text = tekst

        sprawdzUrzadzenie.setOnClickListener {
            tekst = "Podłaczone zostało urządzenie USB:\n\n"

            val dev = applicationContext.getSystemService(Context.USB_SERVICE) as UsbManager
            val urzadzenieUSB = dev.deviceList
            for (usb in urzadzenieUSB) {
                tekst = tekst + usb.value.productName + "\n VID: "
                tekst = tekst + "0x" + java.lang.Integer.toHexString(usb.value.vendorId) + "\n PID: "
                tekst = tekst + "0x" + java.lang.Integer.toHexString(usb.value.productId)+ "\n\n"
                state = true
            }

            if(state){
                wynik.text = tekst
            } else {
                tekst = "Nie zostało podłaczone urządzenie USB!"
                wynik.text = tekst
            }
            state = false
        }
    }
}