package com.subha.myusergithubapp.core.helper

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Vibrator
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.subha.myusergithubapp.GithubApp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object UitilsDefault {


    fun copyToClipboard(context: Context, text: CharSequence) {
        var clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)

    }

    fun pastefromClipboard(context: Context): String {
        val clipBoardManager =
            context.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val text = clipBoardManager.primaryClip?.getItemAt(0)?.text.toString()
        return text
    }

    fun isOnline(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        val cm =
            GithubApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                val netInfo = cm.activeNetworkInfo
                if (netInfo != null) {
                    return (netInfo.isConnected() && (netInfo.getType() == ConnectivityManager.TYPE_WIFI || netInfo.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                val netInfo = cm.activeNetwork
                if (netInfo != null) {
                    val nc = cm.getNetworkCapabilities(netInfo);

                    return (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc!!.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ));
                }
            }
        }

        return haveConnectedWifi || haveConnectedMobile
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun makeStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decor = activity.window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            val w = activity.window
            w.statusBarColor = Color.TRANSPARENT
        }
    }

    fun isEmailValid(email: String): Boolean {
        val ePattern =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = Pattern.compile(ePattern)
        val m = p.matcher(email)
        return m.matches()

    }

    fun isvalidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val specialCharacters = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
        val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$specialCharacters])(?=\\S+$).{8,20}$"
        // val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z][a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()

    }

    fun getDatefromMili(value: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val dateString = simpleDateFormat.format(Date(value))
        return dateString
    }

    fun getDateTimestamp(milliSeconds: Long): String? {
        // Create a DateFormatter object for displaying date in specified
        // format.
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.time)
    }

    fun DecimalIndianFormat(value: Double): String {
        var formater = DecimalFormat("#,##,###.##")
        var res = formater.format(value)

        return res
    }

    fun loadImage(imageView: ImageView, url: String?) {

        if (url != null) {
            Glide.with(imageView.rootView)
                .load(url)
                .into(imageView);
        }
    }

    fun loadImageSample(imageView: ImageView, url: Int?) {

        if (url != null) {
            Glide.with(imageView.rootView)
                .load(url)
                .into(imageView);
        }
    }

    fun changeDecimalFormat(value: Double, dec: String): String {
        var lastPriceindecimal: String? = null
        var precision = DecimalFormat(dec)
        if (value != null) {
            lastPriceindecimal = precision!!.format(value)
        }
        return lastPriceindecimal!!
    }


    fun stringFormat(decimalCount: Int, value: Double, locale: Locale): String {
        var res = String.format("%.${decimalCount}f", value, locale)
        return res
    }

    fun getDateTime(date: String): String {
        val dateForm2 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa")
        val utc = TimeZone.getTimeZone("GMT")
        if (date.contains("/")) {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            return dateForm2.format(dateFormat.parse(date))
        } else if (date.contains("T")) {
            val dateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") //2021-03-04T13:54:28.841Z
            dateFormat.timeZone = utc
            //dateFormat.timeZone = TimeZone.getTimeZone("UTC+5:30")
            return dateForm2.format(dateFormat.parse(date))
        } else {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return dateForm2.format(dateFormat.parse(date))
        }
    }

    fun getDateOnly(date: String): String {
        //println("get Date: $date")0
        val dateForm2 = SimpleDateFormat("yyyy-MM-dd")
        val utc = TimeZone.getTimeZone("GMT")
        if (date.contains("/")) {
            val dateFormat = SimpleDateFormat("MM-dd-yyyy HH:mm:ss")
            return dateForm2.format(dateFormat.parse(date))
        } else if (date.contains("T")) {
            val dateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") //2021-03-04T13:54:28.841Z
            dateFormat.timeZone = utc
            //dateFormat.timeZone = TimeZone.getTimeZone("UTC+5:30")
            return dateForm2.format(dateFormat.parse(date))
        } else {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return dateForm2.format(dateFormat.parse(date))
        }
    }

    fun getTimeOnly(date: String): String {
        //println("get Date: $date")
        val dateForm2 = SimpleDateFormat("hh:mm:ss")
        val utc = TimeZone.getTimeZone("GMT")
        if (date.contains("/")) {
            val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            return dateForm2.format(dateFormat.parse(date))
        } else if (date.contains("T")) {
            val dateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") //2021-03-04T13:54:28.841Z
            dateFormat.timeZone = utc
            return dateForm2.format(dateFormat.parse(date))
        } else {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return dateForm2.format(dateFormat.parse(date))
        }
    }

    fun hepticFeedback(context: Context) {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(50)
    }
}