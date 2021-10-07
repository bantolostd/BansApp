package id.gits.si.bansapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PushNotificationResponse(
	val canonicalIds: Int? = null,
	val success: Int? = null,
	val failure: Int? = null,
	val results: List<ResultsItem?>? = null,
	val multicastId: Long? = null
) : Parcelable

@Parcelize
data class ResultsItem(
	val messageId: String? = null
) : Parcelable
