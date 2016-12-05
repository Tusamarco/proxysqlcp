package net.tc.isma.data.hibernate;

import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.*;
import java.sql.*;
import javax.transaction.Synchronization;

public class HTransaction implements Transaction
{
    Transaction tx;

    HTransaction(Transaction tx)
    {
        this.tx = tx;
    }

    public void commit()
    {
        try
        {
            tx.commit();
        }
        catch (HibernateException ex)
        {
            ex.printStackTrace();
        }
    }

    public void rollback()
    {
        try
        {
            tx.rollback();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean wasRolledBack()
    {
        try
        {
            return ((HTransaction) tx).wasRolledBack();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean wasCommitted()
    {
        try
        {
            return ((HTransaction) tx).wasCommitted();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    public void begin() throws HibernateException {
    }

    public boolean isActive() throws HibernateException {
        return false;
    }

    public void registerSynchronization(Synchronization synchronization) throws
            HibernateException {
    }

    public void setTimeout(int _int) {
    }

	@Override
	public boolean getRollbackOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRollbackOnly() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TransactionStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}
}
