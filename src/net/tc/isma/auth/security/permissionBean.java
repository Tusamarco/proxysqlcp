package net.tc.isma.auth.security;

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
public class permissionBean {

    public permissionBean() {
    }

    public boolean canAdd() {
        return canAdd;
    }

    public boolean canDelete() {
        return canDelete;
    }

    public boolean canDemote() {
        return canDemote;
    }

    public boolean canEdit() {
        return canEdit;
    }

    public boolean canPromote() {
        return canPromote;
    }

    public boolean canPublish() {
        return canPublish;
    }

    public boolean canRead() {
        return canRead;
    }

    public boolean canSecurity() {
        return canSecurity;
    }

    public boolean canUnpublish() {
        return canUnpublish;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public void setCanDemote(boolean canDemote) {
        this.canDemote = canDemote;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public void setCanPromote(boolean canPromote) {
        this.canPromote = canPromote;
    }

    public void setCanPublish(boolean canPublish) {
        this.canPublish = canPublish;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public void setCanSecurity(boolean canSecurity) {
        this.canSecurity = canSecurity;
    }

    public void setCanUnpublish(boolean canUnpublish) {
        this.canUnpublish = canUnpublish;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    private boolean canAdd = false ;
    private boolean canDelete = false ;
    private boolean canEdit = false ;
    private boolean canRead = false ;
    private boolean canPublish = false ;
    private boolean canUnpublish = false ;
    private boolean canPromote = false ;
    private boolean canDemote = false ;
    private boolean canSecurity = false ;
    private boolean isAdmin;


}
