package com.richaa2.map.kmp.dependecies

import androidx.lifecycle.ViewModel

class MyViewModel(
    private val repository: MyRepository,

): ViewModel() {

    fun getHelloWorldString(): String {
        return repository.helloWorld()
    }
}