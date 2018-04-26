package com.raju.emogi.Utils;

import android.support.annotation.Nullable;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Raju Yadav
 */

public class CollectionUtils {
    public static final String THUMB = "thumb";

    public static boolean isCollectionEmpty(@Nullable Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /*
    * This method change the sentence in List of word by removing space
    * other regex pattern
    */
    public static List<String> getWords(String text) {
        List<String> words = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(text);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
                words.add(text.substring(firstIndex, lastIndex));
            }
        }

        return words;
    }
}
