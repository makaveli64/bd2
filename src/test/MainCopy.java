package test;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user
 */
public class MainCopy extends JPanel {
    public MainCopy() {
        setLayout(new BorderLayout());

        JTextField modelIdField = new JTextField(10);
//        modelNameField.setActionCommand("login");
        JLabel modelIdFieldLabel = new JLabel("Identyfikator modelu: ");
        modelIdFieldLabel.setLabelFor(modelIdField);

        JTextField modelNameField = new JTextField(10);
        JLabel modelNameFieldLabel = new JLabel("Nazwa modelu: ");
        modelNameFieldLabel.setLabelFor(modelNameField);

//        JPanel pane = new JPanel();
//        setPreferredSize(new Dimension(640, 480));
        // --> TODO --> fit components
        add(modelIdFieldLabel, BorderLayout.LINE_START);
        add(modelIdField, BorderLayout.LINE_END);

        add(modelNameFieldLabel, BorderLayout.LINE_START);
        add(modelNameField, BorderLayout.LINE_END);
/*
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        pane.setLayout(gridBag);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
*/
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Przeglądy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainCopy());

/*
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(640, 480));
        label.setOpaque(true);
        frame.getContentPane().add(label);
*/

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}