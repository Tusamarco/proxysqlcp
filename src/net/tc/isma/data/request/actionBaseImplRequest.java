package net.tc.isma.data.request;

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
import net.tc.isma.utils.HttpQuery;
import net.tc.isma.actions.generic.results;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import net.tc.isma.request.generic.requestorImpl;
import net.tc.isma.request.generic.Requestor;

/**
 * Base class used by any database action in FAOSTAT
*
 */

public class actionBaseImplRequest extends actionImpl
{
	private Connection cpds = null;

	public actionBaseImplRequest()
	{
	}

    public HttpQuery getHttpQuery()
    {
        try
        {
            if ( getRequest().getHttpQuery() != null )
                return getRequest().getHttpQuery() ;
        }
        catch(NullPointerException ex)
        {
            IsmaPersister.getLogDataAccess().debug(ex);
            return null;
        }
        return null;

    }

    public HttpSession getSession()
    {
        try
        {
            if ( getRequest().getSession() != null )
                return getRequest().getSession() ;
        }
        catch(NullPointerException ex)
        {
            IsmaPersister.getLogDataAccess().debug(ex);
            return null;
        }
        return null;

    }

    private Requestor getRequestor( HttpServletRequest req )
    {
        if ( req == null || req.getSession( true ) == null
             || req.getSession( true ).getAttribute( Requestor.class.toString() ) == null )
        {
            java.lang.ref.SoftReference mS = new SoftReference( new
                requestorImpl( req ) ) ;
            return ( Requestor ) mS.get() ;
        }
        else
            return ( Requestor ) req.getSession( true ).getAttribute( Requestor.class.
                toString() ) ;
    }


}
