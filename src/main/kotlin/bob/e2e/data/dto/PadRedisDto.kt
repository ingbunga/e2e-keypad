package bob.e2e.data.dto

import bob.e2e.domain.model.Key
import bob.e2e.domain.model.Pad

class PadRedisDto(
    val id: String,
    val keys: List<Key>
) {
    companion object {
        fun from(pad: Pad) =
            PadRedisDto(
                id = pad.id,
                keys = pad.keys
            )
    }
}