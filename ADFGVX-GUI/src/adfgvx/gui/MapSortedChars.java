/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adfgvx.gui;

import java.util.*;

/**
 *
 * @author Mustafa
 */
public class MapSortedChars {

    static String original, sorted = "", generatedMap = "";
    static ArrayList<Object> lst = new ArrayList<>();

    static String generateMap(String s) {
        generatedMap = "";
        lst.clear();
        original = s;
        sorted = sort_String(original);
        //System.out.println(sorted);
        for (int i = 0; i < original.length(); i++) {
            generatedMap += sorted.indexOf(original.charAt(i)) + 1;
            lst.add(sorted.indexOf(original.charAt(i)) + 1);
        }
        //System.out.println(lst.toString());
        return generatedMap;
    }

    static String sort_String(String inputString) {
        // convert input string to char array
        char tempArray[] = inputString.toCharArray();

        // sort tempArray
        Arrays.sort(tempArray);

        // return new sorted string
        return new String(tempArray);
    }
}
