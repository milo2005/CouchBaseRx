package org.test.couchbasedb.data


class Bovine(var nombre: String, var proposito: String, var propietario: BovineOwner?) {
    val type = "bovine"
    var _id: String? = null
    var _sequence: String? = null
    constructor() : this("", "", null)
}

class BovineOwner(var nombre: String,
                  var celular: String) {
    constructor() : this("", "2")
}