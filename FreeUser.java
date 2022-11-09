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
 * Email: hxia@usc.edu
 */
public class FreeUser extends User {
	public static final int MAX_NUM_PARTIES_CAN_PLAN = 3;
	
	public FreeUser(String name, String email, String password, int age, AccountType accountType,
			ArrayList<Party> allPartiesCreated, HashMap<Party, RsvpStatus> partiesInvitedTo) {
		super(name, email, password, age, accountType, allPartiesCreated, partiesInvitedTo);
	}
	
	public FreeUser(String name, String email, String password, int age) {
		this(name, email, password, age, AccountType.FREE, new ArrayList<>(), new HashMap<>());
	}
	
	
	public FreeUser(PremiumUser premiumUser) {
		this(premiumUser.getName(), premiumUser.getEmail(), premiumUser.getPassword(), premiumUser.getAge());
		this.setAllPartiesCreated(premiumUser.getAllPartiesCreated());
		this.setPartiesInvitedTo(premiumUser.getPartiesInvitedTo());
	}

	public PremiumUser upgradeToPremium(FreeUser freeUser) {
		PremiumUser premiumUser = new PremiumUser(freeUser);
		return premiumUser;
	}
}
