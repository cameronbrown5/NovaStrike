package me.thecamzone.server_connections;

import me.thecamzone.NovaStrike;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ConnectionManager {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    private static BukkitRunnable task;
    private static BufferedReader input;
    private static PrintWriter out;
    private static ServerConnection connection;

    // Creates a thread and connects to the proxy server socket on the new thread.
    public static void connect() {
        if(task != null) {
            Bukkit.getConsoleSender().sendMessage("[NovaStrike] Server is already connected to proxy.");
            return;
        }

        task = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage("[NovaStrike] Connecting to proxy server...");

                try {
                    SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                    SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(SERVER_IP, SERVER_PORT);
                    connection = new ServerConnection(socket);
                    Bukkit.getConsoleSender().sendMessage("[NovaStrike] Connected to " + socket.getInetAddress() + ":" + socket.getPort());
                    connection.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        task.runTaskAsynchronously(NovaStrike.getInstance());
    }

    public static void sendMessageToProxy(String message) {
        out.println(message);
    }
}
