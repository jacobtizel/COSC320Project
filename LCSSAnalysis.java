import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class LCSSAnalysis {

    public static int LCSSSearch(String s1, String s2) {
        int[][] table = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 || j == 0) {
                    table[i][j] = 0;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    table[i][j] = table[i - 1][j - 1] + 1;
                } else {
                    table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
                }
            }
        }

        // Return the length of the LCSS
        return table[s1.length()][s2.length()];
    }

    public static long[] LCSSAlgorithmicTest(String testType, int runNumber, int stepSize) {
        long[] runtimes = new long[runNumber / stepSize]; // Runtime for each independent variable

        String test_string = "aaaab"; // adding on this string each time

        StringBuilder pattern_builder = new StringBuilder(); // make pattern for stringlength and randomstring 5 instances of teststring
        for(int i = 0; i < 5; i++){
            pattern_builder.append(test_string);
        }
        String pattern = pattern_builder.toString();
        StringBuilder data = new StringBuilder(); // stringbuilder more efficient than adding onto strings
        Random r = new Random();

        switch (testType) {
            case "stringlength": //repeatedly adding pattern to data
                for (int i = 0; i < runNumber; i++) {
                    data.append(test_string);
                    if (i % stepSize == 0) {
                        long startTime = System.currentTimeMillis();
                        LCSSSearch(data.toString(), pattern);
                        long totalTime = System.currentTimeMillis() - startTime;
                        runtimes[i / stepSize] = totalTime;
                    }
                }
                break;
            case "randomstring": //randomising data by adding random letters
                for (int i = 0; i < runNumber; i++) {
                    for (int j = 0; j < 5; j++) {
                        char c = (char) (r.nextInt(26) + 'a');
                        data.append(c);
                    }
                    if (i % stepSize == 0) {
                        long startTime = System.currentTimeMillis();
                        LCSSSearch(data.toString(), pattern);
                        long totalTime = System.currentTimeMillis() - startTime;
                        runtimes[i / stepSize] = totalTime;
                    }
                }
                break;
            case "patternanddata": // increasing both length of pattern and length of data
                for (int i = 0; i < runNumber; i++) {
                    data.append(test_string);
                    if (i % stepSize == 0) {
                        long startTime = System.currentTimeMillis();
                        LCSSSearch(data.toString(), data.toString());
                        long totalTime = System.currentTimeMillis() - startTime;
                        runtimes[i / stepSize] = totalTime;
                    }
                }
                break;

            default:
                break;
        }

        return runtimes;
    }

    public static void multiTester(String testType, int runNumber, int stepSize, int numOfTests) {
        int arrayLength = runNumber / stepSize;
        long[][] tests = new long[numOfTests][arrayLength];
        for (int i = 0; i < numOfTests; i++) {
            tests[i] = LCSSAlgorithmicTest(testType, runNumber, stepSize);
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
            FileWriter fw = new FileWriter("./analysis/LCSS" + testType + runNumber + ".json");
            fw.write(Arrays.toString(avg));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        multiTester("stringlength", 50000, 1000, 20);
        multiTester("randomstring", 50000, 1000, 20);
        multiTester("patternanddata", 5000, 250, 10);

        // multiTester("randomstring",100000, 10000, 20);
    }

}