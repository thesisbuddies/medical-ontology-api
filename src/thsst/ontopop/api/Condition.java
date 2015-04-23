package thsst.ontopop.api;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Represents the conditions contained in the ontology.
 */
public class Condition {
	
	private String conditionName;
	private List<String> synonyms;
	private List<String> symptoms;
	private List<Food> neededFood;
	private List<Food> avoidFood;
	private List<String> neededNutrients;
	private List<String> avoidNutrients;

	/**
	 * Creates a new condition object
	 * @param name The name of the condition
	 */
    public Condition(String name)/*, List<String> synonym, List<String> symptoms, 
    		List<Food> fNeed, List<Food> fAvoid, List<String> nNeed, 
    		List<String> nAvoid)*/ {
    	this.conditionName = name;
    	/*this.synonyms = synonym;
    	this.symptoms = symptoms;
    	this.neededFood = fNeed;
    	this.avoidFood = fAvoid;
    	this.neededNutrients = nNeed;
    	this.avoidNutrients = nAvoid;*/
    }

	/**
	 * Returns a string containing the name of this Condition
	 * @return The name of the condition
	 */
    public String getName() {
        // TODO: Returns a string containing the name of this Condition
        return conditionName;
    }

	/**
	 * Returns a list of names this Condition is also known as
	 * @return A list of names this condition is also known as
	 */
    public List<String> getOtherNames() {
        // TODO: Returns a list of names this Condition is also known as
    	OntModel model = Ontology.getModel();
    	
    	OntClass conditionclass = model.getOntClass(Ontology.NS + "Condition");
    	synonyms = new ArrayList<String>();
    	// get Synonyms
		 ExtendedIterator it1 = conditionclass.listInstances();
   		 while(it1.hasNext()){
  	       OntResource instance1 = (OntResource)it1.next();
  	       
  	       NodeIterator nit =  instance1.listPropertyValues(model.getDatatypeProperty(Ontology.NS + "synonym"));
  	       //System.out.println("Synonym: " +instance1.getPropertyValue(model.getDatatypeProperty(Ontology.NS + "synonym")));
  	       while (nit.hasNext()) {
  	    	   String syn = nit.nextNode().toString();
  	    	   synonyms.add(syn);
  	       }
  	     }
        return synonyms;
    }

