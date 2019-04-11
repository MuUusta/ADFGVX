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
public class ADFGVX {

    private Scanner in = new Scanner(System.in);
    private Random rand;
    private int randNumIndex;
    private final String alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final String defaultKey = "CR2BOPIMLE4J7AZV5U83F1X6SWGHYN9DQKT0";
    private final String cipherCode = "ADFGVX";
    private String cipherKey, cipherText;
    private String decrypt_cipherText, decrypt_keyword, decrypt_keysquare, decrypt_intermKey = "";
    private String final_Mat_Str = "";
    private String key_Gen_Tmp, key_Gen_Final, plainText, intermediateText, currentKey;
    private String[][] substitution_Table;
    private Object[][] final_Matrix;

    ADFGVX() {
        rand = new Random();
        key_Gen_Final = "";
        plainText = "";
        intermediateText = "";
        substitution_Table = new String[6][6];
        currentKey = defaultKey;
        cipherKey = "";
        cipherText = "";
        fillInStringArray(substitution_Table, defaultKey);
    }

    String Gen_New_Key() {
        key_Gen_Tmp = alphanum;
        while (key_Gen_Tmp.length() > 0) {
            randNumIndex = rand.nextInt(key_Gen_Tmp.length()) + 0;
            key_Gen_Final += key_Gen_Tmp.charAt(randNumIndex);
            StringBuilder sb = new StringBuilder(key_Gen_Tmp);
            sb.deleteCharAt(randNumIndex);
            key_Gen_Tmp = sb.toString();
        }
        //System.out.println(key_Gen_Final);
        currentKey = key_Gen_Final;
        fillInStringArray(substitution_Table, key_Gen_Final);
        return currentKey;
    }

