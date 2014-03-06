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

package gsontest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *
 * @author peterg
 */
public class GsonTest {
    
    static String  t1  = "{\"id\":\"100810100587936564089\",\"email\":\"pgarst@gmail.com\",\"verified_email\":true,\"name\":\"Peter Garst\",\"given_name\":\"Peter\",\"family_name\":\"Garst\",\"link\":\"https://plus.google.com/100810100587936564089\",\"gender\":\"male\",\"locale\":\"en\"}";

   // /home/peterg/tools/resin-4.0.36/webapps/labnotes/WEB-INF/lib/gson-1.6.jar
// /home/peterg/tools/resin-4.0.36/webapps/LoginNB/WEB-INF/lib/gson-2.2.4.jar

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Gson    gson    = new Gson();
        JsonObject  jobj    = gson.fromJson(t1, JsonObject.class);
        System.out.println("Result: " + jobj);
    }
    
}
