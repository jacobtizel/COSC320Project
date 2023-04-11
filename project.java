import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class project {

    // Rabin Karp String Matching Algorithm

   public static int RKSearch(String n, String m) { 
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

// Longest Common Substring Algorithm
  
public static int LCSSSearch(String s1, String s2) {
    int[][] table = new int[s1.length() + 1][s2.length() + 1];
    for (int i = 0; i <= s1.length(); i++) {
        for (int j = 0; j <= s2.length(); j++) {
            if (i == 0 || j == 0) {
                table[i][j] = 0;
            } else if (s1.charAt(i-1) == s2.charAt(j-1)) {
                table[i][j] = table[i-1][j-1] + 1;
            } else {
                table[i][j] = Math.max(table[i-1][j], table[i][j-1]);
            }
        }
    }
    
    // Return the length of the LCSS
    return table[s1.length()][s2.length()];
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
               matches+=RKSearch(othercontents.get(j),contents[i]); // increment number of matches with every match made
            }
         }
      
         if(matches>=(contents.length)/2){
            System.out.println("This document is plagiarised");
         }
         else{
             System.out.println("This document is not plagiarised");
         }
   }
}
