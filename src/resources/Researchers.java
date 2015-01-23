package resources;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


public class Researchers {

	FileRead fileread = new FileRead();
	int resourcesToFundingProcess = 0;
	public ArrayList<Researcher> researcherArray = new ArrayList<>();
	ArrayList<Researcher> TempResearchersToBeAdded = new ArrayList<>();
	ArrayList<Researcher> TempResearchersToBeRemoved = new ArrayList<>();
	public RandomGenerator randomGenerator = new RandomGenerator();
	private int totalCitations;
	private int totalPapers;
	public int citationsFromRemovedResearchers;
	public int papersFromRemovedResearchers;
	double researchersForPosition1, researchersForPosition2,researchersForPosition3,researchersForPosition4;

	public void setLevelsIntheBeginning() {

		if (researcherArray.size() != 0) {
			int temp1=0;
			int temp2=0;
			int temp3=0;
			int temp4=0;

			for(Researcher dude : researcherArray) {
				if (dude.getPositionInOrganization() == 1) {temp1++;}
				if (dude.getPositionInOrganization() == 2) {temp2++;}
				if (dude.getPositionInOrganization() == 3) {temp3++;}
				if (dude.getPositionInOrganization() == 4) {temp4++;}
			}

			//Values of a certain university, how the population is divided
			researchersForPosition1 = (researcherArray.size()*0.15)-temp1; //15% post-docs 
			researchersForPosition2 = (researcherArray.size()*0.15)-temp2; //15% assistant professors
			researchersForPosition3 = (researcherArray.size()*0.37)-temp3; //37% associate professors
			researchersForPosition4 = (researcherArray.size()*0.33)-temp4; //33%  full professors

			if (researcherArray.size() != 0) {
				for(Researcher dude : researcherArray) {
					if (researchersForPosition1 > 0) { //2-4 years
						dude.setPositionInOrganization(1);
						researchersForPosition1--;
					}

					else if (researchersForPosition2 > 0) {
						dude.setPositionInOrganization(2);
						researchersForPosition2--;
					}

					else if (researchersForPosition3 > 0) {
						dude.setPositionInOrganization(3);
						researchersForPosition3--;
					}

					else if (researchersForPosition4 > 0) {
						dude.setPositionInOrganization(4);
						researchersForPosition4--;
					}

					//To set the starting values: higher level, slightly better skills
					if (dude.getPositionInOrganization() == 2) {
						dude.addApplyingSkill(randomGenerator.createNormalDistributedValue(0.05, 0.01));
						dude.addResearchSkill(randomGenerator.createNormalDistributedValue(0.05, 0.01));
					}
					else if (dude.getPositionInOrganization() == 3) {
						dude.addApplyingSkill(randomGenerator.createNormalDistributedValue(0.07, 0.01));
						dude.addResearchSkill(randomGenerator.createNormalDistributedValue(0.07, 0.01));
					}
					else if (dude.getPositionInOrganization() == 4) {
						dude.addApplyingSkill(randomGenerator.createNormalDistributedValue(0.08, 0.01));
						dude.addResearchSkill(randomGenerator.createNormalDistributedValue(0.08, 0.01));
					}		
					//Sets values for the beginning
			//		dude.setResourcesNeededToBeEfectiveDependingFromPositionInOrganization();
				}
			}

		}
	}

