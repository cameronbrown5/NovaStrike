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
    private final BukkitRunnable task;
    private final BufferedReader in;
    private final PrintWriter out;

    public ServerConnection(Socket socket, BukkitRunnable task) throws IOException {
        this.socket = socket;
        this.task = task;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            out.println("handshake " + NovaStrike.SERVER_PORT);

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
                        Bukkit.getConsoleSender().sendMessage("[Proxy -> Game]: " + input);
                    }
                };
                task.runTask(NovaStrike.getInstance());
            }
        } catch(IOException ignored) { }
        finally {
            ConnectionManager.close();
        }

    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) { }
        finally {
            Bukkit.getConsoleSender().sendMessage("[NovaStrike] Disconnected from proxy.");
        }
    }

    public void sendMessageToProxy(String message) {
        Bukkit.getConsoleSender().sendMessage("[Game -> Proxy]: " + message);

        out.println(message);
    }

    public void sendMessageToProxy(String[] message) {
        String formattedMessage = String.join(" ", message);
        sendMessageToProxy(formattedMessage);
    }
}
