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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author pgarst
 */
public class NGramModel
        implements Serializable, CharGenerator {

    private char[] alphabet;
    private String state, gstate;
    private int ngram;
    private HashMap<String, double[]> statemap;
    private static transient GenRandom rand;

    public NGramModel(Collect collect) {

        alphabet = collect.getAlphabet();
        ngram = collect.getNgram();
        statemap = new HashMap<>();

        createProb(collect, "");

        if (rand != null)
            rand = new GenRandom();
    }

    private void createProb(Collect collect, String statestr) {

        if (statestr.length() < (ngram - 1)) {
            for (char c : alphabet) {
                createProb(collect, statestr + c);
            }
            return;
        }

        // We have a full length state string.
        // First put the full count in each probability slot.
        double total = 0;
        double[] prob = new double[alphabet.length];
        for (int i = 0; i < alphabet.length; i++) {
            String gram = statestr + alphabet[i];
            prob[i] = collect.getCount(gram);
            total += prob[i];
        }

        // Now normalize by the total count.
        for (int i = 0; i < alphabet.length; i++) {
            prob[i] /= total;
        }

        statemap.put(statestr, prob);
    }

    public void init() {

        char[] initstr = new char[ngram - 1];
        for (int i = 0; i < ngram - 1; i++) {
            initstr[i] = alphabet[0];
        }
        state = new String(initstr);
    }

    public char nextChar() {

        double[] prob = statemap.get(state);
        char nc = alphabet[rand.choose(prob)];
        String nstate = state + nc;
        state = nstate.substring(1);

        return nc;
    }

    public static NGramModel read(String modname) {

        FileInputStream finput = null;
        try {
            finput = new FileInputStream(modname);
            ObjectInputStream ois = new ObjectInputStream(finput);
            String name = (String) ois.readObject();
            if (!name.equals("NGramModel"))
                return null;
            NGramModel gen = (NGramModel) ois.readObject();

            if (rand == null)
                rand = new GenRandom();

            ois.close();
            return gen;
        }
        catch (ClassNotFoundException ex) {
        }
        catch (IOException ex) {
        } finally {
            try {
                finput.close();
            }
            catch (IOException ex) {
            }
        }

        return null;
    }

    public void write(String fname) {

        try {
            ObjectOutputStream os
                    = new ObjectOutputStream(new FileOutputStream(fname));
            os.writeObject("NGramModel");
            os.writeObject(this);
            os.close();
        }
        catch (IOException ex) {
        }
    }

    boolean reinit() {

        gstate = "";
        return (ngram == 1);
    }

    // Set up the initial state for generating probs.
    // Return true if we have a big enough state.
    boolean initProb(char c) {

        gstate = gstate + c;
        return (gstate.length() == (ngram - 1));
    }

    double logProb(int cn) {

        // Assume we have data for every state
        double[] probs = statemap.get(gstate);

        double val = Math.log(probs[cn]);

        // Set up next state
        gstate = gstate + alphabet[cn];
        gstate = gstate.substring(1);

        return val;
    }

    public char[] getAlphabet() {

        return alphabet;
    }

    @Override
    public boolean hasNextChar() {
        
        // This generates according to the model - we can go as long as we want
        return true;
    }
}
