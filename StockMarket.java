package com.bmouazen.jpmorgan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/*
 * Very simple stock market program
 * @author : Badr MOUAZEN
 */

public class StockMarket {
	public static double calcGeometricMean(ArrayList<Trade> trades) {
		double price = 1;
		for (Trade trade : trades) {
			price = price * trade.getPrice();
		}
		return Math.pow(price, 1.0 / trades.size());

	}

	public static double calcVolumeWeightedStockPrice(ArrayList<Trade> trades) {
		double vWSP = 0;
		int quantity = 0;
		long nowsTime = Calendar.getInstance().getTime().getTime() / 60000;// 60000
																			// for
																			// minutes
		// DelaTime is 15 minutes ago
		long deltaTime = nowsTime - 15;

		for (Trade trade : trades) {
			long diff = (trade.getTradeDate().getTime() / 60000) - nowsTime;
			if ((trade.getTradeDate().getTime() / 60000) - deltaTime > 0) {

				vWSP = vWSP + trade.getPrice() * trade.getQuantity();
				quantity = quantity + trade.getQuantity();

			}
		}

		return vWSP / quantity;

	}

	public static void main(String[] args) throws IOException {

		/*
		 * Stock Par Values
		 */

		ArrayList<Share> shares = new ArrayList<Share>();
		ArrayList<Trade> trades = new ArrayList<Trade>();
		// stocks initialiation
		Share tea = new Share("TEA", "Common", 0, 100);
		Share pop = new Share("POP", "Common", 8, 100);
		Share ale = new Share("ALE", "Common", 23, 100);
		Share gin = new Share("GIN", "Preferred", 8, 2, 100);
		Share joe = new Share("JOE", "Common", 13, 100);

		/*
		 * Stock list filling
		 */
		shares.add(tea);
		shares.add(pop);
		shares.add(ale);
		shares.add(gin);
		shares.add(joe);

		/*
		 * Manually enter the trade values
		 */

		String display = "Stock Symbol | Type      | Last Dividend | Fixed Dividend | Par Value \n";
		System.out.println(display);
		for (Share share : shares) {
			System.out.println(share.toString());
		}
		System.out
				.println("Please enter the trade informations space separated : [Stock symbol] [Buy or Sell] (\"0\" or \"1\") [Quantity] [Trade price] \n");
		System.out.println("Exemple : tea BUY 5 3 \n");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line;
		while ((line = br.readLine()) != null && line.length() != 0) {
			String stockSymbol = "";
			String buyOrSell = "", quantity = "", tradePrice = "";
			String[] lineVector = line.split(" +");

			stockSymbol = lineVector[0];
			boolean existSymbol = false;
			for (Share share : shares) {
				if (stockSymbol.equalsIgnoreCase(share.getShareSymbol())) {
					existSymbol = true;
					break;
				}

			}
			if (!existSymbol)
				throw new IllegalArgumentException(
						"This share doesn't exist, Please consult stock list");

			buyOrSell = lineVector[1];
			if (buyOrSell.equalsIgnoreCase("buy"))
				buyOrSell = "true";
			else if (buyOrSell.equalsIgnoreCase("sell"))
				buyOrSell = "false";
			else
				throw new IllegalArgumentException(
						"Possible values for trade type are : BUY or SELL");
			quantity = lineVector[2];
			tradePrice = lineVector[3];
			Trade trade = new Trade(shares, stockSymbol,
					Boolean.parseBoolean(buyOrSell),
					Integer.parseInt(quantity), Double.parseDouble(tradePrice));
			trade.calcDividendYield();

			/*
			 * Calculate the P/E Ratio
			 */

			trade.calcPERatio();
			System.out.println(trade.toString());
			trades.add(trade);
			/*
			 * Calcul of Geometric Mean
			 */
			double geometricMean = calcGeometricMean(trades);
			System.out.println("geometricMean " + geometricMean);

			/*
			 * calcul of volume Weighted Stock Price (for the 15 last minutes)
			 */

			double volumeWeightedStockPrice = calcVolumeWeightedStockPrice(trades);
			System.out.println("volumeWeightedStockPrice "
					+ volumeWeightedStockPrice);
		}

	}

}
