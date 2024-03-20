package com.example.myapplication.domain.useCase

import com.example.myapplication.data.repo.RepositoryData

class GetDataUseCase(private val repo: RepositoryData = RepositoryData()) {

    suspend operator fun invoke() = repo.getData()
}