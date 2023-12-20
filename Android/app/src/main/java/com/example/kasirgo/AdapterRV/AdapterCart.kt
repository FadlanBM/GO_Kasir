package com.example.kasirgo.AdapterRV

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.kasirgo.R
import com.example.kasirgo.Util.CartSharePreft
import com.example.kasirgo.item.itemBarang
import com.example.kasirgo.item.itemCart
import kotlinx.coroutines.CoroutineScope

class AdapterCart(val item:List<itemCart>, val context: Context, private val coroutineScope: CoroutineScope,val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<AdapterCart.ViewHolder>() {

    private var result= MutableLiveData<Int>()
    private val readresuld: LiveData<Int> get() = result
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val namaBarang:TextView=view.findViewById(R.id.tvNamaBarangCart)
        val priceBarang:TextView=view.findViewById(R.id.tvPriceBarangCart)
        val codeBarang:TextView=view.findViewById(R.id.tvCodeBarangCart)
        val btnPlus:ImageView=view.findViewById(R.id.btnPlusCart)
        val btnMinus:ImageView=view.findViewById(R.id.btnMinusCart)
        val jumCart:TextView=view.findViewById(R.id.tvJumCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return AdapterCart.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_barang_cart, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items=item[position]
        result.value=items._Count.toInt()
        holder.namaBarang.text=items._NamaBarang
        holder.priceBarang.text=items._Price
        holder.codeBarang.text=items._CodeBarang
        readresuld.observe(lifecycleOwner){
            items._Count=it.toString()
        }
        holder.jumCart.text=items._Count
        holder.btnPlus.setOnClickListener {
            var oldCount=items._Count
            addCount(position)
            readresuld.observe(lifecycleOwner){
                items._Count=it.toString()
            }
            CartSharePreft(context).countUpdate(position,items._Count)
            Log.e("oldCount","data ${oldCount}")
            Log.e("oldCount","data ${items._Count}")
            holder.jumCart.text=items._Count
            holder.btnMinus.isEnabled = items._Count != "0"
        }

        holder.btnMinus.setOnClickListener {
            minusCount(position)
            readresuld.observe(lifecycleOwner){
                items._Count=it.toString()
            }
            CartSharePreft(context).countUpdate(position,items._Count)
            holder.jumCart.text=items._Count
            holder.btnMinus.isEnabled = items._Count != "0"
        }
    }

    fun addCount(potition:Int){
        val oldValue=CartSharePreft(context).getCount()[potition].toInt()
        result.value=(oldValue)?.plus(1)
    }
    fun  minusCount(potition: Int){
        val oldValue=CartSharePreft(context).getCount()[potition].toInt()
        result.value=(oldValue)?.minus(1)
    }
}