package m.z.ratp.test.common.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.DrawableRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import m.z.ratp.test.R
import java.util.Locale


/**
 * Created by Mongi Zaidi on 12/03/2024.
 */
fun Context.getActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun Context.openAppSettings() {
    val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:$packageName")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(appSettingsIntent)
}

fun Context.snackBar(
    message: String,
    @DrawableRes iconRes: Int,
    actionTitle: String? = null,
    action: OnClickListener? = null
) {
    val activity = getActivity()
    var view: View? = activity?.findViewById(android.R.id.content)
    if (activity?.hasWindowFocus() != true) {
        val fragments = (activity as? FragmentActivity)?.supportFragmentManager?.fragments
        fragments?.forEach {
            if (it is DialogFragment && it.isVisible) {
                view = it.dialog?.window?.decorView
            }
        }
    }
    view?.let {
        val hasAction = !TextUtils.isEmpty(actionTitle) && action != null
        Snackbar.make(
            it,
            message,
            if (hasAction) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        ).apply {
            setIcon(iconRes)
            setMaxLines(5)
            if (hasAction) {
                setAction(actionTitle, action)
            }
        }.show()
    }
}

fun Context.snackBarSuccess(message: String) {
    snackBar(message, R.drawable.ic_check_green_24)
}

fun Context.snackBarError(message: String) {
    snackBar(message, R.drawable.ic_error_white_24dp)
}

fun Context.showTwoChoicesAlertAlert(
    title: String?,
    message: String?,
    positiveTitle: String?,
    positiveAction: DialogInterface.OnClickListener?,
    negativeTitle: String?,
    negativeAction: DialogInterface.OnClickListener?,
): AlertDialog {
    val alertBuilder = AlertDialog.Builder(this)
    alertBuilder.setTitle(title)
    if (!TextUtils.isEmpty(message)) alertBuilder.setMessage(message)
    alertBuilder.setPositiveButton(positiveTitle, positiveAction)
    alertBuilder.setNegativeButton(negativeTitle, negativeAction)
    val alertdialog: AlertDialog = alertBuilder.create()
    alertdialog.show()
    return alertdialog
}

fun Context.showOKCancelAlert(
    title: String?,
    message: String?,
    positiveAction: DialogInterface.OnClickListener?
): AlertDialog {
    return showTwoChoicesAlertAlert(
        title,
        message,
        getString(R.string.ok).uppercase(Locale.getDefault()),
        positiveAction,
        getString(R.string.cancel).uppercase(Locale.getDefault()),
        null
    )
}
