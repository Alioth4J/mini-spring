package com.alioth4j.minispring.http.converter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpMessageConverter {

    void write(Object obj, HttpServletResponse response) throws IOException;

}
