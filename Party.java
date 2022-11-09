/**
 * 
 */
package itp265_FinalProject_hxian;

import java.time.LocalDate;
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
public abstract class Party {
	private String partyTitle;
	private PartyType partyType;
	private User host;
	private HashMap<User, RsvpStatus> invitedGuests;
	private String partyDate;
	private String partyAddress;
	private DressCode dressCode;
	
	public Party(String partyTitle, PartyType partyType, User host, HashMap<User, RsvpStatus> invitedGuests,
			String partyDate, String partyAddress, DressCode dressCode) {
		this.partyTitle = partyTitle;
		this.partyType = partyType;
		this.host = host;
		this.invitedGuests = invitedGuests;
		this.partyDate = partyDate;
		this.partyAddress = partyAddress;
		this.dressCode = dressCode;
	}

	public Party(String partyTitle, PartyType partyType, User host, String partyDate, String partyAddress,
			DressCode dressCode) {
		this(partyTitle, partyType, host, new HashMap<>(), partyDate, partyAddress,dressCode);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Party other = (Party) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (partyTitle == null) {
			if (other.partyTitle != null)
				return false;
		} else if (!partyTitle.equals(other.partyTitle))
			return false;
		if (partyType != other.partyType)
			return false;
		return true;
	}
	
	public String getGuestsEmails() {  // return a comma separated list of invited guests' 
		//emails to be written to file
		String s = "";
		if(!this.invitedGuests.isEmpty()) {  // there are invited guests
			for(User u : invitedGuests.keySet()) {
				s += u.getEmail() + ",";
			}
			s = s.substring(0, s.length() - 1);   // remove the last comma
		}
		return s;
	}
	
	public String getGuestsRsvp() {  // return a comma separated list of all invited guests' rsvp status
		String s = "";
		if(!this.invitedGuests.isEmpty()) { // there are invited guests
			for(User u : invitedGuests.keySet()) {
				s += invitedGuests.get(u) + ",";  // return the guest's rsvp status
			}
			s = s.substring(0, s.length() - 1);  // return the last comma
		}
		return s;
	}

	@Override
	public String toString() {
		String s = "\nTitle: " + partyTitle + 
				"\nType: " + partyType + 
				"\nHost: " + host.getName() +
				"\nWhen: " + partyDate +
				"\nWhere: " +  partyAddress;
		return s;
	}
	
	/**
	 * @return
	 */
	public String getFileString() {
		String s = this.getPartyTitle() + "/"  + this.getPartyType() + "/" + this.getHost().getEmail() + "/" 
				+ this.getGuestsEmails() + "/" + this.getGuestsRsvp() + "/" + this.getPartyDate() +
				"/" + this.getPartyAddress() + "/" + this.getDressCode();
		return s;
	}
	
	/**
	 * @return the partyTitle
	 */
	public String getPartyTitle() {
		return partyTitle;
	}
	/**
	 * @param partyTitle the partyTitle to set
	 */
	public void setPartyTitle(String partyTitle) {
		this.partyTitle = partyTitle;
	}
	/**
	 * @return the partyType
	 */
	public PartyType getPartyType() {
		return partyType;
	}
	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(PartyType partyType) {
		this.partyType = partyType;
	}
	/**
	 * @return the host
	 */
	public User getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(User host) {
		this.host = host;
	}
	/**
	 * @return the invitedGuests
	 */
	public HashMap<User, RsvpStatus> getInvitedGuests() {
		return invitedGuests;
	}
	/**
	 * @param invitedGuests the invitedGuests to set
	 */
	public void setInvitedGuests(HashMap<User, RsvpStatus> invitedGuests) {
		this.invitedGuests = invitedGuests;
	}
	/**
	 * @return the partyDate
	 */
	public String getPartyDate() {
		return partyDate;
	}
	/**
	 * @param partyDate the partyDate to set
	 */
	public void setPartyDate(String partyDate) {
		this.partyDate = partyDate;
	}
	/**
	 * @return the partyAddress
	 */
	public String getPartyAddress() {
		return partyAddress;
	}
	/**
	 * @param partyAddress the partyAddress to set
	 */
	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}
	/**
	 * @return the dressCode
	 */
	public DressCode getDressCode() {
		return dressCode;
	}
	/**
	 * @param dressCode the dressCode to set
	 */
	public void setDressCode(DressCode dressCode) {
		this.dressCode = dressCode;
	}
	
}
