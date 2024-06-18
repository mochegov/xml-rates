package mochegov.xmlrates.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mochegov.xmlrates.dto.AnyOperationDto;
import mochegov.xmlrates.dto.CloseAccountDto;
import mochegov.xmlrates.dto.OpenAccountDto;
import mochegov.xmlrates.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/xml-rates/api/v1/account/")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("close")
    public ResponseEntity<String> closeAccount(@RequestBody CloseAccountDto dto) {
        accountService.sendCloseAccountMessage(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Message to close account successfully sent");
    }

    @PostMapping("open")
    public ResponseEntity<String> openAccount(@RequestBody OpenAccountDto dto) {
        accountService.sendOpenAccountMessage(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Message to open account successfully sent");
    }

    @PostMapping("operation")
    public ResponseEntity<String> operationAccount(@RequestBody AnyOperationDto dto) {
        accountService.sendAnyAccountMessage(dto);
        return ResponseEntity.status(HttpStatus.OK).body("Message to process account operation successfully sent");
    }
}
