package clashsoft.cslib.minecraft.update.reader;

import clashsoft.cslib.minecraft.update.updater.IUpdater;


public interface IUpdateReader
{
	public void readFile(IUpdater updater, String[] lines);
}
