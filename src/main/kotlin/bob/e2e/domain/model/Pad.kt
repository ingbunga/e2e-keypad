package bob.e2e.domain.model

data class Pad (
    val id: String,
    var image: String,
    val keys: List<Key>,
    val createdAt: Long,
) {
    fun keysToMap(): Map<String, String> {
        return keys.filter { it.number >= 0 }
                   .associate { it.id to it.number.toString() }
    }
}