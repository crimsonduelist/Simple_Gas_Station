/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gastation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.plaf.basic.BasicBorders;

/**
 *
 * @author WiZ14
 */
public class Gastation extends Application {
   Stage stage;
    TextField creditnumber; //static int creditcard;
    //secondstage
    TextField money; Label typel,moneyl;Scene moneys;RadioButton r1,r2,r3;
    ToggleGroup togglegroup;Button gaschoice;
    //thirdstage
    TextField moneylimit,gaspumped;Label gaspumpedl,moneylimitl;Scene gaspumpeds;
    Timer timer1;double gaspumpedt,moneyt;Button start,stop;double gasprice;
    String moneytstring;String gaspumpedtstring;
    
    @Override
    public void start(Stage primaryStage) {
      stage=primaryStage;
        Label creditl=new Label("credit number");creditnumber=new TextField("4547755341205228");/*"4547755341205228"*//*<-correct creditcard*/
        Button creditcheck=new Button("CreditCheck");
        creditcheck.setOnAction(e-> CreditCheck(creditnumber.getText()));
       // StackPane root = new StackPane();
       // root.getChildren().add(btn);
        VBox root = new VBox(creditl,creditnumber,creditcheck);
        Scene scene = new Scene(root, 300, 250);
       gaschoice=new Button("proceed");gaschoice.setOnAction(e->GasPump(((RadioButton)togglegroup.getSelectedToggle()).getText(),money.getText()));//stage2
        primaryStage.setTitle("GasStation!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**Long.parseLong()
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
public void Test(String creditcardnumber,String gaschoice,String money,String gas,String bill){try {

        String content = "\n"+"Date:"+LocalTime.now()+"\n Time:"+LocalDate.now()+"\n creditcardnumber:"+Long.parseLong(creditcardnumber)%10000+"\n"+"Gaschoice:"+gaschoice+"\n"+"Gas:"+gas+" liters"+"\n"+"bill:"+bill;

        File file = new File(/*insert path here*/"C:\\Users\\User\\Desktop\\testjava\\test.txt");

        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();

       // System.out.println("Done");

    } catch (IOException e) {
        e.printStackTrace();
    }
}
    private void CreditCheck(String creditcardnumber) {
  if (checkLuhn(creditcardnumber)==true){
     /* System.out.println(creditcardnumber);Test(creditcardnumber)*/;Gaschoice();}else{System.out.println(creditcardnumber + "is an invalid credit card number");}

  
        
    }
    static boolean checkLuhn(String cardNo) 
{ 
    int nDigits = cardNo.length(); 
  
    int nSum = 0; 
    boolean isSecond = false; 
    for (int i = nDigits - 1; i >= 0; i--)  
    { 
  
        int d = cardNo.charAt(i) - '0'; 
  
        if (isSecond == true) 
            d = d * 2; 
  
        // We add two digits to handle 
        // cases that make two digits  
        // after doubling 
        nSum += d / 10; 
        nSum += d % 10; 
  
        isSecond = !isSecond; 
    } 
    return (nSum % 10 == 0); 
} //first stage
    public void Gaschoice(){
r1=new RadioButton("regular");
r2=new RadioButton("midgrade");r3=new RadioButton("premium");
togglegroup=new ToggleGroup();r1.setToggleGroup(togglegroup);r2.setToggleGroup(togglegroup);r3.setToggleGroup(togglegroup); r1.setSelected(true);
        typel=new Label("type"); moneyl=new Label("money");money=new TextField("0"); 
       
        VBox root = new VBox(typel,r1,r2,r3,moneyl,money,gaschoice);
        moneys = new Scene(root, 300, 250);
        stage.setScene(moneys);
    }
    public void GasPump(String gaschoice,String money){
        /*System.out.println(gaschoice+money);*/
        moneylimitl=new Label("money");gaspumpedl=new Label("gaspumped");moneylimit=new TextField("0.0");gaspumped=new TextField("0.0");moneylimit.setDisable(true);gaspumped.setDisable(true);
        start=new Button("Start");stop=new Button("Stop");switch (gaschoice){case "regular":gasprice=0.4;break;case "midgrade":gasprice=0.6;break;case "premium":gasprice=0.8;break;}
        
        timer1 = new Timer();
        TimerTask task1 = new TimerTask(){
        public void run()
        {
          gaspumpedt+=0.5;  
          moneyt=Double.valueOf(moneylimit.getText());
          moneyt+=(gasprice*0.5);
          /*String */moneytstring = String.valueOf(moneyt);/* String */gaspumpedtstring = String.valueOf(gaspumpedt);
          Platform.runLater(() -> moneylimit.setText(moneytstring));Platform.runLater(() -> gaspumped.setText(gaspumpedtstring));
          if(moneyt>=Double.valueOf(money)){timer1.cancel();Test(creditnumber.getText(),gaschoice,money,gaspumpedtstring,moneytstring);System.out.println("\n"+"Date:"+LocalTime.now()+"\n Time:"+LocalDate.now()+"\n creditcardnumber:"+Long.parseLong(creditnumber.getText())%10000+"\n "+"Gaschoice:"+gaschoice+"\n "+"Gas:"+gaspumpedtstring+"liters"+"\n "+"bill:"+moneytstring);start.setDisable(true);stop.setDisable(true);/*stage.close();*/}
         }
    };start.setOnAction(e->{timer1.schedule(task1,0,100);});stop.setOnAction(e->{timer1.cancel();Test(creditnumber.getText(),gaschoice,money,gaspumpedtstring,moneytstring);System.out.println("\n"+"Date:"+LocalTime.now()+"\n Time:"+LocalDate.now()+"\n creditcardnumber:"+"creditcardnumber:"+Long.parseLong(creditnumber.getText())%10000+"\n "+"Gaschoice:"+gaschoice+"\n"+" Gas:"+gaspumpedtstring+" liters"+"\n "+"bill:"+moneytstring);start.setDisable(true);stop.setDisable(true);});
        
        VBox root = new VBox(moneylimitl,moneylimit,gaspumpedl,gaspumped,start,stop);
        gaspumpeds = new Scene(root, 300, 250);
        stage.setScene(gaspumpeds);stage.setOnCloseRequest(e->timer1.cancel());
    }
}
