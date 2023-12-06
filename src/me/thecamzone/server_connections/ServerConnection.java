package me.thecamzone.server_connections;

import me.thecamzone.NovaStrike;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ServerConnection implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ServerConnection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while(true) {
                String input = in.readLine();

                String[] args = input.split(" ");
                String command = args[0];
                String[] modifiedArgs = Arrays.copyOfRange(args, 1, args.length);

                // Run task synchronously
                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        ConnectionCommand.handleNetworkCommand(command, modifiedArgs);
                        Bukkit.getConsoleSender().sendMessage("[Proxy -> Game] " + input);
                    }
                };
                task.runTask(NovaStrike.getInstance());
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

    public void sendMessageToProxy(String message) {
        out.println(message);
    }
}
