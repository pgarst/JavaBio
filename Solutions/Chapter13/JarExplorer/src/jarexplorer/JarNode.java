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
package jarexplorer;

import java.util.jar.JarEntry;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author peterg
 */
public class JarNode 
    extends DefaultMutableTreeNode {
    
    private JarEntry    entry;
    private String      fullname;
    
    public JarNode (String filename, JarEntry entry) {
        
        super(entry.getName());
        this.entry  = entry;
        fullname    = entry.getName();
    }
    
    public JarNode (String filename, String fullname) {
        
        super(fullname);
        this.fullname    = fullname;
    }
    
    public String getFullname () {
        
        return fullname;
    }
    
    public JarEntry getEntry () {
        
        return entry;
    }
    
}
