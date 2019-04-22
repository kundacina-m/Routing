package com.example.topnews.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SourceRaw(
	@ColumnInfo(name = "id")
	var id: String? = null,

	@ColumnInfo(name = "name")
	var name: String? = null
) : Parcelable
