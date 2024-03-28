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
    static List<File> javaFiles = new ArrayList<>();
    static String newClassName = "";
    static String destinationFilePath;


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
                    } else if (file.isFile() && file.getName().endsWith(".java")&&!file.getName().contains("module-info.java")) {
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
        try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFilePath+"//"+newClassName+".java", true);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

        for (String imp : imports) {

                bufferedWriter.write(imp);
                bufferedWriter.newLine();

        }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }


    }

    private static void writeJavaFiles() {

        for(File file : javaFiles) {
            try (FileInputStream fileInputStream = new FileInputStream(file.getPath());
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                 FileOutputStream fileOutputStream = new FileOutputStream(destinationFilePath+"//"+newClassName+".java", true);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

                String line;
                while ((line = bufferedReader.readLine()) != null) {


                    // Check if the line does not start with "import", "public", or "package"
                    if (!line.startsWith("import") && !line.startsWith("public") && !line.startsWith("package") &&
                             !line.startsWith("class")) {

                        // Write the line as it is to the output file
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                    // Check if the line starts with "public"
                    else if (line.startsWith("public") ) {

                        // Write the line starting from the first word (excluding "public")

                            bufferedWriter.write(line.replace("public", ""));

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
               if(line.startsWith("import")){
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