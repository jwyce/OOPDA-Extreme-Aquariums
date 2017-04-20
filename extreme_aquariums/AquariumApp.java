package extreme_aquariums;

import java.util.ArrayList;
import java.util.Random;

public class AquariumApp {
	
	private static CustomerTank customerTank;
	private static Random rand = new Random();

	public static void main(String[] args) {
		ArrayList<Fish> availableFish = new ArrayList<Fish>(18);
		createAvailableFish(availableFish);
		
		if(rand.nextBoolean()) {
			customerTank = new CustomerTank("resources/fishtank.png");
		} else {
			customerTank = new CustomerTank("resources/tank2.png");
		}
		
		new FishShop(availableFish);
	}
	
	public static void openExistingTank(CustomerTank tank) {
		customerTank = tank;
	}
	
	public static CustomerTank getTank() {
		return customerTank;
	}
	
	private static void createAvailableFish(ArrayList<Fish> availableFish) {
		availableFish.add(new FreshwaterFish("Algae Eater", Fish.Behavior.peaceful, Fish.SwimmingArea.bottom, "resources/freshwater/algaeeater.png"));
		availableFish.add(new FreshwaterFish("Betta", Fish.Behavior.aggressive, Fish.SwimmingArea.top, "resources/freshwater/bettafish.png"));
		availableFish.add(new FreshwaterFish("Cichlid", Fish.Behavior.semiaggressive, Fish.SwimmingArea.top, "resources/freshwater/cichlid.png"));
		availableFish.add(new FreshwaterFish("Discus", Fish.Behavior.peaceful, Fish.SwimmingArea.midlevel, "resources/freshwater/discusfish.png"));
		availableFish.add(new FreshwaterFish("Goldfish", Fish.Behavior.peaceful, Fish.SwimmingArea.midlevel, "resources/freshwater/goldfish.png"));
		availableFish.add(new FreshwaterFish("Guppy", Fish.Behavior.peaceful, Fish.SwimmingArea.bottom, "resources/freshwater/guppy.png"));
		availableFish.add(new FreshwaterFish("Mandarin", Fish.Behavior.aggressive, Fish.SwimmingArea.midlevel, "resources/freshwater/mandarinfish.png"));
		availableFish.add(new FreshwaterFish("Rainbow Fish", Fish.Behavior.peaceful, Fish.SwimmingArea.bottom, "resources/freshwater/rainbowfish.png"));
		availableFish.add(new FreshwaterFish("Swordtail", Fish.Behavior.semiaggressive, Fish.SwimmingArea.bottom, "resources/freshwater/swordtail.png"));
		availableFish.add(new FreshwaterFish("Tetra", Fish.Behavior.peaceful, Fish.SwimmingArea.top, "resources/freshwater/tetra.png"));
		
		availableFish.add(new SaltwaterFish("Angelfish", Fish.Behavior.peaceful, Fish.SwimmingArea.bottom, "resources/saltwater/angelfish.png"));
		availableFish.add(new SaltwaterFish("Bannerfish", Fish.Behavior.semiaggressive, Fish.SwimmingArea.midlevel, "resources/saltwater/bannerfish.png"));
		availableFish.add(new SaltwaterFish("Clownfish", Fish.Behavior.peaceful, Fish.SwimmingArea.bottom, "resources/saltwater/clownfish.png"));
		availableFish.add(new SaltwaterFish("Eel", Fish.Behavior.aggressive, Fish.SwimmingArea.bottom, "resources/saltwater/eel.png"));
		availableFish.add(new SaltwaterFish("Lionfish", Fish.Behavior.aggressive, Fish.SwimmingArea.midlevel, "resources/saltwater/lionfish.png"));
		availableFish.add(new SaltwaterFish("Pufferfish", Fish.Behavior.semiaggressive, Fish.SwimmingArea.top, "resources/saltwater/pufferfish.png"));
		availableFish.add(new SaltwaterFish("Royal Gramma", Fish.Behavior.peaceful, Fish.SwimmingArea.top, "resources/saltwater/royalgramma.png"));
		availableFish.add(new SaltwaterFish("Starfish", Fish.Behavior.peaceful, Fish.SwimmingArea.midlevel, "resources/saltwater/starfish.png"));
		availableFish.add(new SaltwaterFish("Blue Tang", Fish.Behavior.peaceful, Fish.SwimmingArea.top, "resources/saltwater/tang.png"));
	}

}
