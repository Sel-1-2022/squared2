package sel.group9.squared2.UI.StartScreen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import sel.group9.squared2.ui.screens.StartScreen
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.warmYellow

class ButtonCallbackTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testOnClickSettingsCallsCallback() {
        val name = "name"

        val onChangeTest = mock<(String)->Unit>()
        val onLoginTest = mock<()->Unit>()
        val onColorTest = mock<()->Unit>()
        val onSettingsTest = mock<()->Unit>()

        composeTestRule.setContent {
            SquaredTheme {
                StartScreen(
                    name,
                    onChange=onChangeTest,
                    onStart = onLoginTest,
                    onColorPressed = onColorTest,
                    onCogPressed = onSettingsTest,
                    color = warmYellow
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Settings")
            .performClick()

        composeTestRule.waitForIdle()
        verify(onSettingsTest, atLeastOnce())
    }


}