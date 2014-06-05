package clashsoft.cslib.io;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * A utility class for everything related to connecting to the internet.
 * 
 * @author Clashsot
 */
public class CSWeb
{
	/**
	 * Checks if the given website is available.
	 * 
	 * @param url
	 *            the URL
	 * @return true, if available
	 */
	public static boolean checkWebsiteAvailable(String url)
	{
		try
		{
			URL url1 = new URL(url);
			
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) url1.openConnection();
			con.setRequestMethod("HEAD");
			int response = con.getResponseCode();
			return response == HttpURLConnection.HTTP_OK;
		}
		catch (Exception ex)
		{
			return false;
		}
	}
	
	/**
	 * Reads the given website or downloads its contents and returns them as a
	 * list of lines.
	 * 
	 * @param url
	 *            the URL
	 * @return the lines
	 */
	public static String[] readWebsite(String url)
	{
		try
		{
			URL url1 = new URL(url);
			HttpURLConnection.setFollowRedirects(true);
			HttpURLConnection con = (HttpURLConnection) url1.openConnection();
			con.setDoOutput(false);
			con.setReadTimeout(20000);
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:16.0) Gecko/20100101 Firefox/20.0");
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			
			BufferedInputStream in = new BufferedInputStream(con.getInputStream());
			StringBuffer buffer = new StringBuffer();
			int read;
			while ((read = in.read()) != -1)
			{
				char c = (char) read;
				buffer.append(c);
			}
			return buffer.toString().split("\n");
		}
		catch (Exception ex)
		{
			return new String[] {};
		}
	}
	
	/**
	 * Downloads a file from the given {@code url} to the given {@link File}
	 * {@code output}.
	 * 
	 * @param url
	 *            the URL
	 * @param output
	 *            the output file
	 * @throws IOException
	 */
	public static void download(String url, File output) throws IOException
	{
		URL url1 = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(url1.openStream());
		FileOutputStream fos = new FileOutputStream(output);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}
}
