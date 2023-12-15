package me.thecamzone.server_connections;

import me.thecamzone.NovaStrike;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionManager {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;
    private static BukkitRunnable task;
    private static BufferedReader input;
    private static PrintWriter out;
    public static ServerConnection connection;

    // Creates a thread and connects to the proxy server socket on the new thread.
    public static boolean connect() {
        if(task != null) {
            Bukkit.getConsoleSender().sendMessage("[NovaStrike] Server is already connected to proxy.");
            return false;
        }

        task = new BukkitRunnable() {
            @Override
            public void run() {
                Socket socket;

                Bukkit.getConsoleSender().sendMessage("[NovaStrike] Connecting to proxy server...");

                try {
                    socket = new Socket(SERVER_IP, SERVER_PORT);
                    connection = new ServerConnection(socket, this);
                    Bukkit.getConsoleSender().sendMessage("[NovaStrike] Connected to " + socket.getInetAddress() + ":" + socket.getPort());
                    connection.run();

                    out = new PrintWriter(socket.getOutputStream());
                } catch (IOException | RuntimeException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[NovaStrike] Could not connect to proxy. Type '/dev connect' to try connection again.");
                    task = null;
                }
            }
        };

        task.runTaskAsynchronously(NovaStrike.getInstance());

        return true;
    }

    public static void close() {
        connection.close();
        task.cancel();
        task = null;
    }
}


