package com.dante.curso.resouces.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    /**
     * Decode String list to Integer List
     *
     * @param lista
     * @return
     */
    public static List<Integer> decodeIntegerList(final String lista) {
//        Arrays.stream(lista.split(",")).map(value -> Integer.parseInt(value)).collect(Collectors.toList());
        return Arrays.asList(lista.split(",")).stream().map(value -> Integer.parseInt(value)).collect(Collectors.toList());
    }

    /**
     * Decode String Param to UTF-8
     * @param value
     * @return
     */
    public static String decodeString(final String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //TODO: LOG IT!
            return "";
        }
    }

}
