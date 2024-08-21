package bob.e2e.external.client

import bob.e2e.domain.model.Pad
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class ServerEndpointClient(
    webClientBuilder: WebClient.Builder,
) {
    private val webClient: WebClient = webClientBuilder
        .baseUrl("http://146.56.119.112:8081")
        .build()

    suspend fun sendAuthCoroutine(pad: Pad, userInput: String): String {
        val requestBody = mapOf(
            "userInput" to userInput,
            "keyHashMap" to pad.keysToMapN2I()
        )

        return webClient.post().uri("/auth")
            .bodyValue(requestBody)
            .retrieve()
            .awaitBody<String>()
    }
}
