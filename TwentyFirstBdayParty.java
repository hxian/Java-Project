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
public class TwentyFirstBdayParty extends Party {

	public TwentyFirstBdayParty(String partyTitle, User host,HashMap<User, RsvpStatus> invitedGuests, 
			String partyDate, String partyAddress, DressCode dressCode) {
		super(partyTitle, PartyType.TWENTYFIRSTBDAYPARTY, host, invitedGuests, partyDate, partyAddress, dressCode);
	}

	public TwentyFirstBdayParty(String partyTitle, User host, String partyDate,
			String partyAddress, DressCode dressCode) {
		super(partyTitle, PartyType.TWENTYFIRSTBDAYPARTY, host, partyDate, partyAddress, dressCode);
	}
	
}