    String[][] fillInStringArray(String[][] str, String key) {
        key_Gen_Tmp = key;
        String tmpChar = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tmpChar += key_Gen_Tmp.charAt(0);
                str[i][j] = tmpChar;
                tmpChar = "";
                StringBuilder sb = new StringBuilder(key_Gen_Tmp);
                sb.deleteCharAt(0);
                key_Gen_Tmp = sb.toString();
            }
        }
        return str;
    }

    void print_Key() {
        System.out.println(currentKey);
    }

    void print_Substitution_Table() {
        System.out.println("Substitution_Table: ");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(substitution_Table[i][j] + " ");
            }
            System.out.println("");
        }
    }

    void Gen_Intermediate_Text() {
        
        //System.out.print("Enter PlainText: ");
        //plainText = in.nextLine();
        //System.out.print("Enter CipherKey('NOTE:without dublicated chars!'): ");
        //cipherKey = in.nextLine();
        //IF we want a unique cipher key
        //while(check_String_Dub(cipherKey)==false){
        //   System.out.print("Dublicated Chars Found! try another:");
        //   cipherKey = in.nextLine();
        // }
        String tmp = "";
        intermediateText ="";
        for (int i = 0; i < plainText.length(); i++) {
            tmp += plainText.charAt(i);
            for (int a = 0; a < 6; a++) {
                for (int b = 0; b < 6; b++) {
                    if (tmp.toUpperCase().equals(substitution_Table[a][b])) {
                        intermediateText += cipherCode.charAt(a);
                        intermediateText += cipherCode.charAt(b);
                        //intermediateText += " ";
                        tmp = "";
                        break;
                    }
                }
            }
            tmp = "";
        }
        //System.out.println(intermediateText);
        Gen_Transposition_Matrix();
    }

    String sort_String(String inputString) {
        // convert input string to char array
        char tempArray[] = inputString.toCharArray();

        // sort tempArray
        Arrays.sort(tempArray);

        // return new sorted string
        return new String(tempArray);
    }

    boolean check_String_Dub(String s) {
        boolean check = true;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    check = false;
                    return check;
                }
            }
        }
        return check;
    }

    void Gen_Transposition_Matrix() {
        final_Mat_Str="";
        float rows = (cipherKey.length() * 2) + intermediateText.length();
        rows = rows / cipherKey.length();
        if ((rows / (int) rows) != 1) {
            rows = (int) rows + 1;
        }
        final_Matrix = new Object[(int) rows][cipherKey.length()];
        //final_Mat_Str = cipherKey+MapSortedChars.generateMap(cipherKey)+intermediateText;
        final_Mat_Str = cipherKey + intermediateText;
        MapSortedChars.generateMap(cipherKey);
        System.out.println(MapSortedChars.generatedMap);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cipherKey.length(); j++) {
                if (i == 1) {
                    final_Matrix[i][j] = MapSortedChars.lst.get(j);
                } else {
                    if (final_Mat_Str.length() == 0) {
                        final_Matrix[i][j] = "";
                    } else {
                        final_Matrix[i][j] = final_Mat_Str.charAt(0);
                        StringBuilder sb = new StringBuilder(final_Mat_Str);
                        sb.deleteCharAt(0);
                        final_Mat_Str = sb.toString();
                    }
                }
            }
        }
        Gen_Cipher_Text();
    }

    void Gen_Cipher_Text() {
        for (int i = 1; i <= cipherKey.length(); i++) {
            for (int j = 0; j < final_Matrix[0].length; j++) {
                if (i == Integer.parseInt(final_Matrix[1][j].toString())) {
                    for (int k = 2; k < final_Matrix.length; k++) {
                        cipherText += final_Matrix[k][j].toString();
                    }
                }
            }
        }
        //System.out.println("CipherText: " + cipherText);
    }

    void print_Final_Matrix() {
        System.out.println("Final-Matrix: ");
        for (int i = 0; i < final_Matrix.length; i++) {
            for (int j = 0; j < final_Matrix[0].length; j++) {
                System.out.print(final_Matrix[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    String get_CipherText() {
        return cipherText;
    }

    void start_Encryption(String pText, String cKey) {
        plainText = null;
        cipherKey = null;
        plainText = pText;
        cipherKey = cKey;
        Gen_Intermediate_Text();
    }

    void start_Decryption(String d_ks, String d_k, String d_ct) {

        //System.out.print("Enter KeySquare: ");
        decrypt_keysquare = d_ks;
        //System.out.print("Enter Key: ");
        decrypt_keyword = d_k;
        //System.out.print("Enter CipherText: ");
        decrypt_cipherText = d_ct;
        String decrypt_cipherText_tmp = decrypt_cipherText;
        //generate table
        float rows = (decrypt_keyword.length() * 2) + decrypt_cipherText.length();
        rows = rows / decrypt_keyword.length();
        if ((rows / (int) rows) != 1) {
            rows = (int) rows + 1;
        }
        final_Matrix = new Object[(int) rows][decrypt_keyword.length()];
        //print_Final_Matrix();
        MapSortedChars.generateMap(decrypt_keyword);
        for (int i = 0; i < decrypt_keyword.length(); i++) {
            final_Matrix[0][i] = decrypt_keyword.charAt(i);
        }
        for (int i = 0; i < decrypt_keyword.length(); i++) {
            final_Matrix[1][i] = MapSortedChars.lst.get(i);
        }
        int count = decrypt_cipherText.length();
        for (int i = 2; i < rows; i++) {
            for (int j = 0; j < decrypt_keyword.length(); j++) {
                if (count <= 0) {
                    break;
                } else {
                    final_Matrix[i][j] = '-';
                    count--;
                }
            }
        }
        //print_Final_Matrix();
        //
        fillInStringArray(substitution_Table, decrypt_keysquare);
        //print_Substitution_Table();
        //
        for (int i = 1; i <= decrypt_keyword.length(); i++) {
            for (int j = 0; j < decrypt_keyword.length(); j++) {
                if ((int) final_Matrix[1][j] == i) {
                    for (int k = 2; k < rows; k++) {
                        if (final_Matrix[k][j] == null) {
                            break;
                        } else {
                            final_Matrix[k][j] = decrypt_cipherText_tmp.charAt(0);
                            StringBuilder sb = new StringBuilder(decrypt_cipherText_tmp);
                            sb.deleteCharAt(0);
                            decrypt_cipherText_tmp = sb.toString();
                        }
                    }
                }
            }
        }
        for (int i = 2; i < rows; i++) {
            for (int j = 0; j < decrypt_keyword.length(); j++) {
                if (final_Matrix[i][j] != null) {
                    decrypt_intermKey += final_Matrix[i][j];
                }
            }
        }
        plainText = "";
        String int_tmp = decrypt_intermKey;
        //for(int i=0; i<decrypt_intermKey.length()-1; i++){
        while (int_tmp.length() > 0) {
            plainText += substitution_Table[cipherCode.indexOf(int_tmp.charAt(0))][cipherCode.indexOf(int_tmp.charAt(1))];
            //System.out.print(cipherCode.indexOf(int_tmp.charAt(0)));
            //System.out.println(cipherCode.indexOf(int_tmp.charAt(1)));
            StringBuilder sb = new StringBuilder(int_tmp);
            sb.deleteCharAt(0);
            sb.deleteCharAt(0);
            int_tmp = sb.toString();

        }
        //System.out.println("decrypt_intermediatekey: " + decrypt_intermKey);
        //System.out.println("decrypt plainText: " + plainText);
    }

    String get_PlainText() {
        return plainText;
    }

    public void setCurrentKey(String currentKey) {
        this.currentKey = currentKey;
        fillInStringArray(substitution_Table, this.currentKey);
    }

    public String getCurrentKey() {
        return currentKey;
    }

}
