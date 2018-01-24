package org.biacode.hermes.core.api.rest.facade.test.helper

import org.assertj.core.api.Assertions.assertThat
import org.biacode.hermes.core.api.rest.model.common.ResultResponseModel
import org.biacode.hermes.core.service.test.helper.CommonTestHelper

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 4:46 PM
 */
class FacadeImplTestHelperTestHelper : CommonTestHelper() {

    //region Public methods
    fun assertSuccessResultResponse(resultResponseModel: ResultResponseModel<*>) {
        assertThat(resultResponseModel).isNotNull()
        assertThat(resultResponseModel.hasErrors()).isFalse()
    }
    //endregion
}