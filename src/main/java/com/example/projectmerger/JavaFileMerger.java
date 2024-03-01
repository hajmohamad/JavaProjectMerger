package com.example.projectmerger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class JavaFileMerger {
    static String folderPath;
    static List<String> imports = new ArrayList<>();
    static ArrayList<File> javaFiles = new ArrayList<>();
    static String newClassName = "";


    public static void findJavaFiles(String path) {
         folderPath = path;
        openJavaFiles(new File(folderPath));
    }

    private static void openJavaFiles(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            // List all files in the folder
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        openJavaFiles(file);
                    } else if (file.isFile() && file.getName().endsWith(".java")) {
                        System.out.println("Opening: " + file.getAbsolutePath());
                        javaFiles.add(file);
                    }
                }
            }

        } else {
            System.out.println("Invalid folder path. Please provide a valid folder path.");
        }

    }

    private static void writeImports() {
        for (String imp : imports) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(folderPath+"//"+newClassName+".java", true);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

                bufferedWriter.write(imp);
                bufferedWriter.newLine();

            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    private static void writeJavaFiles() {

        for(File file : javaFiles) {
            try (FileInputStream fileInputStream = new FileInputStream(file.getPath());
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                 FileOutputStream fileOutputStream = new FileOutputStream(folderPath+"//"+newClassName+".java", true);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    String[] words = line.split("\\s");
                    // Check if the line does not start with "import", "public", or "package"
                    if (!Objects.equals(words[0], "import") &&
                            !(Objects.equals(words[0], "public") && Objects.equals(words[1], "class"))
                            && !Objects.equals(words[0], "package")) {

                        // Write the line as it is to the output file
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                    // Check if the line starts with "public"
                    else if (Objects.equals(words[0], "public")) {

                        // Write the line starting from the first word (excluding "public")
                        for (int i = 1; i < words.length; i++) {
                            bufferedWriter.write(words[i] + " ");
                        }
                        bufferedWriter.newLine();
                    }

                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    private static void findImports(String filePath) {

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                // Split the line into words
                String[] words = line.split(" ");

                //check for imports that is not repeating
                if (Objects.equals(words[0], "import")) {
                    imports.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    public static String  mergeIt(String newFileName){
        newClassName = newFileName;
        for(File f:javaFiles){
            findImports(f.getPath());
        }
        writeImports();
        writeJavaFiles();






        return "";
    }


}