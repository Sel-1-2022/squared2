package sel.group9.squared2

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquaredRepository@Inject constructor(val backend: Backend) {
    fun getTest():Boolean{
        return backend.test()
    }
}
