/**
 * 
 */
package itp265_FinalProject_hxian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Brief description of the code
 *
 * @author Haotian Xian
 * ITP 265, Fall 2019
 * Final Project
 * Email: hxian@usc.edu
 */
public abstract class User {
	private String name;
	private String email;
	private String password;
	private int age;
	private AccountType accountType;
	private ArrayList<Party> allPartiesCreated;
	private HashMap<Party, RsvpStatus> partiesInvitedTo;
	public static final int SUBSCRIPTION_COST = 5;
	

	public User(String name, String email, String password, int age, AccountType accountType,
			ArrayList<Party> allPartiesCreated, HashMap<Party, RsvpStatus> partiesInvitedTo) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
		this.accountType = accountType;
		this.allPartiesCreated = allPartiesCreated;
		this.partiesInvitedTo = partiesInvitedTo;
	}

	public boolean verifyPassword(String pword) {
		return password.equals(pword);

	}
	public boolean changePassword(String oldPassWord, String newPassWord) {
		boolean changed = false;
		if(verifyPassword(oldPassWord)) {
			this.password = newPassWord;
			changed = true;
		}
		return changed;	
	}

	//TODO: update to differentiate between cancelled and planned parties
	public void viewAllPartiesCreated() {
		if(this.allPartiesCreated.isEmpty()) {
			System.out.println("You have not created any parties");
		}
		else {
			System.out.println("Here are all the parties you have created:");
			for(Party p : this.allPartiesCreated) {
				System.out.println(p);
			}
		}	
	}

	public void viewAllPartiesInvitedTo() {
		if(this.partiesInvitedTo.isEmpty()) {
			System.out.println("You do not have any active invites.");
		}
		else {
			int num = 1;
			System.out.println("Here are all the parties you are invited to:");
			for(Party p : this.partiesInvitedTo.keySet()) {
				System.out.println("Party#" + num + ":" + p +
						"\nYour RSVP: " + this.partiesInvitedTo.get(p));
				num++;
			}
		}
	}

	public boolean addToAllPartiesCreated(Party p) {
		boolean added = false;
		if(!this.allPartiesCreated.contains(p)) {
			allPartiesCreated.add(p);
			added = true;
		}
		else {
			System.out.println("Party already exists!");
		}
		return added;
	}

	
	public boolean sendInvite(User u, Party p) {
		boolean sent = false;
		if(u.getPartiesInvitedTo().put(p, RsvpStatus.INVITED) == null) {
			sent = true;
		}
		return sent;
	}

	public RsvpStatus checkRsvpForParty(Party p) {
		return this.partiesInvitedTo.get(p);
	}

	public boolean changeRsvpForParty(Party p, RsvpStatus rsvpStatus) {
		boolean changeSuccessful = false;
		if(this.partiesInvitedTo.put(p, rsvpStatus)!= null && this.partiesInvitedTo.get(p) != rsvpStatus) {
			changeSuccessful = true;
		}
		return changeSuccessful;
	}

	public void viewAccountType() {
		System.out.println("Your account type is: " + accountType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (age != other.age)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "/nName:" + name + "/nEmail:" + email + "/nAccount Type:" + accountType;
	}

	/**
	 * @return
	 */
	public String getFileString() {
		String s = name + "/"  + email + "/" + password + "/" + age + "/" + accountType;
		return s;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the accountType
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the allPartiesCreated
	 */
	public ArrayList<Party> getAllPartiesCreated() {
		return allPartiesCreated;
	}

	/**
	 * @param allPartiesCreated the allPartiesCreated to set
	 */
	public void setAllPartiesCreated(ArrayList<Party> allPartiesCreated) {
		this.allPartiesCreated = allPartiesCreated;
	}

	/**
	 * @return the partiesInvitedTo
	 */
	public HashMap<Party, RsvpStatus> getPartiesInvitedTo() {
		return partiesInvitedTo;
	}

	/**
	 * @param partiesInvitedTo the partiesInvitedTo to set
	 */
	public void setPartiesInvitedTo(HashMap<Party, RsvpStatus> partiesInvitedTo) {
		this.partiesInvitedTo = partiesInvitedTo;
	}

	/**
	 * @return the subscriptionCost
	 */
	public static int getSubscriptionCost() {
		return SUBSCRIPTION_COST;
	}

}
