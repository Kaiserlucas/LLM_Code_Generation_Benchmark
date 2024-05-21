package Llama3Sols;


import exceptions.NoMatchingCoinsException;
import exceptions.ParameterOutOfAllowedBoundsException;
import exceptions.UserPaidTooLittleException;
import org.junit.Assert;
import org.junit.Test;
import tasks.CashRegister;
import tasks.CashRegister.Coin;

import java.util.LinkedList;
import java.util.List;

public class CashRegisterLlama3Tests {

    public CashRegister getCashRegister() {
        return new CashRegisterLlama3();
    }

    public Coin getCoin(int value) {
        return new CashRegisterLlama3.CoinImpl(value);
    }

    public void addMoneyToRegister(CashRegister register, int value) {
        register.addMoney(getCoin(value));
    }

    @Test
    public void addCoinTest() {
        CashRegister register = getCashRegister();
        addMoneyToRegister(register, 5);

        List<Coin> coins = register.showCoins();

        assert(coins.size() == 1);
        assert(coins.get(0).getValue() == 5);
    }

    @Test
    public void addMultipleCoinsTest() {
        CashRegister register = getCashRegister();
        addMoneyToRegister(register, 5);
        addMoneyToRegister(register, 5);
        addMoneyToRegister(register, 2);

        List<Coin> coins = register.showCoins();

        assert(coins.size() == 3);

        int sum = 0;
        for(Coin coin: coins) {
            sum += coin.getValue();
        }
        assert(sum == 12);
    }

    //Only needs to use each coin once
    @Test
    public void getChangeTest1() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException {
        CashRegister register = getCashRegister();
        addMoneyToRegister(register, 1);
        addMoneyToRegister(register, 2);
        addMoneyToRegister(register, 5);
        addMoneyToRegister(register, 10);
        addMoneyToRegister(register, 20);
        addMoneyToRegister(register, 50);
        addMoneyToRegister(register, 100);
        addMoneyToRegister(register, 200);

        List<Coin> change = register.getChange(27);

        assert(change.size() == 3);

        int sum = 0;
        for(Coin coin: change) {
            sum += coin.getValue();
        }
        assert(sum == 27);

    }

    //Needs to deal with coin duplicates
    @Test
    public void getChangeTest2() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException {
        CashRegister register = getCashRegister();
        addMoneyToRegister(register, 1);
        addMoneyToRegister(register, 2);
        addMoneyToRegister(register, 5);
        addMoneyToRegister(register, 10);
        addMoneyToRegister(register, 20);
        addMoneyToRegister(register, 50);
        addMoneyToRegister(register, 100);
        addMoneyToRegister(register, 200);
        addMoneyToRegister(register, 200);

        List<Coin> change = register.getChange(427);

        assert(change.size() == 5);

        int sum = 0;
        for(Coin coin: change) {
            sum += coin.getValue();
        }
        assert(sum == 427);

    }


    //Needs to deal with coins not being nicely sorted
    @Test
    public void getChangeTest3() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException {
        CashRegister register = getCashRegister();
        addMoneyToRegister(register, 50);
        addMoneyToRegister(register, 20);
        addMoneyToRegister(register, 20);
        addMoneyToRegister(register, 20);
        addMoneyToRegister(register, 50);

        List<Coin> change = register.getChange(60);

        assert(change.size() == 3);

        int sum = 0;
        for(Coin coin: change) {
            sum += coin.getValue();
        }
        assert(sum == 60);

    }

    @Test
    public void makePurchaseTest1() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException, UserPaidTooLittleException {
        CashRegister register = getCashRegister();
        List<Coin> payment = new LinkedList<Coin>();
        payment.add(getCoin(20));
        payment.add(getCoin(20));
        payment.add(getCoin(2));
        payment.add(getCoin(2));
        payment.add(getCoin(1));


        List<Coin> change = register.makePurchase(payment, 45);

        assert(change.size() == 0);

    }

    @Test
    public void makePurchaseTest2() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException, UserPaidTooLittleException {
        CashRegister register = getCashRegister();
        List<Coin> payment = new LinkedList<Coin>();
        payment.add(getCoin(20));
        payment.add(getCoin(20));
        payment.add(getCoin(20));
        payment.add(getCoin(2));
        payment.add(getCoin(2));
        payment.add(getCoin(1));


        List<Coin> change = register.makePurchase(payment, 45);

        assert(change.size() == 1);
        assert(change.get(0).getValue() == 20);

    }

