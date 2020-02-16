package casinoSim;

import java.util.Scanner ;

import java.util.Random ;


public class casinoSim 
{
	// created these two globally so I could close them at the end of the program
	//Eclipse kept popping up warnings about closing resources
	 public static Scanner in = new Scanner(System.in);
	 public static Random rand = new Random();
	
	// my player class, keeps track of name and accounts
	// pretty basic fields with getters and setters and to string method
	
	public static class Player
	{
		private String name;
		private double account;
		
		public Player(String name, double account)
		{
			this.name = name;
			this.account = account;
		}
		
		public Player()
		{
			name = "";
			account = 00.00;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}
		
		public String getName()
		{
			return name;
		}
		
		public void setAccount(double account)
		{
			this.account = account;
		}
		
		public double getAccount()
		{
			return account;
		}
		
		public String toString(Player player)
		{
			String str = name + "'s account: $" + account;
			return str;
		}
		
		//makes sure player has money before they are allowed to play
		public boolean checkAccount(Player Player)
		{
			boolean broke = false;
			if(Player.getAccount() <= 0)
			{
				System.out.println("Sorry, " + Player.getName() + " your account is out of money, deposit more or go home.");
				broke = true;
			}
			else
			{
				System.out.println(Player.getName() + " you have $" + Player.getAccount() + " in your account, good luck!");
				broke = false;
			}
			return broke;
		}
		
		//gives option to add money if player runs out
		public void addMoney(Player Player)
		{
			
			System.out.println("Would you like to add money? (y/n)");
			String choice = in.nextLine();
			if(choice.equalsIgnoreCase("y")) 
			{
				System.out.println("How much money would you like to add?");
				double moneyAdded = in.nextDouble();
				Player.setAccount(moneyAdded);
			}
			else if(choice.equalsIgnoreCase("n"))
			{
				System.out.println("Goodebye");
				System.exit(0);
			}
			
		}
		
		// getting initial deposit for player
		public static double getDeposit()
		{
			boolean goodDeposit = false;
			double deposit = 0;
			while(goodDeposit == false)
			{	
				System.out.println("Enter your deposit ammount\n"
						+ "Min deposit = $20.00\n"
						+ "Max deposit = $1000.00");
				deposit = in.nextDouble();
				
				if(deposit > 1000)
				{
					System.out.println("Thats not within the parameters");
					goodDeposit = false;
				}
				else if(deposit < 20)
				{
					System.out.println("Thats not within the parameters");
					goodDeposit =false;
				}
				else 
				{
					System.out.println("That is a good deposit");
					goodDeposit = true;
				}
			}
		return deposit;
		}
	}

	// this is where I went AWOl from the instructions and created a deck
	// instead of dice, hope thats okay
	public static class Deck
	{
		//creating my deck, i tried to do the same odds as a typical 52 card deck
		//minus the ace being either one or eleven
		int [] deck = {2,3,4,5,6,7,8,9,10,10,10,10,11};
		
		//generating a random number and assigning it to a value in my deck array
		public int getCard(Deck Deck)
		{
			int i = rand.nextInt(12) + 1;
			return deck[i];
		}
		
		//this method is going to decide if a player wants another card
		public boolean hitMe(BlackJack Game, Player Player)
		{
			boolean hitMe = false;
			boolean looper = false;
			
			while(looper == false)
			{
				System.out.println(Game.toString(Game, Player) + "\nWould you like another card(y/n)?");
				String anotherCard = in.nextLine();
			
				if (anotherCard.equalsIgnoreCase("y"))
				{
					hitMe = true;
					looper = true;
				}
				else if(anotherCard.equalsIgnoreCase("n"))
				{
					hitMe = false;
					looper = true;
				}
				else
				{
					System.out.println("Not a valid response. try again.");
					looper = false;
				}
			}
			return hitMe;
		}
		
	}

