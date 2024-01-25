package fr.gblaquiere.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class HelloWorld extends HttpServlet {

    protected void proccessRequest(
        HttpServletRequest request,
        HttpServletResponse response)
        throws
        IOException {
        String filename = "test.csv";
        String content = testContent();
        createFile(filename);
        writeFile(filename, content);

        File downloadFile = new File(filename);

        String sjis_filename = new String(filename.getBytes("Windows-31J"), "ISO8859_1");

        FileInputStream fileIS = new FileInputStream(downloadFile);
        
        response.setContentType("application/octet-stream");
        int readLen;
        byte[] buff = new byte[1024];
        response.setHeader("Content-disposition", "attachent; filename=\"" + sjis_filename + "\"");
        ServletOutputStream out = response.getOutputStream();
        
        while ((readLen = fileIS.read(buff)) != -1){
            out.write(buff, 0, readLen);
        }

        out.close();
        fileIS.close();
    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            IOException {
        proccessRequest(request, response);
    }

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws
            IOException {
        proccessRequest(request, response);
    }

    private void createFile(String filename){
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void writeFile(String filename, String content){
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String testContent() {
        String sampleLine = "Files in Java might be tricky, but it is fun enough!";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 1000; i++){
            sb = sb.append(sampleLine).append('\n');
        }
        return sb.toString();
    }
}
