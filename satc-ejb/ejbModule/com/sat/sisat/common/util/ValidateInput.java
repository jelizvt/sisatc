package com.sat.sisat.common.util;

public class ValidateInput {
	  // validate first name
      public static boolean validateFirstName( String firstName )
      {
    	//return firstName.matches( "[A-Z][a-zA-Z]*" );
    	return firstName.matches( "[a-zñA-ZÑ]+([ '-][a-zñA-ZÑ]+)*" );
      } // end method validateFirstName
      
      // validate last name
      public static boolean validateLastName( String lastName )
      {
         return lastName.matches( "[a-zñA-ZÑ]+([ '-][a-zñA-ZÑ]+)*" );
      } // end method validateLastName
   
      // validate address
      public static boolean validateAddress( String address )
      {
         return address.matches(                          
            "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" );
      } // end method validateAddress
   
      // validate city
      public static boolean validateCity( String city )
      {
         return city.matches( "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" );
      } // end method validateCity
   
      // validate state
      public static boolean validateState( String state )
      {
         return state.matches( "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)" ) ;
      } // end method validateState
   
      // validate zip
      public static boolean validateZip( String zip )
      {
         return zip.matches( "\\d{5}" );
      } // end method validateZip
   
      // validate phone
      public static boolean validatePhone( String phone )
      {
         return phone.matches( "[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}" );
      } // end method validatePhone
}
