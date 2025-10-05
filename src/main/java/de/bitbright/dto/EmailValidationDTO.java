package de.bitbright.dto;

public final class EmailValidationDTO extends DTO {
    private String[] mails;

    public String[] getMails() {
        return this.mails;
    }

    public void setMails(String[] mails) {
        this.mails = mails;
    }
}