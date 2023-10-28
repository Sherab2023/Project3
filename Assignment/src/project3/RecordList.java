package project3;


import java.util.NoSuchElementException;


/**
 * This class stores all record objects
 * default constructor provides empty recordlist object
 *
 * inherits SortedLinkedList<Record> class
 *
 * @author Sherab
 */

public class RecordList {

    private SortedLinkedList<Session> sessions;

    /**
     * Default constructor to create an empty RecordList
     */

    public RecordList() {
        sessions = new SortedLinkedList<Session>();
    }

    // Getter for sessions
    public SortedLinkedList<Session> getSessions() {
        return sessions;
    }

    /**
     * Add a new session to the RecordList
     * login record will be null if login attribute is false
     *
     * @param session The session to add
     */

    public void addSession(Session session) {
        if (session != null && session.getLoginRecord() != null) {
            sessions.add(session);
        } else {
            throw new IllegalArgumentException("Invalid session or login record is null.");
        }
    }

    /**
     * Get the first login session for the specified user
     *
     * @param user The user name to search for
     * @return The first login session for the user.
     * @throws NoSuchElementException if no matching session is found
     */

    public Session getFirstSession(String user) {
        for(Session session: sessions) {
            if(session.getUsername().equals(user)) {
                return session;
            }
        }
        throw new NoSuchElementException("No user matching"+ user+" found.");
    }

    /**
     * Get the last login session for the specified user
     *
     * @param user The user name to search for
     * @return The last login session for the user
     * @throws NoSuchElementException if no matching session if found
     */

    public Session getLastSession(String user) {
        for(int i=sessions.size()-1; i>=0; i--) {  //iterating in reverse order
            Session session= sessions.get(i);
            if(session.getUsername().equals(user)) {
                return session;
            }
        }
        throw new NoSuchElementException("No user matching"+ user+ "found");
    }

    /**
     * Get the total time in milliseconds that the user was logged in
     *
     * @param user The user name to calculate the total time for
     * @return The total time the user was logged in.
     * @throws NoSuchElementException if no matching session is found.
     */

    public long getTotalTime(String user) {
        long totalTime =0;

        for(Session session: sessions) {
            if(session.getUsername().equals(user)) {
                long sessionDuration = session.getDuration();
                if(sessionDuration>0) {
                    totalTime=totalTime+sessionDuration;
                }
            }
        }

        if(totalTime == 0) {
            throw new NoSuchElementException("No user matching "+ user + "found.");
        }

        return totalTime;
    }

    public void updateSession(Record record) {
        if (record != null) {
            for (Session existingSession : sessions) {
                if (existingSession.getTerminal() == record.getTerminal()
                        && existingSession.getUsername().equals(record.getUsername())
                        && existingSession.getLogoutTime() == null) {

                    existingSession.updateLogout(record);
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("Record cannot be null");
        }
    }

    /**
     * Get a sorted list of all login sessions associated with the specified user
     *
     * @param user The user name to search for
     * @return A sorted list of login sessions for the user
     * @throws NoSuchElementException if no matching session is found
     */

    public SortedLinkedList<Session> getAllSessions(String user){
        SortedLinkedList<Session> userSessions = new SortedLinkedList<Session>();

        for(Session session: sessions) {
            if(session.getUsername().equals(user)) {
                userSessions.add(session);
            }
        }

        if(userSessions.isEmpty()) {
            throw new NoSuchElementException("No user matching"+ user+ " found");
        }

        return userSessions;
    }

}