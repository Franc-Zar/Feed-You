package com.example.app1

import AccountPage
import AlertDialogPage
import ChangePasswordPage
import LoginPage
import MainPage
import MenuPage
import Page
import PasswordRecoveryPage
import PreferencePage
import SignUpPage
import TopBarPage
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso
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
    fun signInSuccessfully() {
        Firebase.auth.createUserWithEmailAndPassword("test@test.com", "Test1#")
        Page.on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("Test1#")
            .tapOnSignIn()
            .on<MainPage>()
            .verify()
        assert(
            Firebase.auth.currentUser!!.email == "test@test.com")
    }

    @Test
    fun signUpAndSignInSuccessfully() {
        Page.on<LoginPage>()
            .tapOnSignUp()
            .on<SignUpPage>()
            .typeEmail("newuser@mail.com")
            .typePassword("Passw0rd#")
            .checkTermsPolicy()
            .tapOnSignUp()

        Espresso.pressBack()

        Page.on<LoginPage>()
            .typeEmail("newuser@mail.com")
            .typePassword("Passw0rd#")
            .tapOnSignIn()
            .on<MainPage>()
            .verify()
        assert(Firebase.auth.currentUser!!.email == "newuser@mail.com")
        Firebase.auth.currentUser!!.delete()
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

        Espresso.pressBack()

        Page.on<LoginPage>()
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

        Espresso.pressBack()

        Page.on<LoginPage>()
            .typeEmail("email@email.com")
            .typePassword("weak")
            .tapOnSignIn()
            .on<LoginPage>()
            .verify()
        assert(Firebase.auth.currentUser == null)

    }

    @Test
    fun signInAndDeleteAccount() {
        Firebase.auth.createUserWithEmailAndPassword("test@test.com", "Test1#")
        Page.on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("Test1#")
            .tapOnSignIn()
            .on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnAccount()
            .on<AccountPage>()
            .tapOnDeleteAccount()
            .on<AlertDialogPage>()
            .tapAlertPositive()
            .on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("Test1#")
            .tapOnSignIn()
            .on<LoginPage>()
            .verify()
        assert(Firebase.auth.currentUser == null)

    }

    @Test
    fun signInAndChangePasswordSuccessfully() {
        Firebase.auth.createUserWithEmailAndPassword("test@test.com", "Test1#")
        Page.on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("Test1#")
            .tapOnSignIn()
            .on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnAccount()
            .on<AccountPage>()
            .tapOnChangePassword()
            .on<ChangePasswordPage>()
            .typeOldPassword("Test1#")
            .typeNewPassword("NewP@ssw0rd")
            .typeConfirmNewPassword("NewP@ssw0rd")
            .tapOnChangePassword()

        Espresso.pressBack()
        Espresso.pressBack()

        Page.on<MenuPage>()
            .tapOnLogOut()
            .on<AlertDialogPage>()
            .tapAlertPositive()
            .on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("NewP@ssw0rd")
            .tapOnSignIn()
            .on<MainPage>()
            .verify()
        assert(Firebase.auth.currentUser!!.email == "test@test.com")

        Firebase.auth.currentUser!!.delete()

        }

    @Test
    fun signInAndChangePasswordConfirmationFail() {
        Firebase.auth.createUserWithEmailAndPassword("test@test.com", "Test1#")
        Page.on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("Test1#")
            .tapOnSignIn()
            .on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnAccount()
            .on<AccountPage>()
            .tapOnChangePassword()
            .on<ChangePasswordPage>()
            .typeOldPassword("Test1#")
            .typeNewPassword("password_not_matching")
            .typeConfirmNewPassword("different_password")
            .tapOnChangePassword()

        Espresso.pressBack()
        Espresso.pressBack()

        Page.on<MenuPage>()
            .tapOnLogOut()
            .on<AlertDialogPage>()
            .tapAlertPositive()
            .on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("password_not_matching")
            .tapOnSignIn()
            .on<LoginPage>()
            .verify()
        assert(Firebase.auth.currentUser == null)

        }

    @Test
    fun signInAndChangePasswordWeakFail() {
        Firebase.auth.createUserWithEmailAndPassword("test@test.com", "Test1#")
        Page.on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("Test1#")
            .tapOnSignIn()
            .on<MainPage>()
            .on<TopBarPage>()
            .tapOnMenu()
            .on<MenuPage>()
            .tapOnAccount()
            .on<AccountPage>()
            .tapOnChangePassword()
            .on<ChangePasswordPage>()
            .typeOldPassword("Test1#")
            .typeNewPassword("weak")
            .typeConfirmNewPassword("weak")
            .tapOnChangePassword()

        Espresso.pressBack()
        Espresso.pressBack()

        Page.on<MenuPage>()
            .tapOnLogOut()
            .on<AlertDialogPage>()
            .tapAlertPositive()
            .on<LoginPage>()
            .typeEmail("test@test.com")
            .typePassword("password_not_matching")
            .tapOnSignIn()
            .on<LoginPage>()
            .verify()
        assert(Firebase.auth.currentUser == null)

        }

    }