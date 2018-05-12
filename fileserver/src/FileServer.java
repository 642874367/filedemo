/**
 * Created by askyer on 2018/5/11.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer extends Thread{
    private ServerSocket ss;

    public FileServer(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
                saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream("./receivedfile");
        byte[] buffer = new byte[4096];

        int totalRead = 0;
        int count;
        while ((count = dis.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
            totalRead += count;
        }

        fos.close();
        dis.close();
        System.out.println("fileSize :" + totalRead);
    }

    public static void main(String []args){
        if (args.length != 1) {
            System.out.println("Usage : java -jar fileserver.jar port");
            return;
        }
        FileServer fs = new FileServer(Integer.parseInt(args[0]));
        fs.start();
    }
}
