package Llama3Sols;

import exceptions.InsufficientMoneyException;
import exceptions.OutOfStockException;
import exceptions.ProductNotFoundException;
import tasks.VendingMachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineLlama3 implements VendingMachine {

    public static class ProductImpl implements Product {
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

    private List<Product> productStock;
    private double activeBalance;
    private double inactiveBalance;

    public VendingMachineLlama3() {
        productStock = new ArrayList<>();
        activeBalance = 0;
        inactiveBalance = 0;
    }

    @Override
    public List<Product> getProductStock() {
        return new ArrayList<>(productStock);
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
        for (Product product : productStock) {
            if (product.getName().equals(name)) {
                return product.getPrice();
            }
        }
        throw new ProductNotFoundException("Product not found: " + name);
    }

    @Override
    public int getStockOfProduct(String name) throws ProductNotFoundException {
        for (Product product : productStock) {
            if (product.getName().equals(name)) {
                return product.getStock();
            }
        }
        throw new ProductNotFoundException("Product not found: " + name);
    }

    @Override
    public int restockProduct(String name, int amount) throws ProductNotFoundException {
        for (Product product : productStock) {
            if (product.getName().equals(name)) {
                product.setStock(product.getStock() + amount);
                return product.getStock();
            }
        }
        throw new ProductNotFoundException("Product not found: " + name);
    }

    @Override
    public void addProducts(List<Product> products) {
        productStock.addAll(products);
    }

    @Override
    public void removeProduct(String name) throws ProductNotFoundException {
        for (Product product : productStock) {
            if (product.getName().equals(name)) {
                productStock.remove(product);
                return;
            }
        }
        throw new ProductNotFoundException("Product not found: " + name);
    }

    @Override
    public void changeProductPrice(String name, double newPrice) throws ProductNotFoundException {
        for (Product product : productStock) {
            if (product.getName().equals(name)) {
                product.setPrice(newPrice);
                return;
            }
        }
        throw new ProductNotFoundException("Product not found: " + name);
    }

    @Override
    public void insertMoney(double amount) {
        activeBalance += amount;
    }

    @Override
    public double getBackMoney() {
        double balance = activeBalance;
        activeBalance = 0;
        return balance;
    }

    @Override
    public double purchaseProduct(String name) throws OutOfStockException, InsufficientMoneyException, ProductNotFoundException {
        Product product = getProductByName(name);
        if (product.getStock() == 0) {
            throw new OutOfStockException("Product out of stock: " + name);
        }
        if (activeBalance < product.getPrice()) {
            throw new InsufficientMoneyException("Insufficient balance to purchase: " + name);
        }
        product.setStock(product.getStock() - 1);
        inactiveBalance += product.getPrice();
        activeBalance -= product.getPrice();
        return activeBalance;
    }

    private Product getProductByName(String name) throws ProductNotFoundException {
        for (Product product : productStock) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new ProductNotFoundException("Product not found: " + name);
    }
}