	/**
	 * Might need a bit of adjustment
	 */
	public void promote() {

		if (researcherArray.size() != 0) {
			int temp1=0;
			int temp2=0;
			int temp3=0;
			int temp4=0;

			for(Researcher dude : researcherArray) {
//				System.out.println(dude.name + " " + dude.getProductivity());
				if (dude.getPositionInOrganization() == 1) {temp1++;}
				if (dude.getPositionInOrganization() == 2) {temp2++;}
				if (dude.getPositionInOrganization() == 3) {temp3++;}
				if (dude.getPositionInOrganization() == 4) {temp4++;}
			}


			researchersForPosition1 = (researcherArray.size()*0.10)-temp1; //15% post-docs 
			researchersForPosition2 = (researcherArray.size())*0.25-temp2; //15% assistant professor
			researchersForPosition3 = (researcherArray.size()*0.35)-temp3; //37% associate professors
			researchersForPosition4 = (researcherArray.size()*0.20)-temp4; //33%  full professors
			//			System.out.println(" 2 Positions available: " + researchersForPosition2 + " 3Positions available: " + researchersForPosition3 + " 4Positions available: " + researchersForPosition4);
			for(Researcher dude : researcherArray) {

				
		//		System.out.println("dude ei p��ssy:" + dude.getYearsInAcademia() + " " +dude.getCitations() + " "+(getTotalCitations())*0.1/researcherArray.size());

				if (dude.getPositionInOrganization() == 1 && researchersForPosition2 >= 0 && dude.getYearsInAcademia() > randomGenerator.nextInt(1,10) && dude.getCitations() >= ((getTotalCitations())*0.05/researcherArray.size())){
					dude.setPositionInOrganization(2);
			//		System.out.println("Joining: " + dude.getPositionInOrganization() + " " + dude.getYearsInAcademia());
					researchersForPosition2--;
					researchersForPosition1++;
			//		System.out.println("dude p��s:" + dude.getYearsInAcademia() + " " +dude.getCitations() + " "+(getTotalCitations())*0.1/researcherArray.size());

					//				System.out.println("got to 2 " + ((getTotalCitations())/researcherArray.size()) + " Citations: " + dude.getCitations());
				}
				
			
				
				else if (dude.getPositionInOrganization() == 2 && researchersForPosition3 > 0 && dude.getYearsInAcademia() >= randomGenerator.nextInt(10,18) && dude.getCitations() >= ((getTotalCitations())*0.05/researcherArray.size())) {
					dude.setPositionInOrganization(3);
			//		System.out.println("Joining: " + dude.getPositionInOrganization() + " " + dude.getYearsInAcademia());
					researchersForPosition3--;
					researchersForPosition2++;
					//			System.out.println("got to 3 " + ((getTotalCitations())/researcherArray.size()) + " Citations: " + dude.getCitations());
				}

				else if (dude.getPositionInOrganization() == 3 && researchersForPosition4 > 0 && dude.getYearsInAcademia() >= 18){// && dude.getCitations() >= ((getTotalCitations()*2)/researcherArray.size())) {
					dude.setPositionInOrganization(4);
			//		System.out.println("Joining: " + dude.getPositionInOrganization() + " " + dude.getYearsInAcademia());
					researchersForPosition4--;
					researchersForPosition3++;
					//		System.out.println("got to 4 " + ((getTotalCitations()*2)/researcherArray.size()) + " Citations: " + dude.getCitations());
				}
				//	dude.setBaseTimeAvailableForResearchDependingFromPosition(writingCostNotWanted);
		//		dude.setTimeAvailableForResearch(resourcesToFundingProcess);
	//			dude.setAikaaTutkimukseen(resourcesToFundingProcess);
			}
		}
	}
	
/*
	public void resetCitations() {
		totalCitations = 0;
		totalPapers = 0;
	}
	*/


	/**
	 * Adds researcher to organization, but only to bottom position
	 * Could be combined with add&remove researchers
	 */
	public void CountResearchersTobeAddedAndRemoved(int year) {

		int i = 0; //2 100 50% = 1-50 25% 1-25 20% 1-40
		int j = (int) (randomGenerator.nextDouble(1.0, Math.rint(researcherArray.size()*0.088))); //Adds new researchers 19% of the total population every year 
		do{//ehk� 95 tai 97
			
			TempResearchersToBeAdded.add(new Researcher(randomGenerator.nextSessionId() , 0, randomGenerator.createSkill(), randomGenerator.createSkill(), 0, 0, 1));
			i++;
		}while(i <= j);
		
		/**
		 * emit researchers
		 */
		for (Researcher ukkeli : researcherArray) {
			if (ukkeli.getPositionInOrganization() == 1) {
				if (randomGenerator.createRandomDouble() <= 0.23){
					ukkeli.setSackingProbability(randomGenerator.createRandomDouble()/2);
				}
			}

			else if (ukkeli.getPositionInOrganization() == 3 || ukkeli.getPositionInOrganization() == 2) {
				if (randomGenerator.createRandomDouble() <= 0.1){
					ukkeli.setSackingProbability(randomGenerator.createRandomDouble()/2.5);
				}
			}

			else if (ukkeli.getPositionInOrganization() == 4) {
				if (randomGenerator.createRandomDouble() <= 0.1){
					ukkeli.setSackingProbability(randomGenerator.createRandomDouble()/3);
				}
			}

			if (ukkeli.getYearsInAcademia() >= 34) {
				ukkeli.setSackingProbability(randomGenerator.createRandomDouble()/4);
			}
		}
	}


