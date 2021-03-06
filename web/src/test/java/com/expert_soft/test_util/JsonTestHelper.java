package com.expert_soft.test_util;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

public class JsonTestHelper {


    public static final ObjectMapper jsonObject = new ObjectMapper();
    static {
        jsonObject.configure(DeserializationConfig.Feature.WRAP_EXCEPTIONS, true);
        jsonObject.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        jsonObject.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonObject.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
    }

    public static JsonNode parse(String s) throws IOException {
        return jsonObject.readTree(s);
    }

    public static <T> T parseToObj(String s, Class<T> clazz ) throws IOException {
        return jsonObject.readValue(s, clazz);
    }

    private String getJSONReques(MockHttpServletRequest request) throws IOException {
        ObjectNode jsonNodes = buildObject(request);
        return jsonObject.writerWithDefaultPrettyPrinter()
                          .writeValueAsString(jsonNodes);
    }

    public static ObjectNode buildObject(MockHttpServletRequest req) throws IOException {
        ObjectNode requestNode = jsonObject.createObjectNode();
        requestNode.put("httpMethod", req.getMethod());
        requestNode.put("clientIP", req.getRemoteAddr());
        requestNode.put("URI:", req.getRequestURI());
        requestNode.put("URL:", req.getRequestURL().toString());
        requestNode.put("queryString", req.getQueryString());
        requestNode.put("paramNames",
                Collections.list(req.getParameterNames()).toString());
        requestNode.put("headerNames", Collections.list(req.getHeaderNames()).toString());

        Enumeration<String> headerNames = req.getHeaderNames();
        return requestNode;

    }
}
