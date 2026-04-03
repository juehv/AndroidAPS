package app.aaps.plugins.main.di

import app.aaps.plugins.main.general.nfcCommands.NfcBuildActivity
import app.aaps.plugins.main.general.nfcCommands.NfcCommandsFragment
import app.aaps.plugins.main.general.nfcCommands.NfcControlActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("unused")
abstract class NFCCommandsModule {
    @ContributesAndroidInjector abstract fun contributesNfcControlActivity(): NfcControlActivity

    @ContributesAndroidInjector abstract fun contributesNfcBuildActivity(): NfcBuildActivity

    @ContributesAndroidInjector abstract fun contributesNfcCommandsFragment(): NfcCommandsFragment
}
