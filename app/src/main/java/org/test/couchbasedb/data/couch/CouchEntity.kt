package org.test.couchbasedb.data.couch

open class CouchEntity{

    var _id:String? = null
    var _sequence:Long? = null
    var type:String = javaClass.simpleName
    var channels:List<String> = emptyList()


}