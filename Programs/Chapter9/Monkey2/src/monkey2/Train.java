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
package monkey2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Peter
 */
public class Train {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Train configfile outfile");
            System.exit(0);
        }

        Collect collect = new Collect();
        String  outname = "model.mod";

        try (BufferedReader rd = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = rd.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(line);
                if (!tok.hasMoreTokens())
                    continue;

                String tag = tok.nextToken();
                if (tag.equals("alphabet"))
                    collect.setAlphabet(tok.nextToken());
                else if (tag.equals("input"))
                    collect.addFile(tok.nextToken());
                else if (tag.equals("ngram"))
                    collect.setNgram(Integer.parseInt(tok.nextToken()));
                else if (tag.equals("outname"))
                    outname = tok.nextToken();
            }
        }
        catch (IOException e) {
        }

        collect.finish();

        if (collect.getNgram() == 1) {
            UnigramModel model = new UnigramModel(collect);
            model.write(outname);
        } else {
            NGramModel model = new NGramModel(collect);
            model.write(outname);
        }
    }
}
