package com.example.phase2.meetings;

import com.example.phase2.highabstract.Manager;
import com.example.phase2.trades.TradeManager;
import com.example.phase2.users.TraderManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A MeetingManager class that manages meetings
 */
public class MeetingManager extends Manager implements Serializable {
    private final HashMap<Integer, Meeting> meetings;

    /**
     * Constructs MeetingManager class
     * @param meetings A HashMap of Integer and Meeting
     */
    public MeetingManager(HashMap<Integer, Meeting> meetings){
        this.meetings = meetings;
    }

    /**Getter a meeting based on the trade's Id
     * @param id Id of the trade
     * @return Return Meeting object iff it exists. Otherwise return null
     */
    public Meeting getMeeting(Integer id){
        if (!containMeeting(id)){
            return null;
        }
        return meetings.get(id);
    }

    /**
     * Returns a list of tradeIDs that have on-going meetings
     * @param tradeIds The ids corresponding to the meetings
     * @return A list of Trade ids where their respective meeting status is other than "Completed"
     */
    public List<Integer> getOnGoingMeetings(List<Integer> tradeIds){
        List<Integer> temp = new ArrayList<>();
        for(Integer i:tradeIds){
            if(!getMeeting(i).getTradeStatus().equals("Completed")){
                temp.add(i);
            }
        }
        return temp;
    }

    /**
     * Returns a list of all tradeIDs that have on delivery meetings
     * @return A list of Trade ids where their respective delivery status is other than "Completed"
     */
    public List<Integer> getOnDeliverMeetings(){
        List<Integer> temp = new ArrayList<>();
        for(int i = 0; i<meetings.size(); i++){
            if(!getMeeting(i).getDeliverStatus().equals("Completed") && bothAgreed(i)
                    && !getMeeting(i).getTradeStatus().equals("Completed")){
                temp.add(i);
            }
        }
        return temp;
    }

    /**Getter for description of a meeting
     * @param id the id of the trade
     * @return the string representation of a meeting
     */
    public String getMeetingDescription(int id){
        if(!containMeeting(id)){
            return null;
        }
        return Objects.requireNonNull(meetings.get(id)).toString();
    }

    /**
     * Creates a meeting to be added to the system
      * @param tradeId The id of the trade that this meeting represents
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade offer
     * @param isPermanent a boolean representing if the trade is permanent
     */
    public void createMeeting(int tradeId, String initiator, String receiver, boolean isPermanent){
        if(!meetings.containsKey(tradeId)){
        Meeting m = new Meeting(tradeId);

        m.setAgree(initiator, false);
        m.setAgree(receiver, false);

        m.setConfirm(initiator, false);
        m.setConfirm(receiver, false);

        m.setReturn(initiator, false);
        m.setReturn(receiver, false);

        HashMap<String, Integer> edits = new HashMap<>();
        edits.put(initiator, 0);
        edits.put(receiver, 0);
        m.setNumberOfEdits(edits);

        m.setPermanent(isPermanent);


        meetings.put(tradeId, m);
        }
    }

    /**
     * A setter for the meeting information
     * @param id the id of the meeting
     * @param tradeDate the date of the trade
     * @param returnDate the return date of the trade
     * @param location the location of the meeting
     * @param returnLocation the return location of the meeting
     */
    public void setMeetingInfo(int id, LocalDate tradeDate, LocalDate returnDate,
                               String location, String returnLocation){
        Meeting meeting = meetings.get(id);
        assert meeting != null;
        meeting.setTradeDate(tradeDate);
        meeting.setReturnDate(returnDate);
        meeting.setLocation(location);
        meeting.setReturnLocation(returnLocation);
    }

    /**
     * Returns the meeting status of the inputted trade.
     * @param id the id of the trade in question.
     * @return the status of the meeting.
     */
    public String getMeetingStatus(Integer id){
        return Objects.requireNonNull(meetings.get(id)).getTradeStatus();
    }


    /**
     * Removes the given meeting object from meetings
     * @param id The trade id
     */
    public void removeMeeting(int id){
        if(containMeeting(id)){
            meetings.remove(id);
        }
    }

    /**edit the meeting based on the new location
     * @param id id of the trade
     * @param location the new meeting location
     */
    public void editLocation(int id, String location){
        getMeeting(id).setLocation(location);
    }

