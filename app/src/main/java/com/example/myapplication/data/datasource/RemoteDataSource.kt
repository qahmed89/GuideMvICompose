package com.example.myapplication.data.datasource

import kotlinx.coroutines.delay

class RemoteDataSource {


    suspend fun getData(): List<String> {
        delay(1000L)
        return (1..100).map {
            it.toString()
        }
    }
}