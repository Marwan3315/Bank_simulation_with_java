package bank;

/**
 * Interface CalculateBill
 * hat eine abstrakte Klasse calculate, die in den Klassen Payment und Transfer jeweils anders implementiert (Override)
 */
public interface CalculateBill {

    public double calculate();



}
