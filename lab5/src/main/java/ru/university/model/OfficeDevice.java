package ru.university.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class OfficeDevice {
    protected int id;
    protected static int idCounter = 1;
    protected String model;
    protected int paperCount;
    protected Queue<String> taskQueue;
    protected double wearPercentage;
    protected boolean isPaperJammed;

    protected Random random = new Random();

    protected OfficeDevice(String model, int paperCount) {
        this.id = idCounter++;
        this.model = model;
        this.paperCount = paperCount;

        this.taskQueue = new LinkedList<>();
        this.wearPercentage = 0.0;
        this.isPaperJammed = false;
    }

    public abstract void startTask(int pages);

    public void stopTask(String model) {
        System.out.println("[" + model + "] текущая операция остановлена");
    }

    public void loadPaper(int amount) {
        paperCount += amount;
        System.out.println("Добавлено " + amount + ". Итог:" + paperCount);
    }

    public void clearJam() {
        isPaperJammed = false;
        wearPercentage = 0;
        System.out.println("Бумага выпрямлена");
    }

    public void cancelAllTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("Список задач пуст");
        } else {
            taskQueue.clear();
        }
    }

    protected void isWearPercentage() {
        double delta = 0.5 + (2.0 - 0.5) * random.nextDouble();
        this.wearPercentage = Math.min(100, this.wearPercentage + delta);
        if (wearPercentage == 100.0) {
            isPaperJammed = true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model != null && !model.trim().isEmpty()) {
            this.model = model;
        }
    }

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = Math.max(0, paperCount);
    }

    public Queue<String> getTaskQueue() {
        return taskQueue;
    }

    public double getWearPercentage() {
        return wearPercentage;
    }

    public void setWearPercentage(double wearPercentage) {
        this.wearPercentage = Math.max(0.0, Math.min(100.0, wearPercentage));
    }

    public boolean isPaperJammed() {
        return isPaperJammed;
    }

    public void setPaperJammed(boolean paperJammed) {
        isPaperJammed = paperJammed;
    }
}