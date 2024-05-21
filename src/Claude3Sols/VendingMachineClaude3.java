package Claude3Sols;

import exceptions.InsufficientMoneyException;
import exceptions.OutOfStockException;
import exceptions.ProductNotFoundException;
import tasks.VendingMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendingMachineClaude3 implements VendingMachine {

    private List<Product> productStock;
    private double activeBalance;
    private double inactiveBalance;

    public VendingMachineClaude3() {
        this.productStock = new ArrayList<>();
        this.activeBalance = 0.0;
        this.inactiveBalance = 0.0;
    }

    @Override
    public List<Product> getProductStock() {
        return Collections.unmodifiableList(this.productStock);
    }

    @Override
    public double getActiveBalance() {
        return this.activeBalance;
    }

    @Override
    public double getInactiveBalance() {
        return this.inactiveBalance;
    }

    @Override
    public double getProductPrice(String name) throws ProductNotFoundException {
        Product product = getProductByName(name);
        return product.getPrice();
    }

    @Override
    public int getStockOfProduct(String name) throws ProductNotFoundException {
        Product product = getProductByName(name);
        return product.getStock();
    }

    @Override
    public int restockProduct(String name, int amount) throws ProductNotFoundException {
        Product product = getProductByName(name);
        product.setStock(product.getStock() + amount);
        return product.getStock();
    }

    @Override
    public void addProducts(List<Product> products) {
        this.productStock.addAll(products);
    }

    @Override
    public void removeProduct(String name) throws ProductNotFoundException {
        Product product = getProductByName(name);
        this.productStock.remove(product);
    }

    @Override
    public void changeProductPrice(String name, double newPrice) throws ProductNotFoundException {
        Product product = getProductByName(name);
        product.setPrice(newPrice);
    }

    @Override
    public void insertMoney(double amount) {
        this.activeBalance += amount;
    }

    @Override
    public double getBackMoney() {
        double refund = this.activeBalance;
        this.activeBalance = 0.0;
        return refund;
    }

    @Override
    public double purchaseProduct(String name) throws OutOfStockException, InsufficientMoneyException, ProductNotFoundException {
        Product product = getProductByName(name);

        if (product.getStock() <= 0) {
            throw new OutOfStockException("Product " + name + " is out of stock.");
        }

        if (this.activeBalance < product.getPrice()) {
            throw new InsufficientMoneyException("Insufficient money to purchase " + name + ".");
        }

        this.activeBalance -= product.getPrice();
        this.inactiveBalance += product.getPrice();
        product.setStock(product.getStock() - 1);

        double change = this.activeBalance;
        this.activeBalance = 0.0;
        return change;
    }

    private Product getProductByName(String name) throws ProductNotFoundException {
        for (Product product : this.productStock) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new ProductNotFoundException("Product " + name + " not found.");
    }

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
            return this.name;
        }


        @Override
        public double getPrice() {
            return this.price;
        }

        @Override
        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public int getStock() {
            return this.stock;
        }

        @Override
        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}