package bob.e2e.data.repository

import bob.e2e.domain.model.Pad

interface PadRepository {
    fun insert(pad: Pad)

    fun selectById(id: Int): Pad?
}