package thsst.ontopop.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * The Ontology class represents an Ontology in Java. It stores all information read from the Medical Ontology file and contains methods to access them.
 */
public class Ontology {

	/**
	 * An object containing the JENA representation of an ontology model.
	 */
	private static OntModel model;

	private List<Condition> conditionList;
	private static String fname;
	private static String foodGroup;
	private static String Nname;
	private static Double Nvalue;
	private static String Nunit;
	private static List<Nutrient> nutrientList;
	private static List<Food> foodList;

	/**
	 * A local constant that represents the format of the ontology file.
	 */
    private static final String ONTOLOGY_FORMAT = "RDF/XML-ABBREV";

	/**
	 * A local constant that contains the source of the ontology.
	 */
    protected static final String SOURCE = "http://www.dlsu.edu.ph/technologies/ontology";

	/**
	 * A local constant that contains the namespace of the ontology.
	 */
    protected static final String NS = SOURCE + "#";

	/**
	 * Getter method for the model attribute of this ontology instance.
	 * @return A reference to the model of the Ontology instance.
	 */
    public static OntModel getModel(){
    	return model;
    }

	/**
	 * Opens an OWL ontology.
	 * @param pathToOWLOntology the path to the ontology file to be loaded
	 */
    public static void loadOntology(String pathToOWLOntology) {


        // STAGE 1: Read ontology file
        try {
            // Try to read an existing ontology file.
            model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(new FileInputStream(pathToOWLOntology), null, ONTOLOGY_FORMAT);
        }
        catch (FileNotFoundException ex) {
            // File not found, call createOntology()
        }

        model.setNsPrefix("obj", NS);
    }

	/**
	 * Retrieves a condition from the ontology by name.
	 * @param conditionName the name of the condition to retrieve
	 * @return This returns a Condition object that matches the condition name passed as an argument. Returns null if no match is found.
	 */
    public Condition getCondition(String conditionName) {
    	// TODO: Retrieves a condition from the ontology by name. This returns a Condition object.
 
    	for(int i = 0; i < conditionList.size(); i++){
    		if(conditionList.get(i).getName().equalsIgnoreCase(conditionName)){
    			return conditionList.get(i);
    		}
    	}
		return null;
    }

	/**
	 * Returns a list of all medical conditions currently stored in the ontology.
	 * @return An ArrayList of Condition objects
	 */
    public List<Condition> getAllConditions() {
        // TODO: Returns a list of all medical conditions currently stored in the ontology.
    	
    	OntClass conditionclass = model.getOntClass(NS + "Condition");
        ExtendedIterator it = conditionclass.listInstances();
        String cname; 
        while(it.hasNext()){
        	OntResource instance = (OntResource)it.next();
        	cname = instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
        	//System.out.println("Condition: " +instance.getPropertyValue(model.getDatatypeProperty(NS + "name")));
        	
        	// get Condition instance
            conditionList = new ArrayList<Condition>();
            
            Condition condition = new Condition(cname);
            conditionList.add(condition);
        }
		return conditionList;

    }

	/**
	 * Retrieves a food item from the ontology by name.
	 * @param foodName the name of the food item to retrieve
	 * @return This returns a Food object that matches the food name passed as an argument.
	 */
    public Food getFood(String foodName) {
        // TODO: Retrieves a food item from the ontology by name. This returns a Food object.

    	for(int i = 0; i < foodList.size(); i++){
    		if(foodList.get(i).getName().equalsIgnoreCase(foodName)){
    			return foodList.get(i);
    		}
    	}
		return null;
    }

