package BingSols;

import exceptions.NoMatchingCoinsException;
import exceptions.ParameterOutOfAllowedBoundsException;
import exceptions.UserPaidTooLittleException;
import tasks.CashRegister;

import java.util.ArrayList;
import java.util.List;

public class CashRegisterBing implements CashRegister {

    private List<Coin> coins;

    public CashRegisterBing() {
        coins = new ArrayList<>();
    }

    @Override
    public void addMoney(Coin coin) {
        coins.add(coin);
    }

    @Override
    public List<Coin> showCoins() {
        return coins;
    }

    @Override
    public List<Coin> getChange(int changeValue) throws NoMatchingCoinsException, ParameterOutOfAllowedBoundsException {
        if (changeValue < 0) {
            throw new ParameterOutOfAllowedBoundsException("Change value cannot be negative.");
        }

        List<Coin> changeCoins = new ArrayList<>();
        int remainingChange = changeValue;

        // Sort coins in descending order of value (largest to smallest)
        coins.sort((c1, c2) -> Integer.compare(c2.getValue(), c1.getValue()));

        for (Coin coin : coins) {
            while (coin.getValue() <= remainingChange) {
                changeCoins.add(coin);
                remainingChange -= coin.getValue();
            }
        }

        if (remainingChange > 0) {
            throw new NoMatchingCoinsException("Cannot make exact change with available coins.");
        }

        coins.removeAll(changeCoins);
        return changeCoins;
    }

    @Override
    public List<Coin> makePurchase(List<Coin> paidCoins, int purchasePrice)
            throws NoMatchingCoinsException, UserPaidTooLittleException, ParameterOutOfAllowedBoundsException {
        if (purchasePrice < 0) {
            throw new ParameterOutOfAllowedBoundsException("Purchase price cannot be negative.");
        }

        int paidAmount = paidCoins.stream().mapToInt(Coin::getValue).sum();
        if (paidAmount < purchasePrice) {
            throw new UserPaidTooLittleException("Insufficient payment for the purchase.");
        }

        // Add paid coins to the cash register
        coins.addAll(paidCoins);

        // Calculate change
        int changeValue = paidAmount - purchasePrice;
        return getChange(changeValue);
    }

    // Inner class representing a coin
    public static class CoinBing implements Coin {
        private int value;

        public CoinBing(int value) {
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
}
