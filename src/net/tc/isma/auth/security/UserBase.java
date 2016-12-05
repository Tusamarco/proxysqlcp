package net.tc.isma.auth.security;

import java.io.Serializable ;
import java.util.* ;

import javax.servlet.http.HttpSession ;

import org.apache.commons.lang.builder.* ;
import net.tc.isma.data.objects.Module;

/** @author Hibernate CodeGenerator */
public class UserBase implements Serializable {

    private HttpSession session1 = null;
    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String surname;

    /** nullable persistent field */
    private String username;

    /** nullable persistent field */
    private String name;
    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private java.util.Date noactDate;
    private java.util.Set roles;
    private String password;
    private java.util.Set group;
    private int[] ip = new int[]{0,0,0,0,0};
    private boolean anonymous = false;
    private boolean isInternal = false;


    /** full constructor */
    public UserBase(java.lang.String id, java.lang.String surname, java.lang.String username, java.lang.String password, java.lang.String email, java.util.Date noactDate,HttpSession sessionIn) {
        this.id = id;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.noactDate = noactDate;
        this.session1  = sessionIn;
    }

    /** default constructor */
    public UserBase() {
    }

    /** minimal constructor */
    public UserBase(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getId() {
        return this.id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }
    public java.lang.String getSurname() {
        return this.surname;
    }

    public void setSurname(java.lang.String surname) {
        this.surname = surname;
    }
    public java.lang.String getUsername() {
        return this.username;
    }

    public void setUsername(java.lang.String username) {
        this.username = username;
    }
    public java.lang.String getEmail() {
        return this.email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    public java.util.Date getNoactDate() {
        return this.noactDate;
    }

    public void setNoactDate(java.util.Date noactDate) {
        this.noactDate = noactDate;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserBase) ) return false;
        UserBase castOther = (UserBase) other;
        return new EqualsBuilder()
            .append(this.id, castOther.id)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }

