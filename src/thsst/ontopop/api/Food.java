package thsst.ontopop.api;

import java.util.List;

/**
 * Represents the food items contained in the ontology.
 */
public class Food {
	
	private String name;
	private String foodGroup;
	private List<String> synonyms;
	private List<Nutrient> listOfNutrients;

	/**
	 * Creates a new food instance.
	 * @param name A string containing the name of this food instance.
	 * @param foodGroup A string containing the name of the food group this food instance belongs to
	 * @param nutrients A list of Nutrients contained by this food instance.
	 */
    public Food(String name, String foodGroup, List<Nutrient> nutrients) {
    	this.name = name;
    	this.foodGroup = foodGroup;
    	this.listOfNutrients = nutrients;
    }

	/**
	 * Returns a string containing the name of this Food item
	 * @return The name of the food
	 */
    public String getName() {
        return name;
    }

	/**
	 * Returns a string containing the food group this food item belongs to.
	 * @return The name of the food group this food item belongs to
	 */
    public String getFoodGroup(){
    	return foodGroup;
    }

	/**
	 * Returns a list of Nutrients this Food item has.
	 * @return A list of nutrients this food item contains
	 */
    public List<Nutrient> getNutrients() {
        return listOfNutrients;
    }

	/**
	 * Returns true if the Food item contains a specific nutrient, false otherwise
	 * @param nutrientName the name of the nutrient to check food items for
	 * @return true if the Food item contains a specific nutrient, false otherwise
	 */
    public boolean contains(String nutrientName) {
        // TODO: Returns true if the Food item contains a specific nutrient, false otherwise
    	for(int i = 0; i < listOfNutrients.size(); i++){
    		if(listOfNutrients.get(i).getName().equals(nutrientName)){
    			return true;
    		}
    	}
        return false;
    }

	/**
	 * Returns true if the Food item contains at least minAmount of a specific nutrient, false otherwise.
	 * @param nutrientName the name of the nutrient to check food items for
	 * @param minAmount the minimum amount required for nutrient nutrientName
	 * @return true if the Food item contains at least minAmount of a specific nutrient, false otherwise.
	 */
    public boolean contains(String nutrientName, int minAmount){
    	// TODO: Returns true if the Food item contains at least minAmount of a specific nutrient, false otherwise.
    	
    	for(int i = 0; i < listOfNutrients.size(); i++){
    		if(listOfNutrients.get(i).getName().equals(nutrientName) &&
    				listOfNutrients.get(i).getValue() >= minAmount){
    			return true;
    		}
    	}
        return false;
    }

	/**
	 * Returns a string representation of this object
	 * @return a string representation of this object.
	 */
    @Override
    public String toString(){
    	return name;
    }
}
