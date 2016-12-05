package net.tc.isma.data.datareader.tserial;

import net.tc.isma.data.datareader.DataFactory;
import net.tc.isma.data.datareader.DataFile;
import net.tc.isma.data.datareader.DataItemsCollection;
import java.util.Map;

public class tserialFactory
	implements DataFactory
{
	protected tserialFactory()
	{
	}

	public static DataFactory getInstance( DataFile dFile, String name )
	{
		tserialFactory factory = null;

		if(dFile != null && name != null && !name.equals("") )
		{
			factory = new tserialFactory();
		}
	/** @todo To implement the Protected  constructor*/
		return factory;
	}

	public static DataFactory getInstance( Map parameter)
	{
		tserialFactory factory = null;

	/** @todo To implement the Protected  constructor for the db where in the map there is
	 * 0 = list of the fields to be use as key
	 * 1 = sql statment
	 * 2 = jdbc string
	 * 3 = jdbc driver
	 * 4 = username
	 * 5 = password
	 *
	 * */
		return factory;
	}

	public DataItemsCollection getCollection( Map query )
	{
		/** @todo To implement the Retrieval of the collection*/
		return null;
	}

	public void writeToDataFile(DataFile dFile)
	{
		if(dFile == null)
			return;
		int defFormat = DataFactory.FORMAT_CSV;
		writeToDataFile(dFile, defFormat);
	}
	public void writeToDataFile(DataFile dFile, int defFormat)
	{
		/** @todo to do the writer */
	}
}
