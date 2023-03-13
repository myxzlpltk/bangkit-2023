package com.example.githubuser.networks

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("followers")
	val followers: Int,
)
