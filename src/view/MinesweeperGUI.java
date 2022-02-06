package view;

import controller.MinesweeperController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;

public class MinesweeperGUI extends JFrame{
    private MinesweeperController controller;


    public MinesweeperGUI(MinesweeperController controller) {
        this.controller = controller;

        JFrame.setDefaultLookAndFeelDecorated(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createButtons();

        createMenu();



    }


    public void createButtons() {
        setResizable(true);
        getContentPane().removeAll();
        getContentPane().repaint();
        getContentPane().setLayout(new GridLayout(controller.getHeight() - 2, controller.getWidth() - 2));


        Border raisedbevel, empty;

        raisedbevel = BorderFactory.createRaisedBevelBorder();
        empty = BorderFactory.createEmptyBorder();
        Font font = new Font("Sans Serif", 1, 16);

        for (int i = 1; i < controller.getHeight() - 1; i++) {
            for (int j = 1; j < controller.getWidth() - 1; j++) {
                JButton button = new JButton();
                button.setBorder(raisedbevel);
                button.setName(String.valueOf(i) + ";" + String.valueOf(j));
                button.setMinimumSize(new Dimension(24, 24));
                button.setPreferredSize(new Dimension(24, 24));

                button.setFont(font);
                button.setFocusPainted(false);
                controller.addButtonToMap(button);
                getContentPane().add(button);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == 3) {

                            controller.rightMouseButtonClicked(button);

                        } else if (e.getButton() == 1) {

                            controller.leftMouseButtonClicked(button);

                        }
                        controller.plotVisibleBoard();
                    }
                });
            }
        }

        setVisible(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void createMenu() {

        MenuBar menuBar = new MenuBar();
        setMenuBar(menuBar);

        Menu game = new Menu("Game");
        menuBar.add(game);
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        game.add(exit);


        Menu size = new Menu("Size");
        menuBar.add(size);
        MenuItem beginner = new MenuItem("Beginner");
        beginner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changeBoardToBeginner();
            }
        });
        size.add(beginner);
        MenuItem intermediate = new MenuItem("Intermediate");
        intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changeBoardToIntermediate();
            }
        });
        size.add(intermediate);
        MenuItem expert = new MenuItem("Expert");
        expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changeBoardToExpert();
            }
        });
        size.add(expert);
        size.add(new MenuItem("-"));
        MenuItem custom = new MenuItem("Custom");
        size.add(custom);

        Menu help = new Menu("Help");
        menuBar.add(help);
        MenuItem about = new MenuItem("About");
        help.add(about);


    }

}



