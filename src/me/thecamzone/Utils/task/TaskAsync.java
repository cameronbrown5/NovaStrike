package me.thecamzone.Utils.task;

import me.thecamzone.NovaStrike;

public class TaskAsync extends Task{

    public TaskAsync(Runnable runnable) {
        super(0, runnable);
    }

    @Override
    protected BukkitTask createTask() {
        return Bukkit.getScheduler().runTaskAsynchronously(NovaStrike.getInstance(), getRunnable());
    }



}
