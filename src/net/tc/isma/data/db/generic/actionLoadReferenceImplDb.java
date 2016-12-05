package net.tc.isma.data.db.generic;

import net.tc.isma.actions.generic.actionImpl;
import java.sql.*;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.data.db.generic.ResultSetWrap;
import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import java.util.Iterator;
import net.tc.isma.data.generic.baseObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import net.tc.isma.utils.Text;
import java.lang.ref.SoftReference;
import net.tc.isma.utils.SynchronizedMap;

/**
 * Base class used by any database action in FAOSTAT
*
 */

public class actionLoadReferenceImplDb extends actionImpl
{
	private Connection cpds = null;

	public actionLoadReferenceImplDb()
	{
	}

 /**
  * get result method by default the record set is FORWARD only
  * CONDURRED READ ONLY and no records number fatch
  */
	public ResultSetWrap getResultSet( String sql )
	{
		return getResultSet( sql, false, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	}
	/**
	 * get result method by default the record set is SCOLLABLE only
	 * CONDURRED READ_WRITEand no records number fatch
	 */

	public ResultSetWrap getResultSet( String sql, boolean fetchRecordsNumber)
	{
		return getResultSet( sql, fetchRecordsNumber ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
	}

	/**
	 * get result method a ResultSetWrap is returned
	 */

	public ResultSetWrap getResultSet( String sql, boolean fetchRecordsNumber, int ResultSetTYPE, int ResultSetUpdatable )
	{
		try
		{
			setCpds();
			cpds = getCpds();
			Statement stmnt = cpds.createStatement( ResultSetTYPE, ResultSetUpdatable );
			ResultSet rs = stmnt.executeQuery( sql );

			if( rs != null )
			{
				ResultSetWrap rw =  new ResultSetWrap( rs, fetchRecordsNumber );
				rw.setCpds(getCpds());
				return rw;

			}

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error(sql, ex );
			return null;
		}

		return null;

	}
/**
 * Retrive Meta information on the Result Set
 */
	public String getMetaInfo( ResultSet rsIn )
	{
		if( rsIn == null )
			return null;

		StringWriter sw = new StringWriter();
		try
		{
			ResultSetMetaData rsm = rsIn.getMetaData();
			PrintWriter pw = new PrintWriter( sw );

			pw.println( "Schema: " + rsm.getSchemaName( 1 ) + " Table:" + rsm.getTableName( 1 ) );
			int columns = rsm.getColumnCount();
			StringBuffer sb = new StringBuffer();
			for( int i = 1; i < columns; i++ )
			{
				sb.append( rsm.getColumnName( i ).toUpperCase() + "," );
			}
			pw.println( sb.toString().substring( 0, sb.toString().length() - 1 ) );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
			return null;
		}

		return sw.toString();
	}

/**
 * Return the full record set as a string comma separated
 */
	public String getResultSetAsStringValues( ResultSet rsIn, int size )
	{
		if( rsIn == null )
			return null;

		StringWriter sw = new StringWriter();
		try
		{
			PrintWriter pw = new PrintWriter( sw );

			int columns = rsIn.getMetaData().getColumnCount();
			rsIn.first();
			do
			{
				StringBuffer sb = new StringBuffer();
				for( int i = 1; i < columns; i++ )
				{
					sb.append( rsIn.getString( i ) + "," );
				}
				pw.println( sb.toString().substring( 0, sb.toString().length() - 1 ) );
				rsIn.next();
			}
			while( !rsIn.isLast() );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
			return null;
		}

		return sw.toString();

	}
	/**
	 * Return the full record set as a LIST for the specified field
	 */
		public Map getResultSetAsMap( ResultSet rsIn, String field )
		{
			return getResultSetAsMap( rsIn, field, field );
		}
		/**
		 * Return the full record set as a LIST for the specified field
		 */

		public Map getResultSetAsMap( ResultSet rsIn, String id, String field )
		{
			try
			{
				if( rsIn == null || ( !rsIn.isFirst() && !rsIn.isLast() ) )
				{
					return null;
				}
			}
			catch( SQLException ex1 )
			{
			}

			Map m = new HashMap();

			try
			{

				rsIn.first();
				while( !rsIn.isLast() )
				{
				    m.put( rsIn.getString(id),rsIn.getString(field));
					rsIn.next();
				}

			}
			catch( Exception ex )
			{
				IsmaPersister.getLogSystem().error( "", ex );
			}

			return m;

		}

	/**
	 * Return the full record set as a LIST for the specified field
	 */
		public Map getResultSetAsMapOfMaps( ResultSet rsIn, String id, String[] fields )
		{
			try
			{
				if( rsIn == null || ( !rsIn.isFirst() && !rsIn.isLast() ) )
				{
					return null;
				}
			}
			catch( SQLException ex1 )
			{
			}

			Map m = new HashMap();

			try
			{

				rsIn.first();
				while( !rsIn.isLast() )
				{
					String idMap = rsIn.getString(id);
					String valueId = rsIn.getString(fields[0]);
					String[] values = new String[fields.length];
					Map mvalues = new HashMap();
					if( m.containsKey( idMap ) )
					{
						mvalues = ( Map ) m.get( idMap );
					}

					for(int idf = 0 ; idf < fields.length ; idf++)
					{
						//rsIn.getString(field);
						values[idf] = rsIn.getString(fields[idf]);
					}
					mvalues.put( valueId, values);
					m.put( idMap, mvalues );
					rsIn.next();
				}

			}
			catch( Exception ex )
			{
				IsmaPersister.getLogSystem().error( "", ex );
			}

			return m;

		}

	/**
	 * Retrive the full set of values as a List of object
	 * is possible to pass a BEAN or an Object that implements the Map interface
	 */
	public Map getResultsetValues( ResultSetWrap rsw, Class cls )
	{
		Map m = null;
		if( rsw.size() < 1 )
			return null;

		ResultSet rs = rsw.getResultSet();
		if(rsw.size() > 0 )
//			m = new HashMap(rsw.size());
			m = new SynchronizedMap(1);
		else
			m = new SynchronizedMap(0);

		try
		{
			rs.first();
			long timestart = System.currentTimeMillis();
			while( !rs.isLast() )
			{
				baseObject bo = ( baseObject ) cls.newInstance();
				List retrivableFields = bo.getRetrivableFields();
				Map rowValues = new HashMap();
				int columns = rs.getMetaData().getColumnCount();

				for( int i = 1; i < columns; i++ )
				{
					if( retrivableFields.contains( rs.getMetaData().getColumnName( i )) )
					{
						rowValues.put( rs.getMetaData().getColumnName( i ), rs.getString( i ) );
					}
				}

				bo = (baseObject)setPropertyValue(bo,rowValues);

				m.put( bo.getId(),bo );
				rs.next();

			};
			long timeend = System.currentTimeMillis();

			String typeBean = "Bean";

			if(cls.newInstance() instanceof Map)
				typeBean = "Map";

			IsmaPersister.getLogDataAccess().info("Required time to load " + cls.getName() + " type = " + typeBean + " time = " + (timeend - timestart) + " millSeconds");

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}
		return m;

	}
	/**
  * Use reflection to populate the object if it's a BEAN or just populate it if is an instance of MAP
  */
	private Object setPropertyValue( Object bean, Map rowValues )
	{
		if(bean == null || rowValues == null)
			return null;

		boolean isMap = false;

		try{
			isMap = ((baseObject)bean).hasMap(bean.getClass());

			Map RetrivableFields = ((baseObject)bean).getRetrivableFieldsMap();
			Iterator it = rowValues.keySet().iterator();
			while( it.hasNext() )
			{
				Object oKey = it.next();
				if( RetrivableFields.containsKey( oKey ) )
				{
					Object value = (Object)rowValues.get(oKey);
					String proname = (String)RetrivableFields.get(oKey);

							if( value != null && !value.equals( "null" ) )
							{
								if( value instanceof String )
									( ( String ) value ).trim();

								if( value instanceof BigDecimal)
									value = new Long(((BigDecimal )value).longValue());

								if( value instanceof BigInteger)
									value = new Long(((BigInteger)value).longValue());


								if(isMap)
								{
									((Map)bean).put(proname,value);
								}
								else
								{
									BeanUtils.setProperty( bean, proname, value );

								}
							}
							else
							{
								if(isMap)
								{
									((Map)bean).put(proname,null);
								}
								else
								{
									BeanUtils.setProperty( bean, proname, null );
								}
							}

				}
			}
		}catch(Exception ex){ex.printStackTrace();}

		return bean;
	}
    private Connection getCpds()
    {
		try{
			if( cpds.isClosed() )
			{
				cpds = null;
				setCpds();
			}
		}catch(Exception ex){};

		return cpds;
    }
	private void setCpds()
	{
		if(cpds == null)
		 setCpds(IsmaPersister.getConnectionlIn());

	}
	private void setCpds(Connection conn)
	{
		cpds = conn;
	}

	/**
	 * Return the full record set as a LIST for the specified field
	 */
	public Map getResultSetAsMapOfMaps( ResultSet rsIn, String[] id, String[] fields )
	{
		try
		{
			if( rsIn == null || ( !rsIn.isFirst() && !rsIn.isLast() ) )
			{
				return null;
			}
		}
		catch( SQLException ex1 )
		{
		}
		java.lang.ref.SoftReference mS = new SoftReference(new HashMap());
		Map m = (Map)mS.get();

		try
		{

			rsIn.first();
			while( !rsIn.isLast() )
			{
				String[] idMap = new String[id.length];
				for(int idi = 0 ; idi < idMap.length ;idi++)
				{
					idMap[idi] = rsIn.getString( id[idi]);
				}

				String valueId = rsIn.getString( fields[0] );
				String[] values = new String[fields.length];
				Map mvalues = new HashMap();
				if( m.containsKey( idMap ) )
				{
					mvalues = ( Map ) m.get( idMap );
				}

				for( int idf = 0; idf < fields.length; idf++ )
				{
					//rsIn.getString(field);
					values[idf] = rsIn.getString( fields[idf] );
				}
				mvalues.put( valueId, values );
				m.put( idMap, mvalues );
				rsIn.next();
			}

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}

		return m;

	}



}
