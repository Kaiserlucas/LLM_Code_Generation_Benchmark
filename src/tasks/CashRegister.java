package tasks;

import exceptions.NoMatchingCoinsException;
import exceptions.ParameterOutOfAllowedBoundsException;
import exceptions.UserPaidTooLittleException;

import java.util.List;

//A cash register that manages coins put into it
//The value of each coin is always given in cents
public interface CashRegister {

    public interface Coin{

        //Product object that has a name, a price and an amount of available stock
        //Should be static and have a sensible constructor

        public int getValue();

        public void setValue(int value);

    }

    //Adds a coin to the cash register
    public void addMoney(Coin coin);

    //Shows the coins currently in the cash register
    public List<Coin> showCoins();

    //Returns a list of coins from the cash register equal to the changeValue
    //Also removes those coins from the cash register in the process
    //If the required change value cannot be made from the available coins, throw a NoMatchingCoinsException
    //Also throw an exception if the changeValue is negative
    public List<Coin> getChange(int changeValue) throws NoMatchingCoinsException, ParameterOutOfAllowedBoundsException;

    //Makes a purchase where the paid coins are added to the cash register, and appropriate change is returned
    //Should handle invalid inputs (like negative purchasePrice, paidCoins summing to an amount lower than purchasePrice etc) and throw exceptions appropriately
    public List<Coin> makePurchase(List<Coin> paidCoins, int purchasePrice)
            throws NoMatchingCoinsException, UserPaidTooLittleException, ParameterOutOfAllowedBoundsException;

}
