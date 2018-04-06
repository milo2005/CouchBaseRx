package org.test.couchbasedb.ui

import android.arch.lifecycle.ViewModel
import com.couchbase.lite.*
import com.couchbase.lite.Dictionary
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import org.test.couchbasedb.util.andEx
import org.test.couchbasedb.util.equalEx
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class Main1ViewModel @Inject constructor(private val db: Database,
                                         @Named("dbName") private val dbName: String) : ViewModel() {

    fun insert(userId: String, nombre: String, genero: String, proposito: String) {
        val id = "$userId.${UUID.randomUUID()}"
        val doc = MutableDocument(id)
        doc.setString("type", "bovino")
        doc.setString("nombre", nombre)
        doc.setString("genero", genero)
        doc.setString("proposito", proposito)
        doc.setDate("createdAt", Date())
        doc.setDate("updatedAt", Date())

        db.save(doc)
    }

    fun update(id: String, nombre: String, proposito: String) {
        val doc = db.getDocument(id).toMutable()
        doc.setString("nombre", nombre)
        doc.setString("proposito", proposito)
        doc.setDate("updatedAt", Date())

        db.save(doc)
    }

    fun remove(id: String) {
        val doc = db.getDocument(id)
        db.delete(doc)
    }

    fun getById(id: String): Document = db.getDocument(id)

    fun getByProposito(proposito: String): Single<List<Dictionary>> {
        val query = QueryBuilder
                .select(SelectResult.all())
                .from(DataSource.database(db))
                .where(Expression.property("type")
                        .equalTo(Expression.string("bovino"))
                        .and(Expression.property("proposito").equalTo(Expression.string(proposito))))

        return query.execute().toObservable()
                .map {

                    it.getDictionary(dbName) }
                .toList()
    }



    fun getByNombre(nombre:String): Maybe<Dictionary> {
        val query = QueryBuilder
                .select(SelectResult.all())
                .from(DataSource.database(db))
                .where(Expression.property("type").equalTo(Expression.string("bovino"))
                        .and(Expression.property("nombre").like(Expression.string("%$nombre%"))))
        return query.execute().toObservable()
                .firstElement()
                .map { it.getDictionary(dbName) }
    }

}