package bob.e2e.data.repository

import bob.e2e.data.dto.PadRedisDto
import bob.e2e.domain.model.Pad
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

interface PadRedisRepository : CrudRepository<PadRedisDto, String>

@Repository
class PadRedisRepositoryImpl(
    @Lazy
    @Autowired
    private val redisRepository: PadRedisRepository
) : PadRepository {
    override fun insert(pad: Pad) {
        redisRepository.save(PadRedisDto.from(pad))
    }

    override fun selectById(id: String): Pad? {
        return redisRepository
            .findById(id)
            .map { it.toPad() }
            .orElse(null)
    }
}