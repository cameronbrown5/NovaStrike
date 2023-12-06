package me.thecamzone.Utils.task;

import me.thecamzone.NovaStrike;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class TaskRepeat extends Task{

    public TaskRepeat(int interval, Runnable runnable) {
        super(interval, runnable);
    }

    @Override
    protected BukkitTask createTask() {
       return Bukkit.getScheduler().runTaskTimer(NovaStrike.getInstance(), getRunnable(), 1, getDelay());
    }

    public void stop() {
        getTask().cancel();
        setTask(null);
    }

    public void restart() {
        stop();
        start();
    }

    public void changeDelay(final int newDelay) {
            stop();
            setDelay(newDelay);
            start();
    }
}
