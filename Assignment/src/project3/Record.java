package project3;

import java.util.Date;

/**
 * This class represents individual records from the login file
 * A valid terminal should be positive
 * throws IllegalArgumentException exception if terminal is invalid
 * no default constructor
 *
 * This class implements Comparable<Record> interface
 * Record Object A is smaller if the time associated with it is before the time associated
 * with Record Object B
 *
 * overrides the equals method from the Object class
 * Two Record objects are equal if they have the same terminal,
 * user name, time and same kind of record.
 *
 * @author Sherab
 *
 * @param <E> the type of elements held in this list
 */
public class Record implements Comparable<Record> {
    //declaring variables
    private int terminal;
    private boolean login;
    private String username;
    private Date time;



    /*constructor with the required four parameters
     * throws an exception if terminal is negative
     * throws exception if user name or time is null
     */
    public Record(int terminal, boolean login, String username, Date time) {
        if(terminal<0) {
            throw new IllegalArgumentException("Terminal has to be a positive integer");
        }

        //additional exceptions it might throw
        if(username==null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        if(time==null) {
            throw new IllegalArgumentException("Time cannot be null");
        }

        this.terminal = terminal;
        this.login= login;
        this.username = username;
        this.time=time;

    }


     // getter methods



    public int getTerminal() {
        return terminal;
    }

    public boolean isLogin() {
        return login;
    }

    public boolean isLogout() { //negating the login value to obtain logout
        return !login;
    }

    public String getUsername() {
        return username;
    }

    public Date getTime() {
        return time;
    }


    /**
     * compares two objects based on their login and logout times
     * an object is smaller if they have earlier login/logout times than the other object
     * @param objectB the object to be compared.
     * @return A string representation of the session including login and logout information.
     */
    //
    //an object is smaller if they have earlier login/logout times than the other object
    @Override
    public int compareTo(Record objectB) {

        if(this.time.compareTo(objectB.getTime())!=0){
            return this.time.compareTo(objectB.getTime());
        }
        else {
            //comparing based on terminal and login status
            if(this.terminal != objectB.getTerminal()) {
                return this.terminal - objectB.getTerminal();
            }
            else {
                //same terminals are compared based on login status
                //considering login records to be smaller
                if(this.login) {
                    return -1; //This record is a login record so it is smaller
                }
                else {
                    return 1; //This is a logout record so it is bigger
                }

            }
        }

    }

    @Override //overrides equals method from Object class
    public boolean equals(Object obj) {

        if(this==obj){
            return true;
        }
        if(obj==null || this.getClass()!= obj.getClass()){
            return false;
        }

        Record otherRecord = (Record) obj;

        return terminal == otherRecord.terminal &&
                login == otherRecord.login &&
                username.equals(otherRecord.username)&&
                time.equals(otherRecord.time);

    }



}