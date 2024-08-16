package bob.e2e.domain.model

data class Pad (
    val id: String,
    var image: String,
    val keys: List<Key>,
)