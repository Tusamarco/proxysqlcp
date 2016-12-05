package net.tc.isma;

public class IsmaException extends Exception
{
	public IsmaException(String message, Throwable cause)
	{
		super(message, cause);

	}

	public IsmaException(String message, Exception cause)
	{
		super(message, cause);
	}

	public IsmaException(Exception e)
	{
		super(e);
	}

	public IsmaException(String message)
	{
		super(message);
	}
}
