package com.bmouazen.jpmorgan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

// The class trade allows the stock, the quantity and the price to be intialised
public class Trade {
	private Share share;
	private int quantity;
	private double price;
	private double dividendYield =0.0;
	private double pERatio;
	private boolean buyOrSell;
	private Date tradeDate;

	public Trade(Share share, boolean buyOrSell, int quantity, double price) {
		this.share = share;
		this.buyOrSell = buyOrSell;
		this.quantity = quantity;
		this.price = price;
		tradeDate = Calendar.getInstance().getTime();

	}

	public Trade(ArrayList<Share> shares, String stockSymbol,
			boolean buyOrSell, int quantity, double price) {
		Iterator<Share> iter = shares.iterator();
		int index = -1;
		while (iter.hasNext()) {
			Share share = iter.next();
			index++;
			if (stockSymbol.equalsIgnoreCase((share.getShareSymbol()))) {
				this.share = shares.get(index);
				this.buyOrSell = buyOrSell;
				this.quantity = quantity;
				this.price = price;
				tradeDate = Calendar.getInstance().getTime();

				break;
			}
		}

	}

	public String toString() {
		String result = "";
		result += "stock symbol : " + share.getShareSymbol() + " \n";
		result += "Buy or Sell : " + buyOrSell + " \n";
		result += "quantity :" + quantity + " \n";
		result += "price : " + price + " \n";
		result += "Dividend Yield : " + dividendYield + " \n";
		result += "P/E Ratio : " + pERatio + " \n";
		result += "tradeDate : " + tradeDate + " \n\n";
		return result;

	}

	public Share getShare() {
		return share;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public double getDividendYield() {
		return dividendYield;
	}

	public double getpERatio() {
		return pERatio;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public double calcDividendYield() {
		if ("Common".equalsIgnoreCase(share.getType())) {
			dividendYield = share.getLastDividend() / price;
		} else if ("Preferred".equalsIgnoreCase(share.getType())) {
			dividendYield = share.getFixedDividend() * share.getParValue()/ price;
		}

		return dividendYield;
	}

	public double calcPERatio() {
		if (dividendYield > 0)
			pERatio = price / dividendYield;
		return pERatio;
	}

}
