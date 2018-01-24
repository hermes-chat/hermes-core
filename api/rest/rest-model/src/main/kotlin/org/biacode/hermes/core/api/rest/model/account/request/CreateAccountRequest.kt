package org.biacode.hermes.core.api.rest.model.account.request

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.validator.routines.EmailValidator
import org.biacode.hermes.core.api.rest.model.common.ErrorCodeModel
import org.biacode.hermes.core.api.rest.model.common.RequestModel
import org.biacode.hermes.core.api.rest.model.common.ValidatableRequestModel

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 4:22 PM
 */
data class CreateAccountRequest(
        @JsonProperty("email")
        val email: String
) : RequestModel, ValidatableRequestModel {
    override fun validate(): MutableMap<ErrorCodeModel, Any> {
        if (!EmailValidator.getInstance().isValid(email)) {
            return mutableMapOf(ErrorCodeModel.INVALID_EMAIL to "invalid email")
        }
        return mutableMapOf()
    }
}