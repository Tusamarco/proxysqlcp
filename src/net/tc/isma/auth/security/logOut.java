package net.tc.isma.auth.security;

import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.actions.generic.actionImpl;
import net.tc.isma.actions.generic.results;
import net.tc.isma.actions.Results;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class logOut extends actionImpl{
    public logOut() {
    }

    private results resultLocal = null;
    public Results execute()
    {
        resultLocal = (results)this.getResult();
        if (!resultLocal.processSuccesflullyExecuted())
            return (Results) resultLocal;

        try{
            resultLocal = (results)this.getResult();
            HttpServletRequest req = this.getRequest();

            UserBase user = new UserBase();
            user.setId("0");
            user.setName("Anonym");
            user.setUsername("anonym");
            user.setSurname("Anonym");
            user.setAnonymous(true);
            user.setIp(req.getRemoteAddr());

            this.getRequest().setUserBean(user);
            this.getRequest().getSession(true).removeAttribute(UserBase.class.toString());
        }
        catch(Exception ex)
        {
            IsmaPersister.getLogController().error(ex);
            resultLocal.processSuccesflullyExecuted(false);
        }

        return (Results) resultLocal;
    }


}