	//BlackJack class keeps count of both dealer's and player's score
	//also has typical getter and setter for fields and a to string method
	public static class BlackJack
	{
		private int dealerScore = 0;
		private int playerScore = 0;
		
		public BlackJack()
		{
			dealerScore = 0;
			playerScore = 0;
		}
		
		public void setDealerScore(int dealerScore)
		{
			this.dealerScore = dealerScore;
		}
		
		public void setPlayerScore(int playerScore)
		{
			this.playerScore = playerScore;
		}
		
		public int getDealerScore()
		{
			return dealerScore;
		}
		
		public int getPlayerScore()
		{
			return playerScore;
		}
		
		public String toString(BlackJack Game, Player Player)
		{
			String str = Player.getName() + " Score: " + Game.getPlayerScore() + "\n" + 
					"Dealer score: " + Game.getDealerScore();
			return str;
		}
		
		//this method checks to see if the player hit 21
		public boolean checkfor21(BlackJack Game)

		{
			boolean twentyOne = false;
			if (Game.playerScore == 21)
			{
				twentyOne = true;
				System.out.println("You hit 21, that means you tripled your wager!!!");
			}
			return twentyOne;
		}
		
		//welcomes player to game
		public void blackjackIntro(Player Player)
		{
			System.out.println("Welcome to the blackJack table, let's get started!");
		}
		
		//collects and returns player wager
		public double getWager(Player Player)
		{
			boolean goodWager = false;
			double wager = 0;
			
			while(goodWager == false)
			{
				System.out.println("Enter the amount you would like to wager");
				wager = in.nextDouble();
			
			
				if(wager > Player.getAccount())
				{
					System.out.println("You don't have that much money, try again");
					goodWager = false;
				}
				else if(wager == 0)
				{
					System.out.println("You have to bet more than zero");
					goodWager = false;
				}
				else if(wager > 0 && wager <= Player.getAccount())
				{
					System.out.println("Thats a good wager, lets do this");
					goodWager = true;
				}
			}
			return wager;	
		}
		
		//finds out who actually won and returns true if player wins
		public boolean getWinner(BlackJack Game)
		{
			boolean playerWon = false;
			if(Game.playerScore > Game.dealerScore)
			{
				if(Game.playerScore <= 21)
				{
					if(Game.dealerScore > 21)
						
						{
							System.out.println("Congrats, you won with a score of " + Game.playerScore + 
									" versus the dealer's score of " + Game.dealerScore + "!");
							playerWon = true;
						}
				}
			}
			else if(Game.dealerScore > 21)
			{
				if(Game.playerScore <= 21)
				{
					System.out.println("Congrats, the dealer busted!");
					playerWon = true;
				}
			}
			else
			{
				System.out.println("Sorry, you lost with a score of " + Game.playerScore + 
						" versus the dealer's score of " + Game.dealerScore);
				playerWon = false;
			}
			return playerWon;
		}
	}

	//This class stores one field, an array
	public static class SlotMachine
	{
		//the array for the spin method to pull from
		private static String [] machineImages = {"cherries" , "oranges", "bells", "plums", "melons", "bars"};
		
		//collects wager for slot games
		public static double getWager(Player Player) 
		{
			double wager = 0;
			boolean goodWager = false;
			
			while(goodWager == false)
			{
				System.out.println("Enter the amount you would like to wager");
				wager = in.nextDouble();
			
			
				if(wager > Player.getAccount())
				{
					System.out.println("You don't have that much money, try again");
					goodWager = false;
				}
				else if(wager == 0)
				{
					System.out.println("You have to bet more than zero");
					goodWager = false;
				}
				else if(wager > 0 && wager <= Player.getAccount())
				{
					System.out.println("Thats a good wager, lets do this");
					goodWager = true;
				}
			}
			return wager;	
		}
		
