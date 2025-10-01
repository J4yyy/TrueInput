package de.bitbright.validation;

import de.bitbright.util.GenRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public final class ValidationService {
    //region [Category: Email Validation]
    public ResponseEntity<Object> validateEmail(String[] email) {
        Map<String, Boolean> checks = new HashMap<>();
        for(String mail : email) {
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
    //endregion [Category: Email Validation]

    //region [Category: IBAN Validation]
    private static final Map<String, String> IBAN_PATTERNS = new HashMap<>();
    static {
        IBAN_PATTERNS.put("AL", "^AL\\d{26}$"); // Albania
        IBAN_PATTERNS.put("AD", "^AD\\d{22}$"); // Andorra
        IBAN_PATTERNS.put("AT", "^AT\\d{18}$"); // Austria
        IBAN_PATTERNS.put("AZ", "^AZ\\d{26}$"); // Azerbaijan
        IBAN_PATTERNS.put("BH", "^BH\\d{20}$"); // Bahrain
        IBAN_PATTERNS.put("BE", "^BE\\d{14}$"); // Belgium
        IBAN_PATTERNS.put("BA", "^BA\\d{18}$"); // Bosnia and Herzegovina
        IBAN_PATTERNS.put("BR", "^BR\\d{27}$"); // Brazil
        IBAN_PATTERNS.put("BG", "^BG\\d{20}$"); // Bulgaria
        IBAN_PATTERNS.put("CR", "^CR\\d{20}$"); // Costa Rica
        IBAN_PATTERNS.put("HR", "^HR\\d{19}$"); // Croatia
        IBAN_PATTERNS.put("CY", "^CY\\d{26}$"); // Cyprus
        IBAN_PATTERNS.put("CZ", "^CZ\\d{22}$"); // Czech Republic
        IBAN_PATTERNS.put("FO", "^FO\\d{16}$"); // Faroe Islands
        IBAN_PATTERNS.put("GL", "^GL\\d{16}$"); // Greenland
        IBAN_PATTERNS.put("DK", "^DK\\d{16}$"); // Denmark
        IBAN_PATTERNS.put("DO", "^DO\\d{26}$"); // Dominican Republic
        IBAN_PATTERNS.put("EE", "^EE\\d{18}$"); // Estonia
        IBAN_PATTERNS.put("EG", "^EG\\d{27}$"); // Egypt
        IBAN_PATTERNS.put("FI", "^FI\\d{16}$"); // Finland
        IBAN_PATTERNS.put("FR", "^FR\\d{25}$"); // France
        IBAN_PATTERNS.put("GE", "^GE\\d{20}$"); // Georgia
        IBAN_PATTERNS.put("DE", "^DE\\d{20}$"); // Germany
        IBAN_PATTERNS.put("GI", "^GI\\d{21}$"); // Gibraltar
        IBAN_PATTERNS.put("GR", "^GR\\d{25}$"); // Greece
        IBAN_PATTERNS.put("GT", "^GT\\d{26}$"); // Guatemala
        IBAN_PATTERNS.put("HU", "^HU\\d{26}$"); // Hungary
        IBAN_PATTERNS.put("IS", "^IS\\d{24}$"); // Iceland
        IBAN_PATTERNS.put("IE", "^IE\\d{20}$"); // Ireland
        IBAN_PATTERNS.put("IL", "^IL\\d{21}$"); // Israel
        IBAN_PATTERNS.put("IT", "^IT\\d{25}$"); // Italy
        IBAN_PATTERNS.put("JO", "^JO\\d{28}$"); // Jordan
        IBAN_PATTERNS.put("KZ", "^KZ\\d{18}$"); // Kazakhstan
        IBAN_PATTERNS.put("XK", "^XK\\d{18}$"); // Kosovo
        IBAN_PATTERNS.put("KW", "^KW\\d{28}$"); // Kuwait
        IBAN_PATTERNS.put("LV", "^LV\\d{19}$"); // Latvia
        IBAN_PATTERNS.put("LB", "^LB\\d{26}$"); // Lebanon
        IBAN_PATTERNS.put("LI", "^LI\\d{19}$"); // Liechtenstein
        IBAN_PATTERNS.put("LT", "^LT\\d{18}$"); // Lithuania
        IBAN_PATTERNS.put("LU", "^LU\\d{18}$"); // Luxembourg
        IBAN_PATTERNS.put("MK", "^MK\\d{17}$"); // North Macedonia
        IBAN_PATTERNS.put("MT", "^MT\\d{29}$"); // Malta
        IBAN_PATTERNS.put("MR", "^MR\\d{25}$"); // Mauritania
        IBAN_PATTERNS.put("MU", "^MU\\d{28}$"); // Mauritius
        IBAN_PATTERNS.put("MD", "^MD\\d{22}$"); // Moldova
        IBAN_PATTERNS.put("MC", "^MC\\d{25}$"); // Monaco
        IBAN_PATTERNS.put("ME", "^ME\\d{20}$"); // Montenegro
        IBAN_PATTERNS.put("NL", "^NL\\d{16}$"); // Netherlands
        IBAN_PATTERNS.put("NO", "^NO\\d{13}$"); // Norway
        IBAN_PATTERNS.put("PK", "^PK\\d{22}$"); // Pakistan
        IBAN_PATTERNS.put("PS", "^PS\\d{27}$"); // Palestine
        IBAN_PATTERNS.put("PL", "^PL\\d{26}$"); // Poland
        IBAN_PATTERNS.put("PT", "^PT\\d{23}$"); // Portugal
        IBAN_PATTERNS.put("QA", "^QA\\d{27}$"); // Qatar
        IBAN_PATTERNS.put("RO", "^PT\\d{22}$"); // Romania
        IBAN_PATTERNS.put("RU", "^RU\\d{31}$"); // Russia
        IBAN_PATTERNS.put("LC", "^LC\\d{30}$"); // Saint Lucia
        IBAN_PATTERNS.put("SM", "^SM\\d{25}$"); // San Marino
        IBAN_PATTERNS.put("ST", "^ST\\d{23}$"); // Sao Tome and Principe
        IBAN_PATTERNS.put("SA", "^SA\\d{22}$"); // Saudi Arabia
        IBAN_PATTERNS.put("RS", "^PT\\d{20}$"); // Serbia
        IBAN_PATTERNS.put("SC", "^SC\\d{29}$"); // Seychelles
        IBAN_PATTERNS.put("SK", "^SK\\d{22}$"); // Slovakia
        IBAN_PATTERNS.put("SI", "^SI\\d{17}$"); // Slovenia
        IBAN_PATTERNS.put("SO", "^SO\\d{21}$"); // Somalia
        IBAN_PATTERNS.put("ES", "^ES\\d{22}$"); // Spain
        IBAN_PATTERNS.put("SD", "^SD\\d{16}$"); // Sudan
        IBAN_PATTERNS.put("OM", "^OM\\d{21}$"); // Sultanate of Oman
        IBAN_PATTERNS.put("SE", "^SE\\d{22}$"); // Sweden
        IBAN_PATTERNS.put("CH", "^CH\\d{19}$"); // Switzerland
        IBAN_PATTERNS.put("TL", "^TL\\d{21}$"); // Timor-Leste
        IBAN_PATTERNS.put("TN", "^TN\\d{22}$"); // Tunisia
        IBAN_PATTERNS.put("TR", "^TR\\d{24}$"); // Turkey
        IBAN_PATTERNS.put("UA", "^UA\\d{27}$"); // Ukraine
        IBAN_PATTERNS.put("AE", "^AE\\d{21}$"); // United Arab Emirates
        IBAN_PATTERNS.put("GB", "^GB\\d{20}$"); // United Kingdom
        IBAN_PATTERNS.put("VG", "^VG\\d{22}$"); // Virgin Islands, British
        IBAN_PATTERNS.put("YE", "^YE\\d{28}$"); // Yemen
    }

    public ResponseEntity<Object> validateIBAN(String[] iban) {
        Map<String, Boolean> checks = new HashMap<>();

        for(String input : iban) {
            input = input.replaceAll("\\s+", "").toUpperCase();
            if(input.length() < 4) {
                checks.put(input, false);
                continue;
            }

            String countryCode = input.substring(0, 2);
            String pattern = IBAN_PATTERNS.get(countryCode);

            if(pattern != null && !input.matches(pattern)) {
                checks.put(input, false);
                continue;
            }

            checks.put(input, ibanChecksum(input));
        }

        GenRes res = new GenRes(checks);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private boolean ibanChecksum(String iban) {
        String rearranged = iban.substring(4) + iban.substring(0, 4);
        StringBuilder numeric = new StringBuilder();
        for (char c : rearranged.toCharArray()) {
            if (Character.isLetter(c)) {
                numeric.append((c - 'A') + 10);
            } else {
                numeric.append(c);
            }
        }
        return mod97(numeric.toString()) == 1;
    }

    private int mod97(String input) {
        int remainder = 0;
        for (int i = 0; i < input.length(); i++) {
            int digit = input.charAt(i) - '0';
            remainder = (remainder * 10 + digit) % 97;
        }
        return remainder;
    }
    //endregion [Category: IBAN Validation]
}