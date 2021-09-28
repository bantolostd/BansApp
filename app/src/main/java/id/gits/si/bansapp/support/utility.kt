package id.gits.si.bansapp.support

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.text.SimpleDateFormat
import java.util.*
import android.text.format.DateUtils
import java.lang.Exception
import java.util.concurrent.TimeUnit


fun setFullScreen(window : Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun lightStatusBar(window : Window, isLight : Boolean = true) {
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = isLight
}

fun konversiTanggal (tanggal: String): String{
    var tgl = tanggal.take(10)
    var jam =  tanggal.takeLast(8)
    val wib = jam.split(":")
    val tanggal = tgl.split("-")
    val bulan = when(tanggal[1]){
        "01" -> "Januari"
        "02" -> "Februari"
        "03" -> "Maret"
        "04" -> "April"
        "05" -> "Mei"
        "06" -> "Juni"
        "07" -> "Juli"
        "08" -> "Agustus"
        "09" -> "September"
        "10" -> "Oktober"
        "11" -> "November"
        "12" -> "Desember"
        else -> "Terjadi kesalahan"
    }
    var final = "${tanggal[2]} $bulan ${tanggal[0]} ${wib[0]}:${wib[1]} WIB"
    return final
}

fun formatTimeAgo(tanggal: String): String {  // Note : date1 must be in   "yyyy-MM-dd hh:mm:ss"   format
    var ok = ""
    try {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val past = format.parse(tanggal)
        val now = Date()
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
        val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

        //
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");
        if (seconds < 60) {
            ok = ("$seconds detik yang lalu")
        } else if (minutes < 60) {
            ok =("$minutes menit yang lalu")
        } else if (hours < 24) {
            ok = ("$hours jam yang lalu")
        } else {
            ok = ("$days hari yang lalu")
        }
    } catch (j: Exception) {
        j.printStackTrace()
    }
    return ok
}