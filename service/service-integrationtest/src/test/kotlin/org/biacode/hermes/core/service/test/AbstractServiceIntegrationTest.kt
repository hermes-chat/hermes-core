package org.biacode.hermes.core.service.test

import org.biacode.hermes.core.service.test.helper.ServiceIntegrationTestHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 6:38 PM
 */
@DataJpaTest
@ContextConfiguration("classpath:serviceContext.xml")
@TestPropertySource(locations = ["classpath:application-integrationtest.properties"])
abstract class AbstractServiceIntegrationTest : AbstractTransactionalJUnit4SpringContextTests() {
    @Autowired
    protected lateinit var helper: ServiceIntegrationTestHelper
}