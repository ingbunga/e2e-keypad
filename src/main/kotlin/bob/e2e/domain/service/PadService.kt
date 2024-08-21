package bob.e2e.domain.service

import bob.e2e.data.repository.PadRepository
import bob.e2e.domain.model.Key
import bob.e2e.domain.model.Pad
import jakarta.validation.constraints.Null
import org.springframework.stereotype.Service
import java.awt.Image
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import java.util.*

@Service
class PadService (
    private val padRepository: PadRepository,
) {
    fun getRandomPad(id: String) : Pad {
        val numberList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1)

        val randomKeyList = numberList.shuffled().map {
            Key(
                if (it >= 0) UUID.randomUUID().toString() else "",
                it
            )
        }

        return Pad(
            id = id,
            image = "",
            keys = randomKeyList,
            createdAt = System.currentTimeMillis(),
        )
    }

    // TODO(Image cache)
    fun getImageFromPad(pad: Pad, rows: Int, cols: Int) : String {
        val imagePath = "./src/main/resources/keypad"
        require(pad.keys.size == rows * cols) { "숫자 개수가 행과 열의 곱과 일치해야 합니다." }

        val firstImage = ImageIO.read(File("$imagePath/_0.png"))
        val width = firstImage.width
        val height = firstImage.height

        val resultImage = BufferedImage(width * cols, height * rows, BufferedImage.TYPE_INT_ARGB)
        val g2d = resultImage.createGraphics()

        pad.keys.forEachIndexed { index, key ->
            val name = if (key.number >= 0) key.number else "blank"
            val img = ImageIO.read(File("$imagePath/_$name.png"))
            val x = (index % cols) * width
            val y = (index / cols) * height
            g2d.drawImage(img, x, y, null)
        }

        val outputStream = ByteArrayOutputStream()
        ImageIO.write(resultImage, "png", outputStream)
        val imageBytes = outputStream.toByteArray()
        outputStream.close()

        return Base64.getEncoder().encodeToString(imageBytes)
    }

    fun savePad(pad: Pad) {
        padRepository.insert(pad)
    }

    fun getPadFromId(id: String): Pad? {
        return padRepository.selectById(id)
    }

    fun decode(pad: Pad, userInput: String, keyLength: Int): String {
        val keyMap = pad.keys.associate { it.id to it.number.toString() }
        return userInput.chunked(keyLength)
                        .mapNotNull(keyMap::get)
                        .joinToString("")
    }
}