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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.template.AbstractSequence;
import org.biojava3.core.sequence.template.Compound;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peterg
 */
public class DPMatrix4Test {
    
    private static String sseq1 = ">gi\nFSLCFSK", sseq2 = ">gi\nFSLCFPK";      
    private static AbstractSequence<Compound> seq1, seq2;

    public DPMatrix4Test() {
    }

    @BeforeClass
    public static void setUpClass() {

        try {
            InputStream stream = new ByteArrayInputStream(sseq1.getBytes());
            LinkedHashMap lseq
                    = FastaReaderHelper.readFastaProteinSequence(stream);

            Object[] parray = lseq.values().toArray();
            seq1 = (AbstractSequence<Compound>) parray[0];

            stream = new ByteArrayInputStream(sseq2.getBytes());
            lseq = FastaReaderHelper.readFastaProteinSequence(stream);
            parray = lseq.values().toArray();
            seq2 = (AbstractSequence<Compound>) parray[0];
        }
        catch (Exception e) {
            System.out.println("Setup exception " + e);
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAlignment method, of class DPMatrix4.
     */
    @Test
    public void testGetAlignment() {
        System.out.println("getAlignment");
        DPMatrix4 instance = new DPMatrix4(seq1, seq2);
        Alignment result = instance.getAlignment();
 
        // Expect a diagonal alignment. We can tell from the length
        // of the alignment, if we allow for the gaps at each end.
        assertEquals(result.getLength(), 9);
    }

}
