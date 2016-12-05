package net.tc.isma.data.jdbc;

import java.util.*;
import java.sql.*;
//import org.hibernate.*;
import java.lang.InstantiationException;
import java.io.Serializable;
import net.tc.isma.data.*;
import net.tc.isma.*;
//import org.hibernate.LockMode;
import net.tc.isma.data.hibernate.HybernateObject;

/**
 * Implementation of DatastoreSession specific to Hibernate.  Wraps main
 * store/query mechanisms with a thin wrapper.  Also uses reflection to find
 * implementation of queries.
 *
 * <p>Title: ISMA</p>
 * <p>Description: Information System Modular Architecture</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author
 * @version 1.0
 */
public abstract class JdbcDatastoreSession{
    protected Connection conn;

    protected JdbcDatastoreSession()
    {
    }

    JdbcDatastoreSession(Connection conn)
    {
        this.conn = conn;
    }


    public void close()
    {
        try
        {
            conn.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
    }

    public boolean isOpen()
    {
        try
        {
            return !conn.isClosed();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }





    public void flush()
    {
        try
        {
            conn.commit();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
//    public Object load(Class cls, Serializable id, LockMode lockmode)
//    {
//        throw new UnsupportedOperationException("load() not valid in JDBC sessions!");
//    }

    public Object load(Class cls, Serializable id)
    {
        throw new UnsupportedOperationException("load() not valid in JDBC sessions!");
    }





    private void closeStatement(Statement stmt)
    {
        try
        {
            if (stmt != null)
                stmt.close();
        }
        catch (SQLException ex1)
        {
            ex1.printStackTrace();
        }
    }

//    public Iterator iterate(DataQueryBean query) throws DatastoreException
//    {
//        List res = find(query);
//
//        return res.iterator();
//    }


   public Connection connection()
   {
       return conn;
   }

   public static List resultSetToList(ResultSet rs)
       throws SQLException
   {
       List resultList = new Vector();
       ResultSetMetaData rsmd = rs.getMetaData();
       int cols = rsmd.getColumnCount();

       while ( rs.next() )
       {
           Object[] vals = new Object[cols];
           for (int i = 0; i < cols; i++)
           {
               vals[i] = rs.getObject(i+1);
           }
           if ( cols == 1 )
           {
               resultList.add(vals[0]);
           }
           else
           {
               resultList.add(vals);
           }
       }

       return resultList;
   }

}
