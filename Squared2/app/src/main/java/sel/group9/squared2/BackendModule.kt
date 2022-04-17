package sel.group9.squared2

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class BackendModule {
    @Singleton
    @Provides
    fun provideBackend(): Backend {
        return Backend()
    }
}
