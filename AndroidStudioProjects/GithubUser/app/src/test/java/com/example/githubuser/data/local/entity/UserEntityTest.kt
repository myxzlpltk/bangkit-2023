package com.example.githubuser.data.local.entity

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserEntityTest {

    private lateinit var userEntity: UserEntity
    private val defaultId = 1001
    private val defaultName = "Test User"
    private val defaultAvatarUrl = "https://example.com"
    private val defaultBio = "Test Bio"
    private val defaultPublicRepos = 9
    private val defaultFollowing = 10
    private val defaultFollowers = 11

    @Before
    fun before() {
    }

    @Test
    fun getUsername() {
        userEntity = injectUserEntity("TESTUSERNAME")
        assertEquals("testusername", userEntity.username)

        userEntity = injectUserEntity("jOhNdOe138843")
        assertEquals("johndoe138843", userEntity.username)
    }

    private fun injectUserEntity(username: String) = UserEntity(
        defaultId,
        username,
        defaultName,
        defaultAvatarUrl,
        defaultBio,
        defaultPublicRepos,
        defaultFollowing,
        defaultFollowers,
    )
}