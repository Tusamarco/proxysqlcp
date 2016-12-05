package net.tc.isma.modules.requestor;


import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.results;
import net.tc.isma.persister.IsmaPersister;

import net.tc.isma.data.request.actionBaseImplRequest;
import javax.servlet.http.HttpSession;
import net.tc.isma.utils.HttpQuery;
import net.tc.isma.request.generic.Requestor;
import net.tc.isma.request.generic.requestorImpl;


public class addRequestRequestor extends actionBaseImplRequest
{
	private results resultLocal = null;

	public addRequestRequestor()
	{
	}

	public Results execute()
	{
        try{
            resultLocal = ( results )this.getResult() ;
            HttpSession session1 = this.getSession() ;
            HttpQuery qs = this.getHttpQuery() ;
            Requestor reqestor = ( Requestor ) session1.getAttribute( Requestor.class.toString() ) ;
//            String pageToreturn = getRequest().getRequest().getParameter("oldequest" ) ;
            qs.removeAttribute("oldequest");

            if ( reqestor == null )
                reqestor = new requestorImpl( this.getRequest().getRequest() ) ;
            else
                reqestor.setRequest(this.getRequest().getRequest() );

            if ( !resultLocal.processSuccesflullyExecuted() )
                return ( Results )this.getResult() ;
        }
        catch(Exception ex )
        {IsmaPersister.getLogRemoteAccess().error(ex);}

        return (Results)resultLocal;
    }
}
