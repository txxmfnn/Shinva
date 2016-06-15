package com.yanz.machine.shinva.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

	public JsonUtil() {
		
	}
    
	public static Object jsonToObject(String json,Class<?> clazz) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
		
	}
}
