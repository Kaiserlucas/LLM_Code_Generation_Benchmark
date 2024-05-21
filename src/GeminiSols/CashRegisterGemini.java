package GeminiSols;

import exceptions.NoMatchingCoinsException;
import exceptions.ParameterOutOfAllowedBoundsException;
import exceptions.UserPaidTooLittleException;
import tasks.CashRegister;

import java.util.*;

public class CashRegisterGemini implements CashRegister {

    // List to store the coins in the cash register
    private final List<Coin> coins = new ArrayList<>();

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

    @Override
    public void addMoney(Coin coin) {
        coins.add(coin);
    }

    @Override
    public List<Coin> showCoins() {
        return Collections.unmodifiableList(new ArrayList<>(coins)); // Return an unmodifiable copy
    }

    @Override
    public List<Coin> getChange(int changeValue) throws NoMatchingCoinsException, ParameterOutOfAllowedBoundsException {
        if (changeValue < 0) {
            throw new ParameterOutOfAllowedBoundsException("Change value cannot be negative.");
        }

        List<Coin> change = new ArrayList<>();
        // Sort coins in descending order to prioritize larger denominations
        coins.sort(Comparator.comparing(Coin::getValue).reversed());

        // Greedy approach to find the combination of coins for change
        for (Coin coin : coins) {
            while (changeValue >= coin.getValue()) {
                change.add(coin);
                changeValue -= coin.getValue();
            }
        }

        if (changeValue > 0) {
            throw new NoMatchingCoinsException("Cannot provide change for the given amount.");
        }

        // Remove coins used for change from the cash register
        coins.removeAll(change);
        return change;
    }

    @Override
    public List<Coin> makePurchase(List<Coin> paidCoins, int purchasePrice) throws NoMatchingCoinsException, UserPaidTooLittleException, ParameterOutOfAllowedBoundsException {
        if (purchasePrice < 0) {
            throw new ParameterOutOfAllowedBoundsException("Purchase price cannot be negative.");
        }

        int paidAmount = paidCoins.stream().mapToInt(Coin::getValue).sum();

        if (paidAmount < purchasePrice) {
            throw new UserPaidTooLittleException("Paid amount is less than the purchase price.");
        }

        //Initial version of the code that would not compile
        //addMoney(paidCoins); // Add paid coins to cash register

        //Fixed version
        for (Coin coin : paidCoins) {
            addMoney(coin);
        }

        return getChange(paidAmount - purchasePrice); // Return change using getChange method
    }
}
