import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws Exception, PurchaseException {
        VendingMachine vm = new VendingMachine();

        System.out.println("Adding the items to the vending machine");
        String[] items = {"Water", "Kola Zero", "Bepsi Maxima", "Nars Bar", "Spride", "Kola"};
        int[] prices = {100, 170, 160, 130, 160, 200};
        for(int i = 0; i < prices.length; i++)
        {
            Item item = new Item(items[i], prices[i]);
            vm.addItem(item, 5);
        }

        System.out.println("Adding coins for the vending machine, five of each.");
        for (Coin c : Coin.values()) {
            for(int i = 0; i < 5; i++)
            {
                vm.addCoin(c);
            }
        }

        System.out.println("Customer wants to buy a Kola and puts in a two pound coin.");
        vm.insertCoin(Coin.TWO_POUND);

        System.out.println("Displaying the different item codes for the customer to choose from.");
        vm.getAllItemCodes();

        System.out.println("Selecting and requesting purchase of the item.");
        vm.selectItem(6);
        vm.requestPurchaseItem();

        System.out.println("Customer wants to now buy a water with a two pound coin, expects one pound coin back.");
        vm.insertCoin(Coin.TWO_POUND);
        vm.selectItem(1);
        vm.requestPurchaseItem();

        System.out.println("Customer puts in a fifty pence coin but realises he can't afford anything to requests a refund");
        vm.insertCoin(Coin.FIFTY_PENCE);
        System.out.println("Checking the customers' balance");
        vm.getCurrentBalance();
        System.out.println("Requesting a refund.");
        vm.requestRefund();
    }
}
