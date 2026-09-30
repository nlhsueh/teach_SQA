package fcu.junitdemo;

import java.util.*;
import static java.lang.System.out;

public class Infix {
    private static int priority(char c) {
        return c == '+' || c == '-' ? 1 : c == '*' || c == '/' ? 2 : 0;
    }
    
    private static String toPostfix(String infix, boolean isPost) {
        String expr = isPost ? 
            infix : new StringBuffer(infix).reverse().toString();
        LinkedList<Character> stack = new LinkedList<Character>();
        StringBuilder output = new StringBuilder();
        char toStack = isPost ? '(' : ')';
        char toOutput = isPost ? ')' : '(';
        for(char c : expr.toCharArray()) {
            if(c == toStack) { stack.add(c); }
            else if("+-*/".indexOf(c) != -1) {
                while(!stack.isEmpty() && 
                       priority(stack.getLast()) >= priority(c)) { 
                    output.append(stack.removeLast());
                } 
                stack.add(c);
            }
            else if(c == toOutput) {
                while(stack.getLast() != toStack) { 
                    output.append(stack.removeLast());
                } 
                stack.removeLast();
            }
            else { output.append(c); }
        }
        
        while(!stack.isEmpty()) { output.append(stack.removeLast()); }
        
        return isPost ? output.toString() : output.reverse().toString();
    }
    
    public static String toPostfix(String infix) {
        return toPostfix(infix, true);
    }
    
    public static String toPrefix(String infix) {
        return toPostfix(infix, false);
    }
    
    public static void main(String[] args) {
        String infix = "(a*b)";
        out.println(toPostfix(infix));
        out.println(toPrefix(infix));
    }
}  