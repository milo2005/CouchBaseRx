package org.test.couchbasedb.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.test.couchbasedb.di.ViewModelKey
import org.test.couchbasedb.ui.Main1ViewModel
import org.test.couchbasedb.ui.Main2ViewModel
import org.test.couchbasedb.util.AppViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(Main1ViewModel::class)
    abstract fun bindMain1ViewModel(main1ViewModel: Main1ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(Main2ViewModel::class)
    abstract fun bindMain2ViewModel(main2ViewModel: Main2ViewModel): ViewModel


}