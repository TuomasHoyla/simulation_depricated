package resources;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;



public class RandomGenerator {

	//	private LogNormalDistribution logNormal;
	Random randgenerator = new Random();
	private static SecureRandom random = new SecureRandom();
	static double r;
	double powerOfNoise = 4.5; //This indicates how much given resources affect to noise in the system; ie. if 3.0, 50% of resources cause 30% noise, and if 4.5, 50% allocation creates 16.3% noise.

	public double createRandomDouble() {
		r =randgenerator.nextDouble();
		return r;
	}

	public double createNormalDistributedValue(double mean, double sDeviation) {
		Random rantsu = new Random();
		double distributedValue =rantsu.nextGaussian()*sDeviation+mean;
		return distributedValue;
	}

	public double createNormalDistributedValue() {
		return randgenerator.nextGaussian();
	}

	/**
	 * For creating random names for researchers
	 * @return
	 */
	public static String nextSessionId(){
		return new BigInteger(40, random).toString(32);
	}

	public int getSkipperNumber() {
		return randgenerator.nextInt();
	}

	/**
	 * The method will split the papers citations for more than 1 year
	 * @param thisYear
	 * @param creationYear
	 * @return
	 */
	/*
	public double createLogNormalCitations(int thisYear, int creationYear) {
		LogNormalDistribution distvalue = new LogNormalDistribution(1.4925,0.5);
		return distvalue.density(thisYear-creationYear+2);
	}
	*/

	/**
	 * 
	 * @param beta, Global parameter
	 * @param skill research skill, fitness
	 * @param t year
	 * @param immediacy How long it takes to reach a peak
	 * @param longevity Decay rate
	 * @return
	 */
	public double Mic(double m, double beta, double skill, int t, double immediacy, double longevity) {
		//No "A"
		NormalDistribution Ndf = new NormalDistribution();
		double ni = skill;
		double b = beta;
		return m*(Math.pow(Math.E, b*ni*Ndf.density((Math.log(t)-immediacy)/longevity))-1);
	}		//M											  beta , skill															t
//new Paper(createLogDistributedRandomValue(0.0, 0.5),1.0, (researcher.getResearchSkill()*researcher.getResearchSkill()), currentYear, 1, 1));


	/**
	 * 
	 * @param number Divide double n to random parts and add those parts to array 
	 * @param part array of randomly divided numbers
	 * @return
	 */
	public double[] divideUniformlyRandomly(double number, int part) {
		double uniformRandoms[] = new double[part];
		Random random = new Random();

		double mean = number / part;
		double sum = 0.0;

		for (int i=0; i<part / 2; i++) {
			uniformRandoms[i] = random.nextDouble() * mean;
			uniformRandoms[part - i - 1] = mean + random.nextDouble() * mean;
			sum += uniformRandoms[i] + uniformRandoms[part - i -1];
		}
		uniformRandoms[(int)Math.ceil(part/2)] = uniformRandoms[(int)Math.ceil(part/2)] + number - sum;
		return uniformRandoms;
	}


	public static double logb( double a, double b )
	{
		return Math.log(a) / Math.log(b);
	}

	public double log1_5( double a )
	{
		return logb(a,1.5);
	}
	

	/**
	 * Creates exponential array for user interface
	 * @param x
	 * @param linear
	 * @param curve
	 * @return
	 */
	public double fExp(double x, double linear, double curve) {
		return Math.exp(x*linear)*curve;


	}
	/**
	 * Creates linear array for user interface
	 * @param slope
	 * @param x
	 * @param Intercept
	 * @return
	 */
	public double fLin(double slope, double x, double Intercept) {
		return slope*x+Intercept; // mx+b
	}

	/**
	 * 
	 * @param location μ, 0
	 * @param scale σ , 0.25
	 * @return distributed value
	 */
	public double createLogDistributedRandomValue(double location, double scale) {
		double value = new LogNormalDistribution(location,scale).sample(); //0, 0.25
//		double value = new NormalDistribution(location, scale).sample();
		return value;
	}
	

	/**Returns logNormal distributed value between 0-inf, usually around  1. -0.0637 is because otherwise the average would be that much over one.
	 * 
	 * @return Skill (Applying or research, can be used for both)
	 */
	public double createSkill() {

		return createLogDistributedRandomValue(0.0, 0.35)-0.0637;
	}
	
	/**
	 * Sub-method for Mic Model to create beta value
	 * @return random beta between 0 and 4.164, mean ~ 1
	 */
	private double createBetaForMic() {
		return createLogDistributedRandomValue(0.0, 0.29)-0.043;
	}

	/**
	 * Creates a random value between given border values
	 * @param start first number
	 * @param end last number
	 * @return
	 */
	public int nextInt(int start, int end) {
		return (int)(start+Math.random()*end);
	}

	public double nextDouble(double start, double end) {
		return (start+Math.random()*end);
	}

	/**
	 * How much noise is created with the resources given to the system
	 * @param resources = assigned resources for applying process from total amount of resources
	 * @return
	 
	public double noiseCalculator(double resources) {
		//Here possibly the amount of resources reduce
		return 1/(Math.pow(1+resources, powerOfNoise));
	}
	*/
	/**
	 * This noisecalculator calculates the resources for each position separately depending from the noise
	 * @param resources
	 * @return
	 
	public double noiseCalculator2(double resources, int position) {
		//Here possibly the amount of resources reduce
		//TODO EN OO VARMA TARVIIKO T�ATA EES
		//	y = ax^2+bx
		return 1/(Math.pow(1+resources, powerOfNoise));
	}
	*/

	public static void main(String[] args) {
		/**
		RandomGenerator rantsu = new RandomGenerator();
		for (int i = 0; i < 20; i++) {
		System.out.println(rantsu.createNormalDistributedValue(0, 0.1));
		}
		 **/

		RandomGenerator rantsu = new RandomGenerator();
		//rantsu.Mic(m, beta, skill, t, immediacy, longevity)
		double citations = 0;
		double skill = 2;
		
		Paper poo = new Paper(1, 1, skill, 0, 1, 1);
		
		for (int i = 0; i <= 20;i++){
		poo.updateCitations2(i);
		int val = poo.getCitations();
	
		//	System.out.println(val +" " + rantsu.Mic(1, 1, skill, i, 1, 1));
		citations +=val;
		}
		System.out.println(citations);
	//	NormalDistribution Ndf = new NormalDistribution();
	//	System.out.println(rantsu.Mic(1, 4.164, 5.42857, 9, 3.5, 1.19));
		/*
		int i = 0;
		double skilli = 0;
		double rans = 0;
		do {
			rans = rantsu.createSkill();
		if (rans > skilli) skilli = rans;
		System.out.println(skilli);
		} while (i <= 2);
	


		*/
		/*
		double best = 0;
		
		for (int i = 0; i < 500000;i++) {
			double j = rantsu.createNormalDistributedValue(1,0.1);
			best += j;
			
		}
		
		System.out.println(best/500000);
		*/
	//	System.out.println(rantsu.createBetaForMic());
		
		//System.out.println(rantsu.createNormalDistributedValue(2, (double) 30/100));
	

	}

	public double createResSkill() {
		
	//	randgenerator.
		
		
		
		return 2*random.nextDouble()*createLogDistributedRandomValue(0, 0.2);
		 
//		return random.nextDouble();
		
		
	}


}
