import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

// If you are looking for Java data structures, these are highly useful.
// Remember that an important part of your mark is for doing as much in SQL (not Java) as you can.
// Solutions that use only or mostly Java will not received a high mark.  
import java.util.ArrayList; 
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Assignment2 {

   // A connection to the database
   Connection connection;

   Assignment2() throws SQLException {
      try {
         Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
   }

  /**
   * Connects and sets the search path.
   *
   * Establishes a connection to be used for this session, assigning it to
   * the instance variable 'connection'.  In addition, sets the search
   * path to bnb.
   *
   * @param  url       the url for the database
   * @param  username  the username to connect to the database
   * @param  password  the password to connect to the database
   * @return           true if connecting is successful, false otherwise
   */
   public boolean connectDB(String URL, String username, String password) {
      // Implement this method!

      
      try {
          connection = DriverManager.getConnection(URL, username, password);
      }
      catch (SQLException e){   //once the connection fails
          return false;
      }

      return true;
   }

  /**
   * Closes the database connection.
   *
   * @return true if the closing was successful, false otherwise
   */
   /*
   * One reference I found on stackoverflow
   * http://stackoverflow.com/questions/2225221/closing-database-connections-in-java
   */
   public boolean disconnectDB() {
      
      try{
          connection.close();
      }
      catch (SQLException e){
          return false;
      }

      return true;
   }

   /**
    * Returns the 10 most similar homeowners based on traveller reviews. 
    *
    * Does so by using Cosine Similarity: the dot product between the columns
    * representing different homeowners. If there is a tie for the 10th 
    * homeowner (only the 10th), more than 10 records may be returned. 
    *
    * @param  homeownerID   id of the homeowner
    * @return               a list of the 10 most similar homeowners
    */
   public ArrayList homeownerRecommendation(int homeownerID) {
      	// Implement this method!
      	String queryString;
      	PreparedStatement ps;
      	ResultSet rs;
      
      	try{
		queryString =
<<<<<<< HEAD
=======
			"SET search_path TO bnb, public;"+
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f
			"create view TR as "+
			"select Booking.travelerID, TravelerRating.listingID, TravelerRating.rating "+
			"from TravelerRating inner join Booking "+
			"on TravelerRating.listingID = Booking.listingID and TravelerRating.startDate = Booking.startDate;"+
	
			"create view TRH as "+ 
			"select TR.travelerID, Listing.owner, TR.rating "+
			"from TR inner join Listing on TR.listingID = Listing.listingID;"+

			"create view Rate as "+ 
			"select travelerID, owner, avg(rating) as rating "+
			"from TRH "+
<<<<<<< HEAD
			"group by travelerID, owner;"+
	
			"select t2.owner, sum(t1.rating * t2.rating) as similarity "+
=======
			"group by travelerID, owner;";
		ps = connection.prepareStatement(queryString);
		ps.executeUpdate();
		queryString = 
			"select t2.owner "+
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f
			"from Rate as t1 inner join Rate as t2 "+
			"on t1.travelerID = t2.travelerID "+
			"where t1.owner = ? and t2.owner <> ? "+
			"group by t2.owner "+
<<<<<<< HEAD
			"order by similarity DESC, owner ASC;";
=======
			"order by sum(t1.rating * t2.rating) DESC, owner ASC;";
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f
		ps = connection.prepareStatement(queryString);
		ps.setInt(1, homeownerID);
		ps.setInt(2, homeownerID);
		
		rs = ps.executeQuery();
		
<<<<<<< HEAD
		for(int i = 0; rs.next() || i < 10; i++){
			int owner = rs.getInt("owner");
			System.out.println(owner);
		}
      	}catch(SQLException se){
		System.out.println("Error");
=======
		ArrayList<Integer> result = new ArrayList<Integer>();
		int counter = 0;
		while(rs.next()){
			counter ++;
			int owner = rs.getInt("owner");
			if(counter <= 10)
				result.add(owner);
			else if(counter > 10 && owner == result.get(9))
				result.add(owner);
			else
				break;
			System.out.println(owner);
		}
		
		return result;
      	}catch(SQLException se){
		se.printStackTrace();
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f
	}
      return null;
   }

   /**
    * Records the fact that a booking request has been accepted by a 
    * homeowner. 
    *
    * If a booking request was made and the corresponding booking has not been
    * recorded, records it by adding a row to the Booking table, and returns 
    * true. Otherwise, returns false. 
    *
    * @param  requestID  id of the booking request
    * @param  start      start date for the booking
    * @param  numNights  number of nights booked
    * @param  price      amount paid to the homeowner
    * @return            true if the operation was successful, false otherwise
    */
   public boolean booking(int requestId, Date start, int numNights, int price) {
      
      String queryString;
      ResultSet rs;
      PreparedStatement ps;
      
      try{

          //check if the booking request has been made
          queryString = "SELECT * FROM BookingRequest WHERE requestId = ?";
          ps = connection.prepareStatement(queryString);

          ps.setInt(1, requestId);
          rs = ps.executeQuery();

          if (rs.next() == false){ //the booking requestid is not found
              return false;
          }

          //find the listingId given the requestId
          int listingId = rs.getInt("listingId");
<<<<<<< HEAD
	  int travelerId = rs.getInt("travelerID");
	  int numGuests = rs.getInt("numGuests");
          if (rs.next() == true){ //there exists more than one requestId
              return false;
          }
	  
          //check if the same booking has been added to the Booking table
          queryString = "SELECT * FROM Booking WHERE listingId = ? AND startdate = ? AND numNights = ? AND price = ?";
=======
          if (rs.next() == true){ //there exists more than one requestId
              System.out.println("More than one listingIds");
              return false;
          }

          //check if the same booking has been added to the Booking table
          queryString = "SELECT * FROM BookingRequest WHERE listingId = ? AND startdate = ? AND numNights = ? AND price = ?";
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f
          ps = connection.prepareStatement(queryString);

          ps.setInt(1, listingId);
          ps.setDate(2, new java.sql.Date(start.getTime()));
          ps.setInt(3, numNights);
          ps.setInt(4, price);
          rs = ps.executeQuery();

          if (rs.next() == true){ //there is already something in the booking table
<<<<<<< HEAD
	      return false;
=======
              return false;
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f
          }

          //if it hasn't been added then insert the entry to the Booking table
      
<<<<<<< HEAD
          queryString = "SET search_path TO bnb, public; " + 
	  		"INSERT INTO Booking " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
          ps = connection.prepareStatement(queryString);
	  
          ps.setInt(1, listingId);
          ps.setDate(2, new java.sql.Date(start.getTime()));
          ps.setInt(3, travelerId); 
          ps.setInt(4, numNights);
          ps.setInt(5, numGuests);
          ps.setInt(6, price);
	  ps.executeUpdate();
=======
          queryString = "INSERT INTO Booking " +
                        " VALUES (?, ?, ?, ?, ?, ?)";
          ps = connection.prepareStatement(queryString);

          ps.setInt(1, listingId);
          ps.setDate(2, new java.sql.Date(start.getTime()));
          ps.setNull(3, java.sql.Types.INTEGER); //can travelerID be null?
          ps.setInt(4, numNights);
          ps.setNull(5, java.sql.Types.INTEGER);
          ps.setInt(6, price);
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f

          return true; 
      }
      catch(SQLException se){
<<<<<<< HEAD
      	  se.printStackTrace();
=======
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f
          return false;
      }
   }

   public static void main(String[] args) {
<<<<<<< HEAD
   
       System.out.println("Beiginning!");
       
       Assignment2 a2;
       try{
           System.out.println("In try block");
           a2 = new Assignment2();
	   a2.connectDB("jdbc:postgresql://localhost:5432/csc343h-zhouyuh9", "zhouyuh9", "");
	   
	   SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd"); 
           boolean bookingSuccessful = a2.booking(6000, ft.parse("2016-10-05"), 2, 120);
           System.out.println(bookingSuccessful);
	   
	   a2.disconnectDB();
       }
       catch(Exception se){
           System.out.println("Exception error");
	   return;
       }
       
       return;
=======
      // You can put testing code in here. It will not affect our autotester.
      try{
      Assignment2 a2 = new Assignment2();
      a2.connectDB("jdbc:postgresql://localhost:5432/csc343h-hushi4?searchpath=bnb", "hushi4", "");
      a2.homeownerRecommendation(4000);
      //a2.disconnectDB();
      }catch(SQLException se){
      		System.out.println("Error");
      }
      System.out.println("Boo!");
>>>>>>> ee81d2afad59d3bce38bb2dafd46a424466f871f
   }
   
   
   
   
   
   
   
   
   

}
