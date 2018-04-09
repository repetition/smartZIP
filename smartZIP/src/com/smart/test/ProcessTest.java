package com.smart.test;

import com.sun.xml.internal.bind.v2.TODO;
import junit.framework.TestCase;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 执行dos命令
     */
    @Test
    public void testKillTomcat() {

        ProcessBuilder processBuilder = newProcessBuilder();
/*        Map<String, String> environment = builder.environment();
        for (Map.Entry<String, String> stringEntry : environment.entrySet()) {
            System.out.println(stringEntry.getKey() + " - " + stringEntry.getValue());
        }*/
        System.out.println("--------------查找进程--------------------");
        List<String> cmd = new ArrayList<>();
        // netstat -ano | findstr 8081 执行管道命令
        cmd.add("cmd.exe");
        cmd.add("/c");
        // cmd.add("start");
        cmd.add("netstat");
        cmd.add("-ano");
        cmd.add("|");
        cmd.add("findstr");
        cmd.add("8080");
        System.out.println(cmd.toString());
        String result = "";
        try {
            Process process = processBuilder.command(cmd).start();
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            List<String> cmdResult = getCmdResult(inputStream);
            List<String> cmdEorrorResult = getCmdResult(errorStream);
            String occupyPidStr = cmdResult.get(0);
            // Pattern pattern = Pattern.compile("LISTENING       (.+)");
            //Pattern pattern = Pattern.compile("LISTENING\\s+(.\\d+)");

            String pidStr = matcher("LISTENING       (.+)", cmdResult.get(0));
            System.out.println("--------------查找PID--------------------");
            List<String> cmdTaskList = new ArrayList<>();
            // tasklist | findstr 9768 执行管道命令
            cmdTaskList.add("cmd.exe");
            cmdTaskList.add("/c");
            cmdTaskList.add("tasklist");
            cmdTaskList.add("|");
            cmdTaskList.add("findstr");
            cmdTaskList.add(pidStr);
            System.out.println(cmdTaskList.toString());
            ProcessBuilder taskListProcessBuilder = newProcessBuilder();
            Process taskListProcess = taskListProcessBuilder.command(cmdTaskList).start();
            List<String> cmdTaskListResult = getCmdResult(taskListProcess.getInputStream());
            List<String> cmdTaskListErrorResult = getCmdResult(taskListProcess.getErrorStream());
            System.out.println("--------------终止PID--------------------");

            List<String> cmdTaskKill = new ArrayList<>();
            // taskkill -F -PID 15792
            //  cmdTaskKill.add("cmd.exe");
            //  cmdTaskKill.add("/c");
            cmdTaskKill.add("taskkill");
            cmdTaskKill.add("/F ");
            cmdTaskKill.add("/PID ");
            cmdTaskKill.add(pidStr);
            System.out.println(cmdTaskKill.toString());
            ProcessBuilder taskKillProcessBuilder = newProcessBuilder();
            taskKillProcessBuilder.redirectErrorStream(true);
            //TODO 暂使用拼接形式， list暂不能结束进程，随后查找原因
            // Process taskKillProcess = taskKillProcessBuilder.command(cmdTaskList).start();
            Process taskKillProcess = taskKillProcessBuilder.command("taskkill", "/F", "/PID", pidStr).start();

            taskKillProcess.waitFor();

            // Process taskKillProcess = Runtime.getRuntime().exec("taskkill /F /PID "+pidStr);
            List<String> cmdTaskKillResult = getCmdResult(taskKillProcess.getInputStream());
            List<String> cmdTaskKillErrorResult = getCmdResult(taskKillProcess.getErrorStream());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取cmd命令实例
     *
     * @return
     */
    public ProcessBuilder newProcessBuilder() {
        return new ProcessBuilder();
    }

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

    public String matcher(String regex, String source) {
        // Pattern pattern = Pattern.compile("LISTENING       (.+)");
        //Pattern pattern = Pattern.compile("LISTENING\\s+(.\\d+)");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (!matcher.find()) {
            System.out.println("未找到匹配内容!");
            return "";
        }
        String matcherStr = matcher.group(1);
        //   System.out.println("matcher.group(0):" + matcher.group(0));
        System.out.println("matcher.group(1):" + matcher.group(1));
        return matcherStr;
    }

}
