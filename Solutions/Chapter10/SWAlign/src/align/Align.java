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
import java.io.PrintStream;

/**
 * This project contains solutions to all the problems in section 10.5.4.4.
 *
 * @author peter
 */
public class Align {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: align file1 file2 [outfile]");
            System.exit(0);
        }

        File f1 = new File(args[0]);
        File f2 = new File(args[1]);

        try {
            PrintStream out = null;
            if (args.length > 2)
                out = new PrintStream(args[2]);

            AlignSeq as = new AlignSeq(f1, f2);

            while (true) {
                Alignment al = as.getAlign();
                if (al == null)
                    break;

                if (out != null)
                    al.printAlignment(out);

                al.printAlignment(System.out);
            }

        }
        catch (Exception ex) {
        }
    }
}
