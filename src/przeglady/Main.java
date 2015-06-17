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
    JButton przegladButton;

    public Main() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Main screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        modelButton = new JButton();
        typPojazduButton = new JButton();
        pojazdButton = new JButton();
        przegladButton = new JButton();

        modelButton.setText("Model");
        typPojazduButton.setText("Typ pojazdu");
        pojazdButton.setText("Pojazd");
        przegladButton.setText("Planowanie przeglądu");

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
                Pojazd.start();
            }
        });
        przegladButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Pojazd.start();
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
                                                .addComponent(pojazdButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(przegladButton)))
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
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(przegladButton))
                                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
