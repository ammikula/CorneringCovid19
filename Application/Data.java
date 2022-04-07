/////////////////////////////////////////////////////////////////////////////////////////////////
// Class: CS400, Summer 2020
// Name: Anna Mikula
// Email: ammikula@wisc.edu
// Project: Final Project
// Description: This class is used for the check box data and getting that data back to the GUI.
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
public class Data implements DataStructureADT {
  
  private double[][]data;
  
  /**
   * This constructor initializes the data to be used for getting the information.
   * 
   * @param data  The data of the state [date][confirmed cases]
   */
  public Data(double[][]data) {
    this.data = data;
  }

  /**
   * This method gets the minimum case number from the data.
   * 
   * @return String minimum  The minimum case number from the data.
   */
  @Override
  public String getMinimumCaseNumber() {
    int minimum = (int) data[0][1];
    
    for(int i = 1; i < data.length; i++) {
      if(data[i][0] != 0) { //should be the date, so that is when you know its an empty entry
        if(data[i][1] < minimum && data[i][1] >= 0) {
          minimum = (int) data[i][1];
        }
      }
    }
    
    return String.valueOf(minimum);
  }

  /**
   * This method gets the maximum case number from the data.
   * 
   * @return String maximum  The maximum case number from the data.
   */
  @Override
  public String getMaximumCaseNumber() {
    int maximum = (int) data[0][1];
    
    for(int i = 1; i < data.length; i++) {
      if(data[i] != null) {
        if(data[i][1] > maximum) {
          maximum = (int) data[i][1];
        }
      }
    }
    
    return String.valueOf(maximum);
  }
  
  /**
   * This method gets the average case number from the time period from the data.
   * 
   * @return String average  The average case number per day.
   */
  @Override
  public String getAverageCases() {
    int average = 0;
    int count = 0;
    
    for(int i = 0; i < data.length; i++) {
      if(data[i] != null) {
        average += (int) data[i][1];
        count++;
      }
    }
    
    average = average / count;
    
    return String.valueOf(average);
  }

}
