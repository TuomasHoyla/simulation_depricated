package resources;
import java.util.Vector;

import org.apache.commons.math3.genetics.ElitisticListPopulation;

public class Researcher  {

	static RandomGenerator randgenerator = new RandomGenerator();
	public String name;
	//	private boolean EligbleForFunding;
	double aplTimeResources = 0;
	private double qualityOfApplication;
	private double researchSkill;
	private double applyingSkill;
	public double timeAvailableForResearch = 1;
	private int yearsInAcedemia;
	public double monetaryFrustration = 0;
	public double promotionalFrustration = 0;
	private double totalFrustration;
	//	private int citations; //This could be removed, and there could be just method to return all the citations of the researcher
	public double ResourcesForResearch = 0;
	private int positionInOrganization; //What would be the advantages for this to be enum instead?
	private double resourcesNeededToBeEfective;
	private double productivity;
	private double baseProductivity;
	private double monetaryProductivity;
	private double sackingProbability;
	private boolean leavingOrganization;
	private double skillsSummedUp;
	double timeUsedForApplying;
	public Vector<Paper> papers = new Vector<Paper>(); 

	//Constructors TODO Citations voi ottaa huoletta pois
	Researcher() {
		this("Researcher",0,50,50,0,0,0);
	}

	public Researcher (String name) {
		this(name, 0,1,1,0,0,0); 
	}

	Researcher (String name, int yearsInAcademia) {
		this(name, yearsInAcademia,50,50,0,0,0); 
	}

	Researcher (String name, int yearsInAcademia, double researchSkill) {
		this(name, yearsInAcademia,researchSkill,50,0,0,0); 
	}

	Researcher (String name, int yearsInAcademia, double researchSkill, double applyingSkill) {
		this(name, yearsInAcademia,researchSkill,applyingSkill,0,0,0); 
	}

	Researcher (String name, int yearsInAcademia, double researchSkill, double applyingSkill, int citations) {
		this(name, yearsInAcademia,researchSkill,applyingSkill,citations,0,0); 
	}

	Researcher (String name, int yearsInAcademia, double researchSkill, double applyingSkill, int citations, int ResourcesForResearch) {
		this(name, yearsInAcademia,researchSkill,applyingSkill,citations,ResourcesForResearch,0); 
	}

	public Researcher(String name, int yearsInAcademia, double researchSkill, double applyingSkill, int citations, int ResourcesForResearch, int positionInOrganization){
		this.name = name;
		this.yearsInAcedemia = yearsInAcademia;
		this.researchSkill = researchSkill;
		this.applyingSkill = applyingSkill;
		//	this.citations = citations;
		this.ResourcesForResearch = 0;
		this.positionInOrganization = positionInOrganization;
		this.productivity = 1.0; //no need to be one...
		this.leavingOrganization = false;
		this.resourcesNeededToBeEfective = 0.215; //Average
		this.skillsSummedUp = this.researchSkill + this.applyingSkill; //T�ll� hetkell� molemmat ovat yht� vahvoja
	}
	/*
public boolean getEligbleForFunding() {
return EligbleForFunding;
}

public void SetEligbleForFunding(boolean fundIn) {
EligbleForFunding = fundIn;
}
	 */
	public void setMoney(double dallaDallaBillYall) {
		ResourcesForResearch += dallaDallaBillYall;
	}
	
	public double getResourcesForResearch() {
		return ResourcesForResearch;
	}
		


	public void consumeMoney() {
		ResourcesForResearch=0; 
		if (ResourcesForResearch < 0) ResourcesForResearch = 0; 
	}


	public double getResearchSkill() {
		return researchSkill;
	}

	public void addResearchSkill(double skill) {
		researchSkill += skill;
	}

	public void addApplyingSkill(double skill) {
		applyingSkill += skill;
	}
	/**
public double getApplyingSkill() {
return applyingSkill;
}
	 */

	public double getSumSkill() {
		return skillsSummedUp;
	}

	/**
	 * Gives the parameter for application quality
	 * @param normalDistributedSkill 1-efectivenessOfFundingProcess/100, is the distribution ratio
	 */
	public void setSkillSum(double normalDistributedSkill) { 
		skillsSummedUp = normalDistributedSkill;//randgenerator.createNormalDistributedValue((researchSkill), normalDistributedSkill); //T�ss� tekee ison muutoksen se jos applying skillin osa on suurempi, se vaikuttaa negatiivisesti tulokseen koska huonot tutkijat p��sev�t julkaisemaan
	}

	public double getApplyingSkill() {
		return applyingSkill;}
	public double justSumSkillsTogetherAndReturnThem() {

		return researchSkill + applyingSkill;
	}


	public int getYearsInAcademia() {
		return yearsInAcedemia;
	}

	public String getName() {
		return name;
	}

	public double getFrustration() {
		return totalFrustration;
	}

