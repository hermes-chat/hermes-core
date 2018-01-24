package org.biacode.hermes.core.api.rest.resource.account

import com.fasterxml.jackson.core.type.TypeReference
import org.assertj.core.api.Assertions.assertThat
import org.biacode.hermes.core.api.rest.model.account.request.CreateAccountRequest
import org.biacode.hermes.core.api.rest.model.account.response.CreateAccountResponse
import org.biacode.hermes.core.api.rest.model.common.ResultResponseModel
import org.biacode.hermes.core.api.rest.resource.test.AbstractResourceIntegrationTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 7:55 PM
 */
class AccountResourceIntegrationTest : AbstractResourceIntegrationTest() {

    //region Dependencies
    @Autowired
    private lateinit var mvc: MockMvc
    //endregion

    //region Test methods
    @Test
    fun `test create`() {
        val requestJson = helper.toJson(CreateAccountRequest("biacoder@gmail.com"))
        val contentAsByteArray = mvc
                .perform(
                        post("/account")
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk)
                .andReturn()
                .response
                .contentAsByteArray
        val result: ResultResponseModel<CreateAccountResponse> = helper.fromJson(
                contentAsByteArray,
                object : TypeReference<ResultResponseModel<CreateAccountResponse>>() {}
        )
        val uuid = result.result!!.uuid
        assertThat(uuid).isNotNull()
    }
    //endregion

}