package com.example.topnews.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tags")
data class Tag(

	@PrimaryKey
	@ColumnInfo(name = "name")
	var name: String
) : Parcelable