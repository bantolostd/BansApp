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

class PostAPIAdapter(val results : ArrayList<DataItem>) : RecyclerView.Adapter<PostAPIAdapter.ViewHolder>() {
    fun setData(data : List<DataItem>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAPIAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAPIAdapter.ViewHolder, position: Int) {
        val data = results[position]
        //val URL_FOTO = ""
        //Glide.with(holder.itemView).load(URL_FOTO+data.nama).apply(RequestOptions().override(320,320)).into(holder.foto)
        holder.postTitle.text = data.nama
        holder.postBody.text = data.noHp
        holder.penggunaUsername.text = data.instagram
        holder.postTime.text = data.instagram
        holder.cvPost.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateUserActivity::class.java)
            intent.putExtra("id_user", data.id)
            intent.putExtra("nama", data.nama)
            intent.putExtra("email", data.email)
            intent.putExtra("no_hp", data.noHp)
            intent.putExtra("alamat", data.alamat)
            intent.putExtra("instagram", data.instagram)
            holder.cvPost.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val postTitle : TextView = itemView.findViewById(R.id.tv_post_title)
        val postBody : TextView = itemView.findViewById(R.id.tv_post_body)
        val penggunaUsername : TextView = itemView.findViewById(R.id.tv_pengguna_username)
        val postTime : TextView = itemView.findViewById(R.id.tv_post_time)
        val postImage : ImageView = itemView.findViewById(R.id.iv_post_image)
        val cvPost : CardView = itemView.findViewById(R.id.cv_user)

    }
}