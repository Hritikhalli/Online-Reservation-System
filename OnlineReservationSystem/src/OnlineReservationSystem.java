import java.sql.*;
import java.util.Scanner;
import java.util.Random;

public class OnlineReservationSystem{
    public static final int min = 1000;
    public static final int max = 9999;

    public static class user{
        private String username;
        private String password;

        Scanner sc = new Scanner(System.in);

        public user(){
        }

    public String getUsername()
    {
        System.out.println( "Enter Username :");
        username = sc.nextLine();
        return username;
    }

    public String getPassword()
    {
        System.out.println("Enter Password :");
        password = sc.nextLine();
        return password;
    }
}

    public static class PnrRecord{
        private int pnrNumber;
        private String passengerName;
        private String trainNumber;
        private String classType;
        private String journeyData;
        private String from;
        private String to;
    
        Scanner sc = new Scanner(System.in);

        public int getpnrNumber()
        {
            Random random = new Random();
            pnrNumber = random.nextInt(max)+ min ;
            return pnrNumber;
        }

        public String getPassengerName()
        {
            System.out.println("Enter passenger Name:");
            passengerName = sc.nextLine();
            return passengerName;
        }

        public String gettrainNumber()
        {
            System.out.println("Enter Train Number:");
            trainNumber = sc.nextLine();
            return trainNumber;
        }

        public String getjourneyDate()
        {
            System.out.println("Enter the Journey date as 'YYYY-MM-DD' format:");
            journeyData = sc.nextLine();
            return journeyData;
        }

        public String getclassType()
        {
            System.out.println("Enter the class type :");
            classType = sc.nextLine();
            return classType;
        }

        public String getfrom()
        {
            System.out.println("Enter the starting place Name:");
            from = sc.nextLine();
            return from;
        }

        public String getto()
        {
            System.out.println("Enter the destiantion :");
            to = sc.nextLine();
            return to;
        }
    }


    public static void main(String[] args){
        Scanner sc = new Scanner (System.in);
        OnlineReservationSystem.user u1 = new OnlineReservationSystem.user();
        String username = u1.getUsername();
        String password = u1.getPassword();

        String url ="jdbc:mysql://localhost:1521/system";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            try(Connection connection = DriverManager.getConnection(url, username, password)){
                System.out.println("User Connection Granted.\n");
                while(true){
                    String InsertQuery = "insert into reservations values (?,?,?,?,?,?,?)";
                    String DeleteQuery = "delete from reservations WHERE pnr_number = ?";
                    String ShowQuery = "Select * from reservations";

                    System.out.println("Enter your choics: ");
                    System.out.println("1. Insert Records.\n");
                    System.out.println("2. Delete Records.\n");
                    System.out.println("3. Show All Records.\n");
                    System.out.println("4. Exit. \n");
                    int choice = sc.nextInt();

                    if(choice ==1){

                        PnrRecord p1 = new PnrRecord();
                        int pnr_number = p1.getpnrNumber();
                        String passengerName = p1.getPassengerName();
                        String trainNumber = p1.gettrainNumber();
                        String classType = p1.getclassType();
                        String journeyDate = p1.getjourneyDate();
                        String from = p1.getfrom();
                        String to = p1.getto();
                        
                        try(PreparedStatement preparedStatement = connection.prepareStatement(InsertQuery))
                        {
                            preparedStatement.setInt(1, pnr_number);
                            preparedStatement.setString(2, passengerName);
                            preparedStatement.setString(3, trainNumber);
                            preparedStatement.setString(4, classType);
                            preparedStatement.setString(5, journeyDate);
                            preparedStatement.setString(6, from);
                            preparedStatement.setString(7, to);

                            int rowsAffected = preparedStatement.executeUpdate();
                            if(rowsAffected > 0){
                                System.out.println("Successfully Record has Been Added!");
                            }
                            else{
                                System.out.println("No Records were added!!");
                            }
                        }
                    }
                }
 
            }
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }finally {
             sc.close();
            u1.sc.close();
            }    
    }
}


