package sel.group9.squared2.UI.SettingsScreen

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.internal.tls.OkHostnameVerifier.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.*
import org.mockito.kotlin.*
import sel.group9.squared2.ui.screens.ColorSelectionScreen
import sel.group9.squared2.ui.screens.SettingsScreen
import sel.group9.squared2.ui.screens.StartScreen
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.colorList
import sel.group9.squared2.ui.theme.warmYellow

class ButtonCallbackTests {
    
    @get:Rule
    val testComposable = createComposeRule()
    val sound = 0.5f
    val music = 0.5f
    val onSoundChangeTest = mock<(Float)->Unit>()
    val onMusicChangeTest = mock<(Float)->Unit>()
    val onBackTest = mock<()->Unit>()
    val onConfirmTest = mock<()->Unit>()
    
    @Before
    fun initialiseComposable() {
        testComposable.setContent {
            SquaredTheme {
                SettingsScreen(
                    sound,
                    music,
                    onSoundChangeTest,
                    onMusicChangeTest,
                    onBackTest,
                    onConfirmTest
                )
            }
        }
    }
    
    @After
    fun validate() {
        validateMockitoUsage()
    }
    
    @Test
    fun testOnClickBackCallsCallback()
    {
        testComposable
            .onNodeWithTag("Back")
            .performClick()
        testComposable.waitForIdle()
        verify(onBackTest, times(1))()
    }
    
    @Test
    fun testOnClickOkayCallsCallback()
    {
        testComposable
            .onNodeWithTag("Okay")
            .performClick()
        testComposable.waitForIdle()
        verify(onConfirmTest, times(1))()
    }
    
    @Test
    fun testOnClickMusicSliderCallsCallback()
    {
        val slider = testComposable.onNodeWithTag("MusicSlider")
        slider.performClick()
        testComposable.waitForIdle()
        verify(onMusicChangeTest, times(1))(0.5f)
    }
    
    @Test
    fun testOnClickSoundSliderCallsCallback()
    {
        val slider = testComposable.onNodeWithTag("SoundSlider")
        slider.performClick()
        testComposable.waitForIdle()
        verify(onSoundChangeTest, times(1))(0.5f)
    }
    
}
