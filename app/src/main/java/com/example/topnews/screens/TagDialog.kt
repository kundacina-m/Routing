package com.example.topnews.screens

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.StyleRes
import com.example.topnews.R.layout
import com.example.topnews.R.style
import com.example.topnews.data.model.Article
import kotlinx.android.synthetic.main.dialog_tag.btCancel
import kotlinx.android.synthetic.main.dialog_tag.btConfirm
import kotlinx.android.synthetic.main.dialog_tag.etName

class TagDialog
private constructor(private val builder: Builder) : Dialog(builder.context, builder.themeId) {


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layout.dialog_tag)


		btConfirm.setOnClickListener {
			builder.confirmedTag.invoke(builder.article.publishedAt,etName.text.toString())
		}

		btCancel.setOnClickListener {

		}

	}

	companion object {

		fun build(context: Context, block: Builder.() -> Unit): TagDialog {
			return Builder(context).apply(block).build()

		}
	}

	class Builder(var context: Context) {

		lateinit var article: Article

		lateinit var confirmedTag: ((String, String) -> Unit?)

		@StyleRes
		var themeId: Int = style.AppTheme_Dialog

		fun build() = TagDialog(this)

	}

	interface OnConfirmedTag {
		fun onTagConfirmed(dataItem: Article, tag: String)
	}
}