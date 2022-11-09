/**
 * 
 */
package itp265_FinalProject_hxian;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Brief description of the code
 *
 * @author Haotian Xian
 * ITP 265, Fall 2019
 * Final Project
 * Email: hxian@usc.edu
 */
public class PremiumUser extends User {

	public PremiumUser(String name, String email, String password, int age, AccountType accountType,
			ArrayList<Party> allPartiesCreated, HashMap<Party, RsvpStatus> partiesInvitedTo) {
		super(name, email, password, age, accountType, allPartiesCreated, partiesInvitedTo);
	}
	
	public PremiumUser(String name, String email, String password, int age) {
		this(name, email, password, age, AccountType.PREMIUM, new ArrayList<>(), new HashMap<>());
	}
	
	public PremiumUser(FreeUser freeUser) {
		this(freeUser.getName(), freeUser.getEmail(), freeUser.getPassword(), freeUser.getAge());
		this.setAllPartiesCreated(freeUser.getAllPartiesCreated());
		this.setPartiesInvitedTo(freeUser.getPartiesInvitedTo());
	}

	public FreeUser cancelPremium(PremiumUser premiumUser) {
		FreeUser freeUser = new FreeUser(premiumUser);
		return freeUser;
	}
}
