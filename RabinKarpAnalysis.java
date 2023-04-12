import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RabinKarpAnalysis {

    public static int search(String n, String m) { // Rabin Karp String Matching Algorithm
        int matches = 0;
        int nLen = n.length();
        int mLen = m.length();
        int mHash = m.hashCode();
        int nHash = n.substring(0, mLen).hashCode();

        if (nLen < mLen) {
            return matches;
        }
        if (mHash == nHash && m.equals(n.substring(0, mLen))) {
            matches++;
        }
        for (int i = mLen; i < nLen; i++) {
            nHash = nHash - n.charAt(i - mLen) * (int) Math.pow(31, mLen - 1);
            nHash = nHash * 31 + n.charAt(i);
            if (mHash == nHash && m.equals(n.substring(i - mLen + 1, i + 1))) {
                matches++;
            }
        }
        return matches;
    }

    public static String[] readOriginal(String filename) { // Reads content from original file into an array of sentences
        String contents = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                contents += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] sentences = contents.split("[.!?]\\s*");
        return sentences;
    }

    public static List<String> readOther(String originalfile) { 
        // Reads contents from files other than the original file
        String dataDirectory = "./data";
        List<File> fileList = new ArrayList<>(Arrays.asList(new File(dataDirectory).listFiles()));
        List<String> contentsList = new ArrayList<>();

        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (file.isFile() && !file.getName().equals(originalfile)) {
                String filename = file.getAbsolutePath();
                String contents = "";

                try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        contents += line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                contentsList.add(contents);
            }
        }
        return contentsList;
    }

    public static long[] algorithmicTest(String testType, int runNumber, int stepSize) {
        long[] runtimes = new long[runNumber/stepSize]; //Runtime for each independent variable
        List<String> dataset = new ArrayList<>();
        String[] contents = new String[runNumber];
        Arrays.fill(contents, "");
        String test_string = "aaaaa"; //adding on this string each time
        switch(testType){
            case "datanumber": //independent variable being the number of elements in the dataset
            for(int i = 0; i < 500; i++){contents[0] = contents[0] + test_string;}
            for(int i = 0; i < runNumber; i++){
                dataset.add(test_string);
                if(i%stepSize == 0){
                    long startTime = System.currentTimeMillis();
                    contentComparer(contents, dataset); 
                    long totalTime = System.currentTimeMillis() - startTime;
                    runtimes[i/stepSize] = totalTime;
                }
            }
            break;

            case "datasize": //independent variable being the size of elements in the dataset
            // contents[0] = test_string; 
            for(int i = 0; i < 500; i++){contents[0] = contents[0] + test_string;}
            String s = test_string;
            dataset.add("");
            for(int i = 0; i < runNumber; i++){
                dataset.set(0, s);
                s = s + test_string;
                if(i%stepSize == 0){
                    long startTime = System.currentTimeMillis();
                    contentComparer(contents, dataset); 
                    long totalTime = System.currentTimeMillis() - startTime;
                    runtimes[i/stepSize] = totalTime;
                }
            }
            break;

            case "filenumber": //independent variable being number of elements from file
            for(int i = 0; i < 500; i++){dataset.add(test_string);}
            for(int i = 0; i < runNumber; i++){
                contents[i] = test_string;
                if(i%stepSize == 0){
                    long startTime = System.currentTimeMillis();
                    contentComparer(contents, dataset); 
                    long totalTime = System.currentTimeMillis() - startTime;
                    runtimes[i/stepSize] = totalTime;
                }
            }
            break;

            case "filesize": //independent variable being size of elements from file
            for(int i = 0; i < 500; i++){dataset.add(test_string);}
            for(int i = 0; i < runNumber; i++){
                contents[0] = contents[0] + test_string; 
                if(i%stepSize == 0){
                    long startTime = System.currentTimeMillis();
                    contentComparer(contents, dataset); 
                    long totalTime = System.currentTimeMillis() - startTime;
                    runtimes[i/stepSize] = totalTime;
                }
            }
            break;

            default:
            break;
        }
       return runtimes; 
    }

    public static void multiTester(String testType, int runNumber, int stepSize, int numOfTests){
        int arrayLength = runNumber/stepSize;
        long[][] tests = new long[numOfTests][arrayLength];
        for(int i = 0; i < numOfTests; i++){
            tests[i] = algorithmicTest(testType, runNumber, stepSize);
        }
        long[] avg = new long[arrayLength];
        Arrays.fill(avg, 0);
        for(int i = 0; i < arrayLength; i++){
            for(int j = 0; j < numOfTests; j++){
                avg[i] += tests[j][i];
            }
            avg[i] /= numOfTests;
        }
        System.out.println(Arrays.toString(avg));
        try{
            FileWriter fw = new FileWriter("./analysis/" + testType + runNumber + ".js");
            fw.write(Arrays.toString(avg));
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int contentComparer(String[] contents, List<String> dataset){
        int matches = 0;
        for (int i = 0; i < contents.length; i++) {
            for (int j = 0; j < dataset.size(); j++) {
                if (contents[i].length() <= dataset.get(j).length())
                    matches += search(dataset.get(j), contents[i]); // increment number of matches with every match made
            }
        }
            return matches;
    }

    public static void main(String[] args) {
    //algorithmicTest("datanumber", 500, 10);
    multiTester("datanumber", 2000, 50, 10);
    multiTester("datasize", 2000, 50, 10);
    multiTester("filenumber", 2000, 50, 10);
    multiTester("filesize", 2000, 50, 10);
    }
}
