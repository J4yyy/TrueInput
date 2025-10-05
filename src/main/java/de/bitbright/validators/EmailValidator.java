package de.bitbright.validators;

import de.bitbright.dto.DTO;
import de.bitbright.dto.EmailValidationDTO;
import de.bitbright.exception.InvalidRequestException;
import de.bitbright.util.GenRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class EmailValidator implements Validator {

    private final Class<EmailValidationDTO> inputDTO = EmailValidationDTO.class;

    @Override
    public ResponseEntity<Object> handle(DTO data) {
        if(!(data instanceof EmailValidationDTO dto)) {
            throw new InvalidRequestException("Requestbody malformed");
        }

        Map<String, Boolean> checks = new HashMap<>();
        for(String mail : dto.getMails()) {
            if(!mail.matches("[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,}")) {
                checks.put(mail, false);
                continue;
            }

            if(!doesHostExist(mail.substring(mail.indexOf("@") +1))) {
                checks.put(mail, false);
                continue;
            }
            checks.put(mail, true);
        }

        GenRes res = new GenRes(checks);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private boolean doesHostExist(String host) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            env.put("java.naming.provider.url", "dns:");

            DirContext ctx = new InitialDirContext(env);

            Attributes attrs = ctx.getAttributes(host, new String[]{"MX"});
            Attribute attr = attrs.get("MX");

            if (attr == null) {
                // No MX record — try A record
                attrs = ctx.getAttributes(host, new String[]{"A"});
                attr = attrs.get("A");
                return attr != null;
            } else {
                return attr.size() > 0;
            }
        } catch (NamingException e) {
            System.out.println(e.getExplanation());
        }

        return false;
    }
}