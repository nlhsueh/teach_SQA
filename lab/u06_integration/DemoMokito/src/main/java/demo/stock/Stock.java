package demo.stock;

//Stock 類別描述一個股票的訊息與數量。
public class Stock {
    private String stockId;
    private String name;
    private int quantity;

    public Stock(String stockId, String name, int quantity){
        this.stockId = stockId;
        this.name = name;
        this.quantity = quantity;
    }

    public String getStockId() {
        return stockId;
    }

    public int getQuantity() {
        return quantity;
    }
}
