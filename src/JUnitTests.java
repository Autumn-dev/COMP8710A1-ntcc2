import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class JUnitTests {
    @Test
    public void JUnitTest() {
        Assert.assertTrue(true);
    }

    @Test
    public void requestingPurchaseInstantTest() throws PurchaseException
    {
        thrown.expect(PurchaseException.class);
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.requestPurchaseItem();
    }

    @Test
    public void refundingEmptyTest() throws Exception
    {
        thrown.expect(Exception.class);
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.requestRefund();
    }

    @Test
    public void overStockTest() throws Exception
    {
        thrown.expect(Exception.class);
        VendingMachine vendingMachine = new VendingMachine();
        Item item = new Item("testItem", 150);
        vendingMachine.addItem(item, 101);
    }

    @Test
    public void ChangeTest() throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        Item testItem = new Item("testItem", 150);
        vendingMachine.addItem(testItem, 1);
        vendingMachine.addCoin(Coin.FIFTY_PENCE);
        vendingMachine.insertCoin(Coin.TWO_POUND);
        try{
            vendingMachine.selectItem(1);
        } catch(PurchaseException e){
            System.out.println(e);
        }
        try {
            vendingMachine.requestPurchaseItem();
        }
        catch(PurchaseException e){
            System.out.println(e);
        }
        //Will return zero as current balance gets cleared. Requires manual checking.
        Assert.assertEquals(vendingMachine.getCurrentBalance(), 0);
    }

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void changeThrowException() throws PurchaseException, Exception {
        thrown.expect(PurchaseException.class);
        VendingMachine vendingMachine = new VendingMachine();
        Item testItem = new Item("testItem", 150);
        vendingMachine.addItem(testItem, 1);
        vendingMachine.addCoin(Coin.TWO_POUND);
        vendingMachine.insertCoin(Coin.TWO_POUND);
        vendingMachine.selectItem(1);
        vendingMachine.requestPurchaseItem();

    }


    @Test
    public void addingItemTest() throws Exception
    {
        VendingMachine vendingMachine = new VendingMachine();
        Item testItem = new Item("testItem", 150);
        HashMap<Item, Integer> testMap = new HashMap<>();
        testMap.put(testItem, 1);
        vendingMachine.addItem(testItem, 1);
        Assert.assertEquals(vendingMachine.returnItems(), testMap);
        vendingMachine.printItems();

    }

    @Test
    public void addingMultipleItemsTest() throws Exception
    {
        VendingMachine vendingMachine = new VendingMachine();
        HashMap<Item, Integer> testMap = new HashMap<>();
        for(int i = 0 ; i < 10 ; i++)
        {
            Item testItem = new Item("testItem" + i, 150);
            testMap.put(testItem, i+1);
            vendingMachine.addItem(testItem, i+1);
        }
        Assert.assertEquals(vendingMachine.returnItems(), testMap);
        vendingMachine.printItems();
    }

    @Test
    public void addingCoinTest() {
        VendingMachine vendingMachine = new VendingMachine();
        for (Coin c : Coin.values()) {
            vendingMachine.insertCoin(c);
        }

        Assert.assertEquals(vendingMachine.getCurrentBalance(), 386);
    }

    @Test
    public void getSelectedItemTest() throws Exception {
        VendingMachine vendingMachine = new VendingMachine();
        Item testItem = new Item("testItem", 150);
        vendingMachine.addItem(testItem, 1);
        try{
            vendingMachine.selectItem(1);
        } catch(PurchaseException e){}
        Assert.assertEquals(vendingMachine.getSelectedItem(), testItem.toString());
    }

    @Test
    public void printItemCodesTest() throws Exception{
        VendingMachine vendingMachine = new VendingMachine();
        HashMap<Integer, Item> testMap = new HashMap<>();
        for(int i = 0 ; i < 5 ; i++) {
            Item testItem = new Item("testItem" + i, 150);
            testMap.put(i+1 , testItem);
            vendingMachine.addItem(testItem, i + 1);
        }
        Assert.assertEquals(vendingMachine.getAllItemCodesMap(), testMap);

    }

    @Test
    public void selectingItemException() throws PurchaseException, Exception {
        thrown.expect(PurchaseException.class);
        VendingMachine vendingMachine = new VendingMachine();
        Item testItem = new Item("testItem", 150);
        vendingMachine.addItem(testItem, 1);
        vendingMachine.selectItem(2);
    }

    @Test
    public void selectingItemTest() throws PurchaseException, Exception {
        VendingMachine vendingMachine = new VendingMachine();
        Item testItem = new Item("testItem", 150);
        vendingMachine.addItem(testItem, 1);
        Assert.assertEquals(vendingMachine.selectItem(1), testItem);
    }

    @Test
    public void withdrawingCoinsTest() throws Exception
    {
        VendingMachine vendingMachine = new VendingMachine();
        for (Coin c : Coin.values()) {
            vendingMachine.addCoin(c);
        }
        vendingMachine.withdrawCoins();
        Assert.assertEquals(vendingMachine.getVendingBalance(), 0);
    }

    @Test
    public void withdrawEmptyMachineTest() throws Exception
    {
        thrown.expect(Exception.class);
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.withdrawCoins();
    }

    @Test
    public void nullStockTest() throws PurchaseException, Exception {
        thrown.expect(PurchaseException.class);
        VendingMachine vendingMachine = new VendingMachine();
        Item testItem = new Item("testItem", 100);
        vendingMachine.addItem(testItem, 1);
        vendingMachine.insertCoin(Coin.TWO_POUND);
        vendingMachine.addCoin(Coin.ONE_POUND);
        vendingMachine.selectItem(4);
        vendingMachine.requestPurchaseItem();
    }

    @Test
    public void buyingProcessTest() throws PurchaseException, Exception
    {
        VendingMachine vendingMachine = new VendingMachine();
        for (Coin c : Coin.values()) {
            for(int i = 0; i < 5; i++)
            {
                vendingMachine.addCoin(c);
            }
        }
        Item testItem = new Item("KitKat", 150);
        vendingMachine.addItem(testItem, 5);
        vendingMachine.insertCoin(Coin.TWO_POUND);
        vendingMachine.selectItem(1);
        vendingMachine.requestPurchaseItem();

    }

    @Test
    public void buyingProcessNotEnoughMoneyTest() throws PurchaseException, Exception
    {
        thrown.expect(PurchaseException.class);
        VendingMachine vendingMachine = new VendingMachine();
        for (Coin c : Coin.values()) {
            for(int i = 0; i < 5; i++)
            {
                vendingMachine.addCoin(c);
            }
        }
        Item testItem = new Item("testItem", 150);
        vendingMachine.addItem(testItem, 1);
        vendingMachine.insertCoin(Coin.ONE_POUND);
        vendingMachine.selectItem(1);
        vendingMachine.requestPurchaseItem();

    }

}