package BingSols;

import exceptions.InsufficientMoneyException;
import exceptions.OutOfStockException;
import exceptions.ProductNotFoundException;
import tasks.VendingMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachineBing implements VendingMachine {

    private Map<String, Product> productStock;
    private double activeBalance;
    private double inactiveBalance;

    public VendingMachineBing() {
        this.productStock = new HashMap<>();
        this.activeBalance = 0.0;
        this.inactiveBalance = 0.0;
    }

    public static class ProductImpl implements VendingMachine.Product {

        private String name;
        private double price;
        private int stock;

        public ProductImpl(String name, double price, int stock) {
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public double getPrice() {
            return price;
        }

        @Override
        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public int getStock() {
            return stock;
        }

        @Override
        public void setStock(int stock) {
            this.stock = stock;
        }
    }


    @Override
    public List<Product> getProductStock() {
        // Return a copy of the product stock (to prevent external manipulation)
        return new ArrayList<>(productStock.values());
    }

    @Override
    public double getActiveBalance() {
        return activeBalance;
    }

    @Override
    public double getInactiveBalance() {
        return inactiveBalance;
    }

    @Override
    public double getProductPrice(String name) throws ProductNotFoundException {
        if (!productStock.containsKey(name)) {
            throw new ProductNotFoundException("Product not found: " + name);
        }
        return productStock.get(name).getPrice();
    }

    @Override
    public int getStockOfProduct(String name) throws ProductNotFoundException {
        if (!productStock.containsKey(name)) {
            throw new ProductNotFoundException("Product not found: " + name);
        }
        return productStock.get(name).getStock();
    }

    @Override
    public int restockProduct(String name, int amount) throws ProductNotFoundException {
        if (!productStock.containsKey(name)) {
            throw new ProductNotFoundException("Product not found: " + name);
        }
        Product product = productStock.get(name);
        int currentStock = product.getStock();
        product.setStock(currentStock + amount);
        return product.getStock();
    }

    @Override
    public void addProducts(List<Product> products) {
        for (Product product : products) {
            productStock.put(product.getName(), product);
        }
    }

    @Override
    public void removeProduct(String name) throws ProductNotFoundException {
        if (!productStock.containsKey(name)) {
            throw new ProductNotFoundException("Product not found: " + name);
        }
        productStock.remove(name);
    }

    @Override
    public void changeProductPrice(String name, double newPrice) throws ProductNotFoundException {
        if (!productStock.containsKey(name)) {
            throw new ProductNotFoundException("Product not found: " + name);
        }
        productStock.get(name).setPrice(newPrice);
    }

    @Override
    public void insertMoney(double amount) {
        activeBalance += amount;
    }

    @Override
    public double getBackMoney() {
        double returnedBalance = activeBalance;
        activeBalance = 0.0;
        return returnedBalance;
    }

    @Override
    public double purchaseProduct(String name) throws OutOfStockException, InsufficientMoneyException, ProductNotFoundException {
        if (!productStock.containsKey(name)) {
            throw new ProductNotFoundException("Product not found: " + name);
        }
        Product product = productStock.get(name);
        if (product.getStock() == 0) {
            throw new OutOfStockException("Product out of stock: " + name);
        }
        double price = product.getPrice();
        if (activeBalance < price) {
            throw new InsufficientMoneyException("Insufficient balance to purchase: " + name);
        }
        // Update balances
        activeBalance -= price;
        inactiveBalance += price;
        // Reduce stock
        product.setStock(product.getStock() - 1);
        // Calculate and return change (if any)
        return activeBalance > price ? activeBalance - price : 0.0;
    }
}
