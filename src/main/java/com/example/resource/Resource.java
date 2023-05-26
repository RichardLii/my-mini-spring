package com.example.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Resource
 *
 * @author keemo 2023/5/26
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
