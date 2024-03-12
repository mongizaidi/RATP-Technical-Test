package m.z.ratp.test.common.ui

import android.view.Gravity
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.google.android.material.snackbar.Snackbar
import m.z.ratp.test.R

/**
 * Created by Mongi Zaidi on 12/03/2024.
 */
fun Snackbar.setIcon(@DrawableRes drawableRes: Int) {
    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.let { textView ->
        textView.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
        textView.gravity = Gravity.CENTER_VERTICAL
        textView.compoundDrawablePadding =
            context.resources.getDimensionPixelSize(R.dimen.margin_medium)
    }
}

fun Snackbar.setMaxLines(maxLines: Int) {
    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.let { textView ->
        textView.maxLines = maxLines
    }
}