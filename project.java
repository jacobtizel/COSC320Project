import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class project {

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

   public static String[] readOriginal(String filename){ // Reads content from original file into an array of sentences
      String contents = "";
      try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
         String line;
         while ((line = br.readLine()) != null) {
             contents += line;
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
     String[] sentences= contents.split("[.!?]\\s*");
     return sentences;
   }

   public static List<String> readOther(String originalfile) { // Reads contents from files other than the original file
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
   public static void main(String[] args) {
         String[] contents = readOriginal("./data/test1.txt");
         List<String> othercontents = readOther("test.txt");
         int matches = 0;
         for (int i = 0; i < contents.length; i++) {
            for (int j = 0; j < othercontents.size(); j++) {
               if(contents[i].length()<=othercontents.get(j).length())
               matches+=search(othercontents.get(j),contents[i]); // increment number of matches with every match made
            }
         }
      
         if(matches>=(contents.length)/2){
            System.out.println("This document is plagiarised");
         }
         else{
             System.out.println("This document not plagiarised");
         }
   }
}
