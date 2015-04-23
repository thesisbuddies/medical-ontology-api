package thsst.ontopop.api;

/**
 * Represents the nutrients contained in the ontology.
 */
public class Nutrient {

	/**
	 * A string containing the name of this nutrient instance
	 */
	private String name;

	/**
	 * A double containing the value of this nutrient per 100g of the food instance.
	 */
	private double value;

	/**
	 * A string containing the unit used to measure the value of this nutrient.
	 */
	private String unit;

	/**
	 * Creates a new Nutrient instance
	 * @param name A string containing the name of this nutrient instance
	 * @param value A double containing the value of this nutrient per 100g of the food instance.
	 * @param unit A string containing the unit used to measure the value of this nutrient.
	 */
    public Nutrient(String name, double value, String unit) {
    	this.name = name;
    	this.value = value;
    	this.unit = unit;
    }

	/**
	 * Returns a string containing the name of this Nutrient
	 * @return The name of the food
	 */
    public String getName() {
        // TODO: Returns a string containing the name of this Nutrient
        return name;
    }

	/**
	 * Returns a value representing the amount of this nutrient per 100g of the food instance
	 * @return A decimal value representing the amount of this nutrient
	 */
    public double getValue(){
    	return value;
    }

	/**
	 * Returns the unit used to measure the amount of this nutrient.
	 * @return The unit used to measure the amount of this nutrient
	 */
	public String getUnit(){
		return unit;
	}
}
