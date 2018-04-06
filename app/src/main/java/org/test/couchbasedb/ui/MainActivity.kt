package org.test.couchbasedb.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.rxkotlin.subscribeBy
import org.test.couchbasedb.R
import org.test.couchbasedb.data.Bovine
import org.test.couchbasedb.data.BovineOwner
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

        /*dis add viewModel.insert(bovine)
                .map {
                    bovine._id = it
                    it
                }
                .flatMapMaybe { viewModel.getById(it) }
                .flatMapSingle {
                    it.nombre = "lupita 3"
                    viewModel.update(it, "nombre")
                }
                .flatMapMaybe { viewModel.getById(bovine._id!!) }
                .subscribeBy(
                        onComplete = { Log.i("COUCHDB", "No se encontro") },
                        onSuccess = {
                            Log.i("COUCHDB", "$it")
                        },
                        onError = { Log.i("COUCHDB", it.message) }

                )*/


        dis add viewModel.getByProposito("Leche")
                .subscribe {
                    res->
                    Log.i("EPA", "${res.size}")
                }

    }
}
