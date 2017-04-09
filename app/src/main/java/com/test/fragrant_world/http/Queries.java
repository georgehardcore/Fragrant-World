package com.test.fragrant_world.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;


/** Http queries container. */
@SuppressWarnings("PMD")
public final class Queries {

    /** Ampersand. */
    private static final String AMPERSAND = "&";
    /** SPACE. */
    private static final String SPACE = " ";
    /** Plus. */
    private static final String PLUS = "+";

    private static String locale = Locale.getDefault().getLanguage();

    /** Private constructor. */
    private Queries() {
    }

    public static String catalog() {
        return "http://am.aeroidea.ru/api/catalog";
    }

    /**
     * Encode cyrillic symbols.
     * @param value value
     * @return encoded string
     */
    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
}
