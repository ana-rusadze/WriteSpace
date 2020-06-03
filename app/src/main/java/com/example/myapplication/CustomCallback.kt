package com.example.myapplication

interface CustomCallback {
    fun onFailure(response: String)
    fun onResponse(response: String)

}