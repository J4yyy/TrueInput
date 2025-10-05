package de.bitbright.dto;

public final class IbanValidationDTO extends DTO {
    private String[] ibans;

    public String[] getIbans() {
        return this.ibans;
    }

    public void setIbans(String[] ibans) {
        this.ibans = ibans;
    }
}