	public void addYear() {
		yearsInAcedemia++;
	}
	/*
public void setCitations(double d) {
citations += d;
}

public double getMoney() {
return ResourcesForResearch;
}

/**
	 * sets Monetary frustration, works ok
	 */
//	double values;
	public void setMonetaryFrustration() {

		/**
		 * T�� menis sillein ett� (1-saadut/expected)/7.75
		 */	
		monetaryFrustration += (1-ResourcesForResearch/resourcesNeededToBeEfective)/7;
	}

	/**
	 * sets Promotional frustration, works but needs randomness
	 */
	public void setPromotionalFrustration(){


		if (getPositionInOrganization() == 1 && getYearsInAcademia() > randgenerator.nextInt(2,5)) {
			promotionalFrustration += randgenerator.createRandomDouble()/4;
		}

		if (getPositionInOrganization() == 2 && getYearsInAcademia() > randgenerator.nextInt(8,12)) {
			promotionalFrustration += randgenerator.createRandomDouble()/6;
		}

		if (getPositionInOrganization() == 3 && getYearsInAcademia() > randgenerator.nextInt(10,14)) {
			promotionalFrustration += randgenerator.createRandomDouble()/8;
		}

		if (promotionalFrustration < 0) {promotionalFrustration = 0;} //Frustration cant get negative value

	}
	//TODO Temporary
	public void valiaikanenFrusSet(double frus) {
		totalFrustration = frus;
	}

	/**
	 * Works
	 */
	public void setTotalFrustration() {

		setMonetaryFrustration();
		setPromotionalFrustration();

		totalFrustration = monetaryFrustration+promotionalFrustration;
	}

	public double getResourcesNeededToBeEfective() {
		return resourcesNeededToBeEfective;
	}

	public int getPapers() {
		return papers.size();
	}

	public int getCitations() {
		int citations = 0;
		for (Paper paper: papers){
			citations+= paper.getCitations();
		}

		return citations;
	}
	/*
public void setCitations(int citations) {
this.citations = citations;
}
	 */
	public void setResearchSkill(double skill) {
		this.researchSkill = skill;
	}

	public int getPositionInOrganization() {
		return positionInOrganization;
	}


	public void setPositionInOrganization(int positionInOrganization) { //set level in org and at the same set the expected annually salary
		this.positionInOrganization = positionInOrganization;
		this.promotionalFrustration = 0;
		this.monetaryFrustration = 0;
		//setResourcesNeededToBeEfectiveDependingFromPositionInOrganization();
	}


	public double getProductivity() {
		return productivity;
	}

	/**
	 * Set base and monetary productivity and sums them up
	 */
	public void setProductivity() {
		//	setBaseProductivity();
		//BASEPROD
		this.baseProductivity = (1-getFrustration())+randgenerator.createNormalDistributedValue(0.0,0.1);
		monetaryProductivity =  (ResourcesForResearch/resourcesNeededToBeEfective);
		productivity = baseProductivity+monetaryProductivity;
		//	System.out.println(productivity + " " +baseProductivity + " " +monetaryProductivity + " " + ResourcesForResearch + " " +  resourcesNeededToBeEfective);

	}


	public double getTimeAvailableForResearch() {
		return timeAvailableForResearch;
	}


	public boolean getLeavingOrganization() {
		if (this.sackingProbability >= 1.0 || totalFrustration >= 1.0) {leavingOrganization = true; //System.out.println("pos " + getPositionInOrganization() + " left bcause promo " + promotionalFrustration + " mone " + monetaryFrustration);
		}
		return leavingOrganization;
	}


	/**
	 * 
	 * Sacking probability happens just in first & last level (No tenure & retirement)
	 */
	public void setSackingProbability(double sackingProbability) {
		this.sackingProbability += sackingProbability;
		if (sackingProbability >= 1) System.out.println("SAKKIA " + getPositionInOrganization() + " " + getYearsInAcademia());
	}

	public double getSackingProbability() {
		return sackingProbability;

	}


	/**
	 * Set quality of application and the time used for applying
	 * @param valintaTarkkuus
	 */
	public void setQualityOfApplication (double valintaTarkkuus) {

		double timeUsedOnAverage = 0.11625;

		timeUsedForApplying = (timeUsedOnAverage-timeUsedOnAverage*totalFrustration)*2;
		qualityOfApplication = (Math.sqrt(timeUsedForApplying*researchSkill)*applyingSkill); //+ randgenerator.createNormalDistributedValue(0, 3));
	}

	public void setTimeForResearch() {
	//	System.out.println((ResourcesForResearch + " " + timeUsedForApplying));
		timeAvailableForResearch = 0.2325+ResourcesForResearch-timeUsedForApplying;
	}


	public double getQualityOfApplication() {
		return qualityOfApplication;
	}

	public void setResourcesNeededToBeEfective(double d) {
		if (positionInOrganization == 1) resourcesNeededToBeEfective = 1.3;
		else if (positionInOrganization == 2) resourcesNeededToBeEfective = 1.2;
		else if (positionInOrganization == 3) resourcesNeededToBeEfective = 1.1;
		else resourcesNeededToBeEfective = 1.0;

	}

}