    /**edit the meeting based on the new date
     * @param id id of the trade
     * @param date the new meeting date
     */
    public void editDate(int id, LocalDate date){
        getMeeting(id).setTradeDate(date);
    }

    /**increase the number of edit times
     * @param user the user who wants to edit the meeting
     * @param id the id of the trade
     */
    public void increaseNumEdit(String user, int id){
        getMeeting(id).increaseNumberOfEdits(user);
    }

    /**return true iff the user hasn't edited the trade more than 3 times
     * @param user the user who wants to edit the meeting
     * @param id the tradeID
     * @return whether or not the user is valid to edit the trade
     */
    @SuppressWarnings("ConstantConditions")
    public boolean isValid(String user, int id){
        return meetings.get(id).getNumberOfEdits().get(user) < 3;
    }

    /**Return true iff both traders confirm the meeting
     * Confirms that the meeting happened
     * @param id Id of the trade
     * @param username Username of the Trader confirming that the meeting happened
     */
    public void confirmMeeting(int id, String username){
        getMeeting(id).setConfirm(username, true);
        if(checkAllConfirmed(id)){
            if(getMeeting(id).isPermanent()){
                getMeeting(id).setTradeStatus("Completed");
            }else{
                getMeeting(id).setTradeStatus("Waiting to be returned");
            }
        }else{
            getMeeting(id).setTradeStatus("Confirmed: waiting the other to confirm");
        }
    }

    @SuppressWarnings("ConstantConditions")
    private boolean checkAllConfirmed(int id){
        Meeting meeting = getMeeting(id);
        for(String user: meeting.getIsConfirmed().keySet()){
            if(!meeting.getIsConfirmed().get(user)){return false;}
        }
        return true;
    }

    /**Return true iff both traders agree with the meeting
     * Agrees on the meeting
     * @param id Id of the trade
     * @param username Username of the Trader agreeing on the meeting
     */
    public void agreeOnTrade(int id, String username){
        getMeeting(id).setAgree(username, true);
        if(checkAllAgreed(id)){
            getMeeting(id).setTradeStatus("Both Agreed: waiting to be confirmed");

        }else{
            getMeeting(id).setTradeStatus("Agreed: waiting the other to agree");
        }
    }

    @SuppressWarnings("ConstantConditions")
    private boolean checkAllAgreed(int id){
        Meeting meeting = getMeeting(id);
        for(String user: meeting.getIsAgreed().keySet()){
            if(!meeting.getIsAgreed().get(user)){
                return false;
            }
        }
        return true;
    }



    /**
     * Checks if the Meeting Manager has a meeting with given int id
     * @param id Id of the trade
     * @return Return true iff meeting with the given id exists. Otherwise return false
     */
    public boolean containMeeting(int id){
        return meetings.containsKey(id);
    }

    /**
     * Undoes the last edit made to the meeting.
     * @param id The ID of the meeting that is to be rolled back.
     * @param user The username of the user whose actions are to be undone.
     */


    /**
     * Returns the meeting's date in string format
     * @param id the id of the meeting
     * @return The date in yyyy-mm-dd format
     */
    public String getMeetingDate(int id){
        return Objects.requireNonNull(meetings.get(id)).getTradeDate().toString();
    }

    /**
     * Returns the meeting's current location
     * @param id The id of the meeting
     * @return The location
     */
    public String getMeetingLocation(int id){
        return Objects.requireNonNull(meetings.get(id)).getLocation();
    }

    /**
     * Returns the number of edits the meeting has left for a given user
     * @param id The id of the meeting
     * @param username The username of the user
     * @return number of edits the user has left
     */
    @SuppressWarnings("ConstantConditions")
    public Integer getEditsLeft(int id, String username){
        return 3- meetings.get(id).getNumberOfEdits().get(username);
    }

    /**
     * Returns true if the user has agreed to the meeting.
     * @param id The id of the meeting
     * @param username The username of the user
     * @return true iff the user has agreed to the meeting.
     */
    public boolean hasAgreed(int id, String username){
        if(Objects.requireNonNull(meetings.get(id)).getIsAgreed().containsKey(username)){
            return Objects.requireNonNull(meetings.get(id)).isAgreed(username);
        }
        else{
            return false;
        }
    }

