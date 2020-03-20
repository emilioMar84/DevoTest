package org.cynemi.devotest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.cynemi.devotest.DirectoryContent.DirectoryContentItem;

/**
 * Calculates incrementally the values for TfIdf
 * @author em_m0
 *
 */
public class TfIdfIncrementalCalculator {
	
	private int numDoc = 0;
	private Map<String, Double> tfIdf = new HashMap<>();
	private Map<String, Integer> numOcurrencesDoc = new HashMap<>();
	private List<Entry<String, Double>> ranking = new ArrayList<>();
	private String t;
	private int numDocContaining = 0;
	
	TfIdfIncrementalCalculator(List<DirectoryContentItem> docs, String t)
	{
		this.t = t;
		
		addDocs(docs);
	}
	
	public Map<String, Double> getTfIdf()
	{
		return tfIdf;
	}
	
	public List<Entry<String, Double>> getRanking()
	{
		return ranking;
	}
	
	/**
	 * Calculates incrementally the values for TfIdf
	 * time complexity O(d*n), space complexity (d*n) let d be number of documents and n the average size of the documents
	 * @param docs
	 */
	public void addDocs(List<DirectoryContentItem> docs)
	{
		numDoc += docs.size();
		
		for (DirectoryContentItem doc : docs)
		{
			int countMatches =  StringUtils.countMatches(doc.conntent, t);
			numOcurrencesDoc.put(doc.fileName, countMatches);
			if (countMatches > 0)
			{
				numDocContaining++;
			}
		}
		
		double Idf = Math.log(numDoc /(double)numDocContaining);
		
		for (Entry<String, Integer> entry : numOcurrencesDoc.entrySet())
		{
			tfIdf.put(entry.getKey(), entry.getValue() * Idf);
		}
		
		ranking.clear();
		for (Entry<String, Double> entry : tfIdf.entrySet())
		{
			ranking.add(entry);
		}
		
		ranking.sort(new Comparator<Entry<String, Double>>() {

			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
			
		});
	}

}
