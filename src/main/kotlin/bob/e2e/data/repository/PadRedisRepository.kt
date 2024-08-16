package bob.e2e.data.repository

import bob.e2e.domain.model.Pad
import org.springframework.stereotype.Repository

@Repository
class CarRedisRepository : PadRepository {
    override fun insert(pad: Pad) {
        TODO("Not yet implemented")
    }

    override fun selectById(id: Int): Pad? {
        TODO("Not yet implemented")
    }
}
