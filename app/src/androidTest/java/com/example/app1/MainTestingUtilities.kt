import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.app1.R
import androidx.test.espresso.assertion.ViewAssertions.matches

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
        onView(withId(R.id.toolbar_main)).perform(ViewActions.click())
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

    fun tapOnFeed() : SingleFeedPage{
        onView(withId(R.id.linkList)).perform(ViewActions.click())
        return this
    }
}