		//main logic of slot game, grabs three values from array, prints them, and checks to see how many
		//of them match, as well as sets my winnings multiplier
		@SuppressWarnings("static-access")
		public static double spin(SlotMachine SlotGame, Player Player)
		{
			double winnings = 1;
			int spinOne = rand.nextInt(6);
			int spinTwo = rand.nextInt(6);
			int spinThree = rand.nextInt(6);
			String spinOneString = "";
			String spinTwoString = "";
			String spinThreeString = "";
			
			for(int i = 0; i < machineImages.length; i ++)
			{
				if(spinOne == i)
				{
					spinOneString = machineImages[i];
				}
				if(spinTwo == i)
				{
					spinTwoString = machineImages[i];
				}
				if(spinThree == i)
				{
					spinThreeString = machineImages[i];
				}
			}
			
			System.out.println("\n" + spinOneString + "     " + spinTwoString + "     " + spinThreeString + "\n");
			
			if(spinOneString.equalsIgnoreCase(spinTwoString) && spinOneString.equalsIgnoreCase(spinThreeString))
			{
				System.out.println("Congrats, all three match, you ttipled your wager");
				winnings = 3;
			}
			else if(spinOneString.equalsIgnoreCase(spinTwoString) || spinOneString.equalsIgnoreCase(spinThreeString))
			{
				System.out.println("Congrats, two out of the three matched, this means you doubled your wager!");
				winnings = 2;
			}
			else if(spinTwoString.equalsIgnoreCase(spinThreeString))
			{
				System.out.println("Congrats, two out of the three matched, this means you doubled your wager!");
				winnings = 2;
			}
			else
			{
				System.out.println("Sorry, none of them matched, you lose your wager");
			}
			return winnings;
		}
		
	}
	
	
	public static void main(String[] args) 
	{
		//getting info to fill Player class
		System.out.println("Welcome to the casino\nI am going to get some info from you");
		System.out.println("Please enter your name");
		String name = in.nextLine();
		double deposit = Player.getDeposit();
		Player Player = new Player(name, deposit);
		// pLayer filled
		
		//making sure they have money to play
		if(Player.checkAccount(Player) == true)
		{	
			Player.addMoney(Player);
		}
		
		//setting value to test main loop against
		int choice = -1;
		
		//this loop covers both games and the menu
		do
		{
			//allows user to choice game
			choice = gameChoice();
			
			//using switch structure to decide where the program goes
			switch(choice)
			{
				case 1:
					
					boolean playAgain = false;
					System.out.println("You chose blackjack, good luck");
					
					//this loop controls blackjack game
					//tests against playAgain bool
					do {
						
						//setting up game and getting wagers
						BlackJack Game = new BlackJack();
						
						Game.blackjackIntro(Player);
						
						if(Player.checkAccount(Player) == true)
						{	
							Player.addMoney(Player);
						}
						
						double wager = Game.getWager(Player);
						
						Player.setAccount((Player.getAccount() - wager)); 
						// done setting up game 
						
						//this bool will control some loops down the road
						boolean hitMe = true;
						
						// getting card from deck to create score
						Deck Deck = new Deck();
						
						//this is the beginning of main game logic
						//handing out initial cards
						Game.playerScore += Deck.getCard(Deck);
						Game.dealerScore += Deck.getCard(Deck);
						Game.playerScore += Deck.getCard(Deck);
						
						//checking to see if player hit 21
						boolean twentyone = Game.checkfor21(Game);
						if(twentyone == true)
						{
							Player.setAccount((Player.getAccount() + (wager * 3))); 
							System.out.println("Congrats you tripled your money");
							hitMe = false;
						}
						
						// using this loop to let player keep getting cards
						// until they choose not to or they go over 21
						while (hitMe == true)
						{
							
							hitMe = Deck.hitMe(Game,  Player);
							
							if(hitMe == true && Game.playerScore <= 21)
							{
								Game.playerScore += Deck.getCard(Deck);
								System.out.println(Game.toString(Game, Player));
								twentyone = Game.checkfor21(Game);
								if(twentyone == true)
								{
									Player.setAccount((Player.getAccount() + (wager *3)));
									hitMe = false;
								}
								if(Game.playerScore > 21)
								{
									System.out.println("Sorry you went over twenty-one, maybe next hand");
									hitMe = false;
								}
							}
						}
						
						if(hitMe == false && Game.getPlayerScore() < 21 && Game.getPlayerScore() != 21)
						{
							System.out.println("Player's total score: " + Game.playerScore + "\nNow the dealers turn");
						}
						
						while(Game.dealerScore <= 17)
						{
							Game.dealerScore += Deck.getCard(Deck);
							System.out.println("Dealer Score: " + Game.dealerScore);
						}
						
						boolean playerwon = Game.getWinner(Game);
						
						//deciding if player won and setting their account accordingly
						if(playerwon == true)
						{
							Player.setAccount((wager * 2));
						}
						
						// checking if player would like to play another hand
						playAgain = playAgain();
						if(playAgain == false)
						{
							//this gives usere choice to go back to menu or quit altogether
							choice = toMenu_or_toQuit();
						}
						
					}while(playAgain == true);
					
					break;
					
				case 2:
					System.out.print("You chose to play the slots, good luck");
					playAgain = false;
					
					do 
					{
						//creating a new slot machine instance
						SlotMachine SlotGame = new SlotMachine();
						
						//checking player account
						if(Player.checkAccount(Player) == true)
						{	
							Player.addMoney(Player);
						}
						
						// getting wager and subtracting it from player account
						double wager = SlotMachine.getWager(Player);
						Player.setAccount((Player.getAccount() - wager));
						
						//running the game and printing results.
						double winningsMultiplier = SlotMachine.spin(SlotGame, Player);
						Player.setAccount(Player.getAccount() + (wager * winningsMultiplier));
						
						playAgain = playAgain();
						if(playAgain == false)
						{
							choice = toMenu_or_toQuit();
						}
						
						
					} while(playAgain == true);
					
					break;
					
				case 3:
					System.out.println("You have $" + Player.getAccount());
					choice = -1;
					break;
					
				default:
					System.out.println("Not an option, try again.");
					choice = -1;
					break;
			}
		}while(choice == -1);
		
		//closing Scanner and exiting program
		in.close();
		System.exit(0);
	}
	
