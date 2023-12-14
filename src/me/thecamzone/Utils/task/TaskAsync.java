package me.thecamzone.Utils.task;

import me.thecamzone.NovaStrike;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class TaskAsync extends Task{

    public TaskAsync(Runnable runnable) {
        super(0, runnable);
    }

    @Override
    protected BukkitTask createTask() {
        return Bukkit.getScheduler().runTaskAsynchronously(NovaStrike.getInstance(), getRunnable());
    }



}
