package sel.group9.squared2.UI.ColorScreen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.*
import org.mockito.kotlin.*
import sel.group9.squared2.ui.screens.ColorSelectionScreen
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.colorList

class ButtonCallbackTests {
    @get:Rule
    val testComposable = createComposeRule()
    
    val color = 0
    val onBackTest = mock<()->Unit>()
    val onSelectTest = mock<(Int)->Unit>()
    
    @Before
    fun initialiseComposable() {
        testComposable.setContent {
            SquaredTheme {
                ColorSelectionScreen(
                    colorList[color],
                    onBack = onBackTest,
                    onSelect = onSelectTest
                )
            }
        }
    }
    
    @After
    fun validate() {
        validateMockitoUsage()
    }
    
    @Test
    fun testOnClickColoSelectCallsCallback()
    {
        val nodes = testComposable.onAllNodesWithTag("ColorSelectionBox")
        for (index in colorList.indices)
        {
            nodes[index].performClick()
            verify(onSelectTest, times(1))(index)
        }
    }
    
    @Test
    fun testOnClickOkayCallsCallback()
    {
        testComposable
            .onNodeWithTag("Okay")
            .performClick()
        testComposable.waitForIdle()
        verify(onBackTest, atLeastOnce())()
    }

}