	//gets integer used in switching statement
	public static int gameChoice()
	{
		int choice = 0;
		
			System.out.println("Enter 1 to play blackjack\n"
					+ "Enter 2 to play the slots\n"
					+ "Enter 3 to check your account");
			choice = in.nextInt();
			System.out.println(choice);
			
		return choice;
	}
	
	//gets boolean to test against 
	public static boolean playAgain()
	{
		int choice = 1;
		boolean playAgain = false;
		do
		{
			System.out.println("Would you like to play again?(y/N)");
			String playAgainString = in.nextLine();
			if(playAgainString.equalsIgnoreCase("y"))
			{
				playAgain = true;
				choice = 0;
			}
			else if(playAgainString.equalsIgnoreCase("n"))
			{
				playAgain = false;
				choice = 0;
			}
			else
			{
				choice = 1 ;
			}
			
		}while(choice == 1);
		return playAgain;
	}
	
	//logic to go to menu or quit
	public static int toMenu_or_toQuit()
	{
		System.out.println("Would you like to go to menu or just quit?('menu/'quit')");
		String str = in.nextLine();
		int choice = 99;
		do
		{
			if(str.equalsIgnoreCase("menu"))
			{
				choice = -1;
			}
			else if(str.equalsIgnoreCase("quit"))
			{
				System.exit(0);
			}
			else
			{
				System.out.println("Not an valid option, enter 'quit' or 'menu'");
				choice = 99;
			}
			
		}while(choice == 99);
		return choice ;
	}
}
