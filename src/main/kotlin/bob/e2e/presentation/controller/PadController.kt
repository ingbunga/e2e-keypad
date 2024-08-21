package bob.e2e.presentation.controller

import bob.e2e.domain.service.PadService
import bob.e2e.presentation.dto.PadResponseDto
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/pad")
@RestController
class PadController (
    val padService: PadService
) {
    @GetMapping("/get")
    @CrossOrigin(origins = ["*"], methods = [RequestMethod.GET]) // TODO(fix devil cors code)
    fun getKeypad(): PadResponseDto {
        val padId = UUID.randomUUID().toString()
        val pad = padService.getRandomPad(padId)
        pad.image = padService.getImageFromPad(pad, 3, 4)
        padService.savePad(pad)

        return PadResponseDto.from(pad)
    }
}