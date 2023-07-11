package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer extends Thread {
    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(8888);
            for (; ; ) {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                requestHandler.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BIOServer bio = new BIOServer();
        bio.start();
        for (; ; ) {
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try (Socket client = new Socket(InetAddress.getLocalHost(), 8888)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                bufferedReader.lines().forEach(System.out::println);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

class RequestHandler extends Thread {
    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream())) {
            String text = String.format("%s, say hello!", Thread.currentThread().getName());
            printWriter.write(text);
            printWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
