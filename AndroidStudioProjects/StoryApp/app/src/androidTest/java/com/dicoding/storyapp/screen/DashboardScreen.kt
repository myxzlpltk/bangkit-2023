package com.dicoding.storyapp.screen

import com.dicoding.storyapp.R
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView
import io.github.kakaocup.kakao.screen.Screen

object DashboardScreen : Screen<DashboardScreen>() {
    val bottomNavigationView = KBottomNavigationView {
        withId(R.id.bottom_navigation)
    }

    fun goToProfilePage() {
        bottomNavigationView.setSelectedItem(R.id.profile_action)
    }
}