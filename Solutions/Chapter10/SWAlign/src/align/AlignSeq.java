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
package align;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.biojava3.core.exceptions.CompoundNotFoundError;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.template.AbstractSequence;
import org.biojava3.core.sequence.template.Compound;

/**
 *
 * @author peter
 */
class AlignSeq {

    private AbstractSequence<Compound> seq1 = null, seq2 = null;
    private DPMatrix dp;

    public AlignSeq(File f1, File f2) {

        // LinkedHashMap<String, AbstractSequence<Compound>> map1, map2;
        Collection<AbstractSequence<Compound>> col1, col2;
        col1 = readAnySequence(f1);
        col2 = readAnySequence(f2);
        if ((col1 == null) || (col2 == null))
            return;

        // We expect just one protein sequence in each map
        seq1 = firstSeq(col1);
        seq2 = firstSeq(col2);

        dp = new DPMatrix(seq1, seq2);
    }

    private Collection<AbstractSequence<Compound>> readAnySequence(File f) {

        // Try DNA first
        try {
            // LinkedHashMap<String, DNASequence> tmap;
            LinkedHashMap tmap = FastaReaderHelper.readFastaDNASequence(f);
            if (tmap != null) {
                return tmap.values();
            }
        }
        catch (CompoundNotFoundError nf) {
        }
        catch (Exception e) {
            // Fall through to read protein
        }

        try {
            LinkedHashMap tmap = FastaReaderHelper.readFastaProteinSequence(f);
            if (tmap != null) {
                return tmap.values();
            }
        }
        catch (Exception e) {
            System.out.println("read exception: " + e);
        }

        return null;
    }

    private AbstractSequence<Compound> firstSeq(Collection<AbstractSequence<Compound>> col) {

        // Collection<AbstractSequence<Compound>> col = map.values();
        Iterator<AbstractSequence<Compound>> it = col.iterator();
        if (!it.hasNext()) {
            return null;
        }

        return it.next();
    }

    Alignment getAlign() {

        return dp.getAlignment();
    }

}
