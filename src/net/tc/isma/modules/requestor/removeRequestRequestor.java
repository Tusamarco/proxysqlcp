package net.tc.isma.modules.requestor;


import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.results;
import net.tc.isma.persister.IsmaPersister;

import net.tc.isma.data.request.actionBaseImplRequest;
import javax.servlet.http.HttpSession;
import net.tc.isma.utils.HttpQuery;
import net.tc.isma.request.generic.Requestor;
import net.tc.isma.request.generic.requestorImpl;


public class removeRequestRequestor extends actionBaseImplRequest
{
	private results resultLocal = null;

	public removeRequestRequestor()
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

            int index = qs.getInt("indexRequestor",999);
            if ( reqestor == null &&  index != 999 )
                return (Results)resultLocal;
            else if(index == 998)
            {
                reqestor = null ;
                session1.removeAttribute( Requestor.class.toString());
            }
            else
                reqestor.removeRequest(index);


            if ( !resultLocal.processSuccesflullyExecuted() )
                return ( Results )this.getResult() ;
        }
        catch(Exception ex )
        {IsmaPersister.getLogRemoteAccess().error(ex);}

        return (Results)resultLocal;
    }
}
