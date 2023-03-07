import java.io.BufferedReader;
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
            // System.out.println(nHash);
            // System.out.println(mHash);
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

    public static void algorithmicTest(String type, int run_number) {
        int[] data_length = new int[run_number]; //Place for storing the independant variables
        long[] runtimes = new long[run_number]; //Runtime for each independent variable
        List<String> dataset = new ArrayList<>();
        String[] contents = new String[run_number];
        switch(type){
            case "datanumber": //independant variable being the number of elements in the dataset
            contents[0] = "aaaaa";
            dataset = new ArrayList<String>();
            for(int i = 0; i < run_number; i++){
                long startTime = System.currentTimeMillis();
                dataset.add("aaaaa");
                contentComparer(contents, dataset); 
                long totalTime = System.currentTimeMillis() - startTime;
                data_length[i] = i+1;
                runtimes[i] = totalTime;
            }
            break;
            case "datasize": //independant variable being the size of elements in the dataset
            contents[0] = "aaaaa"; 
            String s = "aaaaa";
            dataset.clear();
            dataset.add("aaaaa");
            for(int i = 0; i < run_number; i++){
                long startTime = System.currentTimeMillis();
                s.concat("aaaaa");
                dataset.set(0, s);
                contentComparer(contents, dataset); 
                long totalTime = System.currentTimeMillis() - startTime;
                data_length[i] = i+1;
                runtimes[i] = totalTime;
            }
            break;
            case "file":
            break;
            default:
            break;
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
        long startTime = System.currentTimeMillis();
        String[] contents = readOriginal("./data/orange.txt");
        List<String> dataset = readOther("orange.txt");
        int matches = contentComparer(contents, dataset);

        if (matches >= (contents.length) / 2) {
            System.out.println("This document is plagiarised");
        } else {
            System.out.println("This document not plagiarised");
        }

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println(totalTime);
    }
}
