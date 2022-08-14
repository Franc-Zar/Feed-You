import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.app1.R
import com.example.app1.utilities.Page
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


class MainPage : Page() {

    override fun verify(): MainPage {
        Thread.sleep(15000)

        onView(withId(R.id.rv))
            .check(matches(isDisplayed()))
        return this
    }

    fun tapOnNews(): MainPage {
        onView(withId(R.id.rv)).perform(ViewActions.click())
        return this
    }
}

class TopBarPage: Page() {
    override fun verify(): TopBarPage {
        onView(withId(R.id.app_bar_search))
            .check(matches(isDisplayed()))
        return this
    }

    fun tapOnMenu() : TopBarPage {
        onView(withContentDescription("btn_menu")).perform(ViewActions.click())
        return this
    }
}


class NewsPage: Page() {
    override fun verify(): NewsPage {
        Thread.sleep(1000)
        onView(withId(R.id.web_view))
            .check(matches(isDisplayed()))
        return this
    }

    fun tapOnBlock() : NewsPage {
        onView(withId(R.id.btn_block)).perform(ViewActions.click())
        return this
    }
}

class MenuPage: Page() {
    override fun verify(): MenuPage {
        onView(withId(R.id.sv_menu))
            .check(matches(isDisplayed()))
        return this
    }

    fun tapOnOption(option: Int) : MenuPage{
        onView(withId(option)).perform(ViewActions.click())
        return this
    }

    fun tapOnAccount(): MenuPage {
        onView(withId(R.id.account_settings)).perform(ViewActions.click())
        return this
    }

    fun tapOnLogOut(): MenuPage {
        onView(withId(R.id.logout)).perform(ViewActions.click())
        return this
    }
}

class AccountPage: Page() {
    override fun verify(): AccountPage {
        Thread.sleep(1000)
        onView(withId(R.id.decoration))
            .check(matches(isDisplayed()))
        return this
    }

    fun tapOnDeleteAccount(): AccountPage {
        onView(withId(R.id.delete_account)).perform(ViewActions.click())
        return this
    }

    fun tapOnChangePassword(): AccountPage {
        onView(withId(R.id.btn_continue)).perform(ViewActions.click())
        return this
    }
}

class ChangePasswordPage: Page() {
    override fun verify(): ChangePasswordPage {
        onView(withId(R.id.btn_continue))
            .check(matches(isDisplayed()))
        return this
    }

    fun typeOldPassword(oldPassword: String): ChangePasswordPage {
        onView(firstView(withId(R.id.old_password))).perform(ViewActions.typeText(oldPassword))
        return this
    }

    fun typeNewPassword(newPassword: String): ChangePasswordPage {
        onView(firstView(withId(R.id.new_password))).perform(ViewActions.typeText(newPassword))
        return this
    }

    fun typeConfirmNewPassword(confirmNewPassword: String): ChangePasswordPage {
        onView(firstView(withId(R.id.confirm_password))).perform(ViewActions.typeText(confirmNewPassword))
        return this
    }

    fun tapOnChangePassword(): ChangePasswordPage {
        onView(withId(R.id.btn_continue)).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btn_continue)).perform(ViewActions.click())
        return this
    }
}

class AlertDialogPage: Page() {
    override fun verify(): AlertDialogPage {
        onView(withId(R.id.logo))
            .check(matches(isDisplayed()))
        return this
    }

    fun tapAlertPositive(): AlertDialogPage {
        onView(withId(android.R.id.button1)).perform(ViewActions.click());
        return this
    }

    fun tapAlertNegative(): AlertDialogPage {
        onView(withId(android.R.id.button2)).perform(ViewActions.click());
        return this
    }
}

class SingleFeedPage: Page(){
    override fun verify(): SingleFeedPage {
        onView(withId(R.id.btn_reset))
            .check(matches(isDisplayed()))
        return this
    }

    fun tapOnFeed(): SingleFeedPage{
        onView(firstView(withId(R.id.btn_link)))
            .perform(ViewActions.click())
        return this
    }

