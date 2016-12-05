package net.tc.isma.workflows;

import java.util.*;
import net.tc.isma.*;
//import net.tc.isma.auth.security.RoleBean;

/**
 * <p>Title: ISMA</p>
 * <p>Description: Information System Modular Architecture</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author not attributable
 * @version 1.0
 */

public interface Step
{
//    public void setStep(String step) throws IsmaException;
//    public String hasStep() throws IsmaException;

    public SortedMap getStepxobj()throws IsmaException;
    public Map getStepxobj(Long id)throws IsmaException;
//    public Object getObject(Object obj)throws IsmaException;
    public String getApplicationName()throws IsmaException;
    public String getModuleName()throws IsmaException;
//    public String getWorkflowName()throws IsmaException;
    public long getIdWf()throws IsmaException;
    public Set getRoles()throws IsmaException;
    public long getIdStep()throws IsmaException;

    public void resetstepXObj();

}
