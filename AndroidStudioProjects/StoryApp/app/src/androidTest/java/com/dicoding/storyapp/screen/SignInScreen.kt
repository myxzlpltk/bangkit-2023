package com.dicoding.storyapp.screen

import com.dicoding.storyapp.R
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

object SignInScreen : Screen<SignInScreen>() {
    val emailField = KTextInputLayout {
        withId(R.id.email_container)
    }
    val passwordField = KTextInputLayout {
        withId(R.id.password_container)
    }
    val signInButton = KButton {
        withId(R.id.sign_in_button)
    }
}