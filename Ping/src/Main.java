
public class Main {
	public static void main(String[] args) {
		int[] pingVKcom = {4,7,5,5,9,5,7,9,6,14,4,4,4,8,7,5,10,8,8,6,12,7,19,14,6,
				           4,11,12,6,8,5,4,5,17,10,6,13,7,6,10,4,8,6,9,9,7,7,7};
		int[] pingYAru = {16,28,17,22,19,19,16,16,16,29,16,15,20,26,19,21,16,19,19,22,31,16,19,19,
				          16,19,20,17,16,19,29,17,21,18,47,23,19,15,16,16,19,16,23,17,17,20,15,17};  
		
		double averTimeVK = 0;
		double averTimeYA = 0;
		
		for (int i : pingVKcom) {
			averTimeVK += i;
		}
		averTimeVK /= pingVKcom.length;
		
		for (int i : pingYAru) {
			averTimeYA += i;
		}
		averTimeYA/= pingYAru.length;
		
		double sumVk = 0;
		
		for (int t : pingVKcom) {
			sumVk += (averTimeVK - t) * (averTimeVK - t);
		}
		double deltaVK = 2 * Math.sqrt(sumVk / (pingVKcom.length * (pingVKcom.length - 1))); //2 дельта с.о.с нужно для доверительного интервала с порогом доверия 95%
		
		double sumYA = 0;
		
		for (int t : pingYAru) {
			sumYA += (averTimeYA - t) * (averTimeYA - t);
		}
		double deltaYA = 2 * Math.sqrt(sumYA / (pingYAru.length * (pingYAru.length - 1))); //2 дельта с.о.с нужно для доверительного интервала с порогом доверия 95%
		
		
		//double topBound = (averTimeYA - averTimeVK) + (Math.sqrt(deltaVK * deltaVK + deltaYA * deltaYA));
		double lowBound = (averTimeYA - averTimeVK) - (Math.sqrt(deltaVK * deltaVK + deltaYA * deltaYA));
		
		if (lowBound > 0) {
			System.out.println("calculations show that it makes sense to consider the hypothesis that ping from VK.com is less than from Ya.ru");
		}
		else {
			System.out.println("calculations show that we cannot reject the hypothesis that the ping of the site ya.ru is less than that of VK.com");
		}
	}
}
