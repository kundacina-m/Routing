package com.example.topnews.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "sources")
data class Source(
	@ColumnInfo(name = "id")
	var id: String? = null,

	@PrimaryKey
	@ColumnInfo(name = "name")
	var name: String
) : Parcelable
