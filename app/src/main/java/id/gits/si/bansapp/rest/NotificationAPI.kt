package id.gits.si.bansapp.rest


import id.gits.si.bansapp.model.PushNotificationResponse
import retrofit2.Call
import retrofit2.http.*

interface NotificationAPI {
    @FormUrlEncoded
    @POST("notification_api.php")
    fun pushNotification(
        @Field("notification_title") notification_title : String,
        @Field("notification_body") notification_body : String,
        @Field("notification_token") notification_token : String
        ): Call<PushNotificationResponse>

}