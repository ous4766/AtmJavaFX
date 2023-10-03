import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    /**
     * create a new bank object with empty lists of users and accounts
     * @param name the name of the bank
     */
    public Bank(String name){

        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }


    /**#
     * generate new universally unique ID for the user
     * @return the uuid
     */
    public String getNewUserUUID() {

        //inits
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        // continue looping until we get a  unique ID
        do{

       //generate the number
            uuid ="";

            for(int c = 0; c <len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
        //check to make it unique
        nonUnique = false;
            for (User u : this.users){
            if (uuid.compareTo(u.getUUID()) ==0){
                nonUnique = true;
                break;
            }
        }

        }while (nonUnique);

        return uuid;

    }

    public String getNewAccountUUID() {
        //inits
        String uuid;
        Random rng = new Random();
        int len = 15;
        boolean nonUnique;
        // continue looping until we get a  unique ID
        do{

            //generate the number
            uuid ="";

            for(int c = 0; c <len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            //check to make it unique
            nonUnique = false;
            for (Account a : this.accounts){
                if (uuid.compareTo(a.getUUID()) ==0){
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique);

        return uuid;

    }

    /**
     * add account for the user
     * @param anAcct the account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }
    public User AddUser(String firstName, String lastName, String pin){
        //create a new user object and add it to the list

        User newUser = new User(firstName,lastName,pin, this);
        this.users.add(newUser);

        //create a savings account
        Account newAccount = new Account("Savings", newUser, this);
        //add to holder and bank lists
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        return newUser;
    }
    public  User userLogin(String userID, String pin){

        //search list of users
        for (User u : this.users){
            //check if the users ID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        //if user isn't found
        return null;
    }

    public String getName(){
        return this.name;
    }
}
