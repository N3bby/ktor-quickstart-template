package com.razacx.project.test

import com.razacx.project.DatabaseConnector
import org.jetbrains.exposed.sql.Database

class DatabaseConnectorTestImpl: DatabaseConnector {
    override fun connect() {
        Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver"
        )
    }
}
