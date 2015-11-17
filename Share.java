package com.bmouazen.jpmorgan;
public class Share {

	private String shareSymbol = "";
	private String type = "Common";
	private double lastDividend = 0;
	private double fixedDividend = 0;
	private int parValue = 0;

	// Calucul values


	public String getShareSymbol() {
		return shareSymbol;
	}

	public String getType() {
		return type;
	}

	public double getLastDividend() {
		return lastDividend;
	}

	public double getFixedDividend() {
		return fixedDividend;
	}

	public int getParValue() {
		return parValue;
	}

	// Constructor without Fixed Dividend
	public Share(String shareSymbol, String type, int lastDividend, int parValue) {
		this.shareSymbol = shareSymbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
	}

	// Constructor with Fixed Dividend
	public Share(String shareSymbol, String type, int lastDividend,
			int fixedDividend, int parValue) {
		this(shareSymbol, type, lastDividend, parValue);
		this.fixedDividend = fixedDividend / 100.0;
	}
	public String toString(){
		String result="";
		result += shareSymbol + "              "+type + "     " + lastDividend + "           " + fixedDividend + "               " + parValue + "\n";
		return result;
	}

}
