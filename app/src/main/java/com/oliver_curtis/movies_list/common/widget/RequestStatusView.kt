package com.oliver_curtis.movies_list.common.widget

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.oliver_curtis.movies_list.R
import kotlinx.android.synthetic.main.view_request_status.view.*


/**
 * Utility view that combines a progress bar and an error message in a single widget.
 * This view has utility method to show 3 status values of the request:
 *
 * 1) in progress
 * 2) success
 * 3) error
 *
 * When the error status is shown a link-button to retry is shown as well.
 * This view accepts a listener to this retry button.
 */
class RequestStatusView : FrameLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_request_status, this)
        findViewById<TextView>(R.id.statusRetry).text = buildRetryLinkText()
        showSuccess()
    }

    private fun buildRetryLinkText(): Spanned {
        val text = resources.getString(R.string.retry_link)

        return formatRetryLinkText(text)
    }

    private fun formatRetryLinkText(text: String): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            return Html.fromHtml(text)
        }
    }

    fun showSuccess() {
        statusError.text = null
        statusError.visibility = View.INVISIBLE
        statusRetry.visibility = View.INVISIBLE
        statusProgress.visibility = View.INVISIBLE
    }

    fun showProgress() {
        statusError.text = null
        statusError.visibility = View.INVISIBLE
        statusRetry.visibility = View.INVISIBLE
        statusProgress.visibility = View.VISIBLE
    }

    fun showError(errorResId: Int) {
        showError(resources.getString(errorResId))
    }

    fun showError(error: String) {
        statusError.text = error
        statusProgress.visibility = View.INVISIBLE
        statusError.visibility = View.VISIBLE
        statusRetry.visibility = View.VISIBLE
    }

    fun setRetryListener(listener: OnClickListener) {
        statusRetry.setOnClickListener(listener)
    }

    fun setRetryListener(listener: (View) -> Unit) {
        statusRetry.setOnClickListener(listener)
    }

    fun removeRetryListener() {
        statusRetry.setOnClickListener(null)
    }
}