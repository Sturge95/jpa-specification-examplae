package example.project.specification.model.dto


class Response<RESPONSE_TYPE : Any>(
    val success: Boolean,
    val responseObject: RESPONSE_TYPE?,
    val errors: List<String>?
)

object ResponseCreation {
    fun <T : Any> success(responseObject: T): Response<T> {
        return Response(success = true, responseObject = responseObject, errors = null)
    }

    fun <T : Any> error(errors: List<String>): Response<T> {
        return Response(success = false, errors = errors, responseObject = null)
    }
}