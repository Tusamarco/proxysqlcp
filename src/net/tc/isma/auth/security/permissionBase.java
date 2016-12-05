package net.tc.isma.auth.security;

import net.tc.isma.data.objects.Module;
import net.tc.isma.persister.IsmaPersister;

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
public abstract class permissionBase implements Permission{
    public permissionBase() {
    }
    public static permissionBean getSecurity(String modulename, UserBase user, Permission perm)
    {
        Module mod = null;
        if (IsmaPersister.getModulesMap() != null &&
            IsmaPersister.getModulesMap().size() > 0
            && modulename != null && !modulename.equals("")
                )
        mod = (Module) IsmaPersister.getModulesMap().get(modulename);

        permissionBean pb = new permissionBean();

        if(mod != null && !user.isAnonymous() && !user.isAdministrator())
        {

            pb.setCanAdd(perm.canAdd(user, mod));
            pb.setCanDelete(perm.canDelete(user, mod));
            pb.setCanDemote(perm.canDemote(user, mod));
            pb.setCanEdit(perm.canEdit(user, mod));
            pb.setCanPromote(perm.canPromote(user, mod));
            pb.setCanPublish(perm.canPublish(user, mod));
            pb.setCanRead(perm.canRead(user, mod));
            pb.setCanSecurity(perm.canSecurity(user, mod));
            pb.setCanUnpublish(perm.canUnpublish(user, mod));
            pb.setIsAdmin(user.isAdministrator());
        }
        else if(user.isAdministrator())
        {
            pb.setCanAdd(true);
            pb.setCanDelete(true);
            pb.setCanDemote(true);
            pb.setCanEdit(true);
            pb.setCanPromote(true);
            pb.setCanPublish(true);
            pb.setCanRead(true);
            pb.setCanSecurity(true);
            pb.setCanUnpublish(true);
            pb.setIsAdmin(true);
        }
        else
        {
            pb.setCanAdd(false);
            pb.setCanDelete(false);
            pb.setCanDemote(false);
            pb.setCanEdit(false);
            pb.setCanPromote(false);
            pb.setCanPublish(false);
            pb.setCanRead(false);
            pb.setCanSecurity(false);
            pb.setCanUnpublish(false);
            pb.setIsAdmin(false);
        }

        return pb;
    }

}
