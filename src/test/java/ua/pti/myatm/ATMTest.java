/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author andrii
 * @author Maria
 */
public class ATMTest {
	
	@Mock
    private Card mockCard;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMoneyInATM() {
        System.out.println("getMoneyInATM");
        ATM instance = mock(ATM.class);
        when(instance.getMoneyInATM()).thenReturn(1000.0);
        double expResult = 1000.0;
        double result = instance.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testValidateCard() {
        System.out.println("validateCard");
            
        int pinCode = 1234;
        ATM instance = new ATM(1000.0);
        when(mockCard.checkPin(pinCode)).thenReturn(true);
        when(mockCard.isBlocked()).thenReturn(false);
       
        boolean result0 = instance.validateCard(mockCard, pinCode);
        assertTrue(result0);
        
        when(mockCard.checkPin(pinCode)).thenReturn(true);
        when(mockCard.isBlocked()).thenReturn(true);
       
        boolean result1 = instance.validateCard(mockCard, pinCode);
        assertFalse(result1);
        
        when(mockCard.checkPin(pinCode)).thenReturn(false);
        when(mockCard.isBlocked()).thenReturn(true);
       
        boolean result2 = instance.validateCard(mockCard, pinCode);
        assertFalse(result2);
        
        when(mockCard.checkPin(pinCode)).thenReturn(false);
        when(mockCard.isBlocked()).thenReturn(false);
       
        boolean result3 = instance.validateCard(mockCard, pinCode);
        assertFalse(result3);
        
        //validateMockitoUsage();
    }
    
    @Test
    public void testGetIsValidCard() {
    	System.out.println("getIsValidCard");
        ATM instance = mock(ATM.class);
        when(instance.getIsValidCard()).thenReturn(true);
        boolean result = instance.getIsValidCard();
        assertTrue(result);
        
        when(instance.getIsValidCard()).thenReturn(false);
        boolean result1 = instance.getIsValidCard();
        assertFalse(result1);
    }
    
    @Test
    public void testGetCardAccount() {
    	System.out.println("getCardAccount");
    	ATM instance = new ATM(1000.0);
    	Account account = mock(Account.class);
    	when(mockCard.getAccount()).thenReturn(account);
    	instance.setCardAccount(mockCard);
    	Account cardAccount = instance.getCardAccount();
    	
    	assertEquals(cardAccount, account);
    }

    @Test
    public void testCheckBalance() {
        System.out.println("checkBalance");
        ATM instance = new ATM(1000.0);
        Account account = mock(Account.class);
    	when(mockCard.getAccount()).thenReturn(account);
    	when(account.getBalance()).thenReturn(2000.0);
    	
    	instance.setCardAccount(mockCard);
    	when(mockCard.checkPin(1234)).thenReturn(true);
        when(mockCard.isBlocked()).thenReturn(false);
    	when(instance.validateCard(mockCard, 1234)).thenReturn(true);
    	
        double expResult1 = 2000.0;
        double result1 = instance.checkBalance();
        assertEquals(expResult1, result1, 0.0);
        
      //if card in not valid
        when(mockCard.checkPin(1234)).thenReturn(false);
        when(mockCard.isBlocked()).thenReturn(false);
    	when(instance.validateCard(mockCard, 1234)).thenReturn(false);

        try {
        	instance.checkBalance();
        	Assert.fail("NoCardInserted");
        }
        catch(UnsupportedOperationException msg) {
        	Assert.assertNotEquals("", msg.getMessage());
        }
        
    }

    @Test
    public void testGetCash() throws UnsupportedOperationException {
        System.out.println("getCash");
        
        double amount = 500.0;
        ATM instance = new ATM(1000.0);
        Account account = mock(Account.class);
    	when(mockCard.getAccount()).thenReturn(account);
    	when(account.getBalance()).thenReturn(2000.0);
    	instance.setCardAccount(mockCard);
    	
    	when(mockCard.checkPin(1234)).thenReturn(true);
        when(mockCard.isBlocked()).thenReturn(false);
    	when(instance.validateCard(mockCard, 1234)).thenReturn(true);
    	
    	when(instance.checkBalance()).thenReturn(2000.0);
    	when(instance.getCardAccount().withdrow(amount)).thenReturn(500.0);
        double expResult = 1500.0;
        double result = instance.getCash(amount);
        assertEquals(expResult, result, 0.0);
    	
        //if not enough money in account
    	when(account.getBalance()).thenReturn(300.0);
    	when(instance.checkBalance()).thenReturn(300.0);

        try {
        	instance.getCash(amount);
        	Assert.fail("NotEnoughMoneyInAccount");
        }
        catch(UnsupportedOperationException msg) {
        	Assert.assertNotEquals("", msg.getMessage());
        }
        
        //if not enough money in ATM
        ATM instance1 = new ATM(200.0);
    	instance1.setCardAccount(mockCard);
    	when(mockCard.checkPin(1234)).thenReturn(true);
        when(mockCard.isBlocked()).thenReturn(false);
    	when(instance1.validateCard(mockCard, 1234)).thenReturn(true);
    	when(instance1.checkBalance()).thenReturn(2000.0);
    	
        try {
        	instance1.getCash(amount);
        	Assert.fail("NotEnoughMoneyInATM");
        }
        catch(UnsupportedOperationException msg) {
        	Assert.assertNotEquals("", msg.getMessage());
        }
        
    }
    
}
