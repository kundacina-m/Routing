package com.example.topnews

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_image.imageDialog

class ImageDialog private constructor (private val builder: Builder) : Dialog(builder
	.context) {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.dialog_image)

		setImageDialog()
	}

	private fun setImageDialog() {
		Glide.with(context).load(builder.urlToImg).into(imageDialog)
	}


	class Builder(var context: Context) {
		var urlToImg: String? = null

		fun withImg(urlToImage: String): Builder {
			this.urlToImg = urlToImage
			return this
		}

		fun build() = ImageDialog(this)

		fun show() = build().show()
	}
}