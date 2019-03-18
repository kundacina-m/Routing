package com.example.topnews.screens.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.screens.FakeData

class RVViewModel: ViewModel() {

        private var data = MutableLiveData<List<RV>>()

        fun getRV(): MutableLiveData<List<RV>> {
            data.value = FakeData.fetchRV()
            return data
        }
    }