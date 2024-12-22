package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class TaskMenager {
    public static void main(String[] args) {
        File file = new File("tasks.csv");
        Scanner scan = null;
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String[][] tasks = new String[100][3];
        do {
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            int index = 0;
            while (scan.hasNextLine() && index < tasks.length) {
                String line = scan.nextLine();
                tasks[index] = line.split(", ");
                index++;
            }

            System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
            System.out.println("add");
            System.out.println("remove");
            System.out.println("list");
            System.out.println("exit");
            input = scanner.nextLine();
            switch (input) {
                case "add":
                    add();
                    break;
                case "remove":
                   tasks = remove(tasks);
                    System.out.println(Arrays.deepToString(tasks));
                    break;
                case "list":
                    list(tasks);
                    break;
                case "exit":
                    break;
            }
        } while (!input.equals("exit"));
    }

    public static void add() {
        Scanner scan = new Scanner(System.in);
        String answer = "";
        do {
            try (FileWriter fileWriter = new FileWriter("tasks.csv", true)) {
                System.out.println("Please add task description: ");
                String name = scan.nextLine();
                System.out.println("Please add task due date: ");
                String Date = scan.nextLine();
                System.out.println("Is your task is important? ");
                String important = scan.nextLine();
                fileWriter.append(name).append(", ").append(Date).append(", ").append(important).append("\n");
                System.out.println("Successfully added task! Do you want to continue adding tasks? (y/n)");
                answer = scan.nextLine();
            } catch (IOException ex) {
                System.out.println("Something went wrong");
            }
        }while (!answer.equals("n"));
    }

    public static String[][] remove(String[][] tasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Witch task do you want to remove? : ");
        String in = scanner.nextLine();
        try {
            if (Integer.parseInt(in) > 0 && Integer.parseInt(in) < tasks.length) {
                tasks = ArrayUtils.remove(tasks, Integer.parseInt(in)-1);
            }
        }catch (NumberFormatException ex) {
            System.out.println("Please enter a valid number.");
        }
        return tasks;
    }

    public static void list(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] != null && tasks[i].length > 0 && !isRowEmpty(tasks[i])) {
                System.out.println((i + 1) + " : " + Arrays.deepToString(tasks[i]));
            }
        }
    }

    private static boolean isRowEmpty(String[] row) {
        for (String element : row) {
            if (element != null && !element.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
