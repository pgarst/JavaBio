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
package monkey3;

import java.util.ArrayList;
import seqtools.CharGenerator;
import seqtools.LogProb;
import seqtools.NGramModel;

/**
 *
 * @author pgarst
 */
class Comparison {

    Comparison(ArrayList<NGramModel> models, ArrayList<CharGenerator> data) {

        for (NGramModel mod : models) {
            for (CharGenerator src : data) {
                LogProb lprob = new LogProb(mod);
                src.init();
                lprob.add(src);
                double p = lprob.prob();
                String pr = String.format("  %6.3f", p);

                System.out.print(pr);
            }

            System.out.println();
        }
    }

}
