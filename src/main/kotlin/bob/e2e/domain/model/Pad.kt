package bob.e2e.domain.model

data class Pad (
    val id: String,
    var image: String,
    val keys: List<Key>,
    val createdAt: Long,
) {
    fun keysToMapI2N(): Map<String, String> {
        return keys.filter { it.number >= 0 }
                   .associate { it.id to it.number.toString() }
    }

    fun keysToMapN2I(): Map<String, String> {
        return keys.filter { it.number >= 0 }
            .associate { it.number.toString() to it.id }
    }
}