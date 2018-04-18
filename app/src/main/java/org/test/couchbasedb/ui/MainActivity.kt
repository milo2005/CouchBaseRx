package org.test.couchbasedb.ui

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxkotlin.subscribeBy
import org.test.couchbasedb.R
import org.test.couchbasedb.data.model.Bovine
import org.test.couchbasedb.data.model.BovineOwner
import org.test.couchbasedb.di.Injectable
import org.test.couchbasedb.util.LifeDisposable
import org.test.couchbasedb.util.buildViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    val dis: LifeDisposable = LifeDisposable(this)

    val viewModel: Main2ViewModel by lazy { buildViewModel<Main2ViewModel>(factory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bovine = Bovine("Lupita", "Leche", BovineOwner("Dar", "301"))

        viewModel.insert(bovine)
                .flatMapMaybe { viewModel.getById(it) }
                .flatMapSingle { viewModel.getByOwnerName("Fer") }
                .subscribeBy(
                        onError = {
                            Log.i("Lupita", "")},
                        onSuccess = {
                            Log.i("Lupita", "")}
                )


    }
}