    /**
     * Returns whether both users in the given meeting have agreed to the meeting location.
     * @param id The id of the meeting
     * @return true iff both have agreed.
     */
    public boolean bothAgreed(int id){
        if(Objects.requireNonNull(meetings.get(id)).getIsAgreed().values().size()==2){
            for(boolean b: (Objects.requireNonNull(meetings.get(id)).getIsAgreed().values())){
                if(!b){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns whether the user has agreed confirmed the meeting with the given id
     * @param id The id of the meeting
     * @param username The username of the user
     * @return true iff they have confirmed.
     */
    public boolean hasConfirmed(int id, String username){
        return Objects.requireNonNull(meetings.get(id)).hasConfirmed(username);
    }

    /**
     * Returns whether both users have confirmed the meeting with the given id.
     * @param id The id of the meeting.
     * @return true iff they have both confirmed.
     */
    public boolean bothConfirmed(int id){
        if(Objects.requireNonNull(meetings.get(id)).getIsConfirmed().values().size()==2){
            for(boolean b: (Objects.requireNonNull(meetings.get(id)).getIsConfirmed().values())){
                if(!b){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Used to set up the second meeting for a temporary trade.
     * @param id The id of the meeting
     */
    public void setReturnInfo(int id){
        Objects.requireNonNull(meetings.get(id)).setReturnLocation(
                Objects.requireNonNull(meetings.get(id)).getLocation());
        Objects.requireNonNull(meetings.get(id)).setReturnDate(
                Objects.requireNonNull(meetings.get(id)).getTradeDate().plusMonths(1));
        Objects.requireNonNull(meetings.get(id)).setBothConfirm(false);
    }

    /**
     * Sets the given meeting status to completed.
     * @param id The id of the meeting.
     */
    public void setMeetingCompleted(int id){
        Objects.requireNonNull(meetings.get(id)).setTradeStatus("Completed");
    }

    /**
     * Sets the given meeting deliver status to completed.
     * @param id The id of the meeting.
     */
    public void setDeliverCompleted(int id){
        Objects.requireNonNull(meetings.get(id)).setDeliverStatus("Completed");
    }

    /**
     * Returns the return location of the meeting.
     * @param id The id of the meeting
     * @return The location of the meeting.
     */
    public String getReturnLocation(int id){
        return Objects.requireNonNull(meetings.get(id)).getReturnLocation();
    }

    /**
     * Returns the return date of the trade with the given id.
     * @param id The id of the trade
     * @return The date of the return.
     */
    public String getReturnDate(int id){
        return Objects.requireNonNull(meetings.get(id)).getReturnDate().toString();
    }

    /**
     * Makes both users in a meeting disagree to the meeting.
     * @param id The id of the meeting.
     */
    public void setBothDisagree(int id){
        Objects.requireNonNull(meetings.get(id)).bothDisagree();
    }

    /**
     * Getter for the identifier of MeetingManager
     * @return a String representing the identifier
     */
    @Override
    public String getIdentifier() {
        return "MeetingManager";
    }

    /**
     * Returns if the max number of edits to the meeting have been reached.
     * @param id The id of the trade
     * @return Whether or not the max number of edits has been reached.
     */
    public boolean isMaxEditsReached(int id){
        for(Integer i: Objects.requireNonNull(meetings.get(id)).getNumberOfEdits().values()){
            if(i<3){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether the given date is after the return meeting date
     * @param id The id of the meeting
     * @param date The date to be checked against
     * @return True if is equal to or after
     */
    @SuppressWarnings("All")
    public boolean dateIsAfterReturnMeeting(int id, LocalDate date){
        if(meetings.get(id).getReturnDate().isEqual(date)){
            return true;
        }
        return meetings.get(id).getReturnDate().isBefore(date);
    }

    /**
     * Returns whether the given date is equal to or after the meeting date
     * @param id The id of the meeting.
     * @param date The date to be checked against.
     * @return True if is equal to or after
     */
    @SuppressWarnings("All")
    public boolean dateIsAfterMeeting(int id, LocalDate date){
        if(meetings.get(id).getTradeDate().isEqual(date)){
            return true;
        }
        return meetings.get(id).getTradeDate().isBefore(date);
    }

}