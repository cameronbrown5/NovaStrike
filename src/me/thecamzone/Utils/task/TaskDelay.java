package me.thecamzone.Utils.task;

import me.thecamzone.NovaStrike;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class TaskDelay extends Task{

    public TaskDelay(int delay, Runnable runnable) {
        super(delay, runnable);
    }

    @Override
    protected BukkitTask createTask() {
        return Bukkit.getScheduler().runTaskLater(NovaStrike.getInstance(), getRunnable(), getDelay());
    }



}
