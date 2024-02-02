package mochegov.xmlrates.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mochegov.xmlrates.dto.CurrencyRateDto;
import mochegov.xmlrates.services.CurrencyRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/xml-rates/api/v1/")
public class MainController {
    private final CurrencyRateService currencyRateService;

    @PostMapping("publish")
    public ResponseEntity<String> publishCurrencyRate(@RequestBody List<CurrencyRateDto> dtoList) {
        log.info("Publication {} currency rates", dtoList.size());
        currencyRateService.sendCurrencyRate(dtoList);
        return ResponseEntity.status(HttpStatus.OK).body("Currency rates have been published");
    }
}
