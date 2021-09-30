package id.gits.si.bansapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.DataPengguna

class PenggunaAPIAdapter(val results : ArrayList<DataPengguna>) : RecyclerView.Adapter<PenggunaAPIAdapter.ViewHolder>() {
    fun setData(data : List<DataPengguna>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenggunaAPIAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PenggunaAPIAdapter.ViewHolder, position: Int) {
        val data = results[position]

        //set tampilan
        holder.penggunaNama.setTextSize(22F)
        holder.penggunaUsername.setTextSize(18F)


        val URL_FOTO = "http://192.168.100.125/gits_api/images/"
        Glide.with(holder.itemView).load(URL_FOTO+data.penggunaFoto).apply(RequestOptions().override(75,75)).into(holder.penggunaImage)

        Glide.with(holder.itemView)
            .load(R.drawable.ic_alternate_email)
            .override(18, 18)
            .into(holder.iconWaktu)
        if(data.penggunaNama.toString().length > 40) {
            holder.penggunaNama.text = data.penggunaNama?.take(40)+"..."
        } else {
            holder.penggunaNama.text = data.penggunaNama
        }
        holder.penggunaUsername.text = data.penggunaUsername
        holder.cvPengguna.setOnClickListener { view ->
            Toast.makeText(
                view.getContext(),
                data.penggunaNama+" (@"+data.penggunaUsername+")",
                Toast.LENGTH_LONG
            ).show()
        }
        /*holder.cvPengguna.setOnClickListener {
            *//*val intent = Intent(holder.itemView.context, DetailPostActivity::class.java)
            intent.putExtra("pengguna_id", data.penggunaId)
            intent.putExtra("pengguna_nama", data.penggunaNama)
            intent.putExtra("pengguna_email", data.penggunaEmail)
            intent.putExtra("pengguna_username", data.penggunaUsername)
            holder.cvPengguna.context.startActivity(intent)*//*
        }*/
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val penggunaNama : TextView = itemView.findViewById(R.id.tv_post_title)
        //val postBody : TextView = itemView.findViewById(R.id.tv_post_body)
        val penggunaUsername : TextView = itemView.findViewById(R.id.tv_post_time)
        val penggunaImage : ImageView = itemView.findViewById(R.id.iv_post_image)
        val iconWaktu : ImageView = itemView.findViewById(R.id.ic_time)
        val cvPengguna : CardView = itemView.findViewById(R.id.cv_post)

    }

}