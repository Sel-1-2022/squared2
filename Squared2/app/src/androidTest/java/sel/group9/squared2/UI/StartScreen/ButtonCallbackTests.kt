package sel.group9.squared2.UI.StartScreen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.internal.tls.OkHostnameVerifier.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.*
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.validateMockitoUsage
import org.mockito.kotlin.verify
import sel.group9.squared2.ui.screens.StartScreen
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.warmYellow

class ButtonCallbackTests {
    @get:Rule
    val testComposable = createComposeRule()

    val name = "name"
    val onChangeTest = mock<(String)->Unit>()
    val onPlayTest = mock<()->Unit>()
    val onColorTest = mock<()->Unit>()
    val onSettingsTest = mock<()->Unit>()

    @Before
    fun initialiseComposable() {
        testComposable.setContent {
            SquaredTheme {
                StartScreen(
                    name,
                    onChange= onChangeTest,
                    onStart = onPlayTest,
                    onColorPressed = onColorTest,
                    onCogPressed = onSettingsTest,
                    color = warmYellow
                )
            }
        }
    }
    
    @After
    fun validate() {
        validateMockitoUsage()
    }
    
    @Test
    fun testOnClickSettingsCallsCallback() {
        testComposable
            .onNodeWithContentDescription("Settings")
            .performClick()
        testComposable.waitForIdle()
        verify(onSettingsTest, atLeastOnce())()
    }
    
    @Test
    fun testOnClickColorCallsCallback() {
        testComposable
            .onNodeWithTag("Color")
            .performClick()
        testComposable.waitForIdle()
        verify(onColorTest, atLeastOnce())()
    }
    
    @Test
    fun testOnClickPlayCallsCallback() {
        testComposable
            .onNodeWithTag("Play")
            .performClick()
        testComposable.waitForIdle()
        verify(onPlayTest, atLeastOnce())()
    }

}
