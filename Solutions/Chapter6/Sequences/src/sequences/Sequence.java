/*
 * Copyright 2014 Peter Garst.
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
package sequences;

import java.util.ArrayList;

/**
 *
 * @author peterg
 */
public class Sequence<T extends Compound>
    extends ArrayList<T>
    implements Comparable<Sequence<T>> {

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Compound c : this)
            sb.append(c.getCode());

        return sb.toString();
    }

    // Just do it by length
    @Override
    public int compareTo(Sequence<T> other) {
        
        return size() - other.size();
    }
}
