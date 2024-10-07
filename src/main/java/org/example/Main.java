package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    private static final String TXT_FILE = "input.txt";
    private static final String SVC_FILE = "input.svc";
    private static final String OUTPUT_FILE = "stats.txt";
    private static final String TEMPLATE_TEXT = "Привет мир";

    public static void main(String[] args) {
        try {
            boolean txtExists = new File(TXT_FILE).exists();
            boolean svcExists = new File(SVC_FILE).exists();

            if (!txtExists && !svcExists) {
                createFile(TXT_FILE, TEMPLATE_TEXT);
                createFile(SVC_FILE, TEMPLATE_TEXT);
                System.out.println("Файлы " + TXT_FILE + " и " + SVC_FILE + " созданы с шаблонным текстом. Программа завершена.");
                return;
            }

            String fileToRead = "";
            if (txtExists) {
                fileToRead = TXT_FILE;
            } else {
                fileToRead = SVC_FILE;
            }

            String content = readFileContent(fileToRead);

            int totalCharacters = content.length();
            int charactersWithoutSpaces = content.replace(" ", "").length();
            int wordCount = countWords(content);

            String report = "Статистика для файла " + fileToRead + ":\n" +
                    "Количество символов: " + totalCharacters + "\n" +
                    "Количество символов без пробелов: " + charactersWithoutSpaces + "\n" +
                    "Количество слов: " + wordCount + "\n";

            System.out.println(report);

            writeFile(OUTPUT_FILE, report);
            System.out.println("Статистика записана в файл " + OUTPUT_FILE);

        } catch (IOException e) {
            System.err.println("Произошла ошибка при работе с файлами: " + e.getMessage());
        }
    }


    private static void createFile(String filename, String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(text);
        }
    }


    private static String readFileContent(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }


    private static void writeFile(String filename, String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) { // true для добавления в конец файла
            writer.write(text);
        }
    }


    private static int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        // Разделяем по пробельным символам
        String[] words = text.trim().split("\\s+");
        return words.length;
    }
}
