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
import id.gits.si.bansapp.activity.DetailPostActivity
import id.gits.si.bansapp.model.DataItems
import id.gits.si.bansapp.support.formatTimeAgo

class PostAPIAdapter(val results : ArrayList<DataItems>) : RecyclerView.Adapter<PostAPIAdapter.ViewHolder>() {
    fun setData(data : List<DataItems>) {
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
        val URL_FOTO = "http://192.168.100.125/gits_api/images/"
        Glide.with(holder.itemView).load(URL_FOTO+data.postImage).apply(RequestOptions().override(75,75)).into(holder.postImage)
        //holder.postTitle.text = data.postTitle
        /*if(data.postBody.toString().length > 99) {
            holder.postBody.text = data.postBody?.take(50)+"..."
        } else {
            holder.postBody.text = data.postBody
        }*/
        if(data.postTitle.toString().length > 40) {
            holder.postTitle.text = data.postTitle?.take(40)+"..."
        } else {
            holder.postTitle.text = data.postTitle
        }
        holder.postTime.text = formatTimeAgo(data.postTime.toString())
//        holder.penggunaUsername.setText(MainActivity().getUsernamePengguna(data.postCredit.toInt()))
        holder.cvPost.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailPostActivity::class.java)
            intent.putExtra("post_id", data.postId)
            holder.cvPost.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val postTitle : TextView = itemView.findViewById(R.id.tv_post_title)
        //val postBody : TextView = itemView.findViewById(R.id.tv_post_body)
        val postTime : TextView = itemView.findViewById(R.id.tv_post_time)
        val postImage : ImageView = itemView.findViewById(R.id.iv_post_image)
        val cvPost : CardView = itemView.findViewById(R.id.cv_post)

    }

}