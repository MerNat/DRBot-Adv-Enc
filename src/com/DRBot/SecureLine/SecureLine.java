package com.DRBot.SecureLine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Meron
 */


public final class SecureLine {
    private final char[] OrgArr = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
    '0','1','2','3','4','5','6','7','8','9','>','-',' ','*','_','<',',','.','(',')','[',']','&','+','=','|','^','%','$','#','@','!','~','`','/','\\',':',';','\'','"'};
    private ArrayList<Integer> secret;
    private HashMap<String,String> myHashEnc,myHashDec;
    
    public SecureLine(){
        genSecret();
    }
    
    
    public void genSecret(){
        ArrayList<Integer> num = new ArrayList<>();
        myHashEnc = new HashMap<>();
        myHashDec = new HashMap<>();
        for(int i = 0;i<OrgArr.length;i++){
            float random = Math.round(Math.random() * 91);
            int index = Math.round(random);
            if(num.contains(index)){
                i--;
            }else{
                num.add(index);
            }
        }
        secret = num;
        for(int i = 0;i<secret.size();i++){
            String one = String.valueOf(OrgArr[secret.get(i)]);
            String two = String.valueOf(OrgArr[i]);
            myHashEnc.put(two,one);
            myHashDec.put(one,two);
        }
    }
    public String encrypt(String text){
        String allText[] = text.split("");
        String encText = "";
        for (String allText1 : allText) {
            encText += myHashEnc.get(allText1);
        }
        return encText;
    }
    public String decrypt(String text){
        String allText[] = text.split("");
        String decText = "";
        for (String allText1 : allText) {
            decText += myHashDec.get(allText1);
        }
        return decText;
    }
    
    public String getSecret(){
        String toSend2 = "";
        for(int j=secret.size()-1;j>=0;j--){
            toSend2 += secret.get(j)+".";
        }
        return "##"+toSend2.substring(0,toSend2.length()-1);
    }
}
