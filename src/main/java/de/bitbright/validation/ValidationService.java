package de.bitbright.validation;

import de.bitbright.dto.DTO;
import de.bitbright.validators.EmailValidator;
import de.bitbright.validators.IbanValidator;
import de.bitbright.validators.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public final class ValidationService {
    private static final Map<String, Validator> handlers = new HashMap<>();

    static {
        handlers.put("email", new EmailValidator());
        handlers.put("iban", new IbanValidator());
    }

    public ResponseEntity<Object> validate(DTO dto) {
        Validator validator = handlers.get(dto.getHandler());
        return validator.handle(dto);
    }
}