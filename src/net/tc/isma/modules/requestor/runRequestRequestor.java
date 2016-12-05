package net.tc.isma.modules.requestor;


import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.results;
import net.tc.isma.persister.IsmaPersister;

import net.tc.isma.data.request.actionBaseImplRequest;
import javax.servlet.http.HttpSession;
import net.tc.isma.utils.HttpQuery;
import net.tc.isma.request.generic.Requestor;



public class runRequestRequestor extends actionBaseImplRequest
{
	private results resultLocal = null;

	public runRequestRequestor()
	{
	}

	public Results execute()
	{
        try{
            resultLocal = ( results )this.getResult() ;
            HttpSession session1 = this.getSession() ;
            HttpQuery qs = this.getHttpQuery() ;
            Requestor reqestor = ( Requestor ) session1.getAttribute( Requestor.class.toString() ) ;
            if ( reqestor == null ||  reqestor.size() <= 0)
                return (Results)resultLocal;

                this.getRequest().getResponse().sendRedirect( qs.toString() ) ;

            if ( !resultLocal.processSuccesflullyExecuted() )
                return ( Results )this.getResult() ;
        }
        catch(Exception ex )
        {IsmaPersister.getLogRemoteAccess().error(ex);}

        return (Results)resultLocal;
    }
}
