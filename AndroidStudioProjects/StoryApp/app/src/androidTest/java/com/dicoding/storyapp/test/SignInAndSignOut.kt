package com.dicoding.storyapp.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.storyapp.KaspressoLifecycleTest
import com.dicoding.storyapp.presentation.ui.sign_in.SignInActivity
import com.dicoding.storyapp.screen.DashboardScreen
import com.dicoding.storyapp.screen.ProfileScreen
import com.dicoding.storyapp.screen.SignInScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInAndSignOut : KaspressoLifecycleTest<SignInActivity>(SignInActivity::class.java) {

    override fun onSetup() {}

    @Test
    fun test() = run {
        step("1. Check sign in form is correct") {
            SignInScreen {
                emailField.isDisplayed()
                passwordField.isDisplayed()
                signInButton {
                    isDisabled()
                    isDisplayed()
                }
            }
        }
        step("2. User fill email field") {
            SignInScreen {
                emailField.edit.typeText("myxzlpltk@gmail.com")
                closeSoftKeyboard()
            }
        }
        step("3. User fill password field") {
            SignInScreen {
                passwordField.edit.typeText("password")
                closeSoftKeyboard()
            }
        }
        step("4. Check sign in button is enabled") {
            SignInScreen {
                signInButton.isEnabled()
            }
        }
        step("5. User click sign button") {
            SignInScreen {
                signInButton.click()
            }
        }
        step("6. Validate dashboard and go to profile page") {
            DashboardScreen {
                bottomNavigationView.isDisplayed()
                goToProfilePage()
            }
        }
        step("7. Proceed sign out") {
            ProfileScreen {
                signOutButton {
                    isDisplayed()
                    isEnabled()
                    click()
                }
            }
        }
        step("8. Verify sign in screen") {
            SignInScreen {
                emailField.isDisplayed()
                passwordField.isDisplayed()
                signInButton {
                    isDisabled()
                    isDisplayed()
                }
            }
        }
    }
}