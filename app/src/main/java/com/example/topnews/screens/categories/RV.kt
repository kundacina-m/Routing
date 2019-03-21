package com.example.topnews.screens.categories

import androidx.annotation.IntDef

const val SECTION = 0
const val ITEM = 1

// Declare the @IntDef for these constants
@IntDef(SECTION, ITEM)
@Retention(AnnotationRetention.SOURCE)
annotation class RvType

abstract class RV(@RvType val type: Int)