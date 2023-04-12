import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class KMPAnalysis {
    // Knuth-Morris-Pratt Algorithm

    public static int KMPSearch(String text, String pattern) {
        int[] prefix = computePrefix(pattern);
        int matches = 0;
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && pattern.charAt(j) != text.charAt(i)) {
                j = prefix[j - 1];
            }
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
            }
            if (j == pattern.length()) {
                matches++;
                j = prefix[j - 1];
            }
        }
        return matches;
    }

    private static int[] computePrefix(String pattern) {
        int[] prefix = new int[pattern.length()];
        int j = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(j) != pattern.charAt(i)) {
                j = prefix[j - 1];
            }
            if (pattern.charAt(j) == pattern.charAt(i)) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }

    public static void multiTester(String testType, int runNumber, int stepSize, int numOfTests) {
        int arrayLength = runNumber / stepSize;
        long[][] tests = new long[numOfTests][arrayLength];
        for (int i = 0; i < numOfTests; i++) {
            tests[i] = KMPAlgorithmicTest(testType, runNumber, stepSize);
        }
        long[] avg = new long[arrayLength];
        Arrays.fill(avg, 0);
        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < numOfTests; j++) {
                avg[i] += tests[j][i];
            }
            avg[i] /= numOfTests;
        }
        System.out.println(Arrays.toString(avg));
        try {
            FileWriter fw = new FileWriter("./analysis/KMP" + testType + runNumber + ".json");
            fw.write(Arrays.toString(avg));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long[] KMPAlgorithmicTest(String testType, int runNumber, int stepSize) {
        long[] runtimes = new long[runNumber / stepSize]; // Runtime for each independent variable

        String test_string = "aaaab"; 
        return runtimes;
    }


    public static void main(String[] args){

    }
}
