package com.stadnikov.brackets.util;

import java.util.Deque;
import java.util.LinkedList;

public class BracketsUtil {

    public final static String START_BRACKETS = "(<[{";
    public final static String END_BRACKETS = ")>]}";

    public static boolean check(String text) {
        Deque<Character> deque = new LinkedList<>();
        int charsBetweenBrackets = 0;
        char currentCharacter;

        for (int i = 0; i < text.length(); i++) {
            currentCharacter = text.charAt(i);
            if (Character.isWhitespace(currentCharacter)) {
                continue;
            }
            charsBetweenBrackets++;

            if (START_BRACKETS.contains(Character.toString(currentCharacter))) {
                deque.push(currentCharacter);
                charsBetweenBrackets = 0;
            }

            if (END_BRACKETS.contains(Character.toString(currentCharacter))) {
                if (deque.isEmpty()) {
                    // остались лишние закрывающие скобки
                    return false;
                }
                if (charsBetweenBrackets < 2) {
                    // между начальной и конечной скобкой нет символов (пустые скобки)
                    return false;
                }
                if (END_BRACKETS.indexOf(currentCharacter) != START_BRACKETS.indexOf(deque.pop())) {
                    // закрывают не той скобкой
                    return false;
                }
            }
        }

        if (deque.isEmpty()) {
            // все скобки сошлись
            return true;
        } else {
            // остались незакрытые скобки
            return false;
        }
    }
}