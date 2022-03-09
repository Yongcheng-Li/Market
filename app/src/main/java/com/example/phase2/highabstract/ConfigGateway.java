package com.example.phase2.highabstract;
import android.os.Bundle;

import com.example.phase2.items.Item;
import com.example.phase2.items.ItemManager;
import com.example.phase2.meetings.Meeting;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.trades.Trade;
import com.example.phase2.trades.TradeManager;
import com.example.phase2.users.Admin;
import com.example.phase2.users.AdminActions;
import com.example.phase2.users.Trader;
import com.example.phase2.users.TraderManager;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ConfigGateway {

    private final String ADMINPATH = "admins.ser";
    private String ITEMPATH = "items.ser";
    private String MEETINGPATH = "meetings.ser";
    private String TRADEPATH = "trade.ser";
    private String TRADERPATH = "traders.ser";

    private AdminActions adminActions;
    private ItemManager itemManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;

    private File contextFilesDir;

    /**
     * Constructor for the ConfigGateway class
     * @param contextFilesDir the File containing the context files directory for the application
     */
    public ConfigGateway(File contextFilesDir){
        this.contextFilesDir = contextFilesDir;
    }

    /**
     * Reads objects from the given path
     * @param path the string path of the file
     * @return a Serializable object containing information for the program
     * @throws IOException from the InputStream
     * @throws ClassNotFoundException from ObjectInputStream
     */
    public Serializable readInfo(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            InputStream fileInput = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(fileInput);
            ObjectInput input = new ObjectInputStream(buffer);

            Serializable serializable = (Serializable) input.readObject();
            input.close();
            return serializable;
        } else {
            if (file.createNewFile()) {
                return readInfo(path);
            }
            else{
                return null;
            }
        }
    }

    private void loadClasses() throws IOException {
        try {
            adminActions = (AdminActions) readInfo(contextFilesDir + ADMINPATH);
            meetingManager = (MeetingManager) readInfo(contextFilesDir + MEETINGPATH);
            itemManager = (ItemManager) readInfo(contextFilesDir + ITEMPATH);
            tradeManager = (TradeManager) readInfo(contextFilesDir + TRADEPATH);
            traderManager = (TraderManager) readInfo(contextFilesDir + TRADERPATH);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            downloadInitial();
        }
    }

    /**
     * Packages the manager classes into a Bundle object
     * @return a Bundle object containing the Manager use case classes in Serializable form
     */
    public Bundle getBundle() throws IOException {
        loadClasses();
        Bundle bundle = new Bundle();
        bundle.putSerializable(traderManager.getIdentifier(), traderManager);
        bundle.putSerializable(itemManager.getIdentifier(), itemManager);
        bundle.putSerializable(tradeManager.getIdentifier(), tradeManager);
        bundle.putSerializable(meetingManager.getIdentifier(), meetingManager);
        bundle.putSerializable(adminActions.getIdentifier(), adminActions);

        return bundle;
    }

    /**
     * Saves the information to the file
     * @param path the string path to the file
     * @param manager the Serializable object containing information about the file
     * @throws IOException from the OutputStream
     */
    public void saveInfo(String path, Serializable manager) throws IOException{
        OutputStream file = new FileOutputStream(path);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(manager);
        output.close();
    }

    /**
     * Writes the contents of the bundle to the phone's storage
     * @param bundle The Bundle of use case manager classes
     */
    public void saveBundle(Bundle bundle) throws IOException {
        saveInfo(contextFilesDir + ADMINPATH, bundle.getSerializable("AdminActions"));
        saveInfo(contextFilesDir + MEETINGPATH, bundle.getSerializable("MeetingManager"));
        saveInfo(contextFilesDir + ITEMPATH, bundle.getSerializable("ItemManager"));
        saveInfo(contextFilesDir + TRADEPATH, bundle.getSerializable("TradeManager"));
        saveInfo(contextFilesDir + TRADERPATH, bundle.getSerializable("TraderManager"));
    }

    private void downloadUsers(){
        HashMap<String, Admin> admins = new HashMap<>();
        admins.put("Admin", new Admin("Admin", "Wordpass", "2022-03-09", true));
        adminActions = new AdminActions(admins);

        traderManager = new TraderManager(new HashMap<String, Trader>(), 100, 1, 0);
        Trader trader1 = new Trader("Trader1", "Password");
        trader1.setHomeCity("Fredericton");
        traderManager.addTrader(trader1);

        traderManager.addTrader(new Trader("Trader2", "Password2"));
        Trader traderFlagged = new Trader("Arjun", "Password3");
        traderFlagged.setHomeCity("Saint John");
        traderFlagged.setFlagged(true);

        traderManager.addTrader(traderFlagged);
        Trader traderUnfreeze = new Trader("Jeffrey", "Password4");
        traderUnfreeze.setFrozen(true);
        traderUnfreeze.setRequestToUnfreeze(true);
        traderManager.addTrader(traderUnfreeze);


        traderManager.addTrader(new Trader("Li", "123456"));
        traderManager.addTrader(new Trader("Yong", "123456"));
        traderManager.addTrader(new Trader("Cheng", "123456"));
    }

    private void downloadItems(){
        HashMap<Integer, Item> tempMap = new HashMap<>();
        itemManager = new ItemManager(tempMap);

        Integer id1 = itemManager.addItem("Bruh", "Arjun");
        itemManager.editCategory(id1, "What do you think?");
        itemManager.editDescription(id1, "bruhbruhbruh");
        itemManager.editQualityRating(id1,5);
        itemManager.changeStatusToAvailable(id1);

        Integer id2 = itemManager.addItem("Apple", "Trader1");
        itemManager.editCategory(id2, "Food");
        itemManager.editDescription(id2, "It's an apple.");
        itemManager.editQualityRating(id2, 8);
        itemManager.changeStatusToAvailable(id2);
    }


    private void downloadInitial() throws IOException {
        downloadUsers();
        downloadItems();

        Bundle bundle = new Bundle();
        bundle.putSerializable("AdminActions", adminActions);
        bundle.putSerializable("TraderManager", traderManager);
        bundle.putSerializable("MeetingManager", meetingManager);
        bundle.putSerializable("TradeManager", tradeManager);
        bundle.putSerializable("ItemManager", itemManager);
        saveBundle(bundle);
    }

}
