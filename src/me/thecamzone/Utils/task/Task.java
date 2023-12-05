package me.thecamzone.Utils.task;

import org.bukkit.scheduler.BukkitTask;

public abstract class Task {

    private final Runnable runnable;
    private int delay;
    private BukkitTask task;

    protected Task(int delay, Runnable runnable) {
        this.delay = delay;
        this.runnable = runnable;
        start();
    }

    public void start() {
        if (task == null) {
            task = createTask();
        }
    }

    public void stop() {
        if (task == null)
            return;
        task.cancel();
    }

    protected abstract BukkitTask createTask();

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    public Runnable getRunnable() {
        return runnable;
    }


    public int getDelay() {
        return delay;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}


