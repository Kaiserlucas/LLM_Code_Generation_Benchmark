package ChatGPTSols;

import exceptions.InsufficientMoneyException;
import exceptions.OutOfStockException;
import exceptions.ProductNotFoundException;
import tasks.VendingMachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineGPT implements VendingMachine {

    private List<Product> productStock;
    private double activeBalance;
    private double inactiveBalance;

    public VendingMachineGPT() {
        this.productStock = new ArrayList<>();
        this.activeBalance = 0;
        this.inactiveBalance = 0;
    }

    @Override
    public List<Product> getProductStock() {
        // Returning a copy of the product stock list
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
                int newStock = product.getStock() + amount;
                product.setStock(newStock);
                return newStock;
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
        productStock.removeIf(product -> product.getName().equals(name));
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
        double backMoney = activeBalance;
        activeBalance = 0;
        return backMoney;
    }

    @Override
    public double purchaseProduct(String name) throws OutOfStockException, InsufficientMoneyException, ProductNotFoundException {
        Product productToPurchase = null;
        for (Product product : productStock) {
            if (product.getName().equals(name)) {
                productToPurchase = product;
                break;
            }
        }

        if (productToPurchase == null) {
            throw new ProductNotFoundException("Product not found: " + name);
        }

        if (productToPurchase.getStock() == 0) {
            throw new OutOfStockException("Product out of stock: " + name);
        }

        if (activeBalance < productToPurchase.getPrice()) {
            throw new InsufficientMoneyException("Insufficient money to purchase: " + name);
        }

        // Transaction successful
        inactiveBalance += productToPurchase.getPrice();
        activeBalance -= productToPurchase.getPrice();
        productToPurchase.setStock(productToPurchase.getStock() - 1);

        // Return the change if any
        return activeBalance > 0 ? getBackMoney() : 0;
    }

    // Product implementation
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
}
