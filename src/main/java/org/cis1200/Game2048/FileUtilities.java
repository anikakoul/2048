package org.cis1200.Game2048;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class organizes some static methods for working with File IO.
 */
public class FileUtilities {

    /**
     * Takes in a filename and creates a BufferedReader.
     * See Java's documentation for BufferedReader to learn how to construct one
     * given a path to a file.
     *
     * @param filePath the path to the CSV file to be turned to a
     *                 BufferedReader
     * @return a BufferedReader of the provided file contents
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public static BufferedReader fileToReader(String filePath) {
        BufferedReader br;
        if (filePath == null) {
            throw new IllegalArgumentException("File path is null");
        } else {
            try {
                br = new BufferedReader(new FileReader(filePath));
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
        }

        return br;
    }

    /**
     * Given a {@code List} of {@code String}s, writes them to a file (one
     * {@code String} per
     * line in the file). This method uses {@code BufferedWriter}, the flip side to
     * {@code BufferedReader}. It may be useful to look at the JavaDocs for
     * {@code FileWriter}.
     * <p>
     * You may assume none of the arguments or strings passed in will be null.
     * <p>
     * If the process of opening the file or writing the data triggers an
     * {@code IOException}, you
     * should catch it and stop writing. (You can also print an error message to the
     * terminal, but we will not test that behavior.)
     *
     * @param stringsToWrite A List of Strings to write to the file
     * @param filePath       the string containing the path to the file where
     *                       the tweets should be written
     * @param append         a boolean indicating whether the new tweets
     *                       should be appended to the current file or should
     *                       overwrite its previous contents
     */
    public static void writeStringsToFile(
            List<String> stringsToWrite, String filePath,
            boolean append
    ) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            for (String line : stringsToWrite) {
                bw.append(line);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
