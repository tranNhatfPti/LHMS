package com.chatapp.services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.http.HttpServletResponse;

public abstract class FileServiceAbstract {
    // folder archive là file nằm ở tomcat(server)
    // Root path: C:\Program Files\Apache Software Foundation\Tomcat 10.1\bin\archive
    public static Path rootLocation = Paths.get("archive");

    public static String rootURL = "";

    public static String toTagHtml(String type, int accountId, String message) {
        String tag = "";
        String url = rootURL + accountId + "/" + message;
        if (type.startsWith("audio")) {
            tag = "<audio controls>\r\n" + "  <source src=\"" + url + "\" type=\"" + type + "\">\r\n" + "</audio>";
        } else if (type.startsWith("video")) {
            tag = "<video width=\"400\" controls>\r\n" + "  <source src=\"" + url + "\" type=\"" + type + "\">\r\n"
                    + "</video>";
        } else if (type.startsWith("image")) {
            tag = "<img src=\"" + url + "\" alt=\"\">";
        } else {
            tag = "<a href=" + url + ">" + message + "</a>";
        }
        return tag;
    }

    protected static final int DEFAULT_BUFFER_SIZE = 10240;

    public abstract void handleStreamFileToClient(File file, String contentType, HttpServletResponse response);
}
