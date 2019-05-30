package com.example.topnews.di

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.topnews.di.components.AppComponent

interface DaggerAppComponentProvider {
	val appComponent: AppComponent
}

val Activity.appDagger get() = (application as DaggerAppComponentProvider).appComponent

val Fragment.appDagger get() = (activity?.application as DaggerAppComponentProvider).appComponent

