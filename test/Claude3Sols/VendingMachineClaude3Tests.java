package Claude3Sols;

import exceptions.InsufficientMoneyException;
import exceptions.OutOfStockException;
import exceptions.ProductNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import tasks.VendingMachine;

import java.util.LinkedList;
import java.util.List;

public class VendingMachineClaude3Tests {

    //Helper functions that access the concrete implementation

    private VendingMachine getBaseVendingMachine() {
        VendingMachine machine = new VendingMachineClaude3();
        machine.addProducts(getBaseProducts());
        return machine;
    }

    private List<VendingMachine.Product> getBaseProducts() {
        List<VendingMachine.Product> baseProducts = new LinkedList();
        VendingMachine.Product product1 = new VendingMachineClaude3.ProductImpl("Water", 0.99, 3);
        VendingMachine.Product product2 = new VendingMachineClaude3.ProductImpl("Cookie", 2.50, 1);
        VendingMachine.Product product3 = new VendingMachineClaude3.ProductImpl("Bubblegum", 1.99, 5);

        baseProducts.add(product1);
        baseProducts.add(product2);
        baseProducts.add(product3);

        return baseProducts;
    }

    private List<VendingMachine.Product> getCakeProduct() {
        List<VendingMachine.Product> cakeList = new LinkedList();
        VendingMachine.Product cake = new VendingMachineClaude3.ProductImpl("Cake", 3.99, 1);
        cakeList.add(cake);

        return cakeList;
    }

    //All code starting from here is generic and only references the Interface

    @Test
    public void addActiveBalanceTest() {
        VendingMachine machine = getBaseVendingMachine();
        machine.insertMoney(2.89);

        assert(machine.getActiveBalance() == 2.89);

        machine.insertMoney(0.61);

        assert(machine.getActiveBalance() == 3.50);
    }

    @Test
    public void addProductsTest() {
        VendingMachine machine = getBaseVendingMachine();
        machine.addProducts(getCakeProduct());

        List<VendingMachine.Product> productList = machine.getProductStock();

        assert(productList.size() == 4);

        boolean containsWater = false;
        boolean containsCookie = false;
        boolean containsBubblegum = false;
        boolean containsCake = false;

        for (VendingMachine.Product product : productList) {
            switch(product.getName()) {
                case "Water":
                    containsWater = true;
                    break;
                case "Cookie":
                    containsCookie = true;
                    break;
                case "Bubblegum":
                    containsBubblegum = true;
                    break;
                case "Cake":
                    containsCake = true;
                    break;
            }
        }

        assert(containsWater);
        assert(containsCookie);
        assert(containsBubblegum);
        assert(containsCake);

    }

    @Test
    public void vendingMachinePurchaseTest1() throws InsufficientMoneyException, ProductNotFoundException, OutOfStockException {
        VendingMachine machine = getBaseVendingMachine();

        machine.insertMoney(3);

        //Check if the returned change is correct
        assert(machine.purchaseProduct("Cookie") == 0.5);

    }

    @Test
    public void vendingMachinePurchaseTest2() throws InsufficientMoneyException, ProductNotFoundException, OutOfStockException {
        VendingMachine machine = getBaseVendingMachine();
        machine.insertMoney(3);
        machine.purchaseProduct("Cookie");

        //Check if activeBalance gets correctly reset
        assert(machine.getActiveBalance() == 0);

    }

    @Test
    public void getProductPriceTest() throws ProductNotFoundException {
        VendingMachine machine = getBaseVendingMachine();

        assert(machine.getProductPrice("Water") == 0.99);
        assert(machine.getProductPrice("Cookie") == 2.5);
        assert(machine.getProductPrice("Bubblegum") == 1.99);
    }

    @Test
    public void getProductStockTest() throws ProductNotFoundException {
        VendingMachine machine = getBaseVendingMachine();

        assert(machine.getStockOfProduct("Water") == 3);
        assert(machine.getStockOfProduct("Cookie") == 1);
        assert(machine.getStockOfProduct("Bubblegum") == 5);
    }

    @Test
    public void getBackMoneyTest() {
        VendingMachine machine = getBaseVendingMachine();
        machine.insertMoney(3);

        assert(machine.getBackMoney() == 3);
        assert(machine.getBackMoney() == 0);
    }

