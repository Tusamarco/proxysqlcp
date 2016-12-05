package net.tc.isma.auth.security ;

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
public class Subscriber extends UserBase
{
    private int limit ;
    private String group = null;
    public Subscriber()
    {
    }

    public void setLimit( int limit )
    {
        this.limit = limit ;
    }

    public int getLimit()
    {
        return limit ;
    }

    public String getGroupString()
    {
        return this.group;
    }

    public void setGroupString(String group)
    {
        this.group = group;
    }
    public void setPasswordEncoded(String password)
    {
        if(password == null || password.equals(""))
            return;

        super.setPassword(EncryptionUtil.encryptString(password));
    }
    public String getPassword()
    {
        return super.getPassword();

    }

}
