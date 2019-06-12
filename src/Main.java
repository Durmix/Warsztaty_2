package src;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        //User user1 = new User("Tomek", "tomek@wp.pl", "ashdas");
        DbService.getInstance().setDb("Warsztaty_2", "root", "coderslab");
        //user1.saveToDB(DbService.getInstance().createConn());
        //User user2 = new User("Kacper", "kacper@gmail.com", "asdfghtre");
        User.loadUserById(DbService.getInstance().createConn(),3);
//        user2.setEmail("aleksan@gmail.com");
//        user2.setPassword("zxcvbnbvc");
//        user2.setUsername("Aleksand");
//        user2.saveToDB(DbService.getInstance().createConn());
//        User[] users = User.loadAllUsers(DbService.getInstance().createConn());
//        for(int i = 0; i < users.length; i++) {
//            System.out.print(users[i].getId() + " ");
//            System.out.print(users[i].getUsername() + " ");
//            System.out.println(users[i].getEmail());
//        }

        User[] users = User.loadAllByGroupId(DbService.getInstance().createConn(), 2);
        System.out.println(users[0].getUsername());
        System.out.println(users[1].getUsername());
        System.out.println(users[2].getUsername());
    }

}
