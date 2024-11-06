import java.util.*;



public class ItemInventory {
    public HashMap<Item, Integer> Inventory;
    public HashMap<Integer, Item> ItemCode;

    public ItemInventory() {
        this.Inventory = new HashMap<>();
        this.ItemCode = new HashMap<>();
    }


    /**
     * Adding an item to the vending machine.
     * A second HashMap is involved so that an Item Code is assigned to the name, that way It is easier to track
     * which item is being selected and purchased.
     *
     * @param item An Item object that holds the name and price.
     * @param quantity The amount of the item you want to add.
     */
    public void addItem(Item item, int quantity) {
        int code = 1;
        for(int i = 0; i < this.ItemCode.size(); i++){
            if(this.ItemCode.containsKey(code)) {
                code++;
            }
        }
        this.Inventory.put(item, quantity);
        this.ItemCode.put(code, item);
    }

    /**
     * Removes an Item from the vending machine.
     * @param item The Item object to remove.
     */
    public void removeItem(Item item) {
        this.Inventory.remove(item);
    }

    /**
     * Alternate parameters to remove a certain quantity of items.
     * @param item Which Item do you want to remove from.
     * @param quantity how many of the items to remove.
     */
    public void removeItem(Item item, int quantity) {
        this.Inventory.put(item, this.Inventory.get(item) - quantity);
    }

    /**
     * Return the quantity of an item as an int.
     * @param item Item object to return the quantity of.
     * @return the quantity of the @param as an int.
     */
    public int getQuantity(Item item) {
        return this.Inventory.get(item);
    }

    /**
     * Returns the inventory as a HashMap.
     * @return The inventory as a HashMap
     */
    public HashMap<Item, Integer> getInventory() {
        return this.Inventory;
    }

    /**
     * Prints the inventory and it's quantity in a readable format.
     */
    public void printInventory() {
        for (Map.Entry<Item, Integer> entry : Inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
