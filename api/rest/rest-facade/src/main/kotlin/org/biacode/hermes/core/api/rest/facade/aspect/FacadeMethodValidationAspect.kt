package org.biacode.hermes.core.api.rest.facade.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.biacode.hermes.core.api.rest.model.common.ResultResponseModel
import org.biacode.hermes.core.api.rest.model.common.ValidatableRequestModel
import org.springframework.stereotype.Component

/**
 * User: Arthur Asatryan
 * Company: SFL LLC
 * Date: 10/28/15
 * Time: 11:15 AM
 */
@Aspect
@Component
class FacadeMethodValidationAspect {

    //region Public methods
    @Around("execution(public * com.biacode.biacentric.centric.rest.facade..* (..)) && args(validatableRequest,..)")
    @Throws(Throwable::class)
    fun around(point: ProceedingJoinPoint, validatableRequest: ValidatableRequestModel): Any {
        val validationResult = validatableRequest.validate()
        if (validationResult.isNotEmpty()) {
            return ResultResponseModel(result = null, errors = validationResult)
        }
        return point.proceed()
    }
    //endregion
}