package org.test.couchbasedb.di.components

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.test.couchbasedb.di.ActivityScope
import org.test.couchbasedb.ui.MainActivity

@Module
abstract class ActivityComponents{

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity

}