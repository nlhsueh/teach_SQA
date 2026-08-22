package demo.stock;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class MarketTest {
    Market market;
    StockService stockService;

    @BeforeEach
    public void setUp(){
        //建立受測物件
        market = new Market();

        //建立股票服務的 mock 物件
        stockService = mock(StockService.class);

        //把股票服務加入 market 中
        market.setStockService(stockService);
    }

    @Test
    public void testMarketValue(){
        //建立一些測試資料（股票）
        List<Stock> stocks = new ArrayList<>();
        Stock tsmc = new Stock("2330","TSMC", 10);
        Stock ETF0050 = new Stock("0050","0050",100);
        stocks.add(tsmc);
        stocks.add(ETF0050);

        //把股票加入 market
        market.setStocks(stocks);

        //模擬股票服務的行為，回傳假設的價格
        //這個動作叫 stub（佈樁）
        when(stockService.getPrice(tsmc)).thenReturn(1035.0);
        when(stockService.getPrice(ETF0050)).thenReturn(195.0);

        double marketValue = market.getMarketValue();
        assertEquals(29850, marketValue);
    }
}
