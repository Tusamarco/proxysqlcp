package net.tc.isma.data.datareader;

import java.util.Map;

public interface DataFactory
{
	public static int FORMAT_CSV = 1;
	public static int FORMAT_XML = 2;
	public static int FORMAT_DB_ORACLE = 3;
  /**
  * this method return a sub set of the Whole collection generated from the dataFile.
	* Mainly the subcollection is created filtering the collection by the passed Map that represent the Key
  */
	public DataItemsCollection getCollection(Map query);
	public void writeToDataFile(DataFile dFile);
	public void writeToDataFile(DataFile dFile, int defFormat);
}
