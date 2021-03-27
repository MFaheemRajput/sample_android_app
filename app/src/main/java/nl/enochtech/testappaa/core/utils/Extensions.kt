package nl.enochtech.testappaa.core.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import nl.enochtech.testappaa.core.Constants

fun View.makeVisible(){
    this.visibility = View.VISIBLE
}

fun View.makeGone(){
    this.visibility = View.GONE
}

fun Context.showShortToast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.getValue(key: String): String{

    val prefs: SharedPreferences = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
    return prefs.getString(key, "")!!

}
fun Context.setValue(key: String, value: String) {

    val prefs: SharedPreferences = this.getSharedPreferences(Constants.PREFS_FILENAME, 0)
    prefs.edit().putString(key, value).apply()

}