	/**
	 * Remove too frustrated researchers and add new ones. Working.
	 */
	public void addAndRemoveResearchers(int year) {

		//Add researchers
		for(Researcher dude : TempResearchersToBeAdded) { 
	//		System.out.println("Joining: " + dude.getPositionInOrganization() + " " + dude.getYearsInAcademia());
			
			researcherArray.add(dude);
		}
		TempResearchersToBeAdded.clear();

		//Remove researchers
		for (Researcher researcherToBeRemoved : researcherArray) {
			if (researcherToBeRemoved.getLeavingOrganization()) { //If true
	//			if (researcherToBeRemoved.getPositionInOrganization() ==2 || researcherToBeRemoved.getPositionInOrganization() ==3  ) System.out.println(researcherToBeRemoved.getPositionInOrganization() + " left");
	//			System.out.println("Leaving: " + researcherToBeRemoved.getPositionInOrganization() + " " + researcherToBeRemoved.getYearsInAcademia() + " " + researcherToBeRemoved.getFrustration() + " " + researcherToBeRemoved.getSackingProbability() + " monetary "  + researcherToBeRemoved.monetaryFrustration + " " + researcherToBeRemoved.promotionalFrustration );
						//+ " "+ researcherToBeRemoved.monetaryFrustration + " " +researcherToBeRemoved.promotionalFrustration);
				TempResearchersToBeRemoved.add(researcherToBeRemoved); //Remove
				if (year >= 90) {
		//			for (Paper paper : researcherToBeRemoved.papers) {
		//				System.out.println(researcherToBeRemoved.name + " " + paper.toString() + " " + paper.getCitations());
		//			}
				}
			}
		}
		//in two pieces because cannot remove a from a changing sized array
		for (Researcher researcherToBeRemoved : TempResearchersToBeRemoved) {
			citationsFromRemovedResearchers += researcherToBeRemoved.getCitations(); //Save citations to counter from researchers who are leaving
			papersFromRemovedResearchers += researcherToBeRemoved.getPapers();		//Save papers to counter from researchers who are leaving
			researcherArray.remove(researcherToBeRemoved);	
		}
		TempResearchersToBeRemoved.clear();
	}


	/**
	 * 
	 * @param publishYear
	 */
	public void publish(int currentYear) {
		for(Researcher researcher : researcherArray) {

			int paperCount = (int) Math.rint(randomGenerator.createNormalDistributedValue((researcher.getTimeAvailableForResearch()*6.3*researcher.getProductivity()), 1.0));
			//Create papers
			for (int i = 1; i <= paperCount; i++) {
				//	if (year <= 2032)	This is if we want to count the amount of papers after the tail
				researcher.papers.add(new Paper(randomGenerator.createLogDistributedRandomValue(0.0, 1.72),1.0, researcher.getResearchSkill(), currentYear, randomGenerator.createLogDistributedRandomValue(0.0, 0.5), randomGenerator.createLogDistributedRandomValue(0.0, 0.5)));
			}//
//*0.5)   0.0 -> nousee
		//	randomGenerator.createNormalDistributedValue(0.5 , 3)
			paperCount = 0;
			//Create citations
			for (Paper paper : researcher.papers) {
				paper.updateCitations2(currentYear+1);
			}
		}
	}


	/**
	 * Starts the comparing process where frustration and productivity are set
	 * This should be inside the researcher class
	 */
	public void compareReceivedMoney() {

		for(Researcher researcher : researcherArray) {
			researcher.setTotalFrustration();			
			researcher.setProductivity();
		}
	}

	/**
	 * Consumes the given resources
	 
	public void consumefunding() {
		for(Researcher dude : researcherArray) {
			dude.consumeMoney(100000);
			dude.setTimeAvailableForResearch(1);
		}
	}
	*/

	/**
	 * Toimii 24.12
	 */
	public void sortResearchersByApplications(){
		for (Researcher researcher : researcherArray) {
			researcher.setSkillSum(randomGenerator.createNormalDistributedValue(researcher.justSumSkillsTogetherAndReturnThem()/2, ((double) resourcesToFundingProcess/100)));
		System.out.println("resuja: "+resourcesToFundingProcess + " sumskill " + researcher.getSumSkill() +" qalatity "+ researcher.getQualityOfApplication());
		}
		Collections.sort(researcherArray, new Comparator<Researcher>(){
			public int compare(Researcher p1, Researcher p2) {
				if (p1.getSumSkill() < p2.getSumSkill()) return -1;
				if (p1.getSumSkill() > p2.getSumSkill()) return 1;
				return 0;
			}
		});
	}

	/**
	 * T�n metodin vois siirt�� promote-kappaleen alkuun jolloin sit� ei tarttis aina py�r�ytt��
	 * @return
	 */
	public int getTotalCitations() {
		totalCitations = 0;
		for (Researcher dude : researcherArray) {
			totalCitations += dude.getCitations();
		}
		return totalCitations;
	}

	public int getTotalPapers() {
		totalPapers = 0;
		for (Researcher dude : researcherArray) {
			totalPapers += dude.getPapers();
		}
		return totalPapers;
	}

	/**
	 * Toimii
	 */
	public void addYearsInOrganizationForResearchers() { //T�n vois nimet� nollaukseks tjsp
		for (Researcher researcher:researcherArray) {
			researcher.addYear(); //Add year to researcher's track record
			researcher.consumeMoney();
			//researcher.setTimeAvailableForResearch(1);
	//		researcher.setAikaaTutkimukseen(((double) resourcesToFundingProcess/100));
		}
	}
	
	public static void main(String[] args) {
	//	Researcher ukko = new Researcher(name, yearsInAcademia, researchSkill, applyingSkill, citations, ResourcesForResearch, positionInOrganization)
		Researcher ukko = new Researcher("erg", 0, 1, 1, 0, 0, 1);
//		ukko.valiaikanenFrusSet(1);	      //y, R, A, C, R, Pos
	//	ukko.setTimeAvailableForResearchDependingFromMotivation();
	}
	
}
