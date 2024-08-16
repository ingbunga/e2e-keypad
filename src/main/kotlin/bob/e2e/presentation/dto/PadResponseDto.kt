package bob.e2e.presentation.dto

import bob.e2e.domain.model.Pad

class PadResponseDto (
    val id: String,
    val image: String,
    val keys: List<String>
) {
    companion object {
        fun from(pad: Pad) =
            PadResponseDto(
                id = pad.id,
                image = pad.image,
                keys = pad.keys.map { it.id }
            )
    }
}