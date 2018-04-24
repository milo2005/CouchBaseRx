package org.test.couchbasedb.ui

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.couchbase.lite.Replicator
import com.jakewharton.rxbinding2.view.clicks
import com.squareup.picasso.Picasso
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
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

    @Inject
    lateinit var replicator: Replicator

    val dis: LifeDisposable = LifeDisposable(this)

    var counter: Int = 0

    val viewModel: Main2ViewModel by lazy { buildViewModel<Main2ViewModel>(factory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()

        dis add viewModel.getByProposito("leche")
                .filter{ it.isNotEmpty() }
                .flatMapMaybe {
                    viewModel.getImage(it[0]._id!!, "gato.jpg") }
                .subscribeBy(
                        onNext = {
                            Picasso.get().load(it)
                                    .into(img)
                            Log.i("hola", "")
                        },
                        onError = {
                            Log.i("hola", "")
                        },
                        onComplete = {
                            Log.i("hola", "")
                        }
                )

        dis add btnInsert.clicks()
                .flatMapSingle {
                    counter++
                    viewModel.insert(Bovine("bovine $counter", "leche", BovineOwner("dario", "301")))

                }
                .flatMapSingle {id-> viewModel.addImage(id, "gato.jpg", assets.open("gato.jpg"))}
                .flatMapMaybe { viewModel.getImage(it.first, it.second) }
                .subscribeBy(
                        onNext = {
                            Picasso.get().load(it)
                                    .into(img)
                        },
                        onError = {
                            Log.i("hola", "")
                        },
                        onComplete = {
                            Log.i("hola", "")
                        }
                )

        dis add btnChannel.clicks()
                .subscribe {
                    replicator.config.channels.add("123")
                    replicator.stop()
                    replicator.start()
                    Log.i("hola", "")
                }

    }
}
