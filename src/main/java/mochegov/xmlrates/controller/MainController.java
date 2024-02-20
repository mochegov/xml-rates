package mochegov.xmlrates.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mochegov.xmlrates.dto.CloseAccountDto;
import mochegov.xmlrates.dto.CurrencyRateDto;
import mochegov.xmlrates.dto.OpenAccountDto;
import mochegov.xmlrates.services.AccountService;
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
    private final AccountService accountService;

    @PostMapping("publish")
    public ResponseEntity<String> publishCurrencyRate(@RequestBody List<CurrencyRateDto> dtoList) {
        log.info("Publication {} currency rates", dtoList.size());
        try {
            currencyRateService.sendCurrencyRate(dtoList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Currency rates have been published");
    }

    @PostMapping("account/close")
    public ResponseEntity<String> closeAccount(@RequestBody CloseAccountDto dto) {
        accountService.sendCloseAccountMessage(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Message to close account successfully sent");
    }

    @PostMapping("account/open")
    public ResponseEntity<String> openAccount(@RequestBody OpenAccountDto dto) {
        accountService.sendOpenAccountMessage(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Message to open account successfully sent");
    }
}
