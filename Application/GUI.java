/////////////////////////////////////////////////////////////////////////////////////////////////
// Class: CS400, Summer 2020
// Name: Anna Mikula
// Email: ammikula@wisc.edu
// Project: Final Project
// Description: This is the GUI for my Cornering COVID program.
//       This program will take your input state and show
//       the COVID-19 daily progression over the chosen start date and end date. This program
//       will also allow the you to pick what additional data for your state you would
//       like to know.
///////////////////////////////////////////////////////////////////////////////////////////////////

package Application;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * This class is the GUI for Cornering COVID. It displays all the questions and receives input,
 * then uses helper classes to produce a line graph, and the total death / minimum case number /
 * maximum case number / and or average case number to the user in a separate stage.
 * 
 * @author annamikula
 *
 */
public class GUI extends Application {
  
  //initializing variables
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 1000;
  private static final String APP_TITLE = "Cornering COVID";
  
  /**
   * Main stage, what the user first see's when opening the program.
   * 
   * Gives information on what the program is, and takes the input to produce the second
   * stage.
   * 
   * @param primaryStage  The stage to show the program.
   * @throws Exception  Throws a NullPointerException if state is not selected.
   * 
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    VBox mainPane = new VBox();
    mainPane.setAlignment(Pos.CENTER);
    mainPane.setStyle("-fx-background-color: #F0F8FF;");
    
    //title
    Label titleLabel = new Label("Cornering COVID");
    titleLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
    
    //image
    FileInputStream input = new FileInputStream("/Users/annamikula/eclipse-workspace/FinalProject/covid.png");
    Image titleImage = new Image(input);
    ImageView view = new ImageView(titleImage);
    view.setFitHeight(50);
    view.setPreserveRatio(true);
    titleLabel.setGraphic(view);
    
    //description
    Text descriptionText = new Text("This program will take your selected state"
        + " and display the number of confirmed cases of COVID-19 in that state"
        + " from the selected start date to the selected end date with a line graph. "
        + "You are also able to check what additional information you'd like to receive" 
        + " about your state. ");
    descriptionText.setFont(new Font("Times New Roman", 20));
    descriptionText.setWrappingWidth(600);
    descriptionText.setTextAlignment(TextAlignment.CENTER);
    
    //picking state label and combo box
    Label stateInputLabel = new Label("Please Pick Your State:");
    stateInputLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    stateInputLabel.setTextAlignment(TextAlignment.CENTER);
    
    ComboBox<String> stateList = new ComboBox<String>();
    String[]stateStrings = {
        "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
        "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
        "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
        "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
        "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
        "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
        "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", 
        "Wisconsin", "Wyoming"
    };
    
    stateList.getItems().addAll(stateStrings);
    stateInputLabel.setLabelFor(stateList);
    stateList.setPromptText("-select one-");
    
    //check boxes for selections
    Label additionalInformationLabel = new Label("Please check additional information"
        + " you would like to know about your state:");
    additionalInformationLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    
    CheckBox totalDeathsCheckBox = new CheckBox("Total Deaths");
    
    CheckBox minimumCaseCheckBox = new CheckBox("Mimimum number of cases in a day");
    
    CheckBox maximumCaseCheckBox = new CheckBox("Maximum number of cases in a day");
    
    CheckBox averageCaseCheckBox = new CheckBox("Average number of cases per day");
    
    //date picker start date
    Label startDateLabel = new Label("Start Date: (between 04/12/2020 - 08/08/2020)");
    startDateLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    DatePicker startDatePicker = new DatePicker(LocalDate.of(2020, 04, 12));
    
    //date picker end date
    Label endDateLabel = new Label("End Date: (between start date - 08/08/2020)");
    endDateLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    DatePicker endDatePicker = new DatePicker(LocalDate.of(2020, 8, 8));
    
    //submit button & action event
    Button submitButton = new Button("Submit");
    submitButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    submitButton.setStyle("-fx-background-color: #0000FF;");
    
    //action even when submit is clicked
    submitButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        boolean moveOn = true;
        boolean[]checkBoxChoices = new boolean[4];
        String stateName = "";
        
        //valid state name check
        try {
          if(stateList.getValue() != null) {
            stateName = stateList.getValue();
          } else {
            throw new NullPointerException();
          }
        } catch (NullPointerException e2) {
          stateInputLabel.setTextFill(Paint.valueOf("#FF0000"));
          stateInputLabel.setText("*REQUIRED* Please Pick Your State:");
          moveOn = false;
        }
        
        checkBoxChoices[0] = totalDeathsCheckBox.isSelected(); // ***IMPORTANT INPUT #2***
        checkBoxChoices[1] = minimumCaseCheckBox.isSelected(); // ***IMPORTANT INPUT #3***
        checkBoxChoices[2] = maximumCaseCheckBox.isSelected(); // ***IMPORTANT INPUT #4***
        checkBoxChoices[3] = averageCaseCheckBox.isSelected(); // ***IMPORTANT INPUT #5***
        
        String startDate = 
            startDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")); 
        // ****IMPORTANT IMPUT #6****
        String endDate = 
            endDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")); 
        // ****IMPORTANT IMPUT #7****
        
        //making sure valid start date
        if(startDate == null) {
          startDateLabel.setTextFill(Paint.valueOf("#FF0000"));
          moveOn = false;
        } else {
          int yearStartNum = Integer.parseInt(startDate.substring(6, 10));
          int monthStartNum = Integer.parseInt(startDate.substring(0, 2));
          int dayStartNum = Integer.parseInt(startDate.substring(3,5));
          if(yearStartNum < 2020 || yearStartNum > 2020) {
            startDateLabel.setText("Must be in the year of 2020");
            startDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          } else if (monthStartNum < 4 || monthStartNum > 8) {
            startDateLabel.setText("Must be between April and August");
            startDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          } else if (monthStartNum == 4 && dayStartNum < 12) {
            startDateLabel.setText("Must be after April 12th");
            startDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          } else if (monthStartNum == 8 && dayStartNum > 8) {
            startDateLabel.setText("Must be before August 8th");
            startDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          }
        }
        
        //making sure valid end date
        if(endDate == null) {
          endDateLabel.setTextFill(Paint.valueOf("#FF0000"));
          moveOn = false;
        } else {
          int yearEndNum = Integer.parseInt(endDate.substring(6, 10));
          int monthEndNum = Integer.parseInt(endDate.substring(0, 2));
          int dayEndNum = Integer.parseInt(endDate.substring(3,5));
          if(yearEndNum < 2020 || yearEndNum > 2020) {
            endDateLabel.setText("Must be in the year of 2020");
            endDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          } else if (monthEndNum < 4 || monthEndNum > 8) {
            endDateLabel.setText("Must be between April and August");
            endDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          } else if (monthEndNum == 4 && dayEndNum < 12) {
            endDateLabel.setText("Must be after April 12th");
            endDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          } else if (monthEndNum == 8 && dayEndNum > 8) {
            endDateLabel.setText("Must be before August 8th");
            endDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          }
        }
        
        //making sure end date is not before the the start date
        if(moveOn) {
          int monthStartNum = Integer.parseInt(startDate.substring(0, 2));
          int dayStartNum = Integer.parseInt(startDate.substring(3,5));
          int monthEndNum = Integer.parseInt(endDate.substring(0, 2));
          int dayEndNum = Integer.parseInt(endDate.substring(3,5));
          
          if(monthStartNum > monthEndNum) {
            endDateLabel.setText("The start date must be before the end date");
            endDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          } else if (monthStartNum == monthEndNum && dayEndNum < dayStartNum) {
            endDateLabel.setText("The start date must be before the end date");
            endDateLabel.setTextFill(Paint.valueOf("#FF0000"));
            moveOn = false;
          }
        }
        
        
        //if everything is valid, move on to next scene
        if(moveOn) {
          try {
            newScene(primaryStage, stateName, checkBoxChoices, startDate, endDate);
          } catch (Exception e1) {
            //e1.printStackTrace();
          }
        }

      }
    });
    
    //reset button
    Button resetButton = new Button("Reset");
    resetButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    resetButton.setStyle("-fx-background-color: #FF0000;");
    resetButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        stateList.getSelectionModel().clearSelection();
        totalDeathsCheckBox.setSelected(false);
        minimumCaseCheckBox.setSelected(false);
        maximumCaseCheckBox.setSelected(false);
        averageCaseCheckBox.setSelected(false);
        startDatePicker.getEditor().clear();
        startDatePicker.setValue(LocalDate.of(2020, 04, 12));
        endDatePicker.getEditor().clear();
        endDatePicker.setValue(LocalDate.now());
      }
    });
    
    //button box
    HBox buttonBox = new HBox();
    buttonBox.getChildren().addAll(submitButton, resetButton);
    buttonBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);
    
    //main pane settings 
    mainPane.setSpacing(10);
    
    VBox.setMargin(stateInputLabel, new Insets(20, 0, 0, 0));
    VBox.setMargin(additionalInformationLabel, new Insets(20, 0, 0, 0));
    VBox.setMargin(startDateLabel, new Insets(20, 0, 0, 0));
    VBox.setMargin(buttonBox, new Insets(40, 0, 0, 0));
    
    ObservableList<Node> list = mainPane.getChildren();
    
    list.addAll(titleLabel, descriptionText, stateInputLabel, stateList, 
        additionalInformationLabel, totalDeathsCheckBox, minimumCaseCheckBox, 
        maximumCaseCheckBox, averageCaseCheckBox, startDateLabel, startDatePicker,
        endDateLabel, endDatePicker, buttonBox);
    
    
    Scene mainScene = new Scene(mainPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    
    // Add the stuff and set the primary stage
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);
    primaryStage.show();
    
  }
  
  /**
   * This is the new scene for the UI. It begins by adding the javafx labels, and then gets the
   * data needed for the line graph and check boxes, and then displays it.
   * 
   * @param primaryStage The stage to set the scene on.
   * @param endDate  The end date picked by user.
   * @param startDate  The start date picked by user.
   * @param checkBoxChoices  The choices for the check boxes, true if picked, false if not.
   * @param stateName  The state name.
   */
  @SuppressWarnings({"rawtypes", "unchecked"}) 
  public void newScene(Stage primaryStage, String stateName, boolean[] checkBoxChoices, 
      String startDate, String endDate) throws Exception {
    
    VBox dataPane = new VBox();
    dataPane.setAlignment(Pos.CENTER);
    dataPane.setStyle("-fx-background-color: #F0F8FF;");
    
    //title
    Label titleLabel = new Label("Cornering COVID");
    titleLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
    
    //description
    Text descriptionText = new Text("This line graph shows the trend of the number of cases per"
        + " day in " + stateName + ". Data numbers are taken from real data sets, and are"
        + " as accurately represented as possible;"
        + " case counts being minorly inaccurate is feasible.");
    descriptionText.setFont(new Font("Times New Roman", 20));
    descriptionText.setWrappingWidth(600);
    descriptionText.setTextAlignment(TextAlignment.CENTER);
    
    //image
    FileInputStream input = new FileInputStream("/Users/annamikula/eclipse-workspace/FinalProject/covid.png");
    Image titleImage = new Image(input);
    ImageView view = new ImageView(titleImage);
    view.setFitHeight(50);
    view.setPreserveRatio(true);
    titleLabel.setGraphic(view);
    
    //getting data to show
    Files files = new Files(startDate, endDate);
    ArrayList<String> fileNames= files.getFiles();
    int numberOfDays = files.getTotalDays(false);
    SegregateState stateData = new SegregateState(stateName, fileNames, numberOfDays);
    double[][]dataInformation = stateData.getStateData();
    Data userInformation = new Data(dataInformation);
    
    //defining line graph variables
    NumberAxis xAxis = new NumberAxis(files.getMonthStartNum(), files.getMonthEndNum() + 1, 1);
    xAxis.setLabel("Month");
    
    NumberAxis yAxis = new NumberAxis(0, 
        Integer.parseInt(userInformation.getMaximumCaseNumber()) + 10, Integer.parseInt(userInformation.getMaximumCaseNumber()) / 100);
    yAxis.setLabel("Number of Cases Per Day");
    
    LineChart covidLineChart = new LineChart(xAxis, yAxis);
    
    //defining x y variables
    XYChart.Series series = new XYChart.Series();
    series.setName("Number of Cases in " + stateName + " Per Day");
    
    
    //adding data to graph
    for(int i = 0; i < dataInformation.length; i++) {
      if(dataInformation[i] != null) {
        series.getData().add(new XYChart.Data(dataInformation[i][0], dataInformation[i][1]));
        i = i + 4;
      }
    }
    
    covidLineChart.getData().add(series); 
    covidLineChart.setMaxWidth(400);
    covidLineChart.setMaxHeight(5000);
    
    ObservableList<Node> list = dataPane.getChildren();
    
    list.add(titleLabel);
    list.add(descriptionText);
    list.add(covidLineChart);
    
    //total deaths check box
    if(checkBoxChoices[0]) {
      Label totalDeathLabel = new Label("Total Deaths: "
          + stateData.getTotalDeaths());
      totalDeathLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
      list.add(totalDeathLabel);
    }
    
    //minimum number of cases check box
    if(checkBoxChoices[1]) {
      Label minimumCaseLabel = new Label("Minimum Number of Cases (in one day): "
          + userInformation.getMinimumCaseNumber());
      minimumCaseLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
      list.add(minimumCaseLabel);
    }
    
    //maximum number of cases check box
    if(checkBoxChoices[2]) {
      Label maximumCaseLabel = new Label("Maximum Number of Cases (in one day): "
          + userInformation.getMaximumCaseNumber());
      maximumCaseLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
      list.add(maximumCaseLabel);
    }
    
    //average number of cases check box
    if(checkBoxChoices[3]) {
      Label averageCasesPerDayLabel = new Label("Average Number of Cases (per day): " 
          + userInformation.getAverageCases());
      averageCasesPerDayLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
      list.add(averageCasesPerDayLabel);
    }
    
    //exit button
    Button exitButton = new Button("Exit");
    exitButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    exitButton.setStyle("-fx-background-color: #FF0000;");
    exitButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        primaryStage.close();
      }
    });
    
    list.add(exitButton);
    
    dataPane.setSpacing(10);
    VBox.setMargin(exitButton, new Insets(40, 0, 0, 0));
    
    //setting scene and show
    Scene dataScene = new Scene(dataPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(dataScene);
    primaryStage.show();
  }
  
  /**
   * Launch method for the program.
   * 
   * @param args unused.
   */
  public static void main(String[] args) {
      launch(args);
  }

}
