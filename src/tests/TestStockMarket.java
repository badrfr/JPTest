package tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

import com.bmouazen.jpmorgan.Share;
import com.bmouazen.jpmorgan.StockMarket;
import com.bmouazen.jpmorgan.Trade;

public class TestStockMarket {

	@Test
	public void testMain() {
		boolean buy = true;
		boolean sell = false;

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

		Trade trade1 = new Trade(tea, buy, 1, 23.5);
		Trade trade2 = new Trade(tea, sell, 2, 23.8);
		Trade trade3 = new Trade(gin, buy, 3, 59);
		Trade trade4 = new Trade(gin, sell, 4, 59.34);
		Trade trade5 = new Trade(joe, buy, 5, 100);
		Trade trade6 = new Trade(pop, buy, 6, 40);
		Trade trade7 = new Trade(ale, sell, 7, 80.7);

		/*
		 * Dividend Yield
		 */
		assertEquals(0.0, trade1.calcDividendYield(), 0.001);
		assertEquals(0.0, trade2.calcDividendYield(), 0.001);
		assertEquals(0.033898305, trade3.calcDividendYield(), 0.001);
		assertEquals(0.033704078, trade4.calcDividendYield(), 0.001);
		assertEquals(0.13, trade5.calcDividendYield(), 0.001);
		assertEquals(0.2, trade6.calcDividendYield(), 0.001);
		assertEquals(0.285006196, trade7.calcDividendYield(), 0.001);

		/*
		 * test PE Ratio
		 */
		assertEquals(0.0, trade1.calcPERatio(), 0.001);
		assertEquals(0.0, trade2.calcPERatio(), 0.001);
		assertEquals(1740.5, trade3.calcPERatio(), 0.001);
		assertEquals(1760.618, trade4.calcPERatio(), 0.001);
		assertEquals(769.2308, trade5.calcPERatio(), 0.001);
		assertEquals(200, trade6.calcPERatio(), 0.001);
		assertEquals(283.1517, trade7.calcPERatio(), 0.001);

		/*
		 * Calcul of Geometric Mean
		 */
		trades.add(trade1);
		trades.add(trade2);
		trades.add(trade3);
		trades.add(trade4);
		trades.add(trade5);
		trades.add(trade6);
		trades.add(trade7);

		double geometricMean = StockMarket.calcGeometricMean(trades);

		/*
		 * testCalcGeometricMean
		 */
		assertEquals(48.5093, geometricMean, 0.001);

		/*
		 * calcul of volume Weighted Stock Price (for the 15 last minutes)
		 */

		double volumeWeightedStockPrice = StockMarket
				.calcVolumeWeightedStockPrice(trades);
		assertEquals(63.9414, volumeWeightedStockPrice, 0.001);

		/*
		 * t + 7 minutes
		 */
		timeAdd(7);
		Trade trade8 = new Trade(tea, buy, 12, 23);
		Trade trade9 = new Trade(gin, sell, 120, 58);

		assertEquals(0.0, trade8.calcDividendYield(), 0.001);
		assertEquals(0.034482759, trade9.calcDividendYield(), 0.001);
		assertEquals(0.0, trade8.calcPERatio(), 0.001);
		assertEquals(1682, trade9.calcPERatio(), 0.001);
		trades.add(trade8);
		assertEquals(44.1889, StockMarket.calcGeometricMean(trades), 0.001);
		assertEquals(51.659, StockMarket.calcVolumeWeightedStockPrice(trades),
				0.001);
		trades.add(trade9);
		assertEquals(45.5446, StockMarket.calcGeometricMean(trades), 0.001);
		assertEquals(56.41475,
				StockMarket.calcVolumeWeightedStockPrice(trades), 0.001);

		/*
		 * t + 15 minutes
		 */
		timeAdd(15);
		Trade trade10 = new Trade(tea, buy, 3, 22);
		Trade trade11 = new Trade(gin, sell, 5, 57);
		assertEquals(0.0, trade10.calcDividendYield(), 0.001);
		assertEquals(0.035087719, trade11.calcDividendYield(), 0.001);
		assertEquals(0, trade10.calcPERatio(), 0.001);
		assertEquals(1624.5, trade11.calcPERatio(), 0.001);
		trades.add(trade10);
		assertEquals(42.3482, StockMarket.calcGeometricMean(trades), 0.001);
		// assertEquals(22, StockMarket.calcVolumeWeightedStockPrice(trades),
		// 0.001);
		trades.add(trade11);
		assertEquals(43.5077354, StockMarket.calcGeometricMean(trades), 0.001);
		// assertEquals(43.875,
		// StockMarket.calcVolumeWeightedStockPrice(trades), 0.001);

	}

	public void timeAdd(int minutes) {
		Calendar cal = Calendar.getInstance();
		Calendar.getInstance().add(Calendar.MINUTE, minutes);
		// cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		cal.add(Calendar.MINUTE, minutes); // adds one hour

	}
}
