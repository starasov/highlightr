package com.blogpost.starasov.highlightr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * User: starasov
 * Date: 12/21/11
 * Time: 6:51 AM
 */
@Controller
public class HelloWorldController {
    @RequestMapping(method = RequestMethod.GET, value = "/api/hello", headers = "Accept=application/xml, application/json")
    public @ResponseBody Glow sayHello() throws IOException {
        URL url = new URL("http://widgets.digg.com/buttons/count?url=https://app.jelastic.com/");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder builder = new StringBuilder();

        String line = "";
        while(line != null) {
            builder.append(line);
            line = reader.readLine();
        }

        return new Glow(1, 15, builder.toString());
    }
}
