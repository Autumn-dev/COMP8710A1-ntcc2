import java.util.*;

public class CoinHandler {

    //Holds the balance of coins + quantity
    private HashMap<Coin, Integer> Balance;

    public CoinHandler() {
        this.Balance = new HashMap<>();
        this.init();
    }

    private void init()
    {
        //Setting up amount of each coin in balance
        for (Coin c : Coin.values()) {
            this.Balance.put(c, 0);
        }
    }

    /**
     * Allows the user to add multiple coins
     * @param c Coin type
     * @param amount How many coins are added
     */
    public void addCoins(Coin c, int amount)
    {
        Balance.put(c, Balance.getOrDefault(c, 0) + amount);
    }

    /**
     * Updates the current balance amount to a new balance HashMap
     * @param newBalance a HashMap containing Coins and the quantity.
     */
    public void changeToBalance(HashMap<Coin, Integer> newBalance)
    {
        this.Balance = newBalance;
    }


    /**
     * Prints each coin in the HashMap provided and how many coins are available.
     * Used to make the balances easily readable by the user.
     * @param hashInventory HashMap provided with Coins and Quantity.
     */
    public static void getBalanceHash(HashMap<Coin, Integer> hashInventory) {
        for (HashMap.Entry<Coin, Integer> entry : hashInventory.entrySet()) {
            Coin c = entry.getKey();
            int amount = entry.getValue();
            System.out.println(c + ": " + amount + " coins.");
        }
    }

    /**
     * Prints the current balance stored in a readable format for the user.
     */
    public void getCurrentBalanceHashStr(){
        for(HashMap.Entry<Coin, Integer> entry : this.Balance.entrySet()){
            Coin c = entry.getKey();
            int amount = entry.getValue();
            System.out.println(c + ": " + amount + " coins.");
        }
    }

    /**
     * Returns the current balance HashMap.
     * @return Current Balance HashMap
     */
    public HashMap<Coin, Integer> getCurrentBalanceHash(){
        return this.Balance;
    }

    /**
     * Returns the total sum of money inside the Balance.
     * @return All coins added together as an int.
     */
    public int getBalanceSum() {
        return Balance.entrySet()
                        .stream()
                        .mapToInt(entry -> entry.getValue() * entry.getKey().getValue())
                        .sum();
    }



}
