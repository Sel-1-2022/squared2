package sel.group9.squared2.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SettingsModule {
    @Singleton
    @Provides
    fun provideBackend(@ApplicationContext cont: Context): Settings {
        return Settings(cont)
    }
}
