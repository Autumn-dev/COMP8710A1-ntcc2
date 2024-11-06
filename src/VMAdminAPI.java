import java.util.HashMap;

public interface VMAdminAPI {

    HashMap<Coin, Integer> withdrawCoins() throws Exception;

    void addCoin(Coin coin);

    void addItem(Item item, int amount) throws Exception;

    HashMap<Item, Integer> returnItems();

    int getVendingBalance();

    void printItems();

}
