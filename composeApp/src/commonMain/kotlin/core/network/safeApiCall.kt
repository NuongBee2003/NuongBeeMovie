package core.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException

suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (ce: CancellationException) {
        throw ce
    } catch (e: ClientRequestException) {
        val text = runCatching { e.response.bodyAsText() }.getOrNull().orEmpty()
        ApiResult.Error(
            message = "HTTP ${e.response.status.value} ${e.response.status.description}" +
                (if (text.isNotBlank()) ": $text" else ""),
            throwable = e,
        )
    } catch (e: ServerResponseException) {
        val text = runCatching { e.response.bodyAsText() }.getOrNull().orEmpty()
        ApiResult.Error(
            message = "HTTP ${e.response.status.value} ${e.response.status.description}" +
                (if (text.isNotBlank()) ": $text" else ""),
            throwable = e,
        )
    } catch (t: Throwable) {
        ApiResult.Error(message = t.message ?: "Unknown error", throwable = t)
    }
}