    @Test
    public void changeProductPriceTest() throws ProductNotFoundException {
        VendingMachine machine = getBaseVendingMachine();

        machine.changeProductPrice("Water", 49.99);
        assert(machine.getProductPrice("Water") == 49.99);
    }

    @Test
    public void removeProductTest() throws ProductNotFoundException {
        VendingMachine machine = getBaseVendingMachine();
        List<VendingMachine.Product> baseProducts = getBaseProducts();

        machine.removeProduct("Water");

        List<VendingMachine.Product> productList = machine.getProductStock();

        assert(productList.size() == 2);

        for (VendingMachine.Product product : productList) {
            if(product.getName().equals("Water"))
                Assert.fail();
        }

    }

    @Test
    public void inactiveBalanceTest() throws InsufficientMoneyException, ProductNotFoundException, OutOfStockException {
        VendingMachine machine = getBaseVendingMachine();

        machine.insertMoney(2);

        assert(machine.getInactiveBalance() == 0);

        machine.purchaseProduct("Water");
        assert(machine.getInactiveBalance() == 0.99);

        try {
            machine.purchaseProduct("Cookie");
        } catch(InsufficientMoneyException e) {
            //Correctly thrown exception
        }

        //InactiveBalance should not have changed
        assert(machine.getInactiveBalance() == 0.99);
    }

    @Test
    public void restockProductTest() throws ProductNotFoundException {
        VendingMachine machine = getBaseVendingMachine();

        assert(machine.getStockOfProduct("Water") == 3);

        machine.restockProduct("Water", 4);
        assert(machine.getStockOfProduct("Water") == 7);
    }

    @Test
    public void getPriceProductNotFoundTest() {
        VendingMachine machine = getBaseVendingMachine();

        try {
            machine.getProductPrice("Hamburger");
            Assert.fail();
        } catch (ProductNotFoundException e) {
            //Exception thrown as expected
        }
    }

    @Test
    public void getStockProductNotFoundTest() {
        VendingMachine machine = getBaseVendingMachine();

        try {
            machine.getStockOfProduct("Hamburger");
            Assert.fail();
        } catch (ProductNotFoundException e) {
            //Exception thrown as expected
        }
    }

    @Test
    public void removeProductProductNotFoundTest() {
        VendingMachine machine = getBaseVendingMachine();

        try {
            machine.removeProduct("Hamburger");
            Assert.fail();
        } catch (ProductNotFoundException e) {
            //Exception thrown as expected
        }
    }

    @Test
    public void restockProductProductNotFoundTest() {
        VendingMachine machine = getBaseVendingMachine();

        try {
            machine.restockProduct("Hamburger", 3);
            Assert.fail();
        } catch (ProductNotFoundException e) {
            //Exception thrown as expected
        }
    }

    @Test
    public void changeProductPriceProductNotFoundTest() {
        VendingMachine machine = getBaseVendingMachine();

        try {
            machine.changeProductPrice("Hamburger", 3);
            Assert.fail();
        } catch (ProductNotFoundException e) {
            //Exception thrown as expected
        }
    }

    @Test
    public void purchaseProductProductNotFoundTest() throws InsufficientMoneyException, OutOfStockException {
        VendingMachine machine = getBaseVendingMachine();

        machine.insertMoney(20);

        try {
            machine.purchaseProduct("Hamburger");
            Assert.fail();
        } catch (ProductNotFoundException e) {
            //Exception thrown as expected
        }
    }

    @Test
    public void outOfStockTest() throws InsufficientMoneyException, ProductNotFoundException, OutOfStockException {
        VendingMachine machine = getBaseVendingMachine();

        machine.insertMoney(20);
        machine.purchaseProduct("Cookie");

        try {
            machine.purchaseProduct("Cookie");
            Assert.fail();
        } catch (OutOfStockException e) {
            //Exception thrown as expected
        }
    }

    @Test
    public void insufficientMoneyTest() throws ProductNotFoundException, OutOfStockException {
        VendingMachine machine = getBaseVendingMachine();

        machine.insertMoney(1);

        try {
            machine.purchaseProduct("Cookie");
            Assert.fail();
        } catch (InsufficientMoneyException e) {
            //Exception thrown as expected
        }
    }
}
