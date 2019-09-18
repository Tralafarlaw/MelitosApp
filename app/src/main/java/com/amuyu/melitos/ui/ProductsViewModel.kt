package com.amuyu.melitos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductsViewModel : ViewModel() {
    private val users = MutableLiveData<ArrayList<Map<String, Any>>>()

    fun getUsers(): LiveData<ArrayList<Map<String, Any>>> {
        return users
    }
    fun  setUsers(va: ArrayList<Map<String, Any>>){
        users.postValue(va)
    }
}