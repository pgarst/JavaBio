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

/**
 *
 * @author Peter
 */
public class UnigramModel
        implements Serializable, CharGenerator {

    private double[] probs;
    private double[] logs;
    private char[] carray;
    
    private static final long   serialVersionUID    = 42;

    private static transient GenRandom rand;

    public UnigramModel(Collect collect) {

        carray = collect.getAlphabet();
        probs = new double[carray.length];
        logs = new double[carray.length];

        double tot = 0;
        for (int i = 0; i < probs.length; i++) {
            int n = collect.getCount("" + carray[i]);
            probs[i] = n;
            tot += n;
        }
        for (int i = 0; i < probs.length; i++) {
            probs[i] /= tot;
            logs[i] = Math.log(probs[i]);
        }

        if (rand == null)
            setRand(new GenRandom());
    }

    public static UnigramModel read(String modname) {

        try (FileInputStream finput = new FileInputStream(modname)) {
            ;
            ObjectInputStream ois = new ObjectInputStream(finput);
            UnigramModel gen = (UnigramModel) ois.readObject();
            ois.close();

            if (rand == null)
                setRand(new GenRandom());

            return gen;
        }
        catch (ClassNotFoundException ex) {
        }
        catch (IOException ex) {
        }

        return null;
    }

    public static void setRand(GenRandom r) {

        rand = r;
    }

    public void init() {
    }

    public char nextChar() {

        return carray[rand.choose(probs)];
    }

    public double logProb(int n) {

        return logs[n];
    }

    @Override
    public char[] getAlphabet() {

        return carray;
    }

    public void write(String fname) {

        try {
            ObjectOutputStream os
                    = new ObjectOutputStream(new FileOutputStream(fname));
            os.writeObject(this);
            os.close();
        }
        catch (IOException ex) {
        }
    }

    @Override
    public boolean hasNextChar() {
        
        return true;
    }

}
