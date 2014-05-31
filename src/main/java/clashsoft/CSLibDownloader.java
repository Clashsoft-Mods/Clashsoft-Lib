package clashsoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class CSLibDownloader
{
	public static File	output	= new File("mods/Clashsoft Lib.jar");
	
	public static void download(String url)
	{
		try
		{
			URL url1 = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(url1.openStream());
			FileOutputStream fos = new FileOutputStream(output);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
		catch (IOException ex)
		{
		}
	}
}
