package com.smart.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 正则匹配内容
     * @param regex
     * @param source
     * @return
     */
    public static String matcher(String regex, String source) {
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
