import androidx.test.espresso.Espresso

open class Page {
    //pattern Page

    companion object {
        inline fun <reified T : Page> on(): T {
            return Page().on()
        }
    }

    inline fun <reified T : Page> on(): T {
        val page = T::class.constructors.first().call()
        page.verify()
        Thread.sleep(500) //To give time when asynchronous calls are invoked
        return page
    }

    open fun verify(): Page {
        // Each subpage should have its default assurances here
        return this
    }

    fun back(): Page {
        Espresso.pressBack()
        return this
    }
}