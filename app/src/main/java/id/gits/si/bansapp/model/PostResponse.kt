package id.gits.si.bansapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostResponse(

	@field:SerializedName("data")
	val data: List<DataItems?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataItems(

	@field:SerializedName("post_title")
	val postTitle: String? = null,

	@field:SerializedName("post_image")
	val postImage: String? = null,

	@field:SerializedName("post_id")
	val postId: String? = null,

	@field:SerializedName("post_credit")
	val postCredit: String? = null,

	@field:SerializedName("post_body")
	val postBody: String? = null,

	@field:SerializedName("post_time")
	val postTime: String? = null
) : Parcelable
