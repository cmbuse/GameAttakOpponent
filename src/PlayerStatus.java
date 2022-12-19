
public class PlayerStatus {
	private  String nickname;
	private int score;
	private int lives;
	private int health = 100;
	private String weaponInHand = "";
	private double positionX;
	private double positionY;
	private static  String gameName = "GAMEONE";

	public  void initPlayer(String newNickname) {
		nickname = newNickname;
	}

	public void initPlayer(String newNickname, int newLives) {
		nickname = newNickname;
		lives = newLives;
	}

	public void initPlayer(String newNickname, int newLives, int newScore) {
		nickname = newNickname;
		lives = newLives;
		score = newScore;
	}

	public void findArtifact(int artifactCode) {
		if (isPerfect(artifactCode)) {
			System.out.println();
			System.out.println("Jucatorul " + this.getNickname() + " a primit artefactul " + artifactCode
					+ " care este numar perfect.\nVa primi o viata in plus si 5000 de puncte. ");
			System.out.println();
			score += 5000;
			lives++;
			health = 100;
		} else if (isPrim(artifactCode)) {
			System.out.println();
			System.out.println("Jucatorul " + this.getNickname() + " a primit artefactul " + artifactCode
					+ " care este numar prim.\nVa primi 2 vieti in plus si 1000 de puncte. ");
			System.out.println();
			score += 1000;
			lives += 2;
			if ((health + 25) > 100) {
				health = 100;
			} else {
				health += 25;
			}
		} else if (isPar(artifactCode) && sumDigits(artifactCode) % 3 == 0) {
			System.out.println();
			System.out.println("Jucatorul " + this.getNickname() + " a primit artefactul " + artifactCode
					+ " care este numar par si numar divizibil cu 3.\nVa pierde 3000 de puncte si 25 de health ");
			System.out.println();
			score -= 3000;
			health -= 25;
			if (health <= 0 && lives > 0) {
				lives--;
				health = 100;
			}
			return;
		}
		
		score += artifactCode;
		System.out.println(this.getNickname() + " a primit 50 puncte.");

	}

	private int sumDigits(int numar) {
		int sumDigits = 0;
		while (numar != 0) {
			sumDigits = sumDigits + numar % 10;
			numar /= 10;
		}
		return sumDigits;
	}

	private boolean isPar(int numar) {
		if (numar % 2 == 0) {
			return true;
		}
		return false;
	}

	private boolean isPrim(int numar) {
		for (int i = 2; i <= Math.sqrt(numar); i++) {
			if (numar % i == 0) {
				return false;
			}
		}
		return true;
	}

	private boolean isPerfect(int numar) {
		int sum = 0;
		for (int i = 1; i < numar; i++) {
			if (numar % i == 0) {
				sum += i;
			}
			if (numar == sum) {
				return true;
			}
		}
		return false;
	}

	public boolean setWeaponInHand(String weaponInHand) {
		if (this.getWeaponInHand().equals(weaponInHand)) {
			return true;
		}
		int priceWeapon = 0;
		if (weaponInHand.equals("knife")) {
			priceWeapon = 1000;
		}

		if (weaponInHand.equals("sniper")) {
			priceWeapon = 10000;
		}
		if (weaponInHand.equals("kalashnikov")) {
			priceWeapon = 20000;
		}
		if (priceWeapon > score) {
			return false;
		}
		if (score >= priceWeapon) {
			this.weaponInHand = weaponInHand;
			score -= priceWeapon;
			return true;

		}
		return false;

	}

	public String getWeaponInHand() {
		return weaponInHand;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public static String getGameName() {
		return gameName;
	}

	protected static  void setGameName(String newgameName) {
		gameName = newgameName;
	}

	public int getLives() {
		return lives;
	}

	public int getScore() {
		return score;
	}

	public int getHealth() {
		return health;
	}

	public void movePlayerTo(double newPositionX, double newPositionY) {
		positionX = newPositionX;
		positionY = newPositionY;
	}

	public String getNickname() {
		return nickname;
	}

	public boolean shouldAttackOpponent(PlayerStatus opponent) {
		if (this.getWeaponInHand().equals(opponent.getWeaponInHand())) {

			System.out.println("Probabilitatea de castig pentru " + this.getNickname() + " este "
					+ this.calculateProbabilityToWin() + " vs  " + opponent.getNickname() + " "
					+ opponent.calculateProbabilityToWin());
			return this.calculateProbabilityToWin() > opponent.calculateProbabilityToWin();

		}

		double theDistanceBetween = this.calculateDistance(opponent.getPositionX(), opponent.getPositionY());
		return calculateWeaponPower(theDistanceBetween) > opponent.calculateWeaponPower(theDistanceBetween);
	}

	public double calculateDistance(double opponentPositionX, double opponentPositionY) {
		return Math.sqrt(Math.pow((positionX - opponentPositionX), 2) + Math.pow((positionY - opponentPositionY), 2));
	}

	public double calculateProbabilityToWin() {
		return (3 * health + score / 1000) / 4;
	}

	public int calculateWeaponPower(double distance) {

		if (distance > 1000) {
			if (getWeaponInHand().equals("sniper")) {
				return 80;
			}
			if (getWeaponInHand().equals("kalashnikov")) {
				return 60;
			}
			if (getWeaponInHand().equals("knife")) {
				return 40;
			}
		}
		if (distance <= 1000) {
			if (getWeaponInHand().equals("kalashnikov")) {
				return 80;
			}
			if (getWeaponInHand().equals("sniper")) {
				return 60;
			}
			if (getWeaponInHand().equals("knife")) {
				return 40;
			}
		}
		return 0;
	}

	public boolean isDead() {

		return lives <= 0;
	}

	public int calculateInjure(PlayerStatus opponent) {
		if (this.getWeaponInHand().equals(opponent.getWeaponInHand())) {
			if (this.calculateProbabilityToWin() > opponent.calculateProbabilityToWin()) {
				return 60;
			}
		}

		double theDistanceBetween = this.calculateDistance(opponent.positionX, opponent.positionY);
		return calculateWeaponPower(theDistanceBetween);

	}

	public void applyInjure(int injury) {
		health -= injury;
		if (health <= 0) {
			if (lives > 0) {
				lives--;
			}
			health = 100;
		}
	}

	public static void gameOver() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Jocul " + PlayerStatus.getGameName() + "S-a incheiat ! Thank`s playing!");

	}

}