/**
 * 
 */
package itp265_FinalProject_hxian;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Party System holds all the data about users and parties. A signed in user can can view parties,
 * send invites, and respond to invites. Two files, one for users and one for parties, hold all the 
 * data, making data available to multiple runs of the program.
 *
 * @author Haotian Xian and Kendra Walther
 * ITP 265, Fall 2019
 * Final Project
 * Email: hxian@usc.edu
 */
public class PartySystem {
	private User currentUser;
	private HashMap <String, User> allUsers;
	private ArrayList<PartyType> typesOfParties;
	private ArrayList<Party> allPlannedParties;
	private static final String USER_FILE = "src/users.txt";
	private static final String PARTY_FILE = "src/parties.txt";
	private Scanner sc;

	// setting up the party system
	public PartySystem() {
		this.currentUser = null;
		allUsers = new HashMap<>();
		readExistingUsersFromFile();
		typesOfParties = new ArrayList<>();
		makeListOfPartyTypes();
		allPlannedParties = new ArrayList<>();
		readExistingPartiesFromFile();
		sc = new Scanner(System.in);
	}

	public boolean login() {
		boolean loggedIn = false;
		String email = InputHelper.readString(sc, "PLease enter account email:");
		User u = findUser(email);
		if(u == null) {   // user not found
			System.out.println("User does not exist with the email you entered.");
			boolean create = InputHelper.readYesNoBoolean(sc, "Would you like to create the user?");
			if(create) {
				createNewUser(email);
			}
		}
		else {   // user has been found, let user enter password
			String pw = InputHelper.readString(sc, "Please enter your password:");
			if(u.verifyPassword(pw)) {   // password is correct
				System.out.println("Log in success!");
				currentUser = u;
				loggedIn = true;
			}
			else {  // password is not correct
				System.out.println("Incorrect password. Log in failed.");
			}
		}
		return loggedIn;
	}

	public FreeUser createNewUser(String email) {
		String name = InputHelper.readString(sc, "Please enter your name:");
		String password = InputHelper.readString(sc, "Please set your password:");
		int age = InputHelper.readPositiveInt(sc, "Please enter your age:");
		FreeUser u =  new FreeUser(name, email, password, age);
		allUsers.put(email, u);
		System.out.println("Registration success!");
		return u;
	}

	public User createNewUser() {
		String email = InputHelper.readString(sc, "Please enter your email:");
		while(allUsers.containsKey(email)) {
			System.out.println("User already exists! Try a differnt email");
			email = InputHelper.readString(sc, "Please enter your email:");
		}
		return createNewUser(email);
	}

