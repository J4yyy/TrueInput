package de.bitbright.validators;

import de.bitbright.dto.DTO;
import org.springframework.http.ResponseEntity;

public interface Validator {
    ResponseEntity<Object> handle(DTO data);
}