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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Peter
 */
public class Typer {

    private CharGenerator gen;

    public Typer(String modname) {

        try {
            FileInputStream finput = new FileInputStream(modname);
            ObjectInputStream ois = new ObjectInputStream(finput);
            
            String modtype = (String) ois.readObject();
            if (modtype.equalsIgnoreCase("unigram"))
                gen = (UnigramModel) ois.readObject();
            else
                gen = (NGramModel) ois.readObject();
            ois.close();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Class exception: bad model file " + modname);
        }
        catch (IOException e) {
            System.out.println("Unable to read " + modname);
        }
    }

    public void generate() {

        char[] buffer = new char[60];
        for (int i = 0; i < 60; i++) {
            buffer[i] = gen.nextChar();
        }

        System.out.println(buffer);
    }

    public boolean isValid() {

        return (gen != null);
    }
}
