package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NIOServer extends Thread {
    @Override
    public void run() {
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), 8899));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            for (; ; ) {
                //阻塞等待就绪的channel
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("selection keys size:" + selectionKeys.size());

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    sayHello((ServerSocketChannel) selectionKey.channel());
                    iterator.remove();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sayHello(ServerSocketChannel serverSocketChannel) throws IOException {
        try (SocketChannel socketChannel = serverSocketChannel.accept()) {
            String ret = String.format("%s: say hello from nio!", Thread.currentThread().getName());
            socketChannel.write(ByteBuffer.wrap(ret.getBytes(StandardCharsets.UTF_8)));
        }
    }

    public static void main(String[] args) {
        NIOServer nio = new NIOServer();
        nio.start();
        for (; ; ) {
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try (Socket client = new Socket(InetAddress.getLocalHost(), 8899)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                bufferedReader.lines().forEach(System.out::println);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
