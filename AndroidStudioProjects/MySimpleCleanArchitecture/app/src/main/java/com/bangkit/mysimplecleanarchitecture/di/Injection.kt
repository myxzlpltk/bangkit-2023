package com.bangkit.mysimplecleanarchitecture.di

import com.bangkit.mysimplecleanarchitecture.data.IMessageDataSource
import com.bangkit.mysimplecleanarchitecture.data.MessageDataSource
import com.bangkit.mysimplecleanarchitecture.data.MessageRepository
import com.bangkit.mysimplecleanarchitecture.domain.IMessageRepository
import com.bangkit.mysimplecleanarchitecture.domain.MessageInteractor
import com.bangkit.mysimplecleanarchitecture.domain.MessageUseCase

object Injection {
    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): IMessageRepository {
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}