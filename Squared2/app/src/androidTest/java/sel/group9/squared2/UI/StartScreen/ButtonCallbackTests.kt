package sel.group9.squared2.UI.StartScreen

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import sel.group9.squared2.ui.screens.StartScreen
import sel.group9.squared2.ui.theme.SquaredTheme

class ButtonCallbackTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testOnClickSettingsCallsCallback() {
        val name = "name"

        composeTestRule.setContent {
            SquaredTheme {
                StartScreen(name, )
            }
        }
        val test = Mock<(String)->Unit>()
    }


}