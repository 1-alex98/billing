
package com.billing.app;

import org.springframework.test.web.servlet.ResultHandler;

public class TestUtils {
    public static ResultHandler expectNoException(){
        return result -> {
            if(result.getResolvedException() != null) {
               throw result.getResolvedException();
            }
        };
    }
}
