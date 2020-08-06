/*
*   Created By: Rohan Amjad 
*   File: Main.java
*/

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    //mergeSort IMPLEMENTATION FOR SORTING ARRAY OF STRINGS Worst Case Time Complexity: O(n*log(n))
    //REFERENCE:
    /*
        Mishra, Rajat. “Merge Sort” GeeksforGeeks,
            07 Jun. 2020, www.geeksforgeeks.org/merge-sort/.
    */
    public static void mergeSort(String[] data){
        if (data.length != 1) {
            int n1 = data.length / 2;
            int n2 = data.length - (data.length / 2);

            //divide data into two arrays and fill them
            String[] arrayOne = new String[n1];
            String[] arrayTwo = new String[n2];
            for (int i = 0; i < n1; i++) {
                arrayOne[i] = data[i];
            }
            for (int j = 0; j < n2; j++) {
                arrayTwo[j] = data[n1 + j];
            }

            //recursive call
            mergeSort(arrayOne);
            mergeSort(arrayTwo);

            //sort and merge arrayOne and arrayTwo into data
            merge(data, arrayOne, arrayTwo);
        }
    }
    public static void merge(String[] data, String[] arrayOne, String[] arrayTwo){
        int i = 0;
        int j = 0;
        int k = 0;

        //while i and j indices remain within their respective arrays
        while (i < arrayOne.length && j < arrayTwo.length) {
            //if the element in arrayOne is larger than the element in arrayTwo
            if (arrayOne[i].compareToIgnoreCase(arrayTwo[j]) > 0) {
                //add the arrayTwo element to data
                data[k] = arrayTwo[j];
                //advance to next index of data and arrayTwo
                j++;
                k++;
            }
            else {
                //else add the element in arrayOne to data
                data[k] = arrayOne[i];
                //advance to next index of arrayOne and data
                i++;
                k++;
            }
        }

        //fill out data with the remaining array elements from arrayOne and arrayTwo
        while (i < arrayOne.length) {
            data[k] = arrayOne[i];
            i++;
            k++;
        }
        while (j < arrayTwo.length) {
            data[k] = arrayTwo[j];
            j++;
            k++;
        }
    }

    //isAnagram FUNCTION CHECKS IF TWO STRINGS ARE ANAGRAMS Worse Case Time Complexity: O(n)
    //REFERENCE:
    /*
        Tiwari, Nikita. “Check Whether Two Strings Are Anagram of Each Other.” GeeksforGeeks,
            27 Dec. 2019, www.geeksforgeeks.org/check-whether-two-strings-are-anagram-of-each-other/.
    */
    public static boolean isAnagram(String a, String b) {
        //if both strings have different lengths they cant be anagrams so return false
        if (a.length() != b.length()) {
            return false;
        }

        int n = 256;
        //array representing all ASCII indices
        int[] count = new int[n];


        for (int i = 0; i < a.length(); i++) {
            //increment the value of count at a's char ASCII index
            count[a.charAt(i)]++;
            //decrement the value of count at b's char ASCII index
            count[b.charAt(i)]--;
        }

        for (int i = 0; i < n; i++) {
            //if the strings are anagrams, all the ASCII indices in count must be 0
            if (count[i] != 0){
                return false;
            }
        }

        return true;
    }

    //printAnagram FUNCTION TO WRITE FORMATTED ANAGRAMS TO OUTPUT FILE
    public static void printAnagram(String[] data, String inputFileName){
        try {

            String outputFileName = "example_" + inputFileName.charAt(8) + "_out.txt";

            File outputFile = new File(outputFileName);
            FileWriter f = new FileWriter(outputFile);

            for (int i = 0; i < data.length; i++) {
                if (!data[i].equals("")) {
                    System.out.print(data[i]);
                    f.write(data[i] + " ");

                    for (int j = i + 1; j < data.length; j++) {
                        if (isAnagram(data[i], data[j])) {
                            
                            System.out.print(" " + data[j]);
                            f.write(data[j] + " ");
                            data[j] = "";

                        }
                    }
                    
                    System.out.println();
                    f.write("\n");
                }
            }

            f.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try{
            File inputFile = new File(args[0]);
            Scanner reader = new Scanner(inputFile);

            List<String> listData = new ArrayList<String>();

            //fill list with each line from text file
            while (reader.hasNextLine()) {
                listData.add(reader.nextLine());
            }
            reader.close();

            //convert list to a 1D array of strings
            String[] strData = listData.toArray(new String[0]);

            //sort array of strings using merge sort
            mergeSort(strData);

            //output formatted anagram list to output file
            printAnagram(strData, inputFile.getName());

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
