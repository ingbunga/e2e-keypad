package bob.e2e.data.dto

import bob.e2e.domain.model.Key
import bob.e2e.domain.model.Pad
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("Pad")
class PadRedisDto(
    @Id
    val id: String,
    val keys: List<Key>,
    val createdAt: Long,
) {
    companion object {
        fun from(pad: Pad) =
            PadRedisDto(
                id = pad.id,
                keys = pad.keys,
                createdAt = pad.createdAt,
            )
    }

    fun toPad() =
        Pad(
            id = id,
            keys = keys,
            createdAt = createdAt,
            image = "",
        )
}