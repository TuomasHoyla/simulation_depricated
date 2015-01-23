package resources;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

//EXTENDS SIMULATION?
public class FundingProvider  {
	public Vector<Funding> fundings = new Vector<Funding>();
	public Vector<Double> fundingDistribution=new Vector<Double>();
	int totalFundings;
	RandomGenerator randomGenerator = new RandomGenerator();

	/**
	 * This is only temporary, below is the real one (but old, might be better)
	 * @param researchers
	 * @param funding
	 * @param writingcostNotWanted
	 * @param randomize
	 
	private void EligbleForFunding(Researchers researchers, Funding funding) { //T�n nimeks vois vaihtaa Call for funding

		//	if (!randomize && !writingcostNotWanted) {

		//When applying process is turned on, we have a rough estimate about researchers skills and to who it is the best idea to give the money
		researchers.sortResearchersByApplications();

		//	for (Researcher researcher : researchers.researcherArray) {
		//		if (researcher.getPositionInOrganization() >= funding.getLevelRequiredForProposal() && researcher.getResearchSkill() >= funding.getSkillRequired()) {
		//		researcher.SetEligbleForFunding(true);
		//This is new one..
		//		if (fundings.size() > 0) researcher.reduceTimeAvailableForResearch();
		//...These have been disabled because of the one value that we use instead
		//		if (fundings.size() > 0) researcher.reduceTimeAvailableForResearch(timeConsumptionOfWriting(researcher.getPositionInOrganization()));
		//		if (researcher.getPositionInOrganization() < 4) researchers.increaseReadingTimeForOther(researcher.getPositionInOrganization());
		//		if (researcher.getPositionInOrganization() == 4) researcher.reduceTimeAvailableForResearch(0.0015); //If position = 4, reduce reading time from yourself
		//			}
	}
	*/
	//	}
	/*
		else {

			//When applying process is turned off, we have no clue which applicants should deserve the money
			Collections.shuffle(researchers.researcherArray);

			for (Researcher researcher : researchers.researcherArray) {
				if (researcher.getPositionInOrganization() >= funding.getLevelRequiredForProposal() && researcher.getResearchSkill() >= funding.getSkillRequired()) {
					researcher.SetEligbleForFunding(true);
				}
			}
		}
	 */
	//	}

	/**
	 * 	Calculates how much time person spends time for writing each proposal
	 * @param positionInOrganization
	 * @return time to reduce from total time available for research

	private double timeConsumptionOfWriting(int positionInOrganization) {
		double randomValueForConsumption = randomGenerator.createNormalDistributedValue(1.0,0.2);
		if 		(positionInOrganization == 1) return (0.200/fundings.size())*randomValueForConsumption;
		else if (positionInOrganization == 2) return (0.115/fundings.size())*randomValueForConsumption; 
		else if (positionInOrganization == 3) return (0.076/fundings.size())*randomValueForConsumption;
		else if (positionInOrganization == 4) return (0.073/fundings.size())*randomValueForConsumption;
		else return 0;
	}
	 */
	/**
	 * Toimii, mutta katso kommentit
	 * @param researchers
	 */
	private void giveFunds(Researchers researchers, Funding funding) {
		
		researchers.sortResearchersByApplications();

		//This is to count who applied and distribute more resources if someone did not apply...
		double hakijacount = 0;
		for (Researcher researcher : researchers.researcherArray) {
	//		if (researcher.getEligbleForFunding()) hakijacount++;
		}
		funding.setMoney(funding.getOriginalResources()*(researchers.researcherArray.size()/hakijacount)); //T�t� vois viel� mietti�, hakijacount voi olla nolla!
		//...It ends here.

		//Goes through every assigned funding
		//		for (Funding eachFunding : fundings) {
		if (researchers.researcherArray.size() >= 0) {

			int i = 0, j = 0, tekija = researchers.researcherArray.size()*fundingDistribution.size(), 
					factorSlot = tekija/fundingDistribution.size(); //Factorslot on sama kun researchers.researcherArray.size()!
			Vector<Double> temp=new Vector<Double>();

			if (factorSlot != 0 && fundingDistribution.size() != 0) {
			}
		}
		//		do{    
			//		temp.add(fundingDistribution.get(j)/factorSlot); 
			//		i++;
			//		if ((i %factorSlot)==0){ //If modulo is the end of new slot
			//			j++;
					} //move to next slot
		//		}while (i <= tekija-1);
			//	i = 0;
				/*
				 *  (Researcher ukko :researchers.researcherArray) {

					do {
	//					if (ukko.getEligbleForFunding()) {
							if (temp.get(i) < 0) {temp.set(i, 0.0);}
							ukko.setMoney(funding.giveResources(temp.get(i)));
	//					}
						else if (!ukko.getEligbleForFunding()) {
						}
						i++;						
						if ((i %fundingDistribution.size())==0){ //If modulo is the end of new slot
							break; 
						}
//					} while (i <= tekija); //TODO Täällä tapahtuu
					//		System.out.println(funding.getOriginalResources() + " " + ukko.getMoney());
				} 
				funding.resetFundingCall(); 
	//		}
		}
	}
			*/
	//	}

	/**
	 * Toimii taysin hyvin
	 * @param researchers

	public void giveFundsRandomly(Researchers researchers) {
		//	int sum = 0;

		for (Funding funding : fundings) {

			if (researchers.researcherArray.size() > 0) {
				double moneyThatWillGiveRandomly = funding.getOriginalResources();
				double[] rahat = null;
				rahat = randomGenerator.divideUniformlyRandomly(moneyThatWillGiveRandomly, researchers.researcherArray.size());
				//If in graph we allocate that this guy tries for money, but he can't have it because of the lack of skill, will the money move for next one?
				Collections.shuffle(researchers.researcherArray);

				//T�ss� on v�h�n omaa...
				Random rgen = new Random();  // Random number generator
				for (int i=0; i < rahat.length; i++) {
					int randomPosition = rgen.nextInt(rahat.length);
					double temp = rahat[i];
					rahat[i] = rahat[randomPosition];
					rahat[randomPosition] = temp;
				}
				//..loppuu t�h�n - shuflettaa rahat arrayss�

				for (int i = 0; i < rahat.length; i++) {
					researchers.researcherArray.get(i).setMoney(rahat[i]);
				}
			}
		}
	}
	 */

	public void ShareFunding(Researchers researchers) {


		for (Funding funding : fundings) { //For each funding...
			//			if (!randomize && !writingCostNotWanted) {
	//		EligbleForFunding(researchers, funding); 
			giveFunds(researchers, funding); //If resources are dealt as wanted
			//			}
			/*
			else if (writingCostNotWanted && !randomize) {
				Collections.shuffle(researchers.researcherArray); //Shuffle and deal
				funding.setSkillRequired(0); //Because resources are given randomly, there cannot be minimum skill that is required
				EligbleForFunding(researchers, funding, writingCostNotWanted, randomize);
				giveFunds(researchers, funding, writingCostNotWanted);
			}
			else {
				funding.setSkillRequired(0); //Because resources are given randomly, there cannot be minimum skill that is required
				giveFundsRandomly(researchers); //Resouces are given completely randomly for the whole population

			}
			 */
		}
	}
}