	/**
	 * Returns a list of all food items currently stored in the ontology.
	 * @return A list of Food objects.
	 */
    public List<Food> getAllFood() {
        // TODO: Returns a list of all food items currently stored in the ontology.
    	
    	OntClass foodClass = model.getOntClass(NS + "Food");
        ExtendedIterator it = foodClass.listInstances();
        foodList = new ArrayList<Food>();
        while(it.hasNext()){
        	OntResource instance = (OntResource)it.next();
        	String[] tokens = instance.toString().split("#");
        	String[] id = null;
        	if(tokens[1].charAt(0) == 'f'){

        		id = tokens[1].split("food");
        		//System.out.println(id[1]);
        		
        		// get Food
	        	fname = instance.getPropertyValue(model.getDatatypeProperty(NS + "name")).toString();
	        	foodGroup = instance.getPropertyValue(model.getDatatypeProperty(NS + "group")).toString();
	        	//System.out.println("Food name: " + fname);
	        	//System.out.println("Food group: " + foodGroup);
	        	
	       		Individual indiv = model.getIndividual(NS + "food" + id[1]);
	       		 		
	       		
	       		// get Nutrients
	            NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(NS + "hasNutrient"));
	            nutrientList = new ArrayList<Nutrient>();
	            while (it2.hasNext()) {
	            	Nutrient nutrient;
	                Resource indivNutrient = it2.nextNode().asResource();
	                Nname = indivNutrient.getProperty(model.getDatatypeProperty(NS + "name")).getObject().toString();
	                Nunit = indivNutrient.getProperty(model.getDatatypeProperty(NS + "unit")).getObject().toString();
	                Nvalue = Double.parseDouble(indivNutrient.getProperty(model.getDatatypeProperty(NS + "value")).getObject().toString());
	                nutrient = new Nutrient(Nname, Nvalue, Nunit);
	                nutrientList.add(nutrient);
	                //System.out.println("Nutrient name: " + Nname);
	                //System.out.println("Nutrient unit: " + Nunit);
	                //System.out.println("Nutrient value: " + Nvalue);
	                
	            }
	            Food food = new Food(fname, foodGroup, nutrientList);
                foodList.add(food);
        	}
        }
        return foodList;
    }

	/**
	 * Returns a list of all food items that contain a certain nutrient.
	 * @param nutrientName the name of the nutrient to check food items for
	 * @return A list of Food objects.
	 */
    public List<Food> getAllFood(String nutrientName) {
        // TODO: Returns a list of all food items that contain a certain nutrient.
    	if(foodList == null){
    		getAllFood();
    	}
    	else{
    		List<Food> foodTemp = new ArrayList<Food>();
        	
        	for(int i = 0; i < foodList.size(); i++){
        		for(int y = 0; y < foodList.get(i).getNutrients().size(); y++){
        			if(foodList.get(i).getNutrients().get(y).getName().equalsIgnoreCase(nutrientName)){
        				foodTemp.add(foodList.get(i));
        			}
        		}
        	}
        	return foodTemp;
    	}
    	return null;
		
    }

	/**
	 * Returns a list of all food items that contain a minimum amount of a certain nutrient.
	 * @param nutrientName the name of the nutrient to check food items for
	 * @param minAmount the minimum amount required for nutrient nutrientName
	 * @return A list of Food objects.
	 */
    public List<Food> getAllFood(String nutrientName, double minAmount){
    	// TODO: Returns a list of all food items that contain a minimum amount of a certain nutrient.
    	if(foodList == null){
    		getAllFood();
    	}
    	else{
	    	List<Food> foodTemp = new ArrayList<Food>();
	    	
	    	for(int i = 0; i < foodList.size(); i++){
	    		for(int y = 0; y < foodList.get(i).getNutrients().size(); y++){
	    			if(foodList.get(i).getNutrients().get(y).getName().equalsIgnoreCase(nutrientName) && 
	    					foodList.get(i).getNutrients().get(y).getValue() >= minAmount){
	    				foodTemp.add(foodList.get(i));
	    				break;
	    			}
	    		}
	    	}
	    	return foodTemp;
    	}
    	return null;
    }

	/**
	 * Returns a list of all food items from a certain food group
	 * @param categoryName the name of the category to retrieve food items from
	 * @return A list of Food objects.
	 */
    public List<Food> getAllFoodFromGroup(String categoryName){
    	// TODO: Returns a list of all food items from a certain food group.
    	
    	List<Food> foodTemp = new ArrayList<Food>();
    	
    	for(int i = 0; i < foodList.size(); i++){
    		if(foodList.get(i).getFoodGroup().equalsIgnoreCase(categoryName)){
    			foodTemp.add(foodList.get(i));
    		}
    	}
    	
    	return foodTemp;
    }
}
