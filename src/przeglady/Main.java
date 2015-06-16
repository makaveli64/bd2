package przeglady;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Created by user
 */
public class Main extends JFrame {
    JButton modelButton;
    JButton typPojazduButton;
    JButton pojazdButton;

    public Main() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Main screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        modelButton = new JButton();
        typPojazduButton = new JButton();
        pojazdButton = new JButton();

        modelButton.setText("Model");
        typPojazduButton.setText("Typ pojazdu");
        pojazdButton.setText("Pojazd");

        modelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Model.start();
            }
        });
        typPojazduButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TypPojazdu.start();
            }
        });
        pojazdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                pojazd(actionEvent);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(modelButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(typPojazduButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(pojazdButton)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(modelButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(typPojazduButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(pojazdButton))
                                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }
/*
    private void pojazd(ActionEvent evt) {
    }
*/
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
