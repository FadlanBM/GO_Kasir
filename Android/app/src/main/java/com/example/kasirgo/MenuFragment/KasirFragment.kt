
package com.example.kasirgo.MenuFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.kasirgo.AdapterRV.AdapterCart
import com.example.kasirgo.AdapterRV.AdapterListBarang
import com.example.kasirgo.R
import com.example.kasirgo.Util.BaseAPI
import com.example.kasirgo.Util.CartSharePreft
import com.example.kasirgo.Util.SharePref.Companion.getAuth
import com.example.kasirgo.item.itemBarang
import com.example.kasirgo.item.itemCart
import com.example.kasirgo.ui.barang.ScanBCBarangActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KasirFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KasirFragment : Fragment() {
    private lateinit var recyleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_kasir, container, false)
        view.findViewById<Button>(R.id.btnScanQR).setOnClickListener {
            val intent=Intent(requireContext(),ScanBCBarangActivity::class.java)
            intent.putExtra("status","addCart")
            startActivity(intent)
        }

/*
        CartSharePreft(requireContext()).clearCart()
*/
        recyleView=view.findViewById(R.id.rvcartBarang)
        getData()

        return view
    }

    private fun getData() {
        lifecycleScope.launch() {
                        val databaranglist = mutableListOf<itemCart>()
            withContext(Dispatchers.IO) {
                try {
                    val ids=CartSharePreft(requireContext()).getId()
                    Log.e("idCart",ids.toString())
                    for (dataid in ids) {
                        val conn =
                            URL("${BaseAPI.BaseAPI}/api/barang/WithQr/${dataid}").openConnection() as HttpURLConnection
                        conn.requestMethod = "GET"

                        requireContext().getAuth()?.let {
                            conn.setRequestProperty(
                                "Authorization",
                                "Bearer ${it.getString("token")}"
                            )
                        }
                        conn.setRequestProperty("Content-Type", "application/json")
                        val code = conn.responseCode
                        Log.e("data", code.toString())

                        val body = if (code in 200 until 300) {
                            conn.inputStream?.bufferedReader()?.use { it.readLine() }
                        } else {
                            conn.errorStream?.bufferedReader()?.use { it.readLine() }
                        }


                        withContext(Dispatchers.Main) {
                            val jsonBarang = JSONObject(body!!)
                            val dataBarang = jsonBarang.getJSONArray("Data")
                            Log.e("data", dataBarang.toString())
                            for(i in 0 until dataBarang.length()){
                                val jsonObject=dataBarang.getJSONObject(i)
                                val id=jsonObject.getString("ID")
                                val nama=jsonObject.getString("name")
                                val codebarang=jsonObject.getString("code_barang")
                                val price=jsonObject.getString("price")
                                databaranglist.add(itemCart("0",id,nama,price,codebarang))
                            }
                            var count=CartSharePreft(requireContext()).getCount()
                            count.mapIndexed { indexdata,count ->
                                databaranglist.mapIndexed { indexdatalist, itemListcart ->
                                    if (indexdata==indexdatalist){
                                        itemListcart._Count=count
                                    }
                                }
                            }
                        }
                    }
                    Log.e("database",databaranglist.toString())
                    val adapter= AdapterCart(databaranglist!!,requireContext(),lifecycleScope,viewLifecycleOwner)
                    recyleView.adapter=adapter
                }catch (e:Exception){
                    Log.e("Error ",e.toString())
                }
            }
        }
    }
}