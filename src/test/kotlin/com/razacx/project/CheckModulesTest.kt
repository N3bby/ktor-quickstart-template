package com.razacx.project

import com.razacx.project.config.createBeanDefinitions
import com.razacx.project.test.UnitTest
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules

class CheckModulesTest: UnitTest() {

    @Test
    fun `check if all of koin's module definitions can be loaded`() {
        koinApplication {
            printLogger()
            modules(createBeanDefinitions())
        }.checkModules()
    }

}
