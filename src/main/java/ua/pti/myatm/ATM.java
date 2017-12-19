package ua.pti.myatm;

public class ATM {
	
	private double money;
	private Account account;
	private boolean isValidateCard = false;
   
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM){
         this.money= moneyInATM;
    }

    // Возвращает каоличестов денег в банкомате
    public double getMoneyInATM() {
         return this.money;
    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. 
    //При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode) {
         if(card.checkPin(pinCode) & !card.isBlocked()) {
        	 this.isValidateCard = true;
        	 return true;
         }
         else {
        	 this.isValidateCard = false;
        	 return false;
         }
    }
    
    public void setCardAccount(Card card) {
    	this.account = card.getAccount();
    }
    
    public Account getCardAccount() {
    	return this.account;
    }
    
    public boolean getIsValidCard() {
    	return this.isValidateCard;
    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance(){
    	if(!this.getIsValidCard()) {
    		throw new UnsupportedOperationException("NoCardInserted");
    	}
    	return this.getCardAccount().getBalance();
    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount){
    	if(!this.getIsValidCard()) {
    		throw new UnsupportedOperationException("NoCardInserted");
    	}
    	if(amount > this.checkBalance()) {
    		throw new UnsupportedOperationException("NotEnoughMoneyInAccount"); 
    	}
    	if(amount > this.getMoneyInATM()) {
    		throw new UnsupportedOperationException("NotEnoughMoneyInATM"); 
    	}
    	
    	double cash = this.getCardAccount().withdrow(amount);
    	this.money = this.getMoneyInATM() - amount;
        
    	return this.checkBalance() - cash;
    }
}
