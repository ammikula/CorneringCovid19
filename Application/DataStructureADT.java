/////////////////////////////////////////////////////////////////////////////////////////////////
// Class: CS400, Summer 2020
// Name: Anna Mikula
// Email: ammikula@wisc.edu
// Project: Final Project
// Description: This is the data structure used for getting data information to the user.
///////////////////////////////////////////////////////////////////////////////////////////////////

package Application;

/**
 * This class gets the data needed for the following check box questions:
 * 
 * 1. Minimum Case Number
 * 2. Maximum Case Number
 * 4. Average Case Number
 * 
 * @author annamikula
 *
 */
public interface DataStructureADT {
  
  /**
   * This method gets the minimum case number from the data.
   * 
   * @return String minimum  The minimum case number from the data.
   */
  String getMinimumCaseNumber();
  
  /**
   * This method gets the maximum case number from the data.
   * 
   * @return String maximum  The maximum case number from the data.
   */
  String getMaximumCaseNumber();
  
  /**
   * This method gets the average case number from the time period from the data.
   * 
   * @return String average  The average case number per day.
   */
  String getAverageCases();
  
}
