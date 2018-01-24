package org.biacode.hermes.core.api.rest.resource.test.helper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.biacode.hermes.core.api.rest.model.common.RequestModel
import org.biacode.hermes.core.api.rest.model.common.ResponseModel
import org.biacode.hermes.core.api.rest.model.common.ResultResponseModel
import org.biacode.hermes.core.service.test.helper.ServiceIntegrationTestHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 6:59 PM
 */
@Component
class ResourceIntegrationTestHelper {

    //region Dependencies
    @Autowired
    private lateinit var jacksonObjectMapper: ObjectMapper

    @Autowired
    private lateinit var serviceIntegrationTestHelper: ServiceIntegrationTestHelper
    //endregion

    //region Public methods
    fun <T : RequestModel> toJson(request: T): String = jacksonObjectMapper.writeValueAsString(request)

    fun <T : ResponseModel> fromJson(
            contentAsByteArray: ByteArray,
            typeReference: TypeReference<ResultResponseModel<T>>
    ): ResultResponseModel<T> = jacksonObjectMapper.readValue<ResultResponseModel<T>>(contentAsByteArray, typeReference)
    //endregion
}