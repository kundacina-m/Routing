package com.example.topnews

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.StyleRes
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_image.imageDialog

class ImageDialog
private constructor(private val builder: Builder) : Dialog(builder.context, builder.themeId) {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.dialog_image)

		setImageDialog()
	}

	private fun setImageDialog() {
		Glide.with(context).load(builder.urlToImg).into(imageDialog)
	}

	companion object {

		fun build(context: Context, block: Builder.() -> Unit): ImageDialog {
			return Builder(context).apply(block).build()

		}
	}

	class Builder(var context: Context) {
		var urlToImg: String? = null

		@StyleRes
		var themeId: Int = R.style.AppTheme_Dialog

		fun build() = ImageDialog(this)

	}
}