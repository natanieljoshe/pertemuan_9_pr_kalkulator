package com.example.kalkulator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var etAngka1: EditText
    private lateinit var etAngka2: EditText
    private lateinit var btnTambah: Button
    private lateinit var btnKurang: Button
    private lateinit var btnKali: Button
    private lateinit var btnBagi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etAngka1 = findViewById(R.id.etAngka1)
        etAngka2 = findViewById(R.id.etAngka2)
        btnTambah = findViewById(R.id.btnTambah)
        btnKurang = findViewById(R.id.btnKurang)
        btnKali = findViewById(R.id.btnKali)
        btnBagi = findViewById(R.id.btnBagi)

        btnTambah.setOnClickListener { hitung('+') }
        btnKurang.setOnClickListener { hitung('-') }
        btnKali.setOnClickListener { hitung('*') }
        btnBagi.setOnClickListener { hitung('/') }
    }

    private fun hitung(operasi: Char) {
        val str1 = etAngka1.text.toString().trim()
        val str2 = etAngka2.text.toString().trim()

        if (str1.isEmpty() || str2.isEmpty()) {
            Toast.makeText(this, "Harap isi kedua angka terlebih dahulu!", Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = str1.toDoubleOrNull()
        val num2 = str2.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            Toast.makeText(this, "Angka tidak valid!", Toast.LENGTH_SHORT).show()
            return
        }

        val result: Double
        val expression: String

        val formattedNum1 = formatDouble(num1)
        val formattedNum2 = formatDouble(num2)

        when (operasi) {
            '+' -> {
                result = num1 + num2
                expression = "$formattedNum1 + $formattedNum2 = ${formatDouble(result)}"
                val fragment = supportFragmentManager.findFragmentById(R.id.container_tambah) as? TambahFragment
                fragment?.addHistory(expression, result)
            }
            '-' -> {
                result = num1 - num2
                expression = "$formattedNum1 - $formattedNum2 = ${formatDouble(result)}"
                val fragment = supportFragmentManager.findFragmentById(R.id.container_kurang) as? KurangFragment
                fragment?.addHistory(expression, result)
            }
            '*' -> {
                result = num1 * num2
                expression = "$formattedNum1 * $formattedNum2 = ${formatDouble(result)}"
                val fragment = supportFragmentManager.findFragmentById(R.id.container_kali) as? KaliFragment
                fragment?.addHistory(expression, result)
            }
            '/' -> {
                if (num2 == 0.0) {
                    Toast.makeText(this, "Tidak dapat membagi dengan nol!", Toast.LENGTH_SHORT).show()
                    return
                }
                result = num1 / num2
                expression = "$formattedNum1 / $formattedNum2 = ${formatDouble(result)}"
                val fragment = supportFragmentManager.findFragmentById(R.id.container_bagi) as? BagiFragment
                fragment?.addHistory(expression, result)
            }
        }
    }

    private fun formatDouble(valNum: Double): String {
        return if (valNum % 1.0 == 0.0) {
            valNum.toLong().toString()
        } else {
            val df = DecimalFormat("#.#####")
            df.format(valNum)
        }
    }
}