    fun tapOnReset(): SingleFeedPage{
        onView(firstView(withId(R.id.btn_reset))).perform(ViewActions.click())
        return this
    }
}

private fun <T> firstView(matcher: Matcher<T>): Matcher<T>? {
    return object : BaseMatcher<T>() {
        var isFirst = true
        override fun matches(item: Any): Boolean {
            if (isFirst && matcher.matches(item)) {
                isFirst = false
                return true
            }
            return false
        }

        override fun describeTo(description: Description) {
            description.appendText("should return first matching item")
        }
    }
}

class BlockPage: Page() {
    override fun verify(): BlockPage {
        onView(withId(R.id.lay_blockedLinks)).check(matches(isDisplayed()))
        return this
    }

    fun tapOnFeed(): BlockPage {
        onView(firstView(withId(R.id.btn_link))).perform(ViewActions.click())
        return this
    }
}

class LoginPage: Page() {
    override fun verify(): LoginPage {
        Thread.sleep(1000)
        onView(withId(R.id.logo)).check(matches(isDisplayed()))
        return this
    }

    fun tapOnAnonymous(): LoginPage {
        onView(firstView(withId(R.id.anonymous_login))).perform(ViewActions.click())
        return this
    }

    fun typeEmail(email: String): LoginPage {
        onView(firstView(withId(R.id.email))).perform(ViewActions.typeText(email))
        return this
    }

    fun typePassword(password: String): LoginPage {
        onView(firstView(withId(R.id.password))).perform(ViewActions.typeText(password))
        return this
    }

    fun tapOnSignIn(): LoginPage {
        onView(withId(R.id.sign_in)).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.sign_in)).perform(ViewActions.click())
        return this
    }

    fun tapOnSignUp(): LoginPage {
        onView(firstView(withId(R.id.sign_up))).perform(ViewActions.click())
        Thread.sleep(100)
        return this
    }

    fun tapOnForgotPassword(): LoginPage {
        onView(firstView(withId(R.id.forgot_password))).perform(ViewActions.click())
        return this
    }
}


class SignUpPage: Page() {
    override fun verify(): SignUpPage {
        onView(withId(R.id.sign_up)).check(matches(isDisplayed()))
        return this
    }

    fun tapOnSignIn(): SignUpPage {
        onView(firstView(withId(R.id.sign_in))).perform(ViewActions.click())
        return this
    }

    fun tapOnSignUp(): SignUpPage {
        onView(withId(R.id.sign_up)).perform(ViewActions.closeSoftKeyboard())
        onView(firstView(withId(R.id.sign_up))).perform(ViewActions.click())
        Thread.sleep(100)
        return this
    }

    fun typeEmail(email: String): SignUpPage {
        onView(firstView(withId(R.id.email))).perform(ViewActions.typeText(email))
        return this
    }

    fun checkTermsPolicy(): SignUpPage {
        onView(withId(R.id.terms_policy)).perform(ViewActions.closeSoftKeyboard())
        onView(firstView(withId(R.id.terms_policy))).perform(ViewActions.click())
        return this
    }

    fun typePassword(password: String): SignUpPage {
        onView(firstView(withId(R.id.password))).perform(ViewActions.typeText(password))
        return this
    }

}


class PasswordRecoveryPage: Page() {
    override fun verify(): PasswordRecoveryPage {
        onView(withId(R.id.reset_password)).check(matches(isDisplayed()))
        return this
    }

    fun tapOnSignIn(): PasswordRecoveryPage {
        onView(firstView(withId(R.id.sign_in))).perform(ViewActions.click())
        return this
    }

}

class PreferencePage: Page() {
    override fun verify(): PreferencePage {
        Thread.sleep(1000)
        onView(withId(R.id.btn_continue)).check(matches(isDisplayed()))
        return this
    }

    fun tapOnSelect(): PreferencePage {
        onView(firstView(withId(R.id.btn_continue))).perform(ViewActions.click())
        return this
    }
}