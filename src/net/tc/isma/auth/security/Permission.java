package net.tc.isma.auth.security;

public interface Permission {
    public boolean canAdd(UserBase user, Object mod);

    public boolean canDelete(UserBase user, Object mod);

    public boolean canEdit(UserBase user, Object mod);

    public boolean canRead(UserBase user, Object mod);

    public boolean canPublish(UserBase user, Object mod);

    public boolean canUnpublish(UserBase user, Object mod);

    public boolean canPromote(UserBase user, Object mod);

    public boolean canDemote(UserBase user, Object mod);

    public boolean canSecurity(UserBase user, Object mod);



}
