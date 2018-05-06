package org.test.couchbasedb.ui

import android.arch.lifecycle.ViewModel
import com.couchbase.lite.Expression
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import org.test.couchbasedb.data.model.Bovine
import org.test.couchbasedb.data.couch.CouchRxMapper
import org.test.couchbasedb.util.applySchedulers
import org.test.couchbasedb.util.equalEx
import org.test.couchbasedb.util.inEx
import org.test.couchbasedb.util.likeEx
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class Main2ViewModel @Inject constructor(private val db:CouchRxMapper):ViewModel(){

    fun insert(bovine: Bovine): Single<String> =
            db.insert(bovine)
                    .applySchedulers()

    fun update(bovine: Bovine): Single<Unit> =
            db.update(bovine._id!!,bovine)
                    .applySchedulers()

    fun remove(id:String): Single<Unit> =
            db.remove(id)
                    .applySchedulers()

    fun getById(id:String): Maybe<Bovine> =
            db.oneById(id, Bovine::class)
                    .applySchedulers()

    fun getByProposito(proposito:String): Observable<List<Bovine>> =
             db.listObsByExp("proposito" equalEx proposito, Bovine::class)
                     .applySchedulers()



    fun getByOwnerName(name:String): Single<List<Bovine>> =
            db.listByExp("propietario.nombre" likeEx "%$name%", Bovine::class)
                    .applySchedulers()

    fun addImage(id:String, field:String, input:InputStream):Single<Pair<String, String>> =
            db.putBlob(id, field, "image/jpeg",input)
                    .applySchedulers()

    fun getImage(id:String, field:String):Maybe<File> = db.getFile(id, field)
            .applySchedulers()

    fun getBovinoByVacuna(vacuna:String):Single<List<Bovine>> =
            db.listByExp("vacunas" inEx vacuna, Bovine::class)
                    .applySchedulers()


}