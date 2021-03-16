package dev.ch8n.toiweather.ui

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import dev.ch8n.toiweather.TOIApp
import dev.ch8n.toiweather.MainCoroutineRule
import dev.ch8n.toiweather.R
import dev.ch8n.toiweather.data.remote.model.WeatherResponse
import dev.ch8n.toiweather.data.remote.sources.WeatherSource
import dev.ch8n.toiweather.utils.Result
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
@Config(application = TOIApp::class)
class MainActivityTest {

    private lateinit var weatherSource: WeatherSource

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun `is Activity displaying`() {
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { view ->
            Truth.assertThat(view.hasWindowFocus()).isTrue()
        }
    }




    @Test
    fun `is loading Called and loader visible`() {
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { view ->
            val spyView = spyk<MainActivity>(view)
            Espresso.onView(ViewMatchers.withId(R.id.image_loading))
                .check { view, noViewFoundException ->
                    Truth.assertThat(view.visibility == View.VISIBLE).isTrue()
                }
        }
    }

    @Test
    fun `on Error and Retry visible`() {
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { view ->
            val spyView = spyk<MainActivity>(view)
            Espresso.onView(ViewMatchers.withId(R.id.btn_retry))
                .check { view, noViewFoundException ->
                    Truth.assertThat(view.visibility == View.VISIBLE).isTrue()
                }
        }
    }

    @Test
    fun `on retry loading is visible`() {
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { view ->
            val spyView = spyk<MainActivity>(view)
            Espresso.onView(ViewMatchers.withId(R.id.btn_retry))
                .check { view1, _ ->

                    Truth.assertThat(view1.visibility == View.VISIBLE).isTrue()

                    Espresso.onView(ViewMatchers.withId(R.id.btn_retry))
                        .perform(ViewActions.click())

                    Espresso.onView(ViewMatchers.withId(R.id.image_loading))
                        .check { view2, _ ->
                            Truth.assertThat(view1.visibility == View.VISIBLE).isTrue()
                        }
                }

        }
    }

    @Test
    fun `on success result data updates on view`() {
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { view ->
            val spyView = spyk<MainActivity>(view)
            val result = Result.build { WeatherResponse.fake() }
            Espresso.onView(ViewMatchers.withId(R.id.text_current_temp))
                .check { view2, _ ->
                    Truth.assertThat((view2 as AppCompatTextView).text.toString()).contains("10")
                }
            Espresso.onView(ViewMatchers.withId(R.id.text_current_city))
                .check { view3, _ ->
                    Truth.assertThat((view3 as AppCompatTextView).text.toString()).contains("delhi")
                }
        }
    }


}