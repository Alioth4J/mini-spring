package com.alioth4j.minispring.http.converter;

import com.alioth4j.minispring.util.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultHttpMessageConverter implements HttpMessageConverter {

    String defaultContentType = "text/json;charset=UTF-8";
    String defaultCharacterEncoding = "UTF-8";

    ObjectMapper objectMapper;

    public DefaultHttpMessageConverter() {
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType(defaultContentType);
        response.setCharacterEncoding(defaultCharacterEncoding);

        writeInternal(obj, response);

        response.flushBuffer();
    }

    private void writeInternal(Object obj, HttpServletResponse response) throws IOException {
        String sJsonStr = this.objectMapper.writeValuesAsString(obj);
        response.getWriter().write(sJsonStr);
    }

}
