import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.app1.R
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


class MainPage : Page() {

    override fun verify(): MainPage {
        Thread.sleep(20000)

        onView(withId(R.id.rv))
            .check(matches(isDisplayed()))
        return this
    }

    fun tapOnNews(): MainPage {
        onView(withId(R.id.rv)).perform(ViewActions.click())
        return this
    }

}

class TopBarPage: Page(){
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