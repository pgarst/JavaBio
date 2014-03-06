/*
 * Copyright 2013 Peter Garst.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package seqtools;

import java.util.HashMap;

/**
 *
 * @author Peter
 */
public class Collect extends HashMap<String, Integer> {

    private char[] alphabet;
    // private HashMap<String, Integer> countmap = new HashMap<>();
    private int ngram = 1;

    public void setAlphabet(String tok) {

        tok = tok.toUpperCase();
        alphabet = tok.toCharArray();

        // Map _ to space
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == '_')
                alphabet[i] = ' ';
        }
     
    }

    public void useSource(CharGenerator dsource) {

        // Set up the initial key
        String key = "";
        for (int i = 0; i < ngram; i++) {
            if (!dsource.hasNextChar())
                return;
            key = key + alphabet[dsource.nextChar()];
        }

        while (true) {
            addKey(key);

            if (!dsource.hasNextChar())
                break;
            key = key + alphabet[dsource.nextChar()];
            key = key.substring(1);
        }
    }

    public void addKey(String key) {

        Integer current = get(key);
        if (current == null)
            put(key, 1);
        else
            put(key, current + 1);
    }

    public int getCount(String key) {

        Integer val = get(key);
        if (val == null)
            return 0;
        return val;
    }

    public char[] getAlphabet() {

        return alphabet;
    }

    public void setNgram(int n) {

        ngram = n;
    }

    public int getNgram() {

        return ngram;
    }

    public void finish() {

        // Smooth the counts - start with add 1
        addOne("");
    }

    private void addOne(String key) {

        if (key.length() == ngram) {
            addKey(key);
            return;
        }

        for (char c : alphabet) {
            addOne(key + c);
        }
    }
}
