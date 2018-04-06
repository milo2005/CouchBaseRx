package org.test.couchbasedb

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.test.couchbasedb.data.preferences.UserSession
import org.test.couchbasedb.di.AppInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var session:UserSession

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        session.userId = "123"
    }

    override fun activityInjector(): AndroidInjector<Activity> = injector

}