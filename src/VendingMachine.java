import java.util.*;

public class VendingMachine implements VMAdminAPI, VMCustomerAPI {

    private final CoinHandler customerBalance;
    private final CoinHandler vendingBalance;
    private final ItemInventory itemInventory;
    private Item selectedItem;;
    private States state;

    public VendingMachine() {
        this.customerBalance = new CoinHandler();
        this.vendingBalance = new CoinHandler();
        this.itemInventory = new ItemInventory();
        this.state = States.IDLE;
    }

    /**
     *  Admin Methods
     */

    /**
     * Returns the balance total as an int.
     * @return Returns the balance total as an int.
     */
    @Override
    public int getVendingBalance()
    {
        return vendingBalance.getBalanceSum();
    }

    /**
     * Withdraws the coins from the vending machine
     * @return How many coins are withdrawn as a HashMap
     * @throws Exception thrown when there is no money to withdraw.
     */
    @Override
    public HashMap<Coin,Integer> withdrawCoins() throws Exception{
        boolean notEmpty = false;
        for(int i = 0; i < Coin.values().length; i++) {
            if(vendingBalance.getCurrentBalanceHash().get(Coin.values()[i]) == 0) {
            }else {
                vendingBalance.getCurrentBalanceHash().put(Coin.values()[i], 0);
                notEmpty = true;
            }
            if(!notEmpty)
            {
                throw new Exception("No money in machine.");
            }
        }
        return vendingBalance.getCurrentBalanceHash();
    }

    /**
     * Prints out the inventory of added items inside the vending machine
     */
    @Override
    public void printItems(){
        this.itemInventory.printInventory();
    }

    /**
     * Returns the inventory of added items inside the vending machine
     * @return a HashMap containing all added Items and the amount.
     */
    @Override
    public HashMap<Item, Integer> returnItems() {

        return this.itemInventory.getInventory();
    }

    /**
     * Add coins to vending machine
     * @param coin What coin to add to the vending machine balance
     */
    @Override
    public void addCoin(Coin coin) {
        vendingBalance.addCoins(coin, 1);
    }

    /**
     * Add multiple of an item to the vending machine.
     * @param item What item object to add.
     * @param amount How many to add
     * @throws Exception Amount is either too large (Over 99) or zero/negative.
     */
    @Override
    public void addItem(Item item, int amount) throws Exception {
        if(amount > 0 && amount < 99) {
            itemInventory.addItem(item, amount);
        }else if(amount > 99)
        {
            throw new Exception("Amount is too large, must be below 99.");
        }
        else { throw new Exception("Invalid amount"); }
    }

    /**
     *  Customer Methods
     */

    /**
     *  Inserting a coin for the customer balance.
     * @param coin Coin object to add to balance.
     */
    @Override
    public void insertCoin(Coin coin) {
        customerBalance.addCoins(coin, 1);
    }

    /**
     * Returns the current customer balance as a sum and prints it out for readability.
     * @return the sum of the customer balance.
     */
    @Override
    public int getCurrentBalance() {
        int bal = customerBalance.getBalanceSum();
        System.out.println(bal);
        return bal;
    }

    /**
     * Selects which item to purchase for later.
     * @param code The code for the item that the user wants to select.
     * @return Return the Item using the code.
     * @throws PurchaseException An invalid code is punched in.
     */
    @Override
    public Item selectItem(int code) throws PurchaseException {
        if(code <= 0 || code > itemInventory.ItemCode.size())
        {
            throw new PurchaseException("Invalid code");
        }else {
            Item itemCode = itemInventory.ItemCode.get(Integer.valueOf(code));
            for(Map.Entry<Item, Integer> entry : itemInventory.Inventory.entrySet())
            {
                if(itemCode.equals(entry.getKey()))
                {
                    this.selectedItem = entry.getKey();
                }
            }
        }
        return this.selectedItem;
    }

    /**
     * Gets the currently selected item
     *
     * @return the currently selected, otherwise return an Error string
     */
    @Override
    public String getSelectedItem() {
        for(Map.Entry<Item, Integer> entry : itemInventory.Inventory.entrySet()) {
            if(entry.getKey().equals(this.selectedItem)){
                return entry.getKey().toString();
            }
        }
        return "Error";
    }

    /**
     * Puts the vending machine in an refund state, checks if there is money inserted before moving onto refund.
     * @throws Exception thrown when there is no money in the machine to refund.
     */
    @Override
    public void requestRefund() throws Exception {
        this.state = States.REFUND;
        boolean notEmpty = false;
        for (int i = 0; i < Coin.values().length; i++) {
            if(vendingBalance.getCurrentBalanceHash().get(Coin.values()[i]) != 0) {
                notEmpty = true;
            }
            if (!notEmpty) {
                this.state = States.IDLE;
                throw new Exception("No money inserted.");
            }
        }
        collect();
    }

