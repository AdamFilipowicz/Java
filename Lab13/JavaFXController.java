import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JOptionPane;

public class JavaFXController implements Initializable {
    
    private List playerLeftCards = new ArrayList<Integer>();
    private List computerLeftCards = new ArrayList<Integer>();
    
    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    private Invocable invocable = null;
    
    private int playerPoints = 0;
    private int computerPoints = 0;
    
    private boolean blad = false;
    private boolean pcTurn = false;
    private boolean playerTurn;
    
    @FXML
    private Label coPoints;
    @FXML
    private Label plPoints;
    @FXML
    private Label turnText;
    
    @FXML
    private RadioButton computerButton;
    @FXML
    private RadioButton playerButton;
    
    @FXML
    private Button nextTurnButton;
    @FXML
    private Button pickCardButton;
    @FXML
    private Button endButton;
    @FXML
    private Button startButton;
    
    @FXML
    private CheckBox seeCardsCheckBox;
    
    @FXML
    private ListView<String> playerCardList;
    @FXML
    private ListView<String> computerCardList;
    
    @FXML
    private TextField playerPlayedCardText;
    @FXML
    private TextField range1Text;
    @FXML
    private TextField range2Text;
    @FXML
    private TextField computerPlayedCardText;
    
    @FXML
    private ComboBox<String> strategyBox;
    
    @FXML
    private MenuItem autor;
    @FXML
    private MenuItem opisProgramu;
    @FXML
    private MenuItem coral;
    @FXML
    private MenuItem aliceBlue;
    @FXML
    private MenuItem aqua;
    @FXML
    private MenuItem mediumSpringGreen;
    @FXML
    private MenuItem tomato;
    
    @FXML
    private AnchorPane anchor;
    
    @FXML
    private VBox box;
    
