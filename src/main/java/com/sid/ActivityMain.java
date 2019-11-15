package com.sid;

import java.awt.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.bson.Document;;

public class ActivityMain {
    public static JFrame mainFrame;
    static ConnectToDB db;
    static ConnectToPostgres leavesDb;
    static Stack<JScrollPane> stActivities;
    static List<Document> depts;
    public static HashMap<String, Document> routes;
    static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        mainFrame = new JFrame("Faculty Portal");
        mainFrame.setSize(600, 800);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1, 1));

        stActivities = new Stack<>();

        db = new ConnectToDB();
        depts = db.findAllDepartments();
        leavesDb = new ConnectToPostgres();
        try {
            routes = leavesDb.getAllRoutes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LoginPage loginPage = new LoginPage();
        setActivity(loginPage.page);

        mainFrame.setVisible(true);
    }

    public static void setActivity(JPanel nActivity) {
        if (!stActivities.empty())
            mainFrame.remove(stActivities.peek());
        if (nActivity != null) {
            JScrollPane scrollPane = new JScrollPane(nActivity, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getVerticalScrollBar().setUnitIncrement(50);
            mainFrame.add(scrollPane);
            stActivities.push(scrollPane);
        } else {
            stActivities.pop();
            mainFrame.add(stActivities.peek());
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}