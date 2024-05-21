package Llama3Sols;

import exceptions.NoMatchingCoinsException;
import exceptions.ParameterOutOfAllowedBoundsException;
import exceptions.UserPaidTooLittleException;
import tasks.CashRegister;

import java.util.*;

public class CashRegisterLlama3 implements CashRegister {

    public static class CoinImpl implements Coin {
        private int value;

        public CoinImpl(int value) {
            this.value = value;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public void setValue(int value) {
            this.value = value;
        }
    }

    private List<Coin> coins;

    public CashRegisterLlama3() {
        this.coins = new ArrayList<>();
    }

    @Override
    public void addMoney(Coin coin) {
        coins.add(coin);
    }

    @Override
    public List<Coin> showCoins() {
        return new ArrayList<>(coins);
    }

    @Override
    public List<Coin> getChange(int changeValue) throws NoMatchingCoinsException, ParameterOutOfAllowedBoundsException {
        if (changeValue < 0) {
            throw new ParameterOutOfAllowedBoundsException("Change value cannot be negative");
        }

        List<Coin> change = new ArrayList<>();
        List<Coin> remainingCoins = new ArrayList<>(coins);

        while (changeValue > 0 && !remainingCoins.isEmpty()) {
            Coin maxCoin = Collections.max(remainingCoins, Comparator.comparingInt(Coin::getValue));
            if (maxCoin.getValue() <= changeValue) {
                change.add(maxCoin);
                remainingCoins.remove(maxCoin);
                changeValue -= maxCoin.getValue();
            } else {
                break;
            }
        }

        if (changeValue > 0) {
            throw new NoMatchingCoinsException("Cannot make change for " + changeValue);
        }

        coins.removeAll(change);
        return change;
    }

    @Override
    public List<Coin> makePurchase(List<Coin> paidCoins, int purchasePrice) throws NoMatchingCoinsException, UserPaidTooLittleException, ParameterOutOfAllowedBoundsException {
        if (purchasePrice < 0) {
            throw new ParameterOutOfAllowedBoundsException("Purchase price cannot be negative");
        }

        int paidAmount = paidCoins.stream().mapToInt(Coin::getValue).sum();
        if (paidAmount < purchasePrice) {
            throw new UserPaidTooLittleException("Paid amount is less than purchase price");
        }

        //Initial version of the code that would not compile
        //addMoney(paidCoins);

        //Fixed version
        for (Coin coin : paidCoins) {
            addMoney(coin);
        }

        int changeValue = paidAmount - purchasePrice;
        return getChange(changeValue);
    }
}