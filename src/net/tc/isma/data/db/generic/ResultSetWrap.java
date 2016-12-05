package net.tc.isma.data.db.generic;

import java.sql.ResultSet;
import net.tc.isma.persister.IsmaPersister;
import java.sql.*;

public class ResultSetWrap
{
	private ResultSet res = null;
	int size = 0;
    private java.sql.Connection cpds;
	public ResultSetWrap( ResultSet resin, boolean fetchRecordsNumner )
	{
		res = resin;
		try
		{
			if( res == null || res.getRow() != 0 || !fetchRecordsNumner)
				return;

			res.last();
			size = res.getRow();

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}
	}

	public ResultSet getResultSet()
	{
		return res;
	}

	public int size()
	{
		return size;
	}
	public void closeCpds()
	{
		try
		{
			cpds.close();
		}
		catch( SQLException ex )
		{
			IsmaPersister.getLogDataAccess().error("",ex);
		}
	}
    public java.sql.Connection getCpds()
    {
	    return cpds;
    }
    public void setCpds(java.sql.Connection cpds)
    {
		this.cpds = cpds;
    }

}
