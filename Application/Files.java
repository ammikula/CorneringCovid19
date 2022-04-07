/////////////////////////////////////////////////////////////////////////////////////////////////
// Class: CS400, Summer 2020
// Name: Anna Mikula
// Email: ammikula@wisc.edu
// Project: Final Project
// Description: This class is used for getting the file names to get the data from.
///////////////////////////////////////////////////////////////////////////////////////////////////

package Application;

import java.util.ArrayList;

/**
 * This class is used for getting the file names to get the data from for the line grpah.
 * 
 * @author annamikula
 *
 */
public class Files {
  
  //variables to be used
  private int yearStartNum;
  private int monthStartNum;
  private int dayStartNum;
  private int monthEndNum;
  private int dayEndNum;
  private ArrayList<String>fileNames = new ArrayList<String>();
  private ArrayList<String> allFileNames = new ArrayList<String>();
  
  /**
   * This constructor initializes the variables passed in from the user.
   * 
   * @param startDate  The start date in format "MM-dd-yyyy"
   * @param endDate The end date in format "MM-dd-yyyy"
   */
  public Files(String startDate, String endDate) {
    this.yearStartNum = Integer.parseInt(startDate.substring(6, 10));
    this.monthStartNum = Integer.parseInt(startDate.substring(0, 2));
    this.dayStartNum = Integer.parseInt(startDate.substring(3,5));
    this.monthEndNum = Integer.parseInt(endDate.substring(0, 2));
    this.dayEndNum = Integer.parseInt(endDate.substring(3,5));
  }
  
  /**
   * This method gets the starting months number.
   * 
   * @return int monthStartNum  The starting month number.
   */
  public int getMonthStartNum() {
    return monthStartNum;
  }
  
  /**
   * This method gets the ending months number
   * 
   * @return int monthEndNum  The ending months number.
   */
  public int getMonthEndNum() {
    return monthEndNum;
  }
  
  /**
   * This method takes every 7 files within the range of dates to get the data for
   * the line graph.
   * 
   * @return ArrayList<String> fileNames  The file names for getting the data.
   */
  public ArrayList<String> getFiles() {
    int i = monthStartNum;
    
    for(int j = dayStartNum; j <= getMonthDays(i); j++) {
        if(monthStartNum < 10) {
          if(j < 10) {
            fileNames.add("0" + i + "-0" + j + "-" + yearStartNum); //for months 1-9
          } else {
            fileNames.add("0" + i + "-" + j + "-" + yearStartNum); //for months 1-9
          }
        } else {
          if(j < 10) {
            fileNames.add(i + "-0" + j + "-" + yearStartNum);
          } else {
            fileNames.add(i + "-" + j + "-" + yearStartNum);
          }
        }
        if(j >= getMonthDays(i)) {
          j = 1;
          i++;
        }
        if(i == monthEndNum && j >= dayEndNum) {
          break;
        }
    }
    
    return fileNames;
  }
  
  /**
   * This method returns ALL files for the dates selected, unlike what was done above for the data
   * for the line graph. This is used for getting the accurate minimum case number and accurate 
   * maximum case number, instead of just picking the min and max from the random selection.
   * 
   * @return ArrayList<String> allFiles  A list of all the files needed to collect data form.
   */
  public ArrayList<String> getAllFiles(){
    int i = monthStartNum;
    
    for(int j = dayStartNum; j <= getMonthDays(i); j++) {
      if(monthStartNum < 10) {
        allFileNames.add("0" + i + "-" + j + "-" + yearStartNum); //for months 1-9
      } else {
        allFileNames.add(i + "-" + j + "-" + yearStartNum);
      }
      if(j >= getMonthDays(i)) {
        j = 1;
        i++;
      }
      if(i == monthEndNum && j >= dayEndNum) {
        break;
      }
    }
    
    return allFileNames;
  }
  
  /**
   * This method gets the number of days in the month.
   * 
   * @param month  The month to get the number of days out.
   * @return int  The number of days in the month.
   */
  private int getMonthDays(int month) {
    
    switch (month) {
      case 1:
        if(monthEndNum == 1) {
          return 31 - dayEndNum;
        } else {
          return 31;
        }
      case 2:
        int days = 28;
        if(yearStartNum % 4 == 0) {
          days = 29;
        } 
        if(monthEndNum == 2) {
          return days - dayEndNum;
        } else {
          return days;
        }
      case 3:
        if(monthEndNum == 3) {
          return 31 - dayEndNum;
        } else {
          return 31;
        }
      case 4:
        if(monthEndNum == 4) {
          return 30 - dayEndNum;
        } else {
          return 30;
        }
      case 5:
        if(monthEndNum == 5) {
          return 31 - dayEndNum;
        } else {
          return 31;
        }
      case 6:
        if(monthEndNum == 6) {
          return 30 - dayEndNum;
        } else {
          return 30;
        }
      case 7:
        if(monthEndNum == 7) {
          return 31 - dayEndNum;
        } else {
          return 31;
        }
      case 8:
        if(monthEndNum == 8) {
          return 31 - dayEndNum;
        } else {
          return 31;
        }
      case 9:
        if(monthEndNum == 9) {
          return 30 - dayEndNum;
        } else {
          return 30;
        }
      case 10:
        if(monthEndNum == 10) {
          return 31 - dayEndNum;
        } else {
          return 31;
        }
      case 11:
        if(monthEndNum == 11) {
          return 30 - dayEndNum;
        } else {
          return 30;
        }
      case 12:
        if(monthEndNum == 12) {
          return 31 - dayEndNum;
        } else {
          return 31;
        }
    }
    
    return 0;
  }
  
  /**
   * Gets the file number amount needed for array building.
   * 
   * @return int allFileNames if allFiles is true, or fileNames if allFiles is false.
   */
  public int getTotalDays(boolean allFiles) {
    if(allFiles) {
      return allFileNames.size();
    } 
    
    return fileNames.size();
  }

}
