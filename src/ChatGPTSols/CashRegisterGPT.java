package ChatGPTSols;

import exceptions.NoMatchingCoinsException;
import exceptions.ParameterOutOfAllowedBoundsException;
import exceptions.UserPaidTooLittleException;
import tasks.CashRegister;

import java.util.ArrayList;
import java.util.List;

public class CashRegisterGPT implements CashRegister {

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

    public CashRegisterGPT() {
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
        int remainingChange = changeValue;

        for (int i = coins.size() - 1; i >= 0; i--) {
            Coin coin = coins.get(i);
            while (coin.getValue() <= remainingChange && remainingChange > 0) {
                change.add(coin);
                remainingChange -= coin.getValue();
            }
        }

        if (remainingChange != 0) {
            throw new NoMatchingCoinsException("Cannot provide exact change");
        }

        coins.removeAll(change);
        return change;
    }

    @Override
    public List<Coin> makePurchase(List<Coin> paidCoins, int purchasePrice) throws NoMatchingCoinsException, UserPaidTooLittleException, ParameterOutOfAllowedBoundsException {
        if (purchasePrice < 0) {
            throw new ParameterOutOfAllowedBoundsException("Purchase price cannot be negative");
        }

        int paidAmount = 0;
        for (Coin coin : paidCoins) {
            paidAmount += coin.getValue();
        }

        if (paidAmount < purchasePrice) {
            throw new UserPaidTooLittleException("Paid amount is less than purchase price");
        }

        int changeValue = paidAmount - purchasePrice;
        if (changeValue == 0) {
            coins.addAll(paidCoins);
            return new ArrayList<>();
        } else {
            return getChange(changeValue);
        }
    }
}
