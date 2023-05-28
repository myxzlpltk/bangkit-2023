package com.bangkit.mysimplecleanarchitecture.data

import com.bangkit.mysimplecleanarchitecture.domain.MessageEntity

interface IMessageDataSource {
    fun getMessageFromSource(name: String): MessageEntity
}