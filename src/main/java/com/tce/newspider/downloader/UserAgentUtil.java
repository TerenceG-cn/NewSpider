package com.tce.newspider.downloader;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;


/**
 * User-Agent用户代理 生成工具
 */
public class UserAgentUtil {
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0";
    private static List<String> userAgents = null;

    static {
        try {
            File file = new File(Resources.getResource("userAgents").getFile());
            userAgents = Files.readLines(file, Charsets.UTF_8);
//            for (String ua : userAgents
//                    ) {
//                System.out.println(ua);
//            }
        } catch (Exception ex) {
        }
    }

    public static String getUserAgent() {

        if (userAgents != null && userAgents.size() != 0) {
            Collections.shuffle(userAgents);
            return userAgents.get(0);
        } else {
            return DEFAULT_USER_AGENT;
        }
    }
}
