import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.UIManager;

public class AppLauncher {
    public static void main(String[] args){

        try {
            UIManager.setLookAndFeel( new FlatMacLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PageStart().setVisible(true);

            }
        });
    }
}
