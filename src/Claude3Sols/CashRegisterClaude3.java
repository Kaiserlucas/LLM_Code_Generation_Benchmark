package Claude3Sols;

import exceptions.NoMatchingCoinsException;
import exceptions.ParameterOutOfAllowedBoundsException;
import exceptions.UserPaidTooLittleException;
import tasks.CashRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CashRegisterClaude3 implements CashRegister {

    private List<Coin> coins;

    public CashRegisterClaude3() {
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
            throw new ParameterOutOfAllowedBoundsException("Change value cannot be negative.");
        }

        List<Coin> change = new ArrayList<>();
        List<Coin> sortedCoins = new ArrayList<>(coins);
        Collections.sort(sortedCoins, (c1, c2) -> Integer.compare(c2.getValue(), c1.getValue()));

        for (Coin coin : sortedCoins) {
            while (changeValue >= coin.getValue()) {
                change.add(coin);
                changeValue -= coin.getValue();
            }
        }

        if (changeValue > 0) {
            throw new NoMatchingCoinsException("Cannot make the required change from the available coins.");
        }

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
            throw new UserPaidTooLittleException("The user paid too little for the purchase.");
        }

        int changeValue = paidAmount - purchasePrice;
        List<Coin> change = getChange(changeValue);
        coins.addAll(paidCoins);
        return change;
    }

    public static class CoinImpl implements CashRegister.Coin {
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
}