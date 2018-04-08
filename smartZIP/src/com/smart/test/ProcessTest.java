package com.smart.test;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProcessTest extends TestCase {
    /**
     * 执行dos命令
     */
    @Test
    public void testKillProcess() {
        System.out.println("111");

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory();
        Map<String, String> environment = builder.environment();
        for (Map.Entry<String, String> stringEntry : environment.entrySet()) {
            System.out.println(stringEntry.getKey() + " - " + stringEntry.getValue());
        }

        List<String> cmd = new ArrayList<>();

        cmd.add("ipconfig");
        cmd.add("/all");
        String result = "";
        try {
            Process process = builder.command(cmd).start();
            InputStream inputStream = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("gbk"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
