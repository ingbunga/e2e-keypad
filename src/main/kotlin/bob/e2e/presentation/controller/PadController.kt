package bob.e2e.presentation.controller

import bob.e2e.domain.service.PadService
import bob.e2e.presentation.dto.PadResponseDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/pad")
@RestController
class PadController (
    val padService: PadService
) {
    @GetMapping("/get")
    @CrossOrigin(origins = ["*"], methods = [RequestMethod.GET]) // TODO(fix devil cors code)
    fun getKeypad(): PadResponseDto {
        val padId = "changeme"
        val pad = padService.getRandomPad(padId)
        pad.image = padService.getImageFromPad(pad, 3, 4)

        return PadResponseDto.from(pad)
    }
}