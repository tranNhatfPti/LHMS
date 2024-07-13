package com.chatapp.models.dtos;

import java.io.FileOutputStream;

public class FileDTO {

    private String filename;
    private String typeFile;
    private FileOutputStream fileOutputStream;
    private int sender;
    private int receiver;
    private String url;

    public FileDTO(String filename, String typeFile, FileOutputStream fileOutputStream, int sender, int receiver, String url) {
        this.filename = filename;
        this.typeFile = typeFile;
        this.fileOutputStream = fileOutputStream;
        this.sender = sender;
        this.receiver = receiver;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public FileOutputStream getFileOutputStream() {
        return fileOutputStream;
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
