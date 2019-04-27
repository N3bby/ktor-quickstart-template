package com.razacx.project

import org.jetbrains.exposed.sql.Database

interface DatabaseConnector {
    fun connect()
}

class DataBaseConnectorImpl : DatabaseConnector {
    override fun connect() {
        //TODO Load this from a config file maybe
        Database.connect(
            url = "jdbc:h2:file:./db.h2",
            driver = "org.h2.Driver"
        )
    }
}
