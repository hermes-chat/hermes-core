package org.biacode.hermes.core.api.rest.facade.test

import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:56 PM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractFacadeImplTest : EasyMockSupport() {
    protected val helper = FacadeImplTestHelperTestHelper()
}