package org.test.couchbasedb.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import org.test.couchbasedb.App
import org.test.couchbasedb.di.modules.AppModule
import org.test.couchbasedb.di.modules.CouchModule
import org.test.couchbasedb.di.modules.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityComponents::class,
    AppModule::class,
    ViewModelModule::class,
    CouchModule::class
])
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


}