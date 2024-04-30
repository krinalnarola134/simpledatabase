package com.example.database

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Showdata : AppCompatActivity() {

    lateinit var re:RecyclerView
    var did=ArrayList<String>()
    var email=ArrayList<String>()
    var pwd=ArrayList<String>()
    var city=ArrayList<String>()
    var gender=ArrayList<String>()
    var hobby=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_showdata)

        re=findViewById(R.id.re)

        var arrr=intent.getStringArrayExtra("arr")

        var q=Volley.newRequestQueue(this)
        var url="https://krinaltest.000webhostapp.com/Database/selectdata.php"
        var r=object:StringRequest(Request.Method.POST,url,{
            did.clear()
            email.clear()
            pwd.clear()
            city.clear()
            gender.clear()
                 var o=JSONObject(it)
                var arr=o.getJSONArray("select")
                for(i in 0 until arr.length())
                {
                    var obj=arr.getJSONObject(i)
                    var didd=obj.getString("did")
                    var emaill=obj.getString("email")
                    var pwdd=obj.getString("pwd")
                    var cityy=obj.getString("city")
                    var genderr=obj.getString("gender")
                    var hobbyy=obj.getString("hobby")

                    did.add(didd)
                    email.add(emaill)
                    pwd.add(pwdd)
                    city.add(cityy)
                    gender.add(genderr)
                    hobby.add(hobbyy)

                }
            var dataa=ViewData(this,did,email,pwd,city,gender,re,hobby,arrr!!)
            re.adapter=dataa
        },{})
        {
            override fun getParams(): MutableMap<String, String> {
                var map=HashMap<String,String>()
                return map
            }
        }
        q.add(r)





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}