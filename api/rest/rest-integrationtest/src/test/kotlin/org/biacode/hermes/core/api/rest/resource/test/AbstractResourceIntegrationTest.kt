package org.biacode.hermes.core.api.rest.resource.test

import org.biacode.hermes.core.api.rest.resource.HermesRestApplication
import org.biacode.hermes.core.api.rest.resource.test.helper.ResourceIntegrationTestHelper
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 6:38 PM
 */
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [(HermesRestApplication::class)])
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application-integrationtest.properties"])
abstract class AbstractResourceIntegrationTest {
    @Autowired
    protected lateinit var helper: ResourceIntegrationTestHelper
}