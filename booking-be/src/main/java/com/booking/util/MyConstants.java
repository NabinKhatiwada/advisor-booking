package com.booking.util;

public final class MyConstants {

	private MyConstants() {
	}

	public static final String MSG_SYSTEM_ERROR = "System Error! Please try later";
	public static final String MSG_SUCCESS = "Success";


	public static final String REG_GENERAL_NAME = "^.{1,100}";
	public static final String REG_EMAIL = "^[a-zA-Z0-9_.]+@[a-zA-Z.]+?\\.[a-zA-Z]{2,3}$";

//	public static final String PATTERN_DATE = "dd-MM-yyyy";
//	public static final String PATTERN_DATE_TIME = "dd-MM-yyyy HH:mm";
//	public static final String PATTERN_TIME = "HH:mm";
	public static final String PATTERN_TIME_WITH_MEREDIEM = "dd-MMM-yyyy h:mm a";



	public static final String ERR_MSG_NOT_FOUND = "Not found: ";
	public static final String ERR_MSG_BAD_REQUEST = "Invalid: ";
	public static final String ERR_MSG_EMPTY = "Empty: ";
	public static final String ERR_MSG_ALREADY_EXIST = "Already Exist: ";






	/* End of Respiratory Constants */

}

//^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
//^                 # start-of-string
//(?=.*[0-9])       # a digit must occur at least once
//(?=.*[a-z])       # a lower case letter must occur at least once
//(?=.*[A-Z])       # an upper case letter must occur at least once
//(?=.*[@#$%^&+=])  # a special character must occur at least once
//(?=\S+$)          # no whitespace allowed in the entire string
//.{8,}             # anything, at least eight places though
//$                 # end-of-string

//Birth Weight REG
//^                         # Start of string
//[0-9]+                   # Require one or more numbers
//      (                  # Begin optional group
//       \.                # Point must be escaped or it is treated as "any character"
//         [0-9]{1,2}      # One or two numbers
//                   )?    # End group--signify that it's optional with "?"
//                     $   # End of string