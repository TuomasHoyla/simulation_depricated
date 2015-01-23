package ui;

/**
 * TÄÄ ON SE FILE JOTA AJETAAN, 
 * 11.12.2014: 
 * otettu liian suuri satunnaisuus hakemuksen laadusta pois
 * Muokattu siten että tyypit järjestetään hakemusten perusteella eikä skillin perusteella
 * 
 */

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;





import resources.*;

public class simulation {

	FileRead fileread = new FileRead();
	DecimalFormat df = new DecimalFormat("#.##");
	applyForFunds funder = new applyForFunds();
	RandomGenerator randomGenerator = new RandomGenerator();
	public ArrayList<Researcher> researcherArray = new ArrayList<>();
	double researchersForPosition1, researchersForPosition2,researchersForPosition3,researchersForPosition4;
	ArrayList<Researcher> TempResearchersToBeAdded = new ArrayList<>();
	ArrayList<Researcher> TempResearchersToBeRemoved = new ArrayList<>();
	public ArrayList<Integer> cumuPaperCounter = new ArrayList<Integer>();
	public ArrayList<Integer> cumuCitationCounter = new ArrayList<Integer>();
	int addableCitations;
	int addablePapers;
	int totalCitations;
	int totalPapers;
	int year;
	private int papersFromRemovedResearchers;
	private int citationsFromRemovedResearchers;
	private double overhead = 0; // 0 - 100%
	private double kuinkaPaljonJaetaanTasan = 1; //0%-100%
	private double maksimiTutkimusResurssi = 0.63;
	private double kuinkaPaljonMaksimiTutkimisResurssistaHalutaan = 1.0;

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
					//	dude.setResourcesNeededToBeEfectiveDependingFromPositionInOrganization(maksimiTutkimusResurssi);
				}
			}

		}
	}
	public void CountResearchersTobeAddedAndRemoved(int year) {

		int i = 0; //2 100 50% = 1-50 25% 1-25 20% 1-40
		int j = (int) (randomGenerator.nextDouble(1.0, Math.rint(researcherArray.size()*0.088))); //Adds new researchers N% of the total population every year 
		do{
		//	double luku = new randomGenerator.createNormalDistributedValue(1, 1);
			TempResearchersToBeAdded.add(new Researcher(RandomGenerator.nextSessionId() , 0, randomGenerator.createResSkill(), randomGenerator.createSkill(), 0, 0, 1));
		//	TempResearchersToBeAdded(new Researcher(name, yearsInAcademia, researchSkill, applyingSkill, citations, ResourcesForResearch, positionInOrganization))
			i++;
		}while(i <= j);

		/**
		 * emit researchers
		 */
		for (Researcher ukkeli : researcherArray) {

				ukkeli.consumeMoney();
				
			ukkeli.addYear();
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
	 * @param citationsFromRemovedResearchers 
	 */
	public void addAndRemoveResearchers() {

		//Add researchers
		for(Researcher dude : TempResearchersToBeAdded) { 

			researcherArray.add(dude);
		}
		TempResearchersToBeAdded.clear();

		//Remove researchers

		for (Researcher researcherToBeRemoved : researcherArray) {
			//        for (Paper paper: researcherToBeRemoved.papers) {
			//        System.out.println(paper.getCitations());}

			if (researcherToBeRemoved.getLeavingOrganization()) { //If true
				//	      System.out.println("Leaving: " + researcherToBeRemoved.getPositionInOrganization() + " " + researcherToBeRemoved.getYearsInAcademia() + " " + researcherToBeRemoved.getFrustration() + " " + researcherToBeRemoved.getSackingProbability() + " monetary "  + researcherToBeRemoved.monetaryFrustration + " " + researcherToBeRemoved.promotionalFrustration );
				TempResearchersToBeRemoved.add(researcherToBeRemoved); //Remove
			}
		}
		//in two pieces because cannot remove a from a changing sized array
		for (Researcher researcherToBeRemoved : TempResearchersToBeRemoved) {
			//	System.out.println(researcherToBeRemoved.getYearsInAcademia());
			//	if (researcherToBeRemoved.getYearsInAcademia() == 20) System.out.println(researcherToBeRemoved.getCitations());
			citationsFromRemovedResearchers += researcherToBeRemoved.getCitations(); //Save citations to counter from researchers who are leaving
			papersFromRemovedResearchers += researcherToBeRemoved.getPapers();        //Save papers to counter from researchers who are leaving
			researcherArray.remove(researcherToBeRemoved);  
			
	//		System.out.println(researcherToBeRemoved.getResearchSkill() + " "+ researcherToBeRemoved.getPapers() + " " + researcherToBeRemoved.getCitations());

			
			/*
			for (Paper paper : researcherToBeRemoved.papers) {
				System.out.println(researcherToBeRemoved.getResearchSkill() + " "+ researcherToBeRemoved.getPapers() + " " + paper.getCitations());
			}*/
		}
		TempResearchersToBeRemoved.clear();
	}

	public void promote() {


		if (researcherArray.size() != 0) {
			int temp1=0;
			int temp2=0;
			int temp3=0;
			int temp4=0;

			for(Researcher dude : researcherArray) {
				
		//		dude.timeAvailableForResearch = 0;
		//		dude.ResourcesForResearch = 0;
				
				//                System.out.println(dude.name + " " + dude.getProductivity());
				if (dude.getPositionInOrganization() == 1) {temp1++;}
				if (dude.getPositionInOrganization() == 2) {temp2++;}
				if (dude.getPositionInOrganization() == 3) {temp3++;}
				if (dude.getPositionInOrganization() == 4) {temp4++;}
			}



			researchersForPosition1 = (researcherArray.size()*0.10)-temp1; //15% post-docs 
			researchersForPosition2 = (researcherArray.size())*0.25-temp2; //15% assistant professor
			researchersForPosition3 = (researcherArray.size()*0.35)-temp3; //37% associate professors
			researchersForPosition4 = (researcherArray.size()*0.20)-temp4; //33%  full professors

			//This section sorts the researchers so that the highest cited are first ones in the line
			Collections.sort(researcherArray, new Comparator<Researcher>(){
				public int compare(Researcher p1, Researcher p2) {
					if (p1.getCitations() < p2.getCitations()) return -1;
					if (p1.getCitations() > p2.getCitations()) return 1;
					return 0;
				}
			});

			Collections.reverse(researcherArray);
			//..Here

			for(Researcher dude : researcherArray) {


				if (dude.getPositionInOrganization() == 1 && researchersForPosition2 >= 0 && dude.getYearsInAcademia() > randomGenerator.nextInt(1,10) && dude.getCitations() >= ((getTotalCitations())*0.05/researcherArray.size())){
					dude.setPositionInOrganization(2);
					researchersForPosition2--;
					researchersForPosition1++;
				}

				else if (dude.getPositionInOrganization() == 2 && researchersForPosition3 > 0 && dude.getYearsInAcademia() >= randomGenerator.nextInt(10,18) && dude.getCitations() >= ((getTotalCitations())*0.05/researcherArray.size())) {
					dude.setPositionInOrganization(3);
					researchersForPosition3--;
					researchersForPosition2++;
				}

				else if (dude.getPositionInOrganization() == 3 && researchersForPosition4 > 0 && dude.getYearsInAcademia() >= 18){// && dude.getCitations() >= ((getTotalCitations()*2)/researcherArray.size())) {
					dude.setPositionInOrganization(4);
					researchersForPosition4--;
					researchersForPosition3++;
				}
			}
		}
	}

	public void overheadAllocation() {

		double valintaTarkkuus = (1.05-Math.sqrt(overhead))*0.6; //T�� menee suoraan TODO T�� ON YKS TUTKITTAVA TEKIJ�
		double nettoTutkimusResurssit = (1-overhead)*maksimiTutkimusResurssi*35; //Joita haetaan tutkimukseen (t�st� on jo poistettu opetus yms.)
		//=0
		double varmatTutkimusResurssit = nettoTutkimusResurssit*kuinkaPaljonJaetaanTasan; //Jotka jaetaan tasaisesti populaation kesken
		//=0
		double varmatTutkimusResurssitPerTutkija = varmatTutkimusResurssit/researcherArray.size();

		//T�ss� asetetaan funderiin rahanjakomekanismit
		funder.funds.funding =(nettoTutkimusResurssit-varmatTutkimusResurssit); //Ep�vermat Resurssit kokonaisuudessaan
		funder.funds.varmaFunding = varmatTutkimusResurssitPerTutkija;

		//T�ss� forrissa asetetaan hakemuksen laatu ja hakuaika
		for (Researcher researcher: researcherArray) {
			researcher.setQualityOfApplication(valintaTarkkuus);
	//		System.out.println(researcher.getQualityOfApplication());
		}

		//Sitten jaetaan tutkijat jonoon hakemuksien perusteella
		Collections.sort(researcherArray, new Comparator<Researcher>(){
			public int compare(Researcher p1, Researcher p2) {
				if (p1.getQualityOfApplication() < p2.getQualityOfApplication()) return -1;
				if (p1.getQualityOfApplication() > p2.getQualityOfApplication()) return 1;
				return 0;
			}
		});
		Collections.reverse(researcherArray);
		
		//lopuksi jaetaan rahat ja asetetaan tutkimusaika
		for (Researcher researcher: researcherArray) {
			researcher.setResourcesNeededToBeEfective(kuinkaPaljonMaksimiTutkimisResurssistaHalutaan);
			researcher.setMoney(varmatTutkimusResurssitPerTutkija); //t�ss� haetaan resut joita ei tartte sen enemp�� hakee
			funder.receiveResearcher(researcher, kuinkaPaljonMaksimiTutkimisResurssistaHalutaan-varmatTutkimusResurssitPerTutkija);
			researcher.setTimeForResearch();
		}
	}

	public void publish(int currentYear) {
		
		int tuloste = 0;
		
		for(Researcher researcher : researcherArray) {
			
				tuloste += researcher.getResourcesForResearch();
				
			int paperCount = (int) Math.rint(randomGenerator.createNormalDistributedValue((researcher.getTimeAvailableForResearch()*6.3*researcher.getProductivity()), 1));
			//	System.out.println(researcher.getTimeAvailableForResearch() + " " + researcher.getProductivity() + " "+ researcher.ResourcesForResearch + " " + paperCount);
			if (paperCount < 0) paperCount = 0; //Prevents negative counts
			//Create papers TODO Nyt j��tiin siihen ett� noita sitaatteja vois muodostua sen perusteella paremmin mit� on tutkijan skillit
			for (int i = 1; i <= paperCount; i++) {
				//	if (year <= 2032)	This is if we want to count the amount of papers after the tail
				researcher.papers.add(new Paper(randomGenerator.createLogDistributedRandomValue(0.0, 0.5),1.0, (researcher.getResearchSkill()*researcher.getResearchSkill()), currentYear, 1, 1));
			}//

			paperCount = 0;
			//Create citations
			for (Paper paper : researcher.papers) {
				paper.updateCitations2(currentYear+1);
				//N�� on vaan testi� varten kuinka paljon voi tulla maksimisitaatteja
				//	if (paper.getCitations() > maxCitation) {maxCitation = paper.getCitations(); maxResSkill = researcher.getResearchSkill();}
				//	if (researcher.getResearchSkill() > maxcurrentResSkill) maxcurrentResSkill = researcher.getResearchSkill();
			//		System.out.println(maxCitation + " " +maxResSkill + " " + maxcurrentResSkill + " "+ addableCitations);
			}
		}
	//	System.out.println(tuloste);
	}

	private void compareReceivedMoney() {
		for (Researcher researcher : researcherArray) {
			researcher.setTotalFrustration();
			researcher.setProductivity();
		}

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
	 * Fill with researchers
	 */
	private void addResearchersToArray() {
		for (int i = 0; i <= 49; i++){
			researcherArray.add(new Researcher(randomGenerator.nextSessionId(), 0, randomGenerator.createResSkill(), randomGenerator.createSkill(), 0, 0, 1));
		}
	}

	public double simulate()  {

		addResearchersToArray();
		setLevelsIntheBeginning();

		do {

			do{


				CountResearchersTobeAddedAndRemoved(year);
				addAndRemoveResearchers();
				promote();

				//This just happens once, reset ÄLÄ KOSKE
				if (year == 200) {
					citationsFromRemovedResearchers = 0;
					papersFromRemovedResearchers = 0;
					for (Researcher researcher : researcherArray) {
						//This is just for cutting the year...
						researcher.papers.clear();
					}
				}

				overheadAllocation(); //Allocate resources, noise, etc.
				compareReceivedMoney(); //Compare what got and what not, sets frustration and productivity
				publish(year);
				year++;
					addableCitations = getTotalCitations()+citationsFromRemovedResearchers;
			//	addableCitations = citationsFromRemovedResearchers;
				addablePapers = getTotalPapers() + papersFromRemovedResearchers;
				cumuCitationCounter.add(addableCitations);
				if ((year % 200) == 0) {

					citationsFromRemovedResearchers = 0;
					papersFromRemovedResearchers = 0;
					//	addablePapers= 0;
					cumuCitationCounter.clear();
					
					for (Researcher researcher : researcherArray) {
						//This is just for cutting the year...
						researcher.papers.clear();

					}			
				//		System.out.println(addableCitations);
					try {
						fileread.writeLines2(addableCitations+"",kuinkaPaljonJaetaanTasan, overhead); //addablePapers + " " + 
					} catch (FileNotFoundException | UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					addableCitations = 0;
					addablePapers = 0;

				}


			}while (year <= 32000);
			kuinkaPaljonJaetaanTasan += 0.1;
			//	System.out.println("**** " + (kuinkaPaljonJaetaanTasan));
			year = 0;
		}while (kuinkaPaljonJaetaanTasan <= 1.09);



		return totalCitations;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		simulation test = new simulation();

		test.simulate();


	}

}