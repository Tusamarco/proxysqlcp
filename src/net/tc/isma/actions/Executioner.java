package net.tc.isma.actions;

public interface Executioner
{
	public void execute();
	public void runChain( ActionChain actionChain );
}
