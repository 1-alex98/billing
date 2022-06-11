
package com.billing.app;

import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.util.Base64Utils;

public class TestUtils {
    public static ResultHandler expectNoException(){
        return result -> {
            if(result.getResolvedException() != null) {
               throw result.getResolvedException();
            }
        };
    }
    public static String admin(){
        return "Basic " + Base64Utils.encodeToString("admin:admin".getBytes());
    }
}
