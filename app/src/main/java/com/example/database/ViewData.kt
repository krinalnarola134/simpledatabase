package com.example.database

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class ViewData(
    var showdata: Showdata,
    var did: ArrayList<String>,
    var email: ArrayList<String>,
    var pwd: ArrayList<String>,
    var city: ArrayList<String>,
    var gender: ArrayList<String>,
    var re: RecyclerView,
    var hobby: ArrayList<String>,
    var arrr: Array<String>
) : RecyclerView.Adapter<ViewData.Baseclass>() {
    class Baseclass(itemView: View) :ViewHolder(itemView) {
        lateinit var p:TextView
        lateinit var e:TextView
        lateinit var c:TextView
        lateinit var g:TextView
        lateinit var h:TextView
        lateinit var delete: Button
        lateinit var edit:Button

        init {
            p=itemView.findViewById(R.id.p)
            e=itemView.findViewById(R.id.e)
            c=itemView.findViewById(R.id.c)
            g=itemView.findViewById(R.id.g)
            h=itemView.findViewById(R.id.h)
            delete=itemView.findViewById(R.id.delete)
            edit=itemView.findViewById(R.id.edit)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Baseclass {
        var vv=LayoutInflater.from(showdata).inflate(R.layout.abc,parent,false)
        return Baseclass(vv)
    }

    override fun getItemCount(): Int {
        return did.size
    }

    override fun onBindViewHolder(holder: Baseclass, position: Int) {
        holder.e.setText(email[position])
        holder.p.setText(pwd[position])
        holder.c.setText(city[position])
        holder.g.setText(gender[position])
        holder.h.setText(hobby[position])

        holder.delete.setOnClickListener {
            var q=Volley.newRequestQueue(showdata)
            var url="https://krinaltest.000webhostapp.com/Database/deletedata.php"
            var r=object:StringRequest(Request.Method.POST,url,{
                var qq=Volley.newRequestQueue(showdata)
                var urll="https://krinaltest.000webhostapp.com/Database/selectdata.php"
                var rr=object:StringRequest(Request.Method.POST,urll,{
                    did.clear()
                    email.clear()
                    pwd.clear()
                    city.clear()
                    gender.clear()
                    var o= JSONObject(it)
                    var arr=o.getJSONArray("select")
                    for(i in 0 until arr.length())
                    {
                        var obj=arr.getJSONObject(i)
                        var didd=obj.getString("did")
                        var emaill=obj.getString("email")
                        var pwdd=obj.getString("pwd")
                        var cityy=obj.getString("city")
                        var genderr=obj.getString("gender")

                        did.add(didd)
                        email.add(emaill)
                        pwd.add(pwdd)
                        city.add(cityy)
                        gender.add(genderr)

                    }
                    var dataa=ViewData(showdata, did, email, pwd, city, gender, re, hobby, arrr)
                    re.adapter=dataa
                },{})
                {
                    override fun getParams(): MutableMap<String, String> {
                        var map=HashMap<String,String>()
                        return map
                    }
                }
                qq.add(rr)
            },{})
            {
                override fun getParams(): MutableMap<String, String>? {
                    var map=HashMap<String,String>()
                    map.put("did",did[position])
                    return map
                }
            }
            q.add(r)
        }

        holder.edit.setOnClickListener {
            var d=Dialog(showdata)
            d.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            d.window?.setContentView(R.layout.opq)
            var emaill: TextInputEditText
            var pwdd: TextInputEditText
            var cityy: AutoCompleteTextView
            var rb: String
            var f:RadioButton
            var m:RadioButton
            var rr: CheckBox
            var dd: CheckBox
            var btnc: AppCompatButton
            var btne: AppCompatButton



            emaill=d.findViewById(R.id.email)
            pwdd=d.findViewById(R.id.pwd)
            cityy=d.findViewById(R.id.city)
            rr=d.findViewById(R.id.r)
            f=d.findViewById(R.id.f)
            m=d.findViewById(R.id.m)
            dd=d.findViewById(R.id.dd)
            btnc=d.findViewById(R.id.btnc)
            btne=d.findViewById(R.id.btne)

            var adapter=ArrayAdapter(showdata,android.R.layout.simple_expandable_list_item_1,arrr)
            cityy.setAdapter(adapter)

            emaill.setText(email[position])
            pwdd.setText(pwd[position])
            cityy.setText(city[position])

            if(gender[position].equals("Female"))
            {
                f.isChecked=true
            }
            else
            {
                m.isChecked=true
            }

            if(hobby[position].equals("DancingReading"))
            {
                rr.setChecked(true)
                dd.setChecked(true)
            }
            else if(hobby[position].equals("Dancing"))
            {
                dd.setChecked(true)
            }
            else if(hobby[position].equals("Reading"))
            {
                rr.setChecked(true)
            }

            btnc.setOnClickListener {
                d.dismiss()
            }

            btne.setOnClickListener {
                var chk=""
                if(emaill.text.toString().isNotEmpty() && pwdd.text.toString().isNotEmpty() && (f.isChecked==true || m.isChecked==true) && (rr.isChecked==true || dd.isChecked==true))
                {
                    if(f.isChecked==true)
                    {
                        rb=f.text.toString()
                    }
                    else
                    {
                        rb=m.text.toString()
                    }

                    if(dd.isChecked)
                    {
                        chk+=dd.text.toString()
                    }
                    if(rr.isChecked)
                    {
                        chk+=rr.text.toString()
                    }

                    var q=Volley.newRequestQueue(showdata)
                    var url="https://krinaltest.000webhostapp.com/Database/editdata.php"
                    var r=object:StringRequest(Request.Method.POST,url,{
                        var qq=Volley.newRequestQueue(showdata)
                        var urll="https://krinaltest.000webhostapp.com/Database/selectdata.php"
                        var rr=object:StringRequest(Request.Method.POST,urll,{
                            did.clear()
                            email.clear()
                            pwd.clear()
                            city.clear()
                            gender.clear()
                            hobby.clear()
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
                            var dataa=ViewData(showdata,did,email,pwd,city,gender,re,hobby,arrr)
                            re.adapter=dataa
                        },{})
                        {
                            override fun getParams(): MutableMap<String, String> {
                                var map=HashMap<String,String>()
                                return map
                            }
                        }
                        qq.add(rr)
                        d.dismiss()
                    },{})
                    {
                        override fun getParams(): MutableMap<String, String>? {
                            var map=HashMap<String,String>()
                            map.put("email",emaill.text.toString())
                            map.put("pwd",pwdd.text.toString())
                            map.put("city",cityy.text.toString())
                            map.put("gender",rb.toString())
                            map.put("hobby",chk.toString())
                            map.put("did",did[position].toString())
                            return map
                        }
                    }
                    q.add(r)
                }
                else
                {
                    if(emaill.text.toString().isEmpty())
                    {
                        emaill.setError("email")
                    }
                    if(pwdd.text.toString().isEmpty())
                    {
                        pwdd.setError("pwd")
                    }
                    if(!f.isChecked && !m.isChecked)
                    {
                        Toast.makeText(showdata,"Select Gender", Toast.LENGTH_SHORT).show()
                    }
                    if(!dd.isChecked && !rr.isChecked)
                    {
                        Toast.makeText(showdata,"Select Hobby", Toast.LENGTH_SHORT).show()
                    }
                }
            }


            d.setCancelable(false)
            d.show()
        }

    }


}
