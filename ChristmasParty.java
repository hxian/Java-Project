/**
 * 
 */
package itp265_FinalProject_hxian;

import java.util.HashMap;

/**
 * Brief description of the code
 *
 * @author Haotian Xian
 * ITP 265, Fall 2019
 * Final Project
 * Email: hxian@usc.edu
 */
public class ChristmasParty extends Party {

	public ChristmasParty(String partyTitle, User host, HashMap<User, RsvpStatus> invitedGuests,
			String partyDate, String partyAddress, DressCode dressCode) {
		super(partyTitle, PartyType.CHRISTMASPARTY, host, invitedGuests, partyDate, partyAddress, dressCode);
	}

	public ChristmasParty(String partyTitle, User host, String partyDate, String partyAddress,
			DressCode dressCode) {
		super(partyTitle, PartyType.CHRISTMASPARTY, host, partyDate, partyAddress, dressCode);
	}

}