	/**
	 * Returns a list of Symptoms associated with this Condition.
	 * @return A list of symptoms associated with this condition.
	 */
    public List<String> getSymptoms() {
        // TODO: Returns a list of Symptoms associated with this Condition.
    	OntModel model = Ontology.getModel();
    	
    	Individual indiv = model.getIndividual(Ontology.NS + "condition/" + getName().replace(' ', '-'));
    	symptoms = new ArrayList<String>();
    	// get Symptoms
        NodeIterator it2 = indiv.listPropertyValues(model.getObjectProperty(Ontology.NS + "hasSymptom"));
        while (it2.hasNext()) {
            Resource indivNutrient = it2.nextNode().asResource();
            String symptomName = indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "name")).getObject().toString();
            symptoms.add(symptomName);
        }
        return symptoms;
    }

	/**
	 * Returns a list of Nutrients that this Condition is deficient in.
	 * @return A list of Nutrient objects
	 */
    public List<String> getNutrientDeficiencies() {
        // TODO: Returns a list of Nutrients that this Condition is deficient in.
    	OntModel model = Ontology.getModel();
    	
    	Individual indiv = model.getIndividual(Ontology.NS + "condition/" + getName().replace(' ', '-'));
    	neededNutrients = new ArrayList<String>();
    	// get deficient nutrients
        NodeIterator it4 = indiv.listPropertyValues(model.getObjectProperty(Ontology.NS + "hasDeficientNutrient"));
        while (it4.hasNext()) {
            Resource indivNutrient = it4.nextNode().asResource();
            String deficientNutrientName = indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "name")).getObject().toString();
            neededNutrients.add(deficientNutrientName);
        }
        
        return neededNutrients;
    }

	/**
	 * Returns a list of Nutrients that this Condition is excessive in.
	 * @return A list of Nutrient objects
	 */
    public List<String> getNutrientExcesses() {
        // TODO: Returns a list of Nutrients that this Condition is excessive in.
    	OntModel model = Ontology.getModel();
    	
    	Individual indiv = model.getIndividual(Ontology.NS + "condition/" + getName().replace(' ', '-'));
    	avoidNutrients = new ArrayList<String>();
    	// get excess nutrients
        NodeIterator it3 = indiv.listPropertyValues(model.getObjectProperty(Ontology.NS + "hasExcessNutrient"));
        while (it3.hasNext()) {
            Resource indivNutrient = it3.nextNode().asResource();
            String excessNutrientName = indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "name")).getObject().toString();
            avoidNutrients.add(excessNutrientName);
        }
        return avoidNutrients;
    }

	/**
	 * Returns a list of Food that is recommended for people that have this Condition
	 * @return A list of Food objects
	 */
    public List<Food> getRecommendedFoodItems() {
        // TODO: Returns a list of Food that is recommended for people that have this Condition.
    	OntModel model = Ontology.getModel();
    	
    	Individual cIndiv = model.getIndividual(Ontology.NS + "condition/" + getName().replace(' ', '-'));
    	neededFood = new ArrayList<Food>();
    	ArrayList<Nutrient> nutrientList;
    	String Nname;
    	String Nunit;
    	double Nvalue;
    	 // get avoids Food
        NodeIterator it5 = cIndiv.listPropertyValues(model.getObjectProperty(Ontology.NS + "needsFood"));
        while (it5.hasNext()) {
            Resource instance1 = it5.nextNode().asResource();
            String avoidsFoodName = instance1.getProperty(model.getDatatypeProperty(Ontology.NS + "name")).getObject().toString();
            String avoidsFoodGroup = instance1.getProperty(model.getDatatypeProperty(Ontology.NS + "group")).getObject().toString();
 
            String[] tokens = instance1.toString().split("#");
        	String[] id = null;
        	
        	if(tokens[1].charAt(0) == 'f'){

        		id = tokens[1].split("food");
  
        		Individual indiv = model.getIndividual(Ontology.NS + "food" + id[1]);
        		// get Nutrients
	            NodeIterator it = indiv.listPropertyValues(model.getObjectProperty(Ontology.NS + "hasNutrient"));
	            nutrientList = new ArrayList<Nutrient>();
	            while (it.hasNext()) {
	            	Nutrient nutrient;
	                Resource indivNutrient = it.nextNode().asResource();
	                Nname = indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "name")).getObject().toString();
	                Nunit = indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "unit")).getObject().toString();
	                Nvalue = Double.parseDouble(indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "value")).getObject().toString());
	                nutrient = new Nutrient(Nname, Nvalue, Nunit);
	                nutrientList.add(nutrient);
	            }
	            Food food = new Food(avoidsFoodName, avoidsFoodGroup, nutrientList);
	            neededFood.add(food);
        	}
        }
        return neededFood;
    }

	/**
	 * Returns a list of Food that should be avoided by people that have this condition
	 * @return A list of Food objects
	 */
    public List<Food> getDiscouragedFoodItems() {
        // TODO: Returns a list of Food that should be avoided by people that have this condition
    	OntModel model = Ontology.getModel();
    	
    	Individual cIndiv = model.getIndividual(Ontology.NS + "condition/" + getName().replace(' ', '-'));
    	avoidFood = new ArrayList<Food>();
    	ArrayList<Nutrient> nutrientList;
    	String Nname;
    	String Nunit;
    	double Nvalue;
    	 // get needs Food
        NodeIterator it5 = cIndiv.listPropertyValues(model.getObjectProperty(Ontology.NS + "avoidsFood"));
        while (it5.hasNext()) {
            Resource instance1 = it5.nextNode().asResource();
            String avoidsFoodName = instance1.getProperty(model.getDatatypeProperty(Ontology.NS + "name")).getObject().toString();
            String avoidsFoodGroup = instance1.getProperty(model.getDatatypeProperty(Ontology.NS + "group")).getObject().toString();
 
            String[] tokens = instance1.toString().split("#");
        	String[] id = null;
        	
        	if(tokens[1].charAt(0) == 'f'){

        		id = tokens[1].split("food");
  
        		Individual indiv = model.getIndividual(Ontology.NS + "food" + id[1]);
        		// get Nutrients
	            NodeIterator it = indiv.listPropertyValues(model.getObjectProperty(Ontology.NS + "hasNutrient"));
	            nutrientList = new ArrayList<Nutrient>();
	            while (it.hasNext()) {
	            	Nutrient nutrient;
	                Resource indivNutrient = it.nextNode().asResource();
	                Nname = indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "name")).getObject().toString();
	                Nunit = indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "unit")).getObject().toString();
	                Nvalue = Double.parseDouble(indivNutrient.getProperty(model.getDatatypeProperty(Ontology.NS + "value")).getObject().toString());
	                nutrient = new Nutrient(Nname, Nvalue, Nunit);
	                nutrientList.add(nutrient);
	            }
	            Food food = new Food(avoidsFoodName, avoidsFoodGroup, nutrientList);
	            avoidFood.add(food);
        	}
        }
        return avoidFood;
    }
}
