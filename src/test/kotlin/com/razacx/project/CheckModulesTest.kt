package com.razacx.project

import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules

class CheckModulesTest: UnitTest() {

    @Test
    fun `check if all of koin's module definitions can be loaded`() {
        koinApplication {
            printLogger()
            modules(koinModule)
        }.checkModules()
    }

}
