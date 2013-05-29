package clashsoft.clashsoftapi;

public enum EnumFontColor
{
	BLACK(0, 0, 0, 0),
	BLUE(0, 0, 0, 1),
	GREEN(0, 0, 1, 0),
	CYAN(0, 0, 1, 1),
	RED(0, 1, 0, 0),
	PURPLE(0, 1, 0, 1),
	ORANGE(0, 1, 1, 0),
	LIGHTGRAY(0, 1, 1, 1),
	DARKGRAY(1, 0, 0, 0),
	LIGHTBLUE(1, 0, 0, 1),
	LIGHTGREEN(1, 0, 1, 0),
	LIGHTCYAN(1, 0, 1, 1),
	LIGHTRED(1, 1, 0, 0),
	PINK(1, 1, 0, 1),
	YELLOW(1, 1, 1, 0),
	WHITE(1, 1, 1, 1);

	private int light;
	private int red;
	private int green;
	private int blue;

	private EnumFontColor(int l, int r, int g, int b)
	{
		light = l;
		red = r;
		green = g;
		blue = b;
	}

	public int getLight() { return light; }
	public int getRed() { return red; }
	public int getGreen() { return green; }
	public int getBlue() { return blue; }
}