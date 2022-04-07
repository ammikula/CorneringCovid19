/////////////////////////////////////////////////////////////////////////////////////////////////
// Class: CS400, Summer 2020
// Name: Anna Mikula
// Email: ammikula@wisc.edu
// Project: Final Project
// Description: This class segregates all the US data into the specific state.
///////////////////////////////////////////////////////////////////////////////////////////////////

package Application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This class takes the file names, gets the data for the specific state, and sends the 
 * data back to the GUI to form it into the line graph.
 * 
 * @author annamikula
 *
 */
public class SegregateState {
  
  //creating variables 
  private ArrayList<String> fileNames;
  private String stateName;
  private int numberOfData;
  
  /**
   * This is the constructor that declares and initializes the variables to be used.
   * 
   * @param state  The state name.
   * @param fileNames  The file names that will be used for data collection.
   * @param numberOfData  Number of files used, this number is used for array purposes.
   */
  public SegregateState(String state, ArrayList<String> fileNames, int numberOfData) {
    this.fileNames = fileNames;
    this.stateName = state;
    this.numberOfData = numberOfData;
  }
  
  /**
   * This method gets the state data for the state. It puts it in the format of the date, followed
   * by number of confirmed cases. 
   * 
   * @return int[][] The data needed to fill the line graph.
   */
  public double[][] getStateData(){
    String[] temp = null;
    String[][] data = new String[numberOfData][2];
    int numOfData = 0;
    
    //iterates through file names
    ListIterator<String> it = fileNames.listIterator();
    
    while (it.hasNext()) {
      
      String fileName = "COVID_DATA/" + it.next() + ".csv"; 
 
      try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
        String line;

        //reads the .cvs files and scans for the state name
        while ((line = br.readLine()) != null) {
          temp = line.split(",");
          if(temp[0].equals(stateName)) {
            data[numOfData][0] = fileName.substring(11, 21);
            data[numOfData][1] = temp[5]; //the confirmed case number is 5 indexes away
            numOfData++;
            break;
          }
        }
      } catch (FileNotFoundException e) {
        //System.out.println("EROOR: fileNotFound" + fileName);
        //this is fine, don't need tons of points
      } catch (IOException e) {
        //System.out.println("EROOR: other");
        //unexpected exception
      }
    }
    
    return getGraphFormattedData(data);
  }
  
  /**
   * This method gets the data into double format to be graphed in the line chart.
   * 
   * @param data  The string data that is used to get the numerical data.
   * @return double[][] graphData  The graph data that will be used for the line graph.
   */
  private double[][] getGraphFormattedData(String[][] data){
    double[][]graphData = new double[numberOfData][2];
    
    for(int i = 0; i < data.length; i++) {
      if(data[i][1] != null) {
        int day = Integer.parseInt(data[i][0].substring(3, 5));
        int month = Integer.parseInt(data[i][0].substring(0, 2));
        int year = Integer.parseInt(data[i][0].substring(6, 10));
        double monthDays = getMonthDays(month, year);
        
        graphData[i][0] = (day / monthDays) + month; //x axis number
        
        if(i == 0) {
          graphData[i][1] = Integer.parseInt(data[i + 1][1]) - Integer.parseInt(data[i][1]);
        } else {
          graphData[i][1] = Integer.parseInt(data[i][1]) - Integer.parseInt(data[i - 1][1]);
        }
      }
    }
    
    return graphData;
  }
  
  /**
   * This private helper method gets the number of days in the specific month.
   * 
   * @param month  The month to get the number of days for.
   * @param year The year, solely for leap year purposes.
   * @return int  The number of days in the month.
   */
  private int getMonthDays(int month, int year) {
    
    switch (month) {
      case 1:
        return 31;
      case 2:
        if(year % 4 == 0) {
          return 29;
        } else {
          return 28;
        }
      case 3:
        return 31;
      case 4:
        return 30;
      case 5:
        return 31;
      case 6:
        return 30;
      case 7:
        return 31;
      case 8:
        return 31;
      case 9:
        return 30;
      case 10:
        return 31;
      case 11:
        return 30;
      case 12:
        return 31;
    }
    
    return 0;
  }
  
  /**
   * This method gets the total deaths for the state.
   * 
   * @return String totalDeaths  The total deaths of the state.
   */
  public String getTotalDeaths() {
    String[] temp = null;
    int totalDeaths = 0;
    String lastFileName = "";
    
    //iterates through file names
    ListIterator<String> it = fileNames.listIterator();
    
    //gets last file which has total deaths cumulative
    while(it.hasNext()) {
      lastFileName = it.next();
    }
    
    String fileName = "COVID_DATA/" + lastFileName + ".csv";
    
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while((line = br.readLine()) != null) {
        temp = line.split(",");
        if(temp[0].equals(stateName)){
          totalDeaths = Integer.parseInt(temp[6]); //death count is 6 index away from state name
          break;
        }
      }
    } catch (FileNotFoundException e) {
      //System.out.println("EROOR: fileNotFound - totalDeaths()");
      //this is fine, not all files are needed 
    } catch (IOException e) {
      //System.out.println("EROOR: other - totalDeaths()");
    }
    
    return String.valueOf(totalDeaths);
  }
  
}
