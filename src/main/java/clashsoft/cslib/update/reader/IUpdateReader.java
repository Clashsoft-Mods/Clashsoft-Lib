package clashsoft.cslib.update.reader;

import clashsoft.cslib.update.updater.IUpdater;

public interface IUpdateReader
{
	public void readFile(IUpdater updater, String[] lines);
}
