package com.example.myapplication.data.repo

import com.example.myapplication.data.datasource.RemoteDataSource

class RepositoryData (private val dataSource: RemoteDataSource = RemoteDataSource()) {


   suspend fun getData() = dataSource.getData()
}