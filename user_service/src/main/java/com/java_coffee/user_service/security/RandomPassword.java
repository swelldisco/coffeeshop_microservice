package com.java_coffee.user_service.security;

import java.util.Random;

public class RandomPassword {
    public String generatePassword(int length) {
        char[] upperCase = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
        char[] lowerCase = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] special = {'!','#','$','%','&','*','~','`','?'};
        StringBuffer sb = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            int charSet = randomNumber(4);
            switch(charSet) {
                case 0: 
                    sb.append(upperCase[randomNumber(upperCase.length)]);
                    break;
                case 1:
                    sb.append(lowerCase[randomNumber(lowerCase.length)]);
                    break;
                case 2:
                    sb.append(randomNumber(10));
                    break;
                case 3:
                    sb.append(special[randomNumber(special.length)]);
                    break;
                default:
            }
        }
        return sb.toString();
    }
    
    private int randomNumber(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }
}
