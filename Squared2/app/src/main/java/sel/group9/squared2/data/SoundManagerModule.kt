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
class SoundManagerModule {
    @Singleton
    @Provides
    fun provideBackend(@ApplicationContext appContext: Context): SoundManager {
        return SoundManager(appContext )
    }
}
