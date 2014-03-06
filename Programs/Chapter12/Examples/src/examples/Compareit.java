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
package examples;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author peterg
 */
public class Compareit {

    void tryit(List<DNA> list) {

        Collections.sort(list, new Comparator<DNA>() {
            @Override
            public int compare(DNA o1, DNA o2) {
                if (o1.size() < o2.size())
                    return -1;
                if (o1.size() == o2.size())
                    return 0;
                return 1;
            }
        });

        Collections.sort(list, (DNA o1, DNA o2) 
            -> {
            if (o1.size() < o2.size())
                return -1;
            if (o1.size() == o2.size())
                return 0;
            return 1;
        });
        
        Collections.sort(list, (o1, o2) 
            -> {
            if (o1.size() < o2.size())
                return -1;
            if (o1.size() == o2.size())
                return 0;
            return 1;
        });
        
        (x, y) -> ((x<y)? -1 : 1);
        
        Comparator  cmp = (x, y) -> ((x<y)? -1 : 1);

    }
}
