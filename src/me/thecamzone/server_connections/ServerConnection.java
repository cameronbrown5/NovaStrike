package me.thecamzone.server_connections;

import me.thecamzone.NovaStrike;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ServerConnection implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ServerConnection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while(true) {
                String input = in.readLine();

                Bukkit.getConsoleSender().sendMessage(input);
            }
        } catch(IOException e) {
            Bukkit.getConsoleSender().sendMessage("[NovaStrike] Disconnected from proxy.");
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
