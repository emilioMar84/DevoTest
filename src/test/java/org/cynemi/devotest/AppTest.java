package org.cynemi.devotest;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	private final static int NUM_DECIMAL_MUL = 10000;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    
    public void testPalindromeOne()
    {
    	assertTrue(App.checkIsPalindrome("AbA"));
    }
    
    public void testPalindromeTwo()
    {
    	assertFalse(App.checkIsPalindrome("Abc"));
    }
    
    public void testPalindromeThree()
    {
    	assertTrue(App.checkIsPalindrome("b"));
    }
    
    public void testPalindromeFour()
    {
    	assertTrue(App.checkIsPalindrome(""));
    }
    
    public void testPalindromeFive()
    {
    	assertFalse(App.checkIsPalindrome(null));
    }
    
    public void testGetKPairOne() 
    {
    	int[] array = {1, 2, 3, 4};
    	
    	List<List<Integer>> result = App.getKPair(array, 5);
    	
    	assertTrue(result.size() == 2);
    	//Pair 1+4
    	assertTrue(result.get(0).size() == 2);
    	assertTrue(result.get(0).get(0) == 0);
    	assertTrue(result.get(0).get(1) == 3);
    	//Pair 2+3
    	assertTrue(result.get(1).size() == 2);
    	assertTrue(result.get(1).get(0) == 1);
    	assertTrue(result.get(1).get(1) == 2);
    }
    
    public void testGetKPairTwo() 
    {
    	int[] array = {1, 2, 3, 4};
    	
    	List<List<Integer>> result = App.getKPair(array, 9);
    	
    	assertTrue(result.size() == 0);
    }
    
    public void testGetKPairThree() 
    {
    	int[] array = {1, 1, 1, 1};
    	
    	List<List<Integer>> result = App.getKPair(array, 2);
    	
    	assertTrue(result.size() == 6);
    	
    	assertTrue(result.get(0).size() == 2);
    	assertTrue(result.get(0).get(0) == 0);
    	assertTrue(result.get(0).get(1) == 1);
    	
    	assertTrue(result.get(1).size() == 2);
    	assertTrue(result.get(1).get(0) == 0);
    	assertTrue(result.get(1).get(1) == 2);
    	
    	assertTrue(result.get(2).size() == 2);
    	assertTrue(result.get(2).get(0) == 0);
    	assertTrue(result.get(2).get(1) == 3);
    	
    	assertTrue(result.get(3).size() == 2);
    	assertTrue(result.get(3).get(0) == 1);
    	assertTrue(result.get(3).get(1) == 2);
    	
    	assertTrue(result.get(4).size() == 2);
    	assertTrue(result.get(4).get(0) == 1);
    	assertTrue(result.get(4).get(1) == 3);
    	
    	assertTrue(result.get(5).size() == 2);
    	assertTrue(result.get(5).get(0) == 2);
    	assertTrue(result.get(5).get(1) == 3);
    }
    
    public void testTfIdf() throws URISyntaxException
    {
    	URL res = getClass().getClassLoader().getResource("\\");
    	File file = Paths.get(res.toURI()).toFile();
    	String absolutePath = file.getAbsolutePath();
    	
    	DirectoryContent directoryContent = new DirectoryContent(absolutePath);
    	
    	TfIdfIncrementalCalculator tfIdfIncrementalCalculator = new TfIdfIncrementalCalculator(directoryContent.getContentList(), "target");
    	
    	List<Map.Entry<String,Double>> ranking = tfIdfIncrementalCalculator.getRanking();
    	
    	//[test2.txt=0.8109302162163288, test1.txt=0.4054651081081644, test3.txt=0.0]
    	assertTrue(ranking.size() == 3);
    	assertTrue((int)(ranking.get(0).getValue() * NUM_DECIMAL_MUL) == 8109);
    	assertTrue((int)(ranking.get(1).getValue() * NUM_DECIMAL_MUL) == 4054);
    	assertTrue((int)(ranking.get(2).getValue() * NUM_DECIMAL_MUL) == 0);
    	
    	System.out.println("Ranking for testTfIdf >> " + ranking);
    }
    
    
}
