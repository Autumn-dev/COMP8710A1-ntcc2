Nathan Cheung
ntcc2

Interfaces:
VMAdminAPI
VMCustomerAPI

Classes:
CoinHandler
ItemInventory
JUnitTests
Main
VendingMachine

The reason I created the Vending machine like this was in order to implement interfaces into the main Class (VendingMachine)
in order to separate the different methods that the Admin and Customer could have access to. The CoinHandler and ItemInventory
are also separate from the VendingMachine class to create private Variables that are not accessible to user but also has standard
methods that are used to manipulate the different items and moneys in the vending machine.