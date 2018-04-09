package com.smart.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CmdUtils {

    /**
     * 获取cmd命令实例
     *
     * @return
     */
    public ProcessBuilder newProcessBuilder() {
        return new ProcessBuilder();
    }

    /**
     * 读取cmd执行后返回值
     *
     * @param is Process输入流
     * @return 返回执行结果
     * @throws IOException
     */
    public List<String> getCmdResult(InputStream is) throws IOException {

        InputStreamReader reader = new InputStreamReader(is, Charset.forName("gbk"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        List<String> cmdSearchPorts = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            cmdSearchPorts.add(line);
        }
        is.close();
        return cmdSearchPorts;
    }

    public String cmdSearchPort(String port) throws IOException {

        List<String> cmd = new ArrayList<>();
        // netstat -ano | findstr 8081 执行管道命令必须执行 cmd.exe /c
        cmd.add("cmd.exe");
        cmd.add("/c");
        // cmd.add("start");
        cmd.add("netstat");
        cmd.add("-ano");
        cmd.add("|");
        cmd.add("findstr");
        cmd.add(port);
        System.out.println(cmd.toString());
        ProcessBuilder processBuilder = newProcessBuilder();
        Process process = processBuilder.command(cmd).start();
        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();

        List<String> cmdResult = getCmdResult(inputStream);
        List<String> cmdErrorResult = getCmdResult(errorStream);

        String portLine = cmdResult.get(0);

        return Utils.matcher("LISTENING       (.+)", portLine);
    }

}
