package nl.enochtech.testappaa.core.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.enochtech.testappaa.R
import nl.enochtech.testappaa.core.Constants
import nl.enochtech.testappaa.core.network.ConnectionLiveData

open class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onStart() {
        registerNetworkCallback(this)
        super.onStart()
    }

    protected fun isNetworkConnected(): Boolean{
        return Constants.isNetworkConnected
    }

    protected fun launchActivity(activity: Activity, className: Class<*>) {
        val mIntent = Intent(activity, className)
        startActivity(mIntent)
    }

    private fun registerNetworkCallback(context: Context) {
        val connectionLiveData = ConnectionLiveData(context)
        connectionLiveData.observe(this, Observer { isConnected ->
            isConnected?.let {
                Log.d("before assigning", "" + isConnected)
                Constants.isNetworkConnected = isConnected
                Log.d("after assigning", "" + Constants.isNetworkConnected)
            }
        })
    }



}