    @Test
    public void makePurchaseTest3() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException, UserPaidTooLittleException {
        CashRegister register = getCashRegister();
        List<Coin> payment = new LinkedList<Coin>();
        payment.add(getCoin(20));
        payment.add(getCoin(20));
        payment.add(getCoin(20));
        payment.add(getCoin(5));
        payment.add(getCoin(2));
        payment.add(getCoin(2));
        payment.add(getCoin(1));
        payment.add(getCoin(200));


        List<Coin> change = register.makePurchase(payment, 45);

        assert(change.size() == 3);

        int sum = 0;
        for(Coin coin: change) {
            sum += coin.getValue();
        }
        assert(sum == 225);

    }

    @Test
    public void makePurchaseCoinsAdded1() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException, UserPaidTooLittleException {
        CashRegister register = getCashRegister();
        List<Coin> payment = new LinkedList<Coin>();
        payment.add(getCoin(20));
        payment.add(getCoin(20));
        payment.add(getCoin(2));
        payment.add(getCoin(2));
        payment.add(getCoin(1));


        register.makePurchase(payment, 45);

        List<Coin> coins = register.showCoins();

        assert(coins.size() == 5);

        int sum = 0;
        for(Coin coin: coins) {
            sum += coin.getValue();
        }
        assert(sum == 45);

    }

    @Test
    public void userPaidTooLittleTest() throws NoMatchingCoinsException, ParameterOutOfAllowedBoundsException {
        CashRegister register = getCashRegister();
        List<Coin> payment = new LinkedList<Coin>();
        payment.add(getCoin(20));
        payment.add(getCoin(20));
        payment.add(getCoin(2));

        try {
            register.makePurchase(payment, 45);
            Assert.fail();
        } catch (UserPaidTooLittleException e) {
            //Payment is insufficient, so exception is expected
        }

    }

    @Test
    public void getChangeErrorTest1() throws ParameterOutOfAllowedBoundsException {
        CashRegister register = getCashRegister();
        addMoneyToRegister(register, 1);
        addMoneyToRegister(register, 2);
        addMoneyToRegister(register, 5);
        addMoneyToRegister(register, 10);
        addMoneyToRegister(register, 50);
        addMoneyToRegister(register, 100);
        addMoneyToRegister(register, 200);

        try {
            register.getChange(27);
            Assert.fail();
        } catch (NoMatchingCoinsException e) {
            //No matching coins, so exception is expected
        }

    }

    @Test
    public void getChangeErrorTest2() throws ParameterOutOfAllowedBoundsException {
        CashRegister register = getCashRegister();
        addMoneyToRegister(register, 1);
        addMoneyToRegister(register, 2);
        addMoneyToRegister(register, 5);
        addMoneyToRegister(register, 10);
        addMoneyToRegister(register, 20);
        addMoneyToRegister(register, 50);
        addMoneyToRegister(register, 100);
        addMoneyToRegister(register, 200);

        try {
            register.getChange(427);
            Assert.fail();
        } catch (NoMatchingCoinsException e) {
            //No matching coins, so exception is expected
        }

    }

    @Test
    public void parameterLimitTest1() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException {
        CashRegister register = getCashRegister();

        List<Coin> coins = register.getChange(0);

        assert(coins.size() == 0);

    }

    @Test
    public void parameterLimitTest2() throws ParameterOutOfAllowedBoundsException, NoMatchingCoinsException, UserPaidTooLittleException {
        CashRegister register = getCashRegister();

        List<Coin> coins = register.makePurchase(new LinkedList<Coin>(),0);

        assert(coins.size() == 0);

    }

    @Test
    public void parameterError1() throws NoMatchingCoinsException {
        CashRegister register = getCashRegister();

        try {
            register.getChange(-1);
            Assert.fail();
        } catch (ParameterOutOfAllowedBoundsException e) {
            //Parameter is negative, so exception is expected
        }

    }

    @Test
    public void parameterError2() throws NoMatchingCoinsException, UserPaidTooLittleException {
        CashRegister register = getCashRegister();

        try {
            register.makePurchase(new LinkedList<Coin>(), -1);
            Assert.fail();
        } catch (ParameterOutOfAllowedBoundsException e) {
            //Parameter is negative, so exception is expected
        }

    }

}
