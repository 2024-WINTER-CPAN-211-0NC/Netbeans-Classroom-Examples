package org.humber.dsa.week8;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class PalindromeCheckSolution {
    public static boolean isPalindrome(String input) {
        input = input.toLowerCase();
        Stack<Character> stackString = new Stack<>();
        for (char s : input.toCharArray()) {
            stackString.push(s);
        }
        StringBuilder reversed = new StringBuilder();
        while (!stackString.isEmpty()) {
            reversed.append(stackString.pop());
        }
        return input.equals(reversed.toString());
    }
    public static void main(String[] args) {
        List<String> inputList = Arrays.asList("HELLO", "1001", "WORLD", "Mom", "dAD");
        for (String each : inputList) {
            System.out.println(each + ": is Palindrome? " + (isPalindrome(each) ? "Yes" : "No"));
        }
    }
}
