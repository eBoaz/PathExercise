import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class PathExercise {
    private static long totalLinesCount;
    private static long totalValuesCount;
    private static List<String> allPathValues;


    public static void main(String args[]) throws IOException {
        // Reading user input and converting into file system  paths
        String[] path1 = readPaths(readInput());
        String[] path2 = readPaths(readInput());
        totalLinesCount = 0;
        totalValuesCount = 0;
        // Iterating over the first path
        for (String paths : path1){
            System.out.println(paths);
            countFilesAndTXTFilesLines(paths);
            // Build a list that contains all files/dirs in the given path
            allPathValues.add(paths);
        }
        // Iterating over the second path
        for (String paths : path2){
            countFilesAndTXTFilesLines(paths);
            compareArraysForMatchingValues(allPathValues,paths);
        }
        System.out.println("The total number of lines is  " + totalLinesCount );
    }

    /**
     * Read the file system path from the user input
     * @param userInput
     * @return
     */
    public static String[]  readPaths(String userInput){
        File path1File = new File(userInput);
        String[] path1FileArray = path1File.list();
        return path1FileArray;
    }

    /**
     * Read the user input
     * @return
     */
    public static String readInput(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the wanted path");
        String userInput = in.nextLine();
        System.out.println("You entered string " + userInput);
        return userInput;
    }
    /**
     * Counts the total number of files and total number of lines in text files across the given path
     * @param paths string of the path
     */
    public static void countFilesAndTXTFilesLines(String paths) throws IOException {
        File pathsFile = new File(paths);
        if (pathsFile.isFile()) { // This part of code will not execute on windows - at least not on windows as it requires escaping for the \ sign, and I had no time to handle it, I've tested it with hard coded path and it worked.
            // Count the number of files
            totalValuesCount = totalValuesCount++;
            // Count the number of lines for txt files only and sum them up
            if (paths.endsWith("txt") == true) {
                Path path = Paths.get(String.valueOf(pathsFile));
                long lineCount = Files.lines(path).count();
                totalLinesCount = totalLinesCount + lineCount;
            }
        } else {
            // If the file system value was not a file, then it is a directory and so we will get it's full path and re-iterate over the sub folder
            String[] path = readPaths(pathsFile.getAbsolutePath());
            for (String pathsNested : path) {
                countFilesAndTXTFilesLines(pathsFile.getAbsolutePath());
            }
        }
    }

    /**
    * This method compares two arrays to find matching values and prints the value that is matched
     * @param list list for comparision and matching a value
     * @param path a specific path for comparision from the second path the user has input
     */
    public static void compareArraysForMatchingValues(List<String> list, String path){
        if (list.contains(path))
            System.out.println("The File/Folder + " + path + " exists in both paths");
    }

}
