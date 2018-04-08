package org.test.couchbasedb.ui

import android.arch.lifecycle.ViewModel
import com.couchbase.lite.Expression
import io.reactivex.Maybe
import io.reactivex.Single
import org.test.couchbasedb.data.model.Bovine
import org.test.couchbasedb.data.couch.CouchRxMapper
import org.test.couchbasedb.util.equalEx
import org.test.couchbasedb.util.likeEx
import javax.inject.Inject

class Main2ViewModel @Inject constructor(private val db:CouchRxMapper):ViewModel(){

    fun insert(bovine: Bovine): Single<String> =
            db.insert(bovine)

    fun update(bovine: Bovine): Single<Unit> =
            db.update(bovine._id!!,bovine)

    fun remove(id:String): Single<Unit> =
            db.remove(id)

    fun getById(id:String): Maybe<Bovine> =
            db.oneById(id, Bovine::class)

    fun getByProposito(proposito:String): Single<List<Bovine>> =
             db.listByExp("proposito" equalEx proposito, Bovine::class)


    fun getByOwnerName(name:String): Single<List<Bovine>> =
            db.listByExp("propietario.nombre" likeEx "%$name%", Bovine::class)

}