package appnexus.example.composesample

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String): Unit {

    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

}