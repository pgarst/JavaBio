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

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.io.FastaReaderHelper;

/**
 *
 * @author pgarst
 */
public class ProteinSource
        implements CharGenerator {

    private static final String alphabet = "acdefghiklmnpqrstvwy";
    private LinkedHashMap<String, ProteinSequence> pmap;
    private Iterator<String> keys;
    private ProteinSequence protein;
    private int position;
    private boolean nvalid = false;
    private char nextch;

    public ProteinSource(String fname) {

        try {
            File f = new File(fname);
            pmap = FastaReaderHelper.readFastaProteinSequence(f);
            keys = pmap.keySet().iterator();
        }
        catch (Exception ex) {
        }
    }

    public String getAlphabet() {

        return alphabet;
    }

    @Override
    public boolean hasNextChar() {

        if (nvalid)
            return true;

        while (true) {
            if (protein == null) {
                if (!getProtein())
                    break;
            }

            // BioJava indexes from 1
            if (position > protein.getLength()) {
                protein = null;
                continue;
            }

            AminoAcidCompound aa = protein.getCompoundAt(position++);

            String base = aa.getUpperedBase().toLowerCase();
            int chind = alphabet.indexOf(base);
            if (chind < 0) {
                System.out.println("Bad aa " + base);
                continue;
            }

            nextch  = base.charAt(0);
            nvalid = true;
            return true;
        }

        return false;
    }

    private boolean getProtein() {

        if (pmap == null)
            return false;
        if (!keys.hasNext())
            return false;

        String pname = keys.next();

        protein = pmap.get(pname);
        position = 1;
        return true;
    }

    @Override
    public char nextChar() {

        if (!nvalid)
            hasNextChar();
        if (!nvalid)
            return 0;

        nvalid = false;
        return nextch;
    }

    @Override
    public void init() {

        keys = pmap.keySet().iterator();
        protein = null;
        nvalid = false;
    }

}
