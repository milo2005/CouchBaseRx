package org.test.couchbasedb.data.model

import org.test.couchbasedb.data.couch.CouchEntity


class Bovine(var nombre: String, var proposito: String, var propietario: BovineOwner?):CouchEntity() {
    constructor() : this("", "", null)
}

class BovineOwner(var nombre: String,
                  var celular: String) {
    constructor() : this("", "2")
}