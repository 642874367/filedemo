/**
 * Created by askyer on 2018/5/11.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
     public static void  sendFile(String filePath, String host, int port){
         Socket s;
         DataOutputStream dos;
         FileInputStream fis = null;
         try {
             s = new Socket(host, port);
             long start = System.currentTimeMillis();
             dos = new DataOutputStream(s.getOutputStream());
             fis = new FileInputStream(filePath);
             byte[] buffer = new byte[4096];
             while (fis.read(buffer) > 0) {
                 dos.write(buffer);
             }
             fis.close();
             dos.close();
             s.close();
             long end = System.currentTimeMillis();
             System.out.println("Total use " + (end - start) + "ms");
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    public static void main(String []args){
        if (args.length != 3) {
            System.out.println("Usage :  java -jar fileclient.jar ipaddress port filename");
            return;
        }
        sendFile(args[2], args[0], Integer.parseInt(args[1]));
    }
}
