# Plagiarism Detector 

### Summary
A working plagiarism detector was implemented using three different approaches:

- A Rabin-Karp String Matching Algorithm
- A KMP (Knuth Morris Pratt) String Matching Algorithm
- A LCSS (Longest Common Subsequence) Algorithm

For each of the three approaches, the algorithms were run with text from the file to be tested against two datasets of existing scientific papers that were sourced from ArXiv and PubMed open access repositories. The dataset consists of multiple json files, each containing article ID, the abstract, the text itself, a list of section names, and a list of the sections themselves. The dataset was used in our plagiarism detector by scraping the article text for each of the articles, and storing them as text files to create a corpus of text.. Then, our program can run string matching algorithms against the files to detect plagiarism in a given text.
A formal analysis was performed on all three algorithms, which included mapping the pseudocode, a time complexity analysis, and a proof of correctness.

### File Structure

    .
    ├── /analysis                  # Data used for analysis of the three employed algorithms.
    ├── /data                      # Test files used for the plagiarism detector.
    ├── project.java               # Implementation of all three algorithms.
    ├── KMPAnalysis.ipynb          # Analysis for the Knuth-Morris Pratt Algorithm
    ├── KMPAnalysis.java                   
    ├── LCSSAnalysis.ipynb         # Analysis for the Longest Common Substring Algorithm
    ├── LCSSAnalysis.java
    ├── RabinKarpAnalysis.ipynb    # Analysis for the Rabin Karp Algorithm
    ├── RabinKarpAnalysis.java
    ├── AlgoComparison.ipynb       # Comparison of all 3 algorithms for up to n of 500,000
    └── README.md

### Analysis Conclusion
After conducting a thorough analysis of the three algorithms that we will be using for our plagiarism detector, we can say that judging solely by worst case asymptotic time complexities of the algorithms (i.e. from the results of our formal analyses), we expect the KMP approach to be the fastest and most efficient at detecting plagiarism, followed by the Rabin-Karp approach, and with the LCSS approach being the slowest.

### Unexpected Cases/Difficulties
- Our implementation for Rabin-Karp differed from the implementations for KMP and LCSS, which made it difficult to test their running times in the same conditions.
- It was also difficult to test the running times of the algorithms against real world data, which is why for our tests we went for custom generated data which was scalable and much more easy to control. This resulted in the graphs communicating much more clearly the difference in running time and the growth functions for these algorithms.

### Task Separation and Responsibilities
- Lakshay - Summary, Formal Analysis, Abstract, Unexpected Cases

- Jacob - Testing and analyzing runtimes, plotting data in Jupyter Notebook, Proofs of correctness

- Ribhav - Implementation, Documentation

### Citations

- "A Discourse-Aware Attention Model for Abstractive Summarization of Long Documents"  
Arman Cohan, Franck Dernoncourt, Doo Soon Kim, Trung Bui, Seokhwan Kim, Walter Chang, and Nazli Goharian  
NAACL-HLT 2018

