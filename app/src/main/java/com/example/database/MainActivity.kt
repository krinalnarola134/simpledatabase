package com.example.database

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var email: TextInputEditText
    lateinit var pwd: TextInputEditText
    lateinit var city: AutoCompleteTextView
    lateinit var rgb: RadioGroup
    lateinit var rrb: RadioButton
    lateinit var d: CheckBox
    lateinit var r: CheckBox
    lateinit var btn: AppCompatButton
    var chk:String=""
    var c=0
    var arr= arrayOf("surat","rajkot","vapi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        email=findViewById(R.id.email)
        pwd=findViewById(R.id.pwd)
        city=findViewById(R.id.city)
        rgb=findViewById(R.id.rgb)
        d=findViewById(R.id.d)
        r=findViewById(R.id.r)
        btn=findViewById(R.id.btn)


        var add=ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,arr)
        city.setAdapter(add)

        city.setHint("select")

        btn.setOnClickListener {
            chk=""
            if(email.text.toString().isNotEmpty() && pwd.text.toString().isNotEmpty() && !city.text.toString().equals("selected") && rgb.checkedRadioButtonId!=-1 &&(d.isChecked || r.isChecked))
            {

                var id=rgb.checkedRadioButtonId
                rrb=findViewById(id)

                if(d.isChecked)
                {
                    chk+=d.text.toString()
                }
                if(r.isChecked)
                {
                    chk+=r.text.toString()
                }

                var q= Volley.newRequestQueue(this)
                var url="https://krinaltest.000webhostapp.com/Database/insertdata.php"
                var r=object: StringRequest(Request.Method.POST,url,{
                    var i= Intent(this@MainActivity,Showdata::class.java)
                    i.putExtra("arr",arr)
                    startActivity(i)
                },{
                    Toast.makeText(this,"not",Toast.LENGTH_SHORT).show()


                })
                {
                    override fun getParams(): MutableMap<String, String>? {
                        var map=HashMap<String,String>()
                        map.put("email",email.text.toString())
                        map.put("pwd",pwd.text.toString())
                        map.put("city",city.text.toString())
                        map.put("gender",rrb.text.toString())
                        map.put("hobby",chk.toString())
                        return map
                    }
                }
                q.add(r)
                Toast.makeText(this,"gender"+rrb.text.toString(),Toast.LENGTH_SHORT).show()

            }
            else
            {
                if(email.text.toString().isEmpty())
                {
                    email.setError("email")
                }
                if(pwd.text.toString().isEmpty())
                {
                    pwd.setError("pwd")
                }
                if(city.text.toString().equals("select"))
                {
                    city.setError("city")
                }
                if(rgb.checkedRadioButtonId==-1)
                {
                    Toast.makeText(this,"Select Gender",Toast.LENGTH_SHORT).show()
                }
                if(!d.isChecked && !r.isChecked)
                {
                    Toast.makeText(this,"Select Hobby",Toast.LENGTH_SHORT).show()
                }
            }


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}