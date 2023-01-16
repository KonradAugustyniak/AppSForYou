package pl.lodz.p.it.spjava.e12.appstore.security;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlainTextHashGenerator implements HashGenerator {

    @Override
    public String generateHash(String input) {
        return input;
    }

}
