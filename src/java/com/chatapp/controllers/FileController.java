package com.chatapp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.chatapp.services.FileServiceAbstract;
import com.chatapp.services.impl.FileService;

@WebServlet("/files/*")
public class FileController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private FileServiceAbstract fileService = FileService.getInstace();

    public FileController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // request.getPathInfo() trả về một chuỗi chứa phần còn lại của URL sau phần đường dẫn servlet (/*). 
        String requestedFile = request.getPathInfo();
        
        if (requestedFile == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            String filePath = FileServiceAbstract.rootLocation.toString();
            File file = new File(filePath, URLDecoder.decode(requestedFile, "UTF-8"));
            if (!file.exists()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                String contentType = getServletContext().getMimeType(file.getName());
                fileService.handleStreamFileToClient(file, contentType, response);
            }
        }
    }
}
