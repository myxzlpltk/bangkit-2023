package com.bangkit.mysimplecleanarchitecture.data

import com.bangkit.mysimplecleanarchitecture.domain.IMessageRepository
import com.bangkit.mysimplecleanarchitecture.domain.MessageEntity

class MessageRepository(
    private val messageDataSource: IMessageDataSource
) : IMessageRepository {
    override fun getWelcomeMessage(name: String): MessageEntity {
        return messageDataSource.getMessageFromSource(name)
    }
}