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
package find;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.filter.EDM;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.FloodFiller;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

/**
 *
 * @author peterg
 */
public class Find_Cells
        implements PlugInFilter {

    @Override
    public int setup(String string, ImagePlus ip) {

        // Specifies what kinds of images we handle.
        return DOES_ALL + NO_CHANGES;
    }

    @Override
    public void run(ImageProcessor ip) {

        // Convert to 16 bit grayscale
        ShortProcessor dup = (ShortProcessor) ip.duplicate().convertToShort(true);

        dup.autoThreshold();        
        ByteProcessor   bp  = fillit(dup);        
        watershed(bp);        
        analyze(bp);
    }
    
    private void watershed (ByteProcessor bp) {
        
        bp.invert();
        EDM edm = new EDM();
        edm.toWatershed(bp);
        bp.invert();
    }
    
    private ByteProcessor fillit (ShortProcessor ip) {
        
        ByteProcessor   bp  = (ByteProcessor ) ip.convertToByte(true);
        fillHoles(bp, 0, 255); 
        
        return bp;
    }
    
    private void analyze (ByteProcessor bp) {
            
        // Need this for analyzer
        ImagePlus   image   = new ImagePlus("Cells", bp);
        ResultsTable        rt  = new ResultsTable();
        ParticleAnalyzer    pa  = new ParticleAnalyzer(0, 0, rt, 4000, 20000);
        pa.analyze(image);
        
        float[] areas   = rt.getColumn(ResultsTable.AREA);
        
        ResultsTable    showtab = new ResultsTable();
        
        int len = 16;
        double[]    countcol    = new double[len];
        double[]    sizecol     = new double[len];
        for (int i = 0; i < len; i++)
            {
            countcol[i] = 0;
            sizecol[i]  = 1000 * (i+5);
            }
        for (int i = 0; i < areas.length; i++)
            {
            for (int j = 0; j < len; j++)
                {
                if (areas[i] < sizecol[j])
                    {
                    countcol[j] += 1;
                    break;
                    }
                }
            }
        for (int i = 0; i < len; i++)
            {
            showtab.incrementCounter();
            showtab.addValue("Size", sizecol[i]);
            showtab.addValue("Count", countcol[i]);
            }
        
        showtab.show("Size histogram for " + areas.length + " particles");
        
        // Uncomment this to see the results
        // image.show();
    }

    // Copied from source for UI level fill holes in binary
    private void fillHoles(ByteProcessor ip, int foreground, int background) {

        int width = ip.getWidth();
        int height = ip.getHeight();
        FloodFiller ff = new FloodFiller(ip);
        ip.setColor(127);
        for (int y = 0; y < height; y++) {
            if (ip.getPixel(0, y) == background)
                ff.fill(0, y);
            if (ip.getPixel(width - 1, y) == background)
                ff.fill(width - 1, y);
        }
        for (int x = 0; x < width; x++) {
            if (ip.getPixel(x, 0) == background)
                ff.fill(x, 0);
            if (ip.getPixel(x, height - 1) == background)
                ff.fill(x, height - 1);
        }
      
        byte[] pixels = (byte[]) ip.getPixels();
        int n = width * height;
        for (int i = 0; i < n; i++) {
            if (pixels[i] == 127)
                pixels[i] = (byte) background;
            else
                pixels[i] = (byte) foreground;
        }
    }

}
