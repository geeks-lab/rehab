package org.example;

import java.io.IOException;

// GET /calculate?operand1=11&operator=*&operand2=55
/***
 * HttpRequest
 *      - RequestLine (GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1)
 *          - HttpMethod
 *          - path
 *          - queryString
 *      - Header
 *      - Body
 * */

public class Main {
    public static void main(String[] args) throws IOException {

        new CustomWebApplicationServer(8080).start();
    }
}