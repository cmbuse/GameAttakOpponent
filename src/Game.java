import java.util.Random;

public class Game {

	public static void runGame() {
		PlayerStatus p1 = new PlayerStatus();
		PlayerStatus p2 = new PlayerStatus();
		p1.initPlayer("Ely", 3, 20000);
		p2.initPlayer("Jane", 3, 20000);

		final double mapSizeX = 2000;
		final double mapSizeY = 2000;
		Random r = new Random();

		System.out.println(" Incepe jocul " + PlayerStatus.getGameName() + " dintre " + p1.getNickname() + " si "
				+ p2.getNickname());
		System.out.println("---------------------------------------------");
		System.out.println(p1.getNickname() + " are " + p1.getLives() + " lives si " + p1.getHealth() + " health "
				+ p1.getScore() + " scor.");
		System.out.println(p2.getNickname() + " are " + p2.getLives() + " lives si " + p2.getHealth() + " health "
				+ p2.getScore() + " scor.");
		System.out.println("---------------------------------------------");

		while (!p1.isDead() && !p2.isDead()) {
			// while( p1.getLives() <= 0 && p2.getLives() <= 0) {
			double newPositionX = r.nextDouble() * mapSizeX;
			double newPositionY = r.nextDouble() * mapSizeY;
			p1.setPositionX(newPositionX);
			p1.setPositionX(newPositionY);
			newPositionX = r.nextDouble() * mapSizeX;
			newPositionY = r.nextDouble() * mapSizeY;
			p2.setPositionX(newPositionX);
			p2.setPositionY(newPositionY);
			//p1.movePlayerTo(newPositionX, newPositionY));
			//p2.movePlayerTo(newPositionX, newPositionY);

			p1.findArtifact(findeRandomArtifact(r));
			p2.findArtifact(findeRandomArtifact(r));

			double theDistanceBetween = p1.calculateDistance(p2.getPositionX(), p2.getPositionY());
			giveWeaponInHand(theDistanceBetween, p1);
			giveWeaponInHand(theDistanceBetween, p2);

			if (p1.shouldAttackOpponent(p2)) {
				int injury = p1.calculateInjure(p2);
				System.out.println();
				System.out.println(p1.getNickname() + " ataca oponentul. ");
				p2.applyInjure(injury);
				System.out.println(p2.getNickname() + " a suferit o avarie de " + injury + ".  Mai are " + p2.getLives()
						+ " lives " + p2.getHealth() + " health");
				System.out.println();

			} else if (p2.shouldAttackOpponent(p1)) {
				int injury = p2.calculateInjure(p1);
				System.out.println();
				System.out.println(p2.getNickname() + " ataca oponentul. ");
				p1.applyInjure(injury);
				System.out.println(p1.getNickname() + " a suferit o avarie de " + injury + ".  Mai are " + p1.getLives()
						+ " lives " + p1.getHealth() + " health. ");
				System.out.println();
			}

			System.out
					.print(p1.getNickname() + " are " + p1.getScore() + " scor si arma " + p1.getWeaponInHand() + ". ");
			System.out.println(
					p2.getNickname() + " are " + p2.getScore() + " scor si arma " + p2.getWeaponInHand() + ". ");
		}
		if (p1.isDead()) {
			int injury = p1.getHealth();
			System.out.println(p1.getNickname() + " a suferit o avarie de " + injury);
			System.out.println("Jocul " + PlayerStatus.getGameName() + " s-a terminat! " + p1.getNickname()
					+ " a murit!\nCastigatorul este " + p2.getNickname());
			PlayerStatus.gameOver();
		}
		if (p2.isDead()) {
			int injury = p2.getHealth();
			System.out.println(p2.getNickname() + " a suferit o avarie de " + injury);
			System.out.println("Jocul " + PlayerStatus.getGameName() + " s-a terminat! " + p2.getNickname()
					+ " a murit!\nCastigatorul este " + p1.getNickname());
			PlayerStatus.gameOver();
		}
	}

	private static int findeRandomArtifact(Random r) {
		int artifactCode = r.nextInt(15);
		switch (artifactCode) {
		case 0:
			return 17;
		case 1:
			return 28;
		case 2:
			return 24;
		default:
			return 50;
		}

	}

	public static void giveWeaponInHand(double theDistanceBetween, PlayerStatus p) {
		if (theDistanceBetween > 1000) {
			if (p.setWeaponInHand("sniper") == false) {
				if (p.setWeaponInHand("kalasnikov") == false) {
					p.setWeaponInHand("knife");
				}
			}
		} else if (theDistanceBetween <= 1000) {
			if (p.setWeaponInHand("kalasnikov") == false) {
				if (p.setWeaponInHand("sniper") == false) {
					p.setWeaponInHand("knife");
				}
			}
		}
	}
}
