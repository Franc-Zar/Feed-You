package com.example.app1

import LoginPage
import Page
import PasswordRecoveryPage
import PreferencePage
import SignUpPage
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.app1.authentication.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/** Suite di test relativi alle funzionalità di autenticazione
 */
@RunWith(AndroidJUnit4::class)
    class AuthenticationTest {

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun start() {
        Firebase.auth.signOut()
        val intent = Intent(context, LoginActivity::class.java).apply {
            putExtra("INITIALIZED", true)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(context, intent, null)
    }

    /** Test di corretta navigazione tra le activity
     */
    @Test
    fun signUp() {
        Page.on<LoginPage>()
            .tapOnSignUp()
            .on<SignUpPage>()
            .verify()
    }

    @Test
    fun forgotPassword() {
        Page.on<LoginPage>()
            .tapOnForgotPassword()
            .on<PasswordRecoveryPage>()
            .verify()
    }

    @Test
    fun signInFromPasswordRecovery() {
        Page.on<LoginPage>()
            .tapOnForgotPassword()
            .on<PasswordRecoveryPage>()
            .tapOnSignIn()
            .on<LoginPage>()
            .verify()
    }

    @Test
    fun signInFromSignUp() {
        Page.on<LoginPage>()
            .tapOnSignUp()
            .on<SignUpPage>()
            .tapOnSignIn()
            .on<LoginPage>()
            .verify()
    }

    @Test
    fun signInWithInexistentAccount() {
        Page.on<LoginPage>()
            .typeEmail("not_existing_email")
            .typePassword("not_correct_password")
            .tapOnSignIn()
        assert(Firebase.auth.currentUser == null)

    }

    @Test
    fun signInSuccessful() {
        Page.on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("Test1*")
            .tapOnSignIn()
            .on<PreferencePage>()
            .verify()
        assert(
            Firebase.auth.currentUser!!.email == "test@test.com"
                    && Firebase.auth.currentUser!!.uid == "NtD2zsiKXCbA3JxsGXJ8zvwHD0v1"
        )
    }

    @Test
    fun signUpAndSignInSuccessful() {
        Page.on<LoginPage>()
            .tapOnSignUp()
            .on<SignUpPage>()
            .typeEmail("newuser@mail.com")
            .typePassword("Passw0rd#")
            .checkTermsPolicy()
            .tapOnSignUp()
            .tapOnSignIn()
            .on<LoginPage>()
            .typeEmail("newuser@mail.com")
            .typePassword("Passw0rd#")
            .tapOnSignIn()
            .on<PreferencePage>()
            .verify()
        assert(Firebase.auth.currentUser!!.email == "newuser@mail.com")

    }

    @Test
    fun signUpMalformedEmail() {
        Page.on<LoginPage>()
            .tapOnSignUp()
            .on<SignUpPage>()
            .typeEmail("malformed_email")
            .typePassword("Passw0rd#")
            .checkTermsPolicy()
            .tapOnSignUp()
            .tapOnSignIn()
            .on<LoginPage>()
            .typeEmail("malformed_email")
            .typePassword("Passw0rd#")
            .tapOnSignIn()
            .on<LoginPage>()
            .verify()
        assert(Firebase.auth.currentUser == null)

    }

    @Test
    fun signUpWeakPassword() {
        Page.on<LoginPage>()
            .tapOnSignUp()
            .on<SignUpPage>()
            .typeEmail("email@email.com")
            .typePassword("weak")
            .checkTermsPolicy()
            .tapOnSignUp()
            .tapOnSignIn()
            .on<LoginPage>()
            .typeEmail("email@email.com")
            .typePassword("weak")
            .tapOnSignIn()
            .on<LoginPage>()
            .verify()
        assert(Firebase.auth.currentUser == null)

    }

}