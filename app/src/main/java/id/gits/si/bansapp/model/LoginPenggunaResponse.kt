package id.gits.si.bansapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginPenggunaResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("pengguna_id")
	val penggunaId: String? = null,

	@field:SerializedName("pengguna_username")
	val penggunaUsername: String? = null,

	@field:SerializedName("pengguna_nama")
	val penggunaNama: String? = null,

	@field:SerializedName("pengguna_password")
	val penggunaPassword: String? = null,

	@field:SerializedName("pengguna_email")
	val penggunaEmail: String? = null,

	@field:SerializedName("pengguna_foto")
	val penggunaFoto: String? = null
) : Parcelable
