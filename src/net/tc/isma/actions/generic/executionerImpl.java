package net.tc.isma.actions.generic;

import java.util.*;

import net.tc.isma.actions.*;
import net.tc.isma.actions.Action;
import net.tc.isma.auth.security.*;
import net.tc.isma.persister.*;
import net.tc.isma.request.generic.*;
import net.tc.isma.utils.*;
import net.tc.isma.views.*;

public class executionerImpl
	implements Executioner
{
	Map chains = null;
        Map securityChain = null;
	String[] chainsNames = null;
	UserBase user = null;
	Results res = null;
	HttpQuery httpQuery = null;
	requestImpl  reqImp = null;

	public executionerImpl( requestImpl requestWrapper )
	{
		if(requestWrapper == null)
			return;

		this.reqImp = requestWrapper;
		this.user = reqImp.getUserBean();

		httpQuery = reqImp.getHttpQuery();

//		req = (HttpServletRequest)result.get(HttpServletRequest.class);
//
//		if(req == null)
//			return;
        if(reqImp.isSystem())
        {
            if(reqImp.getRequest() != null && reqImp.getRequest().getAttribute("ac") != null)
            {
                chainsNames = ((String)reqImp.getRequest().getAttribute("ac")).split( "," ) ;
                ActionFactory af = IsmaPersister.getActionFactory(
                    actionFactoryImpl.class ) ;
                if ( af == null )
                    return ;
                chains = af.getActionChains( chainsNames ) ;

            }
        }
        else
        {
            if ( httpQuery.get( "ac" ) != null &&
                 !httpQuery.get( "ac" ).equals( "" ) )
            {
                chainsNames = httpQuery.get( "ac" ).split( "," ) ;
                ActionFactory af = IsmaPersister.getActionFactory(actionFactoryImpl.class ) ;
                if ( af == null )
                    return ;
                chains = af.getActionChains( chainsNames ) ;
                securityChain = af.getActionChains( new String[]{"usercontroller"} ) ;
            }
        }
		res = reqImp.getResult();

	}
	public void execute()
	{
		if(chains == null)
			return;

                if(checkRestricted() && (reqImp.getUserBean() == null || reqImp.getUserBean().isAnonymous()))
                {
                    doLogin(securityChain);
                    return;
                }


		Iterator it = chains.values().iterator();
		while(it.hasNext())
		{
			ActionChain ac = (ActionChain)it.next();
			if(ac == null && (ac.getActions() != null || ac.getView() == null))
				return;

			runChain(ac);
		}

	}

    /**
     * doLogin
     */
    private void doLogin(Map securityChainIn) {

        try{
            Iterator it = securityChainIn.values().iterator();
            while(it.hasNext())
            {
                    ActionChain ac = (ActionChain)it.next();
                    if(ac == null && (ac.getActions() != null || ac.getView() == null))
                            return;

                    runChain(ac);

            }
        }
        catch (Exception ex)
        {
            IsmaPersister.getLogSystem().error(ex);
        }
    }

    /**
     * checkRestricted
     */
    private boolean checkRestricted()
    {
        Iterator it = chains.values().iterator();
        while(it.hasNext())
        {
            actionChainImpl lac = (actionChainImpl) it.next();
            if(lac.getSecurity() != null && lac.getSecurity().toLowerCase().equals("restricted"))
                return true;
        }


        return false;
    }

    public void runChain( ActionChain actionChain )
	{

		Object[][] actions = actionChain.getActions();
		if(actions != null && actions.length > 0 )
		{
                    IsmaPersister.getLogController().info("Executing action chain " + actionChain.getName() + " [START] ");

			for(int aa = 0 ; aa < actions.length ; aa++)
			{
				this.res = execAction((Action)actions[aa][1]);
			}
                    IsmaPersister.getLogController().info("Executing action chain " + actionChain.getName() + " [END] ");

		}
		if(actionChain.getView() != null)
			showView(actionChain.getView());



	}

	private Results execAction( Action actionIn )
	{
		String className = actionIn.getClasspkg()+"." + actionIn.getClassname();
		if(className == null || className.equals(""))
			return this.res;

		Action action = null;
		try {
				action = (Action)Class.forName(className).newInstance();
				action.setReload(reqImp.getReload());
		} catch (Exception e) { IsmaPersister.getLogSystem().error("", e);
		}
		action.setResult((Results)this.res);
                action.setRequest((requestImpl)reqImp);

		/** @todo ADD check for restriction if class is Restricted */
		Results locRes = action.execute();
		return locRes;
	}

	private void showView( View view )
	{
//		view.setResult(this.res);
		reqImp.setResult(this.res);
		view.setRequestWrapper(reqImp);
		view.render();

	}

}
