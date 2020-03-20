package org.cynemi.devotest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Test Code for DEVO
 *
 */
public class App 
{
	
	private static class OptionParam
	{
		 public String	directory = "";  
		 public int countN = 0;     
	     public int periodP = 0;
	     public String tt = "";
	}
	
	final static String OPT_DIRECTORY = "d";
    final static String OPT_COUNT_N = "n";
    final static String OPT_PERIOD_P = "p";
    final static String OPT_TT = "t";
	
    public static void main( String[] args )
    {
        
        OptionParam optionParam = getOptParam(args);
        
        DirectoryContent directoryContent = new DirectoryContent(optionParam.directory);
        
        TfIdfIncrementalCalculator tfIdfIncrementalCalculator = 
        		new TfIdfIncrementalCalculator(directoryContent.getContentList(), optionParam.tt);
        
        while(true)
        {
        	directoryContent.refresh();
        	tfIdfIncrementalCalculator.addDocs(directoryContent.getNewContentList());
        	printRanking(tfIdfIncrementalCalculator.getRanking(), optionParam.countN);
        	try {
				Thread.sleep(optionParam.periodP * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
        }
    }
    
    private static void printRanking(List<Entry<String, Double>> ranking, int countN)
    {
    	System.out.println(" ------ RANKING ------");
    	System.out.println(" ---------------------");
    	int i = 1;
    	for (Entry<String, Double> entry : ranking)
    	{
    		System.out.println("#" + i + ".  Doc: " + entry.getKey() + " --> " + entry.getValue() );
    		
    		if (i == countN) 
    		{
    			break;
    		}
    		
    		i++;
    	}
    	
    	System.out.println("");
    }
    
    private static OptionParam getOptParam(String[] args)
    {
    	OptionParam result = new OptionParam();
    	
    	Options options = new Options();  
        options.addOption(OPT_DIRECTORY, true, "The directory D where the documents will be written");  
        options.addOption(OPT_COUNT_N, true, "The count N of top results to show");  
        options.addOption(OPT_PERIOD_P, true, "The period P to report the top N in seconds"); 
        options.addOption(OPT_TT, true, "The terms TT to be analyzed");
          
          
        try {  
                      
        	DefaultParser parser  = new DefaultParser();  
            CommandLine cmdLine = parser.parse(options, args);  
                 
            if (cmdLine.hasOption(OPT_DIRECTORY))
            {    
            	result.directory = cmdLine.getOptionValue(OPT_DIRECTORY);
            }
            else
            {
            	new HelpFormatter().printHelp(App.class.getCanonicalName(), options ); 
            	System.exit(-1);
            }
              
            if (cmdLine.hasOption(OPT_COUNT_N))
            {    
            	result.countN = Integer.parseInt(cmdLine.getOptionValue(OPT_COUNT_N));
            }  
            else
            {
            	new HelpFormatter().printHelp(App.class.getCanonicalName(), options ); 
            	System.exit(-1);
            }
            
            if (cmdLine.hasOption(OPT_PERIOD_P))
            {    
            	result.periodP = Integer.parseInt(cmdLine.getOptionValue(OPT_PERIOD_P));
            }  
            else
            {
            	new HelpFormatter().printHelp(App.class.getCanonicalName(), options ); 
            	System.exit(-1);
            }
            
            if (cmdLine.hasOption(OPT_TT))
            {    
            	result.tt = cmdLine.getOptionValue(OPT_TT);
            }  
            else
            {
            	new HelpFormatter().printHelp(App.class.getCanonicalName(), options ); 
            	System.exit(-1);
            }
              
            System.out.println("OK");  
              
        } catch (org.apache.commons.cli.ParseException ex)
        {  
            new HelpFormatter().printHelp(App.class.getCanonicalName(), options );   
        } catch (java.lang.NumberFormatException ex)
        {  
            new HelpFormatter().printHelp(App.class.getCanonicalName(), options );   
        }
        
        return result;
    }
    
    /***
     * returns when str is a palindrome
     * time complexity O(n), space complexity O(n), let n be str.length()
     * @param str
     * @return
     */
    public static boolean checkIsPalindrome(String str) 
    {
    	
    	if (str == null)
    	{
    		return false;
    	}
    	
    	boolean result = true;
    	
    	int backIndex = 0;
    	int frontIndex = str.length() - 1;
    	
    	while (result && backIndex < frontIndex) 
    	{
    		if (!(str.charAt(backIndex) == str.charAt(frontIndex))) 
    		{
    			result = false;
    		}
    		
    		backIndex++;
    		frontIndex--;
    	}
    	
    	return result;
    	
    }
    
    /**
     * returns the k-pairs that array contains 
     * time complexity O(n^2), space complexity(n), let n be array.length
     * @param array
     * @param k
     * @return
     */
    public static List<List<Integer>> getKPair(int[] array, int k)
    {
    	List<List<Integer>> result = new ArrayList<>();
    	
    	for (int i = 0; i < array.length - 1; i++) 
    	{
    	    for (int j = i + 1; j < array.length; j++) 
    	    {
    	        if (array[i] + array[j] == k) 
    	        {
    	        	List<Integer> resultItem = new ArrayList<>();
    	        	
    	        	resultItem.add(i);
    	        	resultItem.add(j);
    	        	
    	        	result.add(resultItem);
    	        }
    	    }
    	}
    	
    	return result;
    }
}
