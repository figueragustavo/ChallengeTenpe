package com.challenge.tenpe.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

    public static String toJson(Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
