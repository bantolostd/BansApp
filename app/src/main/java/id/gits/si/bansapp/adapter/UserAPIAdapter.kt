package id.gits.si.bansapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.gits.si.bansapp.R
import id.gits.si.bansapp.activity.UpdateUserActivity
import id.gits.si.bansapp.model.DataItem

class UserAPIAdapter(val results : ArrayList<DataItem>) : RecyclerView.Adapter<UserAPIAdapter.ViewHolder>() {
    fun setData(data : List<DataItem>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAPIAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAPIAdapter.ViewHolder, position: Int) {
        val data = results[position]
        val URL_FOTO = ""
        Glide.with(holder.itemView).load(URL_FOTO+data.nama).apply(RequestOptions().override(320,320)).into(holder.foto)
        holder.nama.text = data.nama
        holder.no_hp.text = data.noHp
        holder.instagram.text = data.instagram
        holder.cv_user.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateUserActivity::class.java)
            intent.putExtra("id_user", data.id)
            intent.putExtra("nama", data.nama)
            intent.putExtra("email", data.email)
            intent.putExtra("no_hp", data.noHp)
            intent.putExtra("alamat", data.alamat)
            intent.putExtra("instagram", data.instagram)
            holder.cv_user.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val nama : TextView = itemView.findViewById(R.id.tv_post_title)
        val no_hp : TextView = itemView.findViewById(R.id.tv_post_body)
        val instagram : TextView = itemView.findViewById(R.id.tv_pengguna_username)
        val follower : TextView = itemView.findViewById(R.id.tv_post_time)
        val foto : ImageView = itemView.findViewById(R.id.iv_post_image)
        val cv_user : CardView = itemView.findViewById(R.id.cv_user)

    }
}