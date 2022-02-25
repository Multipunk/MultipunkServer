package ru.melonhell.cyberpunkserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {
    public void test() throws IOException {
        ServerSocket socket = new ServerSocket(2077);
        while (true) {
            Socket accept = socket.accept();
            accept.getInputStream();
        }
    }
}
