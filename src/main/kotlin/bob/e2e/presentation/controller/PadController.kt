package bob.e2e.presentation.controller

import bob.e2e.domain.service.PadService
import bob.e2e.presentation.dto.PadResponseDto
import bob.e2e.presentation.dto.SubmitRequestDto
import bob.e2e.presentation.dto.SubmitResponseDto
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

    @PostMapping("/submit")
    @CrossOrigin(origins = ["*"], methods = [RequestMethod.POST]) // TODO(fix devil cors code)
    fun submit(
        @RequestBody submitRequestDto: SubmitRequestDto
    ): SubmitResponseDto {
        val pad = padService.getPadFromId(submitRequestDto.padId)
        val keyLength = 36 // UUID length

        val decoded = pad?.let {
            padService.decode(
                it,
                submitRequestDto.userInput,
                keyLength
            )
        } ?: ""

        return SubmitResponseDto(decoded=decoded)
    }
}