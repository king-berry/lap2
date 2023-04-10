package bai4.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Manager {
    private static final List<String> taskList = new ArrayList<>();
    static class ManagerThread extends Thread {
        public void run() {
            final String[] tasks = {"Task 1", "Task 2", "Task 3", "Task 4", "Task 5"};
            final Random random = new Random();
            while (true) {
                final int index = random.nextInt(tasks.length);
                final String task = tasks[index];
                synchronized (taskList) {
                    taskList.add(task);
                    System.out.println("Added task \"" + task + "\" to taskList");
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class WorkerThread extends Thread {
        private final String name;

        public WorkerThread(final String name) {
            this.name = name;
        }

        public void run() {
            while (true) {
                String task = null;
                synchronized (taskList) {
                    if (!taskList.isEmpty()) {
                        task = taskList.remove(0);
                    }
                }
                if (task != null) {
                    System.out.println(name + " is performing task \"" + task + "\"");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final String[] workerNames = {"Worker 1", "Worker 2", "Worker 3", "Worker 4", "Worker 5"};
        final Thread[] workerThreads = new Thread[workerNames.length];
        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i] = new WorkerThread(workerNames[i]);
            workerThreads[i].start();
        }
        final Thread managerThread = new ManagerThread();
        managerThread.start();
    }
}