package pesonalFinanceApp;

import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class GUITest {

    @Test
    public void getComboBoxModel() {
        ArrayList<Client> testList = Client.clientProfileList;
        Client[] boxmodel = testList.toArray(new Client[0]);
        //assertTrue(getComboBoxModel(new DefaultComboBoxModel<>(boxmodel)));
        //assertNotNull(new DefaultComboBoxModel<>(boxmodel));
        assertSame(new DefaultComboBoxModel<>(boxmodel), GUI.getComboBoxModel(testList));
    }

    @Test
    public void loadAllProfiles() throws IOException, ClassNotFoundException{
        //ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("profiles.bin"));
        //Client.clientProfileList.addAll((List<Client>) objectInputStream.readObject());
        assertNotNull(Client.clientProfileList);
    }

    /*
    @Test
    public void loadProfile() {
        GUI.profilesComboBox = new JComboBox<>();
        GUI.profilesComboBox.setModel(getComboBoxModel(Client.clientProfileList));
        Object selected = GUI.profilesComboBox.getSelectedItem();
        String clientNametest = selected.toString();
        for (Client c : Client.clientProfileList) {
            assertEquals(c.clientName, clientNametest);
        }
    }
    */
}