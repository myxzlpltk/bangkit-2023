package com.dicoding.storyapp.screen

import com.dicoding.storyapp.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

object ProfileScreen : Screen<ProfileScreen>() {
    val signOutButton = KButton {
        withId(R.id.sign_out_button)
    }
}