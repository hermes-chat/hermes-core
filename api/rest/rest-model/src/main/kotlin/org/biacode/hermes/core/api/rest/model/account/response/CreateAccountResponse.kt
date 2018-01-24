package org.biacode.hermes.core.api.rest.model.account.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.biacode.hermes.core.api.rest.model.common.ResponseModel

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 4:23 PM
 */
data class CreateAccountResponse(
        @JsonProperty("uuid")
        val uuid: String
) : ResponseModel