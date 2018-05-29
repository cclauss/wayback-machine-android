package com.archive.waybackmachine.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.archive.waybackmachine.R
import com.archive.waybackmachine.adapter.ShareIntentListAdapter
import kotlinx.android.synthetic.main.dialog_share.*

class ShareIntentDialog : Dialog {
    private var mContext: Context? = null
    private var shareLink: String? = null
    private var title_text: String? = null
    private var isShowDialogTitle: Boolean = false
    private var itemPosition: Int = 0

    private constructor(context: Context) : super(context) {
        this.mContext = context
        isShowDialogTitle = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_share)

        val adapter = ShareIntentListAdapter(context, this, this.shareLink)

        listShareIntents.adapter = adapter
        listShareIntents!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            itemPosition = position
            adapter.setCurrentPosition(position)
            adapter.notifyDataSetChanged()
        }

        shareTitle.text = title_text

        if (!this.isShowDialogTitle) {
            this.shareTitle!!.visibility = View.GONE
        }

        btnCancel!!.setOnClickListener { this.dismiss() }
        btnShare!!.setOnClickListener { adapter.toggleSend(itemPosition) }
    }

    class Builder(val context: Context) {
        var shareLink: String? = null
        var title: String? = null
        var isShowDialogTitle: Boolean = false

        init {
            title = null
            isShowDialogTitle = true
            shareLink = null
        }

        fun setShareLink(link: String): Builder {
            this.shareLink = link
            return this
        }

        fun setEnableShowDialogTitle(enableShowTitle: Boolean): Builder {
            this.isShowDialogTitle = enableShowTitle
            return this
        }

        fun setDialogTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun build(): ShareIntentDialog {
            return ShareIntentDialog(this)
        }

    }

    private constructor(builder: Builder) : super(builder.context) {
        this.mContext = builder.context
        if (builder.title != null) {
            this.title_text = builder.title

        }
        this.isShowDialogTitle = builder.isShowDialogTitle
        this.shareLink = builder.shareLink
    }
}