    @FXML
    private Slider slider;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshPoints();
        nextTurnButton.setDisable(true);
        pickCardButton.setDisable(true);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double val = slider.getValue();
            anchor.setRotate(val);
        });
    }    
    
    @FXML
    private void startButtonActionPerformed(ActionEvent evt) {                                            
        if(!computerButton.isSelected() && !playerButton.isSelected()){
            showError("wybierz kto rozpocznie gre");
            return;
        }
        else if(computerButton.isSelected())
            playerTurn=false;
        else
            playerTurn=true;
        try{
            int i=initCardList(Integer.parseInt(range1Text.getText()), Integer.parseInt(range2Text.getText()));
            if(i==100){
                showError("Podano bledny zakres liczbowy");
            }
            if(i==101){
                showError("Liczby zakresu musza byc mniejsze od 10000 i wieksze od -1000");
            }
            if(i==1){
                showMsg("Gra rozpoczeta");
                if(playerTurn){
                    turnText.setText("Tura gracza");
                    pcTurn = false;
                    pickCardButton.setDisable(false);
                }
                else{
                    turnText.setText("Tura komputera");
                    pcTurn = true;
                    pcTurn();
                }
            }
        }
        catch(Exception e){
            showError("Podaj liczby calkowite");
            return;
        }
        startButton.setDisable(true);
        endButton.setDisable(false);
    }                                           

    private void pcTurn(){
        pickCardButton.setDisable(true);
        
        if(invocable==null){
            showError("Nie zostal wybrany zaden algorytm lub zostal usuniety");
            blad=true;
            return;
        }
        Object result = null;
        try {
            String str = tabToString(computerListToTab());
            String str2 = tabToString(playerListToTab());
            int playerCardd = 0;
            if(!pcTurn)
                playerCardd = Integer.parseInt(playerPlayedCardText.getText());
            result = invocable.invokeFunction("algorytm", str, str2, pcTurn, playerCardd);
        } catch (Exception ex) {
            showError("Bledne wywolanie funkcji");
            blad=true;
            return;
        }
        
        int cardID = (int) Double.parseDouble(result.toString());
        int playedCard = Integer.parseInt((String)computerLeftCards.get(cardID));
        computerLeftCards.remove(cardID);
        String[] listData2 =computerListToTab();
        computerCardList.setItems(FXCollections.observableArrayList(computerLeftCards));
        //computerCardList.setListData(listData2);
        computerPlayedCardText.setText(Integer.toString(playedCard));
        if(playerPlayedCardText.getText()!=null && !playerPlayedCardText.getText().equals("")){
            //zatem komputer odpowiedzial na ture gracza
            pcTurn = true;
            calculatePoints();
            refreshPoints();
            nextTurnButton.setDisable(false);
        }
        else{
            //komputer zaczal ture
            pcTurn = false;
            turnText.setText("Tura gracza");
            pickCardButton.setDisable(false);
        }
        if(playerLeftCards.isEmpty())
            endGame();
    }
    
    @FXML
    private void endButtonActionPerformed(ActionEvent evt) {                                          
        String[] listData = new String[10];
        for(int i=0;i<10;i++)
            listData[i]="";
        playerCardList.setItems(null);
        computerCardList.setItems(null);
        //playerCardList.setListData(listData);
        //computerCardList.setListData(listData);
        endButton.setDisable(true);
        startButton.setDisable(false);
        playerPoints = 0;
        computerPoints = 0;
        playerPlayedCardText.setText("");
        computerPlayedCardText.setText("");
        refreshPoints();
    }                                         

    @FXML
    private void computerButtonActionPerformed(ActionEvent evt) {                                               
        playerButton.setSelected(false);
    }                                              

    @FXML
    private void playerButtonActionPerformed(ActionEvent evt) {                                             
        computerButton.setSelected(false);
    }                                            

    @FXML
    private void pickCardButtonActionPerformed(ActionEvent evt) {   
        String karta = playerCardList.getSelectionModel().getSelectedItem();
        if(karta!=null && !karta.equals("")){
            playerPlay(karta);
        }
    }                                              

    @FXML
    private void nextTurnButtonActionPerformed(ActionEvent evt) {                                               
        nextTurnButton.setDisable(true);
        pickCardButton.setDisable(true);
        playerPlayedCardText.setText("");
        computerPlayedCardText.setText("");
        if(turnText.getText().equals("Tura komputera"))
            pcTurn();
        else
            pickCardButton.setDisable(false);
    }                                              

    @FXML
    private void seeCardsCheckBoxActionPerformed(ActionEvent evt) {                                               
        if(!seeCardsCheckBox.isSelected()){
            computerCardList.setItems(null);
        }
        else{
            computerCardList.setItems(FXCollections.observableArrayList(computerLeftCards));
        }
    }                                              

    @FXML
    private void strategyBoxActionPerformed(ActionEvent evt) {                                            
        loadStrategy();
    }                                           

    private void loadStrategy(){
        String sel = strategyBox.getSelectionModel().getSelectedItem();
        if(sel!=null && !sel.equals("")){
            File strategy = new File("src/Strategies/"+sel+".js");
            if(strategy.exists()){
                try {
                    engine.eval(new FileReader("src/Strategies/"+sel+".js"));
                    invocable = (Invocable) engine;
                    return;
                } catch (Exception ex) {
                    showError("Blad odczytu pliku");
                    invocable = null;
                    return;
                }
                finally{
                    if(blad){
                        blad=false;
                        pcTurn();
                    }
                }
            }
        }
        invocable = null;
    }
    
    @FXML
    private void strategyBoxMouseClicked(MouseEvent evt) {    
        strategyBox.getSelectionModel().clearSelection();
        strategyBox.getItems().clear();
        //strategyBox.removeAllItems();
        File dir = new File("src/Strategies");
        File[] directoryListing = dir.listFiles();
        String filename;
        if (directoryListing != null) {
            for (File strategy : directoryListing) {
                filename = strategy.getName().toString();
                if(filename.substring(filename.length()-3).equals(".js"))
                    strategyBox.getItems().add(filename.substring(0,filename.length()-3));
            }
        }
    }                                        

    private void playerPlay(String number){
        playerPlayedCardText.setText(number);
        int i = playerLeftCards.indexOf((Object)number);
        playerLeftCards.remove(i);
        String[] listData = playerListToTab();
        playerCardList.setItems(FXCollections.observableArrayList(playerLeftCards));
        //playerCardList.setListData(listData);
        if(computerPlayedCardText.getText()!=null && !computerPlayedCardText.getText().equals("")){
            //zatem gracz odpowiedzial na ture komputera
            pcTurn = true;
            calculatePoints();
            refreshPoints();
            nextTurnButton.setDisable(false);
            pickCardButton.setDisable(true);
        }
        else{
            //gracz zaczal ture
            pcTurn = false;
            turnText.setText("Tura komputera");
            pcTurn();
        }
        if(playerLeftCards.isEmpty())
            endGame();
    }
    
    private int initCardList(int from, int to){
        playerLeftCards.clear();
        computerLeftCards.clear();
        if(to<from)
            return 100;
        if(to>=10000 || from >=10000 || to<=-1000 || from<=-1000)
            return 101;
        Random r = new Random();
        int j;
        for(int i=0;i<10;i++){
            j = r.nextInt(to-from+1) + from;
            playerLeftCards.add(Integer.toString(j));
            computerLeftCards.add(Integer.toString(j));
        }
        playerCardList.setItems(FXCollections.observableArrayList(playerLeftCards));
        if(seeCardsCheckBox.isSelected())
            computerCardList.setItems(FXCollections.observableArrayList(computerLeftCards));
        return 1;
    }
    
    private String[] playerListToTab(){
        String[] listData = new String[10];
        for(int i=0;i<playerLeftCards.size();i++)
            listData[i]=(String)playerLeftCards.get(i);
        return listData;
    }
    
    private String[] computerListToTab(){
        String[] listData = new String[10];
        for(int i=0;i<computerLeftCards.size();i++)
            listData[i]=(String)computerLeftCards.get(i);
        return listData;
    }
    
    private String tabToString(String[] tab){
        int k;
        String s = "";

        k = tab.length;
        s = tab[0];
        for (int i= 1 ; i < k; i++) {
            if(tab[i]!=null && !tab[i].equals(""))
                s += "|" + tab[i];
        }
        return s;
    }
    
    private void calculatePoints(){
        if(Integer.parseInt(computerPlayedCardText.getText())>Integer.parseInt(playerPlayedCardText.getText()))
            computerPoints+=2;
        else if(Integer.parseInt(computerPlayedCardText.getText())==Integer.parseInt(playerPlayedCardText.getText())){
            computerPoints++;
            playerPoints++;
        }
        else
            playerPoints+=2;
    }
    
    private void endGame(){
        nextTurnButton.setDisable(true);
        pickCardButton.setDisable(true);
        if(Integer.parseInt(plPoints.getText())>Integer.parseInt(coPoints.getText()))
            turnText.setText("Gratulacje, wygrales z komputerem");
        else if(Integer.parseInt(plPoints.getText())==Integer.parseInt(coPoints.getText()))
            turnText.setText("Zremisowales z komputerem");
        else
            turnText.setText("Przegrales z komputerem");
    }
    
    private void refreshPoints(){
        plPoints.setText(Integer.toString(playerPoints));
        coPoints.setText(Integer.toString(computerPoints));
    }
    
    private boolean showError(String message){
        JOptionPane.showMessageDialog(null, message, "",JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    private void showMsg(String message){
        JOptionPane.showMessageDialog(null, message);
    }
    
    @FXML
    private void opisProgramuActionPerformed(ActionEvent avt) {    
        showMsg("Program do gry w gre karciana z komputerem.\nDomyslnie do wyboru sa trzy strategie:\n-LosowyAlgorytm. Przeciwnik rzuca losowe karty\n-Smarter. Przeciwnik dziala wedlug pewnej strategii.\n-MinMax. Gdy obu graczom zostanie 7 kart, przeciwnik gra wedlug algorytmu MinMax.\n\nW przypadku gry gracz lub komputer bedzie mial wieksza wystawiona karte od\nprzeciwnika, dostaje on 2 punkty. W przypadku, gdy wystawiona zostanie\n ta sama karta obaj gracze dostaja 1 punkt.");
    }
    
    @FXML
    private void autorActionPerformed(ActionEvent avt) {    
        showMsg("Autor programu: Adam Filipowicz\nData utworzenia: 01.06.2018");
    }
    
    @FXML
    private void coralActionPerformed(ActionEvent avt) {    
        anchor.setStyle("-fx-background-color: coral;");
    }
    
    @FXML
    private void aliceBlueActionPerformed(ActionEvent avt) {    
        anchor.setStyle("-fx-background-color: aliceblue;");
    }
    
    @FXML
    private void aquaActionPerformed(ActionEvent avt) {    
        anchor.setStyle("-fx-background-color: aqua;");
    }
    
    @FXML
    private void mediumSpringGreenActionPerformed(ActionEvent avt) {    
        anchor.setStyle("-fx-background-color: mediumspringgreen;");
    }
    
    @FXML
    private void tomatoActionPerformed(ActionEvent avt) {    
        anchor.setStyle("-fx-background-color: tomato;");
    }
    
}
