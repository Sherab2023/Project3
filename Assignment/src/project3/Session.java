package project3;

import java.util.Date;

/**
 * This class represents a single login session
 * A single login session needs to have matching user names and
 * matching terminal number
 * This class implements Comparable<Session> and session objects are compared
 * based on their login records
 * This class overrides the equals method from Object class and two objects are equal
 * if their login/logout sessions match
 *
 * @author - Sherab
 *
 */
public class Session implements Comparable<Session>{


    private Record loginRecord;
    private Record logoutRecord;

    /*
     * throws exception if the parameters are invalid
     * Login record cannot be null but logout record can be null
     * loginTime should not be greater than logoutTime
     * username and terminal should match for it to count as a single login session
     * logout time can be null as user could be still logged in
     */

    public Session(Record login, Record logout) {
        if (login != null) {
            if (logout != null) {
                // Validate that the records match and create the session.
                if (login.getTerminal() == logout.getTerminal() &&
                        login.getTime() == logout.getTime() &&
                        login.getUsername().equals(logout.getUsername())) {
                    this.loginRecord = login;
                    this.logoutRecord = logout;
                } else {
                    throw new IllegalArgumentException("Invalid Login or Logout records for a session");
                }
            } else {
                // Handle the case of a session with only a login record.
                this.loginRecord = login;
            }
        } else {
            throw new IllegalArgumentException("Invalid Login or Logout records for a session");
        }
    }
    /*
     * getter methods to get our values
     */
    public int getTerminal() {
        return loginRecord.getTerminal();
    }

    public Date getLoginTime() {
        if (loginRecord != null) {
            return loginRecord.getTime();
        }
        return null;
    }
    public Date getLogoutTime() {
        if (logoutRecord != null) {
            return logoutRecord.getTime();
        }
        return null;
        // logout value can be null
    }

    public String getUsername() {
        return loginRecord.getUsername();
    }
    public long getDuration() {
        if(logoutRecord==null) {
            return -1;   // user is still logged in
        }
        else {
            return logoutRecord.getTime().getTime()-loginRecord.getTime().getTime();
        }
    }

    /**
     * getter method for the login attribute
     * @return Record object
     */

    public Record getLoginRecord(){
        return loginRecord;
    }

    // New method to update the logout record
    public void updateLogout(Record logoutRecord) {
        if (logoutRecord == null) {
            throw new IllegalArgumentException("Logout record cannot be null.");
        }

        if (this.logoutRecord == null) {
            this.logoutRecord = logoutRecord;
        }
    }

    /**
     * Returns a string representation of this session.
     *
     * @return A string representation of the session including login and logout information.
     */

    @Override
    public String toString() {

        String finalresult= getUsername() +",terminal"+ getTerminal()+ ",duration ";

        if(getDuration()==-1) {
            finalresult+="active session";
        }
        else {
            long duration = getDuration();
            long seconds = duration / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            seconds= seconds%60;
            minutes=minutes%60;
            hours=hours%60;

            finalresult= finalresult+String.format("%d days, %02d hours, %02d minutes, %02d seconds", days,hours,minutes,seconds);
        }

        finalresult= finalresult+ "\n";
        finalresult= finalresult+ "logged in: "+ getLoginTime() + "\n";
        if(getLogoutTime()!= null) {
            finalresult= finalresult+ "logged out: "+getLogoutTime();
        }
        else {
            finalresult=finalresult+ "logged out: user still logged in";
        }

        return finalresult;
    }

    /**
     * compares the login time of this and object otherSession
     * @param otherSession the object to be compared.
     * @return an int value -1,0 or 1 as this session
     */

    @Override
    public int compareTo(Session otherSession) {
        return this.getLoginTime().compareTo(otherSession.getLoginTime());
    }

    /**
     * Checks if this object is equal to another object obj
     *
     * @param obj The object to compare with
     * @return true if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if( obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Session session = (Session) obj;

        if(!loginRecord.equals(session.loginRecord)) {
            return false;
        }
        return logoutRecord != null ? logoutRecord.equals(session.logoutRecord) : session.logoutRecord == null;
    }





}