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
import java.util.Set;

/**
 *
 * @author Peter
 */
public class Collect {

    private char[] alphabet;
    private HashMap<String, Integer> countmap = new HashMap<>();
    private int ngram = 1;
    private int total;

    public static char[] stringToAlphabet(String tok) {

        tok = tok.toLowerCase();
        char[] alph = tok.toCharArray();

        // Map _ to space
        for (int i = 0; i < alph.length; i++) {
            if (alph[i] == '_')
                alph[i] = ' ';
        }

        return alph;
    }

    public void setAlphabet(String tok) {

        alphabet = stringToAlphabet(tok);
    }
    
    public void setAlphabet(char[] tok) {

        alphabet = tok;
    }

    public void useSource(CharGenerator dsource) {

        // Set up the initial key
        String key = "";
        for (int i = 0; i < ngram; i++) {
            if (!dsource.hasNextChar())
                return;
            key = key + dsource.nextChar();
        }

        while (true) {
            addKey(key);

            if (!dsource.hasNextChar())
                break;
            key = key + dsource.nextChar();
            key = key.substring(1);
        }
    }

    private void addKey(String key) {

        Integer current = countmap.get(key);
        if (current == null)
            countmap.put(key, 1);
        else
            countmap.put(key, current + 1);
    }

    public int getCount(String key) {

        Integer val = countmap.get(key);
        if (val == null)
            return 0;
        return val;
    }
    
    public double getProb (String key) {
        
        if (total == 0)
            return 0;
        
        Integer val = countmap.get(key);
        if (val == null)
            return 0;
        
        return ((double ) val) / total;
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
    
    public int getTotal () {
        
        return total;
    }

    public void finish() {

        // Smooth the counts - start with add 1
        total   = 0;
        addOne("");
    }

    private void addOne(String key) {

        if (key.length() == ngram) {
            addKey(key);
            total   += getCount(key);
            return;
        }

        for (char c : alphabet) {
            addOne(key + c);
        }
    }

    public double crossEntropy(Collect col2) {
        
        // We are assuming we have already smoothed and every bin is nonempty.
        
        Set<String> keys    = countmap.keySet();
        
        double  ce  = 0;
        for (String key : keys) {
            double  p   = getProb(key);
            double  q   = col2.getProb(key);
            ce  -= p * Math.log(q);
        }
        
        return ce;
    }
}
