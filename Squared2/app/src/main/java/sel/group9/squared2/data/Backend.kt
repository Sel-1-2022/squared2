package sel.group9.squared2.data

import androidx.compose.ui.graphics.Color

class Backend {
    var color = Color.Red
    fun color(): Color {
        return color
    }
    fun changeColor(new:Color){
        color=new
    }
}