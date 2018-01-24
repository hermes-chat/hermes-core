package org.biacode.hermes.core.api.rest.model.common

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 4:16 PM
 */
class ResultResponseModel<out S>(
        @JsonProperty("result")
        val result: S? = null,
        @JsonProperty("errors")
        var errors: MutableMap<ErrorCodeModel, Any> = mutableMapOf()
) where S : ResponseModel {
    fun hasErrors() = errors.isNotEmpty()
}