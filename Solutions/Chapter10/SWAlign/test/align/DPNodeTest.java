/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package align;

import org.biojava3.core.sequence.template.Compound;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pgarst
 */
public class DPNodeTest
    {
    
    public DPNodeTest()
        {
        System.out.println("costructor");
        }

    @BeforeClass
    public static void setUpClass()
        throws Exception
        {
        System.out.println("setup class");
        }

    @AfterClass
    public static void tearDownClass()
        throws Exception
        {
        System.out.println("teardown class");
        }
    
    @Before
    public void setUp()
        {
        System.out.println("setup");
        }
    
    @After
    public void tearDown()
        {
        System.out.println("teardown");
        }

    /**
     * Test of getRow method, of class DPNode.
     */
    @Test
    public void testGetRow()
        {
        System.out.println("getRow");
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.getRow();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of getCol method, of class DPNode.
     */
    @Test
    public void testGetCol()
        {
        System.out.println("getCol");
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.getCol();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of hasPrev method, of class DPNode.
     */
    @Test
    public void testHasPrev()
        {
        System.out.println("hasPrev");
        DPNode instance = new DPNode();
        boolean expResult = false;
        boolean result = instance.hasPrev();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of getPrev method, of class DPNode.
     */
    @Test
    public void testGetPrev()
        {
        System.out.println("getPrev");
        DPNode instance = new DPNode();
        DPNode expResult = null;
        DPNode result = instance.getPrev();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of symbol method, of class DPNode.
     */
    @Test
    public void testSymbol()
        {
        System.out.println("symbol");
        int n = 0;
        DPNode instance = new DPNode();
        String expResult = "";
        String result = instance.symbol(n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of totalCost method, of class DPNode.
     */
    @Test
    public void testTotalCost()
        {
        System.out.println("totalCost");
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.totalCost();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of getStatus method, of class DPNode.
     */
    @Test
    public void testGetStatus()
        {
        System.out.println("getStatus");
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.getStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of setStatus method, of class DPNode.
     */
    @Test
    public void testSetStatus()
        {
        System.out.println("setStatus");
        int status = 0;
        DPNode instance = new DPNode();
        instance.setStatus(status);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of setCompounds method, of class DPNode.
     */
    @Test
    public void testSetCompounds()
        {
        System.out.println("setCompounds");
        Compound c1 = null;
        Compound c2 = null;
        DPNode instance = new DPNode();
        instance.setCompounds(c1, c2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of findPath method, of class DPNode.
     */
    @Test
    public void testFindPath()
        {
        System.out.println("findPath");
        DPNode left = null;
        DPNode diag = null;
        DPNode down = null;
        DPNode instance = new DPNode();
        instance.findPath(left, diag, down);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of bestLeft method, of class DPNode.
     */
    @Test
    public void testBestLeft()
        {
        System.out.println("bestLeft");
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.bestLeft();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of bestDown method, of class DPNode.
     */
    @Test
    public void testBestDown()
        {
        System.out.println("bestDown");
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.bestDown();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of setUsed method, of class DPNode.
     */
    @Test
    public void testSetUsed()
        {
        System.out.println("setUsed");
        DPNode instance = new DPNode();
        instance.setUsed();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of isUsed method, of class DPNode.
     */
    @Test
    public void testIsUsed()
        {
        System.out.println("isUsed");
        DPNode instance = new DPNode();
        boolean expResult = false;
        boolean result = instance.isUsed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of compareTo method, of class DPNode.
     */
    @Test
    public void testCompareTo()
        {
        System.out.println("compareTo");
        DPNode t = null;
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.compareTo(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of getLsteps method, of class DPNode.
     */
    @Test
    public void testGetLsteps()
        {
        System.out.println("getLsteps");
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.getLsteps();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }

    /**
     * Test of getDsteps method, of class DPNode.
     */
    @Test
    public void testGetDsteps()
        {
        System.out.println("getDsteps");
        DPNode instance = new DPNode();
        int expResult = 0;
        int result = instance.getDsteps();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }
    }
