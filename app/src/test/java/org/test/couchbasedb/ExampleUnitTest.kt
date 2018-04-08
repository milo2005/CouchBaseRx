package org.test.couchbasedb

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Test
import org.test.couchbasedb.data.model.Bovine
import org.test.couchbasedb.data.model.BovineOwner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val bvn = Bovine()
        bvn.nombre = "Hola"
        bvn.propietario = BovineOwner("Dario", "301")

        val mapper = ObjectMapper()
        val map = mapper.convertValue(bvn, Map::class.java) as MutableMap<String, Any>

        println("NOMBRE=>${map["nombre"]}")
        println("PROPIETARIO=>${(map["propietario"] as Map<String, Any>)["nombre"]}")
        println("TYPE=>${map["type"]}")

        assertEquals(4, 2 + 2)
    }
}