    public Date getCreateDate()
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method getCreateDate() not yet implemented.");
    }
    public Date getLastLogin()
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method getLastLogin() not yet implemented.");
    }
    public Object getPerm(String name)
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method getPerm() not yet implemented.");
    }
    public Object getPerm(String name, Object def)
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method getPerm() not yet implemented.");
    }
    public void setHasLoggedIn(Boolean value)
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method setHasLoggedIn() not yet implemented.");
    }
    public boolean hasLoggedIn()
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method hasLoggedIn() not yet implemented.");
    }
    public void incrementAccessCounter()
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method incrementAccessCounter() not yet implemented.");
    }
    public void setLastAccessDate()
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method setLastAccessDate() not yet implemented.");
    }
    public void setLastLogin(Date lastLogin)
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method setLastLogin() not yet implemented.");
    }
    public void setPerm(String name, Object value)
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method setPerm() not yet implemented.");
    }
    public void setCreateDate(Date date)
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method setCreateDate() not yet implemented.");
    }
    public void updateLastLogin() throws Exception
    {
        /**@todo Implement this net.tc.isma.auth.security.User method*/
        throw new java.lang.UnsupportedOperationException("Method updateLastLogin() not yet implemented.");
    }
    public boolean hasRole()
    {
        /**@todo Implement this org.apache.turbine.util.RunData method*/
        throw new java.lang.UnsupportedOperationException("Method hasAction() not yet implemented.");
    }

    public boolean userExists()
    {
        /**@todo Implement this org.apache.turbine.util.RunData method*/
        throw new java.lang.UnsupportedOperationException("Method userExists() not yet implemented.");
    }
    public int getStatusCode()
    {
        /**@todo Implement this org.apache.turbine.util.RunData method*/
        throw new java.lang.UnsupportedOperationException("Method getStatusCode() not yet implemented.");
    }
    public void setStatusCode(int sc)
    {
        /**@todo Implement this org.apache.turbine.util.RunData method*/
        throw new java.lang.UnsupportedOperationException("Method setStatusCode() not yet implemented.");
    }
    public void setRoles(java.util.Set roles)
    {
        this.roles = roles;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public boolean isAnonymous()
    {return this.anonymous;}
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public java.util.Set getGroup()
    {
        return group;
    }
    public void setGroup(java.util.Set group)
    {
        this.group = group;
    }

    public void setAnonymous( boolean anonymousIn )
    {
        this.anonymous = anonymousIn ;
    }

    public void setIsInternal( boolean isInternal )
    {
        this.isInternal = isInternal ;
    }

    public boolean hasGroup(Group group)
    {
        if(group == null || getGroup() == null || getGroup().isEmpty())
            return false;
        return getGroup().contains(group);
    }

    public boolean isAdministrator()
    {
        Iterator it = this.getRoles().iterator();
        while(it.hasNext())
        {
            Role r = (Role) it.next();
            if(r.getId().equals("admin"))
                return true;
        }
        return false;
    }
    public boolean isSecurityAdministrator(Object mod)
    {
        if(getRoles(mod) == null || getRoles(mod).isEmpty())
        {
            return false;
        }
        else
        {
             String[] rolesAr = getRoleNames(mod);
             for(int ir = 0 ; ir < rolesAr.length; ir++)
             {
                 if(rolesAr[ir].equals("securityadmin"))
                 {
                     return true;
                 }
             }
        }
        return false;
    }

    public boolean hasRole(String RoleName, Object mod)
    {
/*        if( mod.getGroup() == null ||mod.getGroup().isEmpty() || getGroup() == null || getGroup().isEmpty())
            return false;

        Iterator it = mod.getGroup().iterator();
        while(it.hasNext())
        {
            Group gp = (Group) it.next();
            if(this.hasGroup(gp))
            {
                Iterator itR = gp.getRole().iterator();
                while(itR.hasNext())
                {
                    if(((Role)itR.next()).getId().equals(RoleName))
                        return true;
                }
            }
        }
		*/
        return false;
    }

    public Set getRoles(Object modIn)
    {
	Module mod = (Module)modIn;
        Set roles = new HashSet();
        if (mod.getGroup() == null || mod.getGroup().isEmpty() || getGroup() == null || getGroup().isEmpty())
            return null;

        Iterator it = mod.getGroup().iterator();
        while (it.hasNext())
        {
            Group gp = (Group) it.next();
            if (this.hasGroup(gp))
            {
                Iterator itR = gp.getRole().iterator();
                while (itR.hasNext())
                {
                    Role r = (Role) itR.next();
                    roles.add(r);
                }
            }
        }

		return roles;
    }

    public Set getRoles()
     {
         Set roles = new HashSet();
         if(getGroup() != null && !getGroup().isEmpty())
         {
             Iterator itG = this.getGroup().iterator();
             while(itG.hasNext())
             {
                 Group gp = (Group) itG.next();
                 Iterator itR = gp.getRole().iterator();
                 while (itR.hasNext())
                 {
                     Role r = (Role) itR.next();
                     roles.add(r);
                 }
             }

         }
         return roles;
     }

     public String[] getRoleNames()
     {
         Set roles = getRoles();
         String[] a = null;
         if (roles != null && !roles.isEmpty())
         {
             a = new String[roles.size()];
             Iterator it = roles.iterator();
             int i = 0;
             while (it.hasNext())
             {
                 a[i] = ( (Role) it.next()).getId();
                 i++;
             }
         }

         return a;
     }

     public String[] getGroupsNames()
     {
         Set groups = getGroup();
         String[] a = null;
         if (groups != null && !groups.isEmpty())
         {
             a = new String[groups.size()];
             Iterator it = groups.iterator();
             int i = 0;
             while (it.hasNext())
             {
                 a[i] = ( (Group) it.next()).getId();
                 i++;
             }
         }

         return a;
     }

     public String[] getRoleNames(Object mod)
     {
         if(this.isAdministrator())
             return new String[]{"Administrator"};
         Set roles = getRoles(mod);
         String[] a = null;
         if (roles != null && !roles.isEmpty())
         {
             a = new String[roles.size()];
             Iterator it = roles.iterator();
             int i = 0;
             while (it.hasNext())
             {
                 a[i] = ( (Role) it.next()).getId();
                 i++;
             }
         }

         return a;
     }
    public int[] getIp()
    {
        return ip;
    }

    public boolean isIsInternal()
    {
        return isInternal ;
    }

    public void setIp(String ip)
    {
      if(ip == null || ip.equals("") ||ip.equals("*"))
          return;
      String[] ips = ip.split("\\.");
      for(int i = 0 ; i < ips.length ; i++)
      {
          try{
              if(i == 3 && ips[i].indexOf("/") > 0)
              {
                  String[] ipsSub = ips[i].split("/");
                  if(ipsSub != null && ipsSub.length == 2)
                  {
                      this.ip[3] = Integer.parseInt( ipsSub[0] ) ;
                      this.ip[4] = Integer.parseInt( ipsSub[1] ) ;
                  }
              }
              else
              {
                  if(!ips[i].equals("*"))
                      this.ip[i] = Integer.parseInt( ips[i] ) ;
                  else
                  {
                      for(int x = i ; x < 4 ; x++)
                          this.ip[x] = 999 ;
                  }
              }
          }catch(NumberFormatException ex){}
      }

    }
    public String getIpOldFashion()
    {
        if(this.ip != null && ip[0] != 0)
        {
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < 3 ; i++)
            {
                if(ip[i] != 999)
                {
                    if ( sb.length() > 0 )
                        sb.append( "." ) ;
                    sb.append( ip[i] ) ;
                }
                else
                {
                    if ( sb.length() > 0 )
                        sb.append( "." ) ;

                    sb.append( "*" ) ;
                    return sb.toString();
                }

            }
            return sb.toString();

        }
        return "*";
    }
}