	private void readExistingUsersFromFile() {
		try(FileInputStream fis = new FileInputStream(USER_FILE);
				Scanner scan = new Scanner(fis))	{  
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				//System.out.println(line);
				User u = parseUser(line);
				allUsers.put(u.getEmail(), u);
			}

		} catch (FileNotFoundException e) {
			System.err.println("File not found exception in readExistingUsersFromFile");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException in readExistingUsersFromFile");
			e.printStackTrace();
		}
	}

	private void readExistingPartiesFromFile() {
		try(FileInputStream fis = new FileInputStream(PARTY_FILE);
				Scanner scan = new Scanner(fis))	{  
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				//System.out.println(line);
				Party p = parseParty(line);
				allPlannedParties.add(p);
			}

		} catch (FileNotFoundException e) {
			System.err.println("File not found exception in readExistingUsersFromFile");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException in readExistingUsersFromFile");
			e.printStackTrace();
		}
	}

	/**
	 * @param line
	 * @return
	 */
	private User parseUser(String line) {
		User u = null;
		// name / email / password / age / account type)
		Scanner parser = new Scanner(line);
		// in our data, tokens are separated by "/"
		parser.useDelimiter("/");
		String name = parser.next();
		String email = parser.next();
		String password = parser.next();
		int age = parser.nextInt();
		String type = parser.next();
		if(type == "FREE") {  // make a free user
			u = new FreeUser(name, email, password, age);
		}
		else {  // make a premium user
			u = new PremiumUser(name, email, password, age);
		}
		return u;
	}

	private Party parseParty(String line) {
		Party p = null;
		// title / type / host email / guest emails / rsvp of guests / date / location/ dress code
		Scanner parser = new Scanner(line);
		// in our data, tokens are separated by "/"
		parser.useDelimiter("/");
		String title = parser.next();
		String type = parser.next();
		String hostEmail = parser.next();
		String guestEmails = parser.next();
		String guestRsvps = parser.next();
		String date = parser.next();
		String location = parser.next();
		String dressCode = parser.next();
		HashMap<User, RsvpStatus> guestMap = makeGuestsMap(guestEmails, guestRsvps);
		DressCode dc = DressCode.valueOf(dressCode);
		switch(type) {
		case "TWENTYFIRSTBDAYPARTY":
			p = new TwentyFirstBdayParty(title, findUser(hostEmail), guestMap, date, location, dc); break;
		case "CHRISTMASPARTY":
			p = new ChristmasParty(title, findUser(hostEmail), guestMap, date, location, dc); break;
		case "PUPPYPARTY":
			p = new PuppyParty(title, findUser(hostEmail), guestMap, date, location, dc); break;
		}
		return p;
	}

	// given two strings, which are both comma separated lists, this method returns a map that has
	// user objects as keys and user rsvp status as values
	public HashMap<User, RsvpStatus> makeGuestsMap(String userEmails, String userRsvps){
		HashMap<User, RsvpStatus> invitedGuests = new HashMap<>();
		Scanner parser1 = new Scanner(userEmails);
		Scanner parser2 = new Scanner(userRsvps);
		// in our data, tokens are separated by ","
		parser1.useDelimiter(",");
		parser2.useDelimiter(",");
		while(parser1.hasNext()) { // as long as there's more emails, keep scanning
			User u = findUser(parser1.next());
			RsvpStatus r = RsvpStatus.valueOf(parser2.next());
			invitedGuests.put(u, r);
		}
		return invitedGuests;
	}

	private void writeUserDataToFile() {
		try (FileOutputStream fos = new FileOutputStream(USER_FILE);
				PrintWriter out = new PrintWriter(fos)){ // closes automatically
			for(String email: allUsers.keySet()) {
				User u = allUsers.get(email);
				out.println(u.getFileString()); 
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void writePartyDataToFile() {
		try (FileOutputStream fos = new FileOutputStream(PARTY_FILE);
				PrintWriter out = new PrintWriter(fos)){ // closes automatically
			for(Party p: allPlannedParties) {
				out.println(p.getFileString()); 
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void printUserMap() {
		String map = "All users (email --> user)\n";
		// go through the map, getting each entry
		for(String email: allUsers.keySet()) {
			// given the key, get the value (user object)
			User u = allUsers.get(email);
			map += "\t" + email + "-->" + u.getName() + " pw:" + u.getPassword() +"\n";
		}
		System.out.println(map);
	}
	
	private void printParties() {
		for(Party p : allPlannedParties) {
			System.out.println(p);
		}
	}
	
	/**
	 * 
	 * @param email
	 * @return the user associated with the email or NULL if the user does not exist
	 */
	public User findUser(String email) {
		User u = allUsers.get(email); // either a user or NULL
		return u;
	}

	/**
	 * 
	 */
	private void makeListOfPartyTypes() {
		for(PartyType pt : PartyType.values()) {
			this.typesOfParties.add(pt);
		}	
	}

	public void displayMainMenu() {
		boolean quit = false;
		while(!quit) {
			System.out.println("Welcome, " + currentUser.getName());
			String s = "";
			s = "\nPlease select from the following options:";
			s += "\n1. View parties you are hosting";
			s += "\n2. View parties you are invited To";
			s += "\n3. Creat a new party";
			s += "\n4. Add a guest to party";
			s += "\n5. View my account type";
			s += "\n6. Log out";
			int choice = InputHelper.readIntBetween(sc, s, 1, 7);
			switch(choice) {
			case 1: currentUser.viewAllPartiesCreated(); break;
			case 2: currentUser.viewAllPartiesInvitedTo(); break;
			case 3: createNewParty();
			case 4: addGuestToParty(); break;
			case 5: currentUser.viewAccountType(); break;
			case 6: quit = true; break;
			}
		}
	}
	
	/**
	 * 
	 */
	private void addGuestToParty() {
		System.out.println("Not yet implemented. Would first take a party title string to "
				+ "identify an existing party object, then"
				+ "take a user email string to get a user object, then add that user object to "
				+ "the party's array list of invited guests.");
	}

	/**
	 * 
	 */
	private void createNewParty() {
		System.out.println("Not yet implemented. would ask user to enter party title, choose"
				+ "party type, enter party date & location, and then choose dress code. then"
				+ "create a party object based on all this info, then add the party obejct"
				+ "to party system's array list of all planned parties");
	}

	public void displayLoginMenu() {
		boolean quit = false;
		while(!quit) { // user keeps using the system
			printUserMap();
			printParties();
			System.out.println("Welcome to party system!");
			String s = "";
			s = "\nPlease select from the following options:";
			s += "\n1. Log in as an existsing user";
			s += "\n2. Create a free account";
			s += "\n3. Save & Quit";
			int choice = InputHelper.readIntBetween(sc, s, 1, 3);
			switch(choice) {
			case 1: if(login()) {displayMainMenu();} break;
			case 2: createNewUser(); break;
			case 3: quit = true; break;
			}
		}
		System.out.println("Thanks for using party system. Have a good day!");
	}

	
	public void saveData() {
		writeUserDataToFile();
		writePartyDataToFile();
	}
	
	/**
	 * 
	 */
	private void run() {
		displayLoginMenu();
		saveData();
	}

	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		PartySystem ps = new PartySystem();
		ps.run();
	}

}
