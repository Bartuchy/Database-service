package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class connectToDB {
    public static void main(String [] args){
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:info.db");
            System.out.println("connected successfully");
            Statement stmt = c.createStatement();

            String username;
            String password;
            String command;
            int proc=0;

            while(proc != 5){
                System.out.println("1. Add User"
                +"\n2. Show DB"
                +"\n3. Delete User"
                +"\n4. Update password"
                +"\n5. Save and exit");

                System.out.println("Choose process:");
                Scanner reader = new Scanner(System.in);
                proc = reader.nextInt();
                switch (proc){
                    case 1:
                        //add users
                        Scanner usernamein = new Scanner(System.in);
                        Scanner passwordin = new Scanner(System.in);

                        System.out.println("enter user name:");
                        username = usernamein.nextLine();

                        System.out.println("enter password:");
                        password = passwordin.nextLine();

                        command = "insert into"
                        +" admins(user_name,password) "
                        + "values('"+username+"','"+password+"')";

                        stmt.executeUpdate(command);
                        System.out.println("Record has been added");
                        break;

                    case 2:
                        //select data from table
                        command = "select * from admins";
                        ResultSet rs = stmt.executeQuery(command);

                        System.out.println("id\tusername\tpassword");
                        while(rs.next()){
                            int id = rs.getInt("id");
                            username = rs.getString("user_name");
                            password = rs.getString("password");
                            System.out.println(id +"\t"+username +"\t\t" +password);
                        }

                        rs.close();
                        break;

                    case 3:
                        //delete record
                        Scanner del = new Scanner(System.in);
                        System.out.println("enter ID");
                        int id = del.nextInt();
                        command = "delete from admins where id="+id;
                        stmt.executeUpdate(command);
                        System.out.println("Record has been deleted");
                        break;

                    case 4:
                        //update password
                        Scanner up = new Scanner(System.in);
                        Scanner newpass = new Scanner(System.in);
                        System.out.println("enter ID");
                        id = up.nextInt();
                        System.out.println("enter new password");
                        password = newpass.nextLine();
                        command = "UPDATE admins set password='"+password+"' where id="+id;
                        stmt.executeUpdate(command);
                        break;

                    default:
                        c.commit();
                        c.close();
                        stmt.close();
                        break;
                }
            }
        }
        catch (Exception ex){
            System.out.println("cannot connect");
            System.exit(0);
        }
    }
}