package de.bitbright.validation;

import de.bitbright.exception.InvalidRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/validation")
public final class ValidationController {
    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/email")
    public ResponseEntity<Object> validateEmail(@RequestBody String[] input, WebRequest req) {
        if(input == null || input.length < 1) throw new InvalidRequestException("body must be set");

        System.out.println(Arrays.toString(input));
        return this.validationService.validateEmail(input, req);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/iban")
    public ResponseEntity<Object> validateIBAN(@RequestBody String[] input, WebRequest req) {
        if(input == null || input.length < 1) throw new InvalidRequestException("body must be set");
        return this.validationService.validateIBAN(input, req);
    }
}