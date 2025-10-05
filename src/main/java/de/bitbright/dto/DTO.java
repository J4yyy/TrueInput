package de.bitbright.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "handler",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailValidationDTO.class, name = "email"),
        @JsonSubTypes.Type(value = IbanValidationDTO.class, name = "iban")
})
public abstract class DTO {
    private String handler;

    public String getHandler() {
        return this.handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
