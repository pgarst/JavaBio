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
package driftswing;

import java.util.Hashtable;
import javax.swing.JLabel;

/**
 * Like ParamControl, but the slider has a log scale.
 * This affects the ticks, the labels, and in setSlider and setText
 * the translation between text and slider.
 * This is an integer parameter.
 *
 * @author Peter
 */
public class LogControl
    extends ParamControl
    {

    public LogControl ()
        {

        super();
        paramSlider.setPaintTicks(false);
        }
/*
    LogControl(String name, int minval, int maxval)
        {

        this();

        paramLabel.setText(name);
        this.minval = minval;
        this.maxval = maxval;

        paramSlider.setPaintTicks(false);

        setLabels();
        }
    
     int getInt()
        {

        return (int) Math.round(getValue());
        }*/

    @Override
    public double getMaxval()
        {
        return maxval;
        }

    @Override
    public double getMinval()
        {
        return minval;
        }

    @Override
    public void setMaxval(double maxval)
        {
        this.maxval = (int ) Math.round(maxval);
        setLabels();
        }

    @Override
    public void setMinval(double minval)
        {
        this.minval = (int ) Math.round(minval);
        setLabels();
        }

    // Put a label at the min and max.
    // Put one near the middle, rounded to a good value.
    private void setLabels()
        {

        Hashtable labelTable = new Hashtable();

        labelTable.put(new Integer(0), new JLabel("" + minval));

        // Do it at the 1/4 positions
        double  delta   = (maxval - minval) / 4.0;
        int mid = (int ) Math.round(minval + delta);
        // labelTable.put(new Integer(logScale(mid)), new JLabel("" + mid));
        mid = (int ) Math.round(minval + 2*delta);
        // labelTable.put(new Integer(logScale(mid)), new JLabel("" + mid));
        mid = (int ) Math.round(minval + 3*delta);
        // labelTable.put(new Integer(logScale(mid)), new JLabel("" + mid));

        labelTable.put(new Integer(100), new JLabel("" + maxval));
        paramSlider.setLabelTable(labelTable);
        
        setText();
        }

    // Map log10(val) to the 0 - 100 slider scale
    private int logScale(int val)
        {

        double minl = Math.log10(minval);
        double maxl = Math.log10(maxval);
        double lval = Math.log10(val);

        double pos = 100 * (lval - minl) / (maxl - minl);

        return (int) Math.round(pos);
        }

    // Given a slider value, 0 - 100 log scale, map to a param value.
    // Inverse of logScale
    private int expScale(int val)
        {

        double minl = Math.log10(minval);
        double maxl = Math.log10(maxval);

        double  lval    = (maxl - minl) * val / 100.0 + minl;
 
        return (int) Math.round(Math.pow(10.0, lval));
        }

    @Override
    protected void setSlider()
        {

        String val = paramText.getText();
        try
            {
            int ival = Integer.parseInt(val);
            int targ = logScale(ival);
            paramSlider.setValue(targ);
            }
        catch (NumberFormatException e)
            {
            setText();
            }
        }
     
    @Override
    public double getValue () {
        return expScale(paramSlider.getValue());
    }

    @Override
    protected void setText()
        {

        // Map the 0 - 100 slider range to our min - max range
        int val = expScale(paramSlider.getValue());

        String valstr = "" + val;
        paramText.setText(valstr);
        }
    
    //private int minval;
    //private int maxval;
    }
