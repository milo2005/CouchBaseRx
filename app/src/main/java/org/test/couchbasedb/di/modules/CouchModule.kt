package org.test.couchbasedb.di.modules

import android.content.Context
import com.couchbase.lite.*

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import org.test.couchbasedb.R
import org.test.couchbasedb.data.preferences.UserSession
import java.io.File
import java.net.URI
import javax.inject.Named
import javax.inject.Singleton

@Module
class CouchModule{

    @Provides
    @Singleton
    @Named("dbName")
    fun provideDataBaseName():String = "ganko-database"

    @Provides
    @Singleton
    fun provideFolderName(context:Context): File = context.filesDir

    @Provides
    fun provideDataBase(context:Context, @Named("dbName") name:String):Database{
        val config = DatabaseConfiguration(context)
        val db = Database(name, config)
        db.createIndex("TypeIndex", IndexBuilder.valueIndex(ValueIndexItem.property("type")))
        return db
    }

    @Provides
    @Singleton
    fun provideMapper():ObjectMapper = ObjectMapper()

    @Provides
    @Singleton
    fun provideReplicator(context:Context, database: Database, session:UserSession): Replicator {
        val config = ReplicatorConfiguration(database, URLEndpoint(URI(context.getString(R.string.url_sync))))
        config.replicatorType = ReplicatorConfiguration.ReplicatorType.PUSH_AND_PULL
        config.isContinuous = true
        //config.authenticator = BasicAuthenticator("demo", "123456")
        config.channels = mutableListOf(session.userId)
        return Replicator(config)
    }


}
