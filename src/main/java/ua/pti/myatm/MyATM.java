package ua.pti.myatm;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class MyATM {

    public static void main(String[] args) {
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        Card card = null;
        atm.validateCard(card, 1234);
        atm.checkBalance();
        atm.getCash(999.99);

    }
}
