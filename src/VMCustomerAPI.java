import java.util.HashMap;

public interface VMCustomerAPI {

    void insertCoin(Coin coin);

    int getCurrentBalance();

    Item selectItem(int code) throws PurchaseException;

    String getSelectedItem();

    void getAllItemCodes();

    void requestRefund() throws Exception;

    void requestPurchaseItem() throws PurchaseException;

    HashMap<Integer,Item> getAllItemCodesMap();

    void collect() throws PurchaseException;
}
