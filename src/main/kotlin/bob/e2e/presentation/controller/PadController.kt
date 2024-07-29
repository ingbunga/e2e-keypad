package bob.e2e.presentation.controller

import bob.e2e.domain.model.Key
import bob.e2e.domain.model.Pad
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.MessageDigest
import java.io.File
import java.util.Base64


const val SECRET_KEY = "SAD:<"


@RequestMapping("/pad")
@RestController
class PadController {
    @GetMapping("/get")
    fun getKeypad(): Pad {
        val numberList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1)
        val userKey = "change me"

        val randomKeyList = numberList.shuffled().map {
            val file =
                if (it >= 0) File("./src/main/resources/keypad/_$it.png")
                else File("./src/main/resources/keypad/_blank.png")

            Key(
                "$it+$userKey+$SECRET_KEY".hashWithAlgorithm("SHA-256"), // Todo: collision fix
                Base64.getEncoder().encodeToString(file.readBytes()),
            )
        }

        return Pad(
            userKey,
            randomKeyList
        )
    }
}

fun String.hashWithAlgorithm(algorithm: String): String {
    val bytes = MessageDigest.getInstance(algorithm).digest(this.toByteArray())
    return Base64.getEncoder().encodeToString(bytes)
}

/*
function appendImg(base){
    var image = new Image();
    image.src = `data:image/png;base64,${base}`;
    document.body.appendChild(image);
}
imageA.forEach(appendImg);
 */