    /**
     * Prints out all item codes and their corresponding item name in a readable format.
     */
    @Override
    public void getAllItemCodes(){
        for(Map.Entry<Integer, Item> entry : itemInventory.ItemCode.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    /**
     * Returns the ItemCode hashmap.
     * @return Returns the ItemCode HashMap.
     */
    @Override
    public HashMap<Integer,Item> getAllItemCodesMap()
    {
        return itemInventory.ItemCode;
    }

    /**
     * Puts the vending machine in a ready to dispense state
     * @throws PurchaseException thrown if the user has not selected an item.
     */
    @Override
    public void requestPurchaseItem() throws PurchaseException {
        if(this.selectedItem == null)
        {
            throw new PurchaseException("No item selected");
        }
        this.state = States.DISPENSE;
        collect();
    }

    /**
     * A switch case method that checks through the different states that the vending machine could be in
     * If the Machine is IDLE, it will break and tell the user that there are no coins deposited.
     * If the Machine is in a SELECTION or REFUND state, it will refund the customer.
     * Finally, if the user is in a DISPENSE state it will calculate the amount of change to give back, dispense the item
     * and clear out the Customers current balance as they would have completed the transaction.
     * The vending machine also checks to see if the item stock will be empty after purchase to remove the empty item.
     * @throws PurchaseException Thrown if:
     * The customer has not put in enough money.
     * There is an error in the stock somehow.
     * The machine does not have enough change to distribute.
     */
    @Override
    public void collect() throws PurchaseException {
        switch(this.state)
        {
            case States.IDLE: {System.out.println("No coins deposited."); break;}
            case States.SELECTION:
            case States.REFUND: { System.out.println("Refunding deposited coin(s)."); customerBalance.getCurrentBalanceHashStr(); break;}
            case States.DISPENSE:{
                if(customerBalance.getBalanceSum() < this.selectedItem.price())
                {
                    throw new PurchaseException("Insufficient funds.");
                }
                System.out.println("Dispensing purchased item: " + this.selectedItem.name());
                if(itemInventory.Inventory.get(this.selectedItem) == 0 || itemInventory.Inventory.get(this.selectedItem) == null)
                {
                    throw new PurchaseException("Stock error.");
                }
                itemInventory.Inventory.put(this.selectedItem, itemInventory.Inventory.get(this.selectedItem) - 1);
                if(itemInventory.Inventory.get(this.selectedItem) == 0)
                {
                    itemInventory.removeItem(this.selectedItem);
                    Integer toRemove = 0;
                    for(Map.Entry<Integer, Item> entry : itemInventory.ItemCode.entrySet())
                    {
                        if(entry.getValue().equals(this.selectedItem)){
                            toRemove = entry.getKey();
                        }
                    }
                    itemInventory.ItemCode.remove(toRemove);
                }
                System.out.println("Dispensing change.");
                int currentPrice = this.selectedItem.price();
                int Change = customerBalance.getBalanceSum() - currentPrice;
                int twopounds = Change / 200;
                int remainder = Change % 200;
                int pound = remainder / 100;
                remainder = remainder % 100;
                int fifty = remainder / 50;
                remainder = remainder % 50;
                int twenty = remainder / 20;
                remainder = remainder % 20;
                int ten = remainder / 10;
                remainder = remainder % 10;
                int five = remainder / 5;
                remainder = remainder % 5;
                int penny = remainder;
                int [] coinArray = {twopounds,pound,fifty,twenty,ten,five,penny};
                HashMap<Coin, Integer> ChangeHash = new HashMap<>();
                HashMap<Coin, Integer> customerBalBackup = customerBalance.getCurrentBalanceHash();
                for(int i = 0; i < Coin.values().length; i++)
                {
                    ChangeHash.put(Coin.values()[i],coinArray[i]);
                    if(vendingBalance.getCurrentBalanceHash().get(Coin.values()[i]) < coinArray[i])
                    {
                        this.state = States.IDLE;
                        for(int j = 0; j < coinArray.length; j++) {
                            customerBalance.getCurrentBalanceHash().put(Coin.values()[j], customerBalBackup.get(Coin.values()[j]));
                        }
                        throw new PurchaseException("Missing change in machine");
                    }else {
                        vendingBalance.getCurrentBalanceHash().put(Coin.values()[i],
                                vendingBalance.getCurrentBalanceHash().get(Coin.values()[i]) - coinArray[i]);
                        customerBalance.changeToBalance(ChangeHash);
                    }
                }
                this.state = States.IDLE;
                CoinHandler.getBalanceHash(ChangeHash);

                //Cleaning out how much the vending machine thinks the Customer has put inside
                for(int i = 0; i < Coin.values().length; i++) {
                        customerBalance.getCurrentBalanceHash().put(Coin.values()[i], 0);
                    }
            }

        }
    }
}
