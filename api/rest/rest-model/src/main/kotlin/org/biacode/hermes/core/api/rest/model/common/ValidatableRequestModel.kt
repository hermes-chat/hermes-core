package org.biacode.hermes.core.api.rest.model.common

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 5:04 PM
 */
interface ValidatableRequestModel {
    fun validate(): MutableMap<ErrorCodeModel, Any>
}