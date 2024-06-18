package mochegov.xmlrates.controller;


import lombok.extern.slf4j.Slf4j;
import mochegov.xmlrates.dto.ae.PrototypeRequestDto;
import mochegov.xmlrates.services.AeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/xml-rates/api/v1/ae/")
public class AeController {

    private final AeService aeService;

    private final String queuePrototypeIn;
    private final String queuePrototypeOut;

    public AeController(AeService aeService,
                        @Value("${ae.queue.prototype.in}") String queuePrototypeIn,
                        @Value("${ae.queue.prototype.out}") String queuePrototypeOut) {
        this.aeService = aeService;
        this.queuePrototypeIn = queuePrototypeIn;
        this.queuePrototypeOut = queuePrototypeOut;
    }

    @PostMapping("prototype/prototype")
    public ResponseEntity<String> aePrototypeRequest(@RequestBody PrototypeRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(aeService.sendMessage(dto, queuePrototypeIn, queuePrototypeOut));
    }
}
