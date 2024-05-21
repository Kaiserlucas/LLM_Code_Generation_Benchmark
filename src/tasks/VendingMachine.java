package tasks;

import exceptions.InsufficientMoneyException;
import exceptions.OutOfStockException;
import exceptions.ProductNotFoundException;

import java.util.List;

public interface VendingMachine {

    public interface Product{

        //Product object that has a name, a price and an amount of available stock
        //Should be static and have a sensible constructor

        public String getName();

        public double getPrice();

        public void setPrice(double price);

        public int getStock();

        public void setStock(int stock);

    }

    //Should return a copy of the list, such that the internal list cannot be manipulated from outside
    public List<Product> getProductStock();

    //Active Balance is the amount the user has put in but not used to make a purchase yet
    public double getActiveBalance();

    //Inactive Balance is the amount of money in the machine, that is not available to the user to make purchases with
    //
    public double getInactiveBalance();

    //Gets the price and stock amounts of specific products
    public double getProductPrice(String name) throws ProductNotFoundException;
    public int getStockOfProduct(String name) throws ProductNotFoundException;

    //Restocks the specified item by the specified amount. Return the new amount in stock
    public int restockProduct(String name, int amount) throws ProductNotFoundException;

    //Adds new products to the machine
    public void addProducts(List<Product> products);

    //Removes all instances of this product from the vending machine
    public void removeProduct(String name) throws ProductNotFoundException;

    //Changes the product's price to the new value
    public void changeProductPrice(String name, double newPrice) throws ProductNotFoundException;

    //Adds money to the activeBalance
    public void insertMoney(double amount);

    //Returns the activeBalance to the user
    public double getBackMoney();

    //Purchases an Item from the machine, reducing the stock and adding money to the inactiveBalance
    //If user pays more than necessary, return the change as the return parameter. Else return 0
    //Then reduce the activeBalance to 0
    public double purchaseProduct(String name) throws OutOfStockException, InsufficientMoneyException, ProductNotFoundException;

}
