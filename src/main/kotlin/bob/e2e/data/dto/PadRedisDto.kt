package bob.e2e.data.dto

import bob.e2e.domain.model.Key
import bob.e2e.domain.model.Pad
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

// 여기에 dto라는 이름을 붙여도 되나? 이건 model에 더 가깝지 않을까?
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