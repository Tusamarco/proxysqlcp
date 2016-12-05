package net.tc.isma.modules.requestor;


import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.results;
import net.tc.isma.persister.IsmaPersister;

import net.tc.isma.data.request.actionBaseImplRequest;
import javax.servlet.http.HttpSession;
import net.tc.isma.utils.HttpQuery;
import net.tc.isma.request.generic.Requestor;
import net.tc.isma.request.generic.requestorImpl;


public class moveRequestRequestor extends actionBaseImplRequest
{
	private results resultLocal = null;

	public moveRequestRequestor()
	{
	}

	public Results execute()
	{
        try{
            resultLocal = ( results )this.getResult() ;
            HttpSession session1 = this.getSession() ;
            HttpQuery qs = this.getHttpQuery() ;
            Requestor requestor = ( Requestor ) session1.getAttribute( Requestor.class.toString() ) ;
//            String pageToreturn = getRequest().getRequest().getParameter("oldequest" ) ;
            qs.removeAttribute("oldequest");
            String move = null;
            int index = qs.getInt("indexRequestor",999);
            move = (String)qs.get("move");

            if ( requestor == null )
                return (Results)resultLocal;
            else if(move.equals("up") && index != 999 && index < requestor.size())
                requestor.moveUp(index);
            else if(move.equals("down") && index != 999 && index < requestor.size())
                requestor.moveDown(index);


            if ( !resultLocal.processSuccesflullyExecuted() )
                return ( Results )this.getResult() ;
        }
        catch(Exception ex )
        {IsmaPersister.getLogRemoteAccess().error(ex);}

        return (Results)resultLocal;
    }
}
