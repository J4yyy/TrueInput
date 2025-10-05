package de.bitbright.validation;

import de.bitbright.dto.DTO;
import de.bitbright.exception.InvalidRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/validation")
public final class ValidationController {
    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> validate(@RequestBody DTO dto) {
        if(dto == null) throw new InvalidRequestException("Request body must be set");
        return this.validationService.validate(dto);
    }
}