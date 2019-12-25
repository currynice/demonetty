package com.cxy.demonetty.IO;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class IOClient {

    //每隔两秒，向服务器写一句话
    public static void main(String[] args) {
        new Thread(() -> {
            try {

                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
