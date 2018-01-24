package org.biacode.hermes.core.service.test

import org.biacode.hermes.core.service.test.helper.ServiceImplTestHelper
import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:56 PM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractServiceImplTest : EasyMockSupport() {
    protected val helper = ServiceImplTestHelper()
}