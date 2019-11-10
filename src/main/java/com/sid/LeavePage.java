package com.sid;

import static com.sid.ActivityMain.leavesDb;
import static com.sid.ActivityMain.setActivity;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import org.bson.Document;

public class LeavePage {
    public JPanel page = new JPanel();
    SpringLayout layout = new SpringLayout();
    List<Document> leaves;
    public static List<Document> pendingLeaveRequest;
    Document facDoc;
    JButton addButton, approveButton, backButton;

    JButton dateAppliedHead = new JButton("Date Of Application");
    JButton fIdHead = new JButton("f_id");
    JButton fromDateHead = new JButton("From (yyyy-MM-dd)");
    JButton toDateHead = new JButton("To (yyyy-MM-dd)");
    JButton reasonHead = new JButton("Reason");
    JButton statusHead = new JButton("Status");
    JButton lastLeave;

    public LeavePage(Document doc) {
        page.setLayout(layout);
        facDoc = doc;

        try {
            leaves = leavesDb.getAllLeaves(Integer.parseInt(facDoc.getString("f_id")));
        } catch (NumberFormatException | SQLException e1) {
            e1.printStackTrace();
        }

        addButton = new JButton("Apply for leave");
        layout.putConstraint(SpringLayout.WEST, addButton, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, addButton, 5, SpringLayout.NORTH, page);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createJOptionPane();
            }
        });
        page.add(addButton);

        JButton leftButton = addButton;
        if (facDoc.getString("position").equals("HOD")) {
            try {
                pendingLeaveRequest = leavesDb.leavesVisibleToHOD(facDoc.getString("d_id"));
            } catch (NumberFormatException | SQLException e1) {
                e1.printStackTrace();
            }
            approveButton = new JButton("Pending Leave Requests (" + pendingLeaveRequest.size() + ")");
            layout.putConstraint(SpringLayout.WEST, approveButton, 5, SpringLayout.EAST, addButton);
            layout.putConstraint(SpringLayout.NORTH, approveButton, 5, SpringLayout.NORTH, page);
            approveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ApproveLeavePage approveLeavePage = new ApproveLeavePage(facDoc);
                    setActivity(approveLeavePage.page);
                }
            });
            page.add(approveButton);
            leftButton = approveButton;
        }
        if (facDoc.getString("position").equals("Dean")) {
            try {
                pendingLeaveRequest = leavesDb.leavesVisibleToDean();
            } catch (NumberFormatException | SQLException e1) {
                e1.printStackTrace();
            }
            approveButton = new JButton("Pending Leave Requests (" + pendingLeaveRequest.size() + ")");
            layout.putConstraint(SpringLayout.WEST, approveButton, 5, SpringLayout.EAST, addButton);
            layout.putConstraint(SpringLayout.NORTH, approveButton, 5, SpringLayout.NORTH, page);
            approveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ApproveLeavePage approveLeavePage = new ApproveLeavePage(facDoc);
                    setActivity(approveLeavePage.page);
                }
            });
            page.add(approveButton);
            leftButton = approveButton;
        }
        backButton = new JButton("Back");
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setForeground(Color.RED);
        layout.putConstraint(SpringLayout.WEST, backButton, 5, SpringLayout.EAST, leftButton);
        layout.putConstraint(SpringLayout.NORTH, backButton, 5, SpringLayout.NORTH, page);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setActivity(null);
            }
        });
        page.add(backButton);

        setUpLeaves(0);
    }

    void setUpLeaves(int index) {
        if (lastLeave == null) {
            dateAppliedHead.setOpaque(false);
            dateAppliedHead.setContentAreaFilled(false);
            dateAppliedHead.setBorderPainted(false);
            dateAppliedHead.setFont(
                    new Font(dateAppliedHead.getFont().getName(), Font.BOLD, dateAppliedHead.getFont().getSize() + 5));
            layout.putConstraint(SpringLayout.WEST, dateAppliedHead, 5, SpringLayout.WEST, page);
            layout.putConstraint(SpringLayout.NORTH, dateAppliedHead, 5, SpringLayout.SOUTH, addButton);
            page.add(dateAppliedHead);

            fIdHead.setOpaque(false);
            fIdHead.setContentAreaFilled(false);
            fIdHead.setBorderPainted(false);
            fIdHead.setFont(new Font(fIdHead.getFont().getName(), Font.BOLD, fIdHead.getFont().getSize() + 5));
            layout.putConstraint(SpringLayout.WEST, fIdHead, 5, SpringLayout.EAST, dateAppliedHead);
            layout.putConstraint(SpringLayout.NORTH, fIdHead, 5, SpringLayout.SOUTH, addButton);
            page.add(fIdHead);

            fromDateHead.setOpaque(false);
            fromDateHead.setContentAreaFilled(false);
            fromDateHead.setBorderPainted(false);
            fromDateHead.setFont(
                    new Font(fromDateHead.getFont().getName(), Font.BOLD, fromDateHead.getFont().getSize() + 5));
            layout.putConstraint(SpringLayout.WEST, fromDateHead, 5, SpringLayout.EAST, fIdHead);
            layout.putConstraint(SpringLayout.NORTH, fromDateHead, 5, SpringLayout.SOUTH, addButton);
            page.add(fromDateHead);

            toDateHead.setOpaque(false);
            toDateHead.setContentAreaFilled(false);
            toDateHead.setBorderPainted(false);
            toDateHead.setFont(new Font(toDateHead.getFont().getName(), Font.BOLD, toDateHead.getFont().getSize() + 5));
            layout.putConstraint(SpringLayout.WEST, toDateHead, 5, SpringLayout.EAST, fromDateHead);
            layout.putConstraint(SpringLayout.NORTH, toDateHead, 5, SpringLayout.SOUTH, addButton);
            page.add(toDateHead);

            reasonHead.setOpaque(false);
            reasonHead.setContentAreaFilled(false);
            reasonHead.setBorderPainted(false);
            reasonHead.setFont(new Font(reasonHead.getFont().getName(), Font.BOLD, reasonHead.getFont().getSize() + 5));
            layout.putConstraint(SpringLayout.WEST, reasonHead, 5, SpringLayout.EAST, toDateHead);
            layout.putConstraint(SpringLayout.NORTH, reasonHead, 5, SpringLayout.SOUTH, addButton);
            page.add(reasonHead);

            statusHead.setOpaque(false);
            statusHead.setContentAreaFilled(false);
            statusHead.setBorderPainted(false);
            statusHead.setFont(new Font(statusHead.getFont().getName(), Font.BOLD, statusHead.getFont().getSize() + 5));
            layout.putConstraint(SpringLayout.WEST, statusHead, 5, SpringLayout.EAST, reasonHead);
            layout.putConstraint(SpringLayout.NORTH, statusHead, 5, SpringLayout.SOUTH, addButton);
            page.add(statusHead);

            lastLeave = dateAppliedHead;
        }
        int i = index;
        for (; i < leaves.size(); i++) {
            JButton dateApplied = new JButton(leaves.get(i).getString("application_date"));
            dateApplied.setOpaque(false);
            dateApplied.setContentAreaFilled(false);
            dateApplied.setBorderPainted(false);
            dateApplied.setForeground(Color.BLUE);
            dateApplied.setActionCommand(leaves.get(i).getString("d_id"));
            dateApplied.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(page, e.getActionCommand());
                }
            });
            layout.putConstraint(SpringLayout.WEST, dateApplied, 5, SpringLayout.WEST, page);
            layout.putConstraint(SpringLayout.NORTH, dateApplied, 5, SpringLayout.SOUTH, lastLeave);
            page.add(dateApplied);

            JButton fId = new JButton(leaves.get(i).getString("f_id"));
            fId.setOpaque(false);
            fId.setContentAreaFilled(false);
            fId.setBorderPainted(false);
            fId.setForeground(Color.BLUE);
            fId.setActionCommand(leaves.get(i).getString("d_id"));
            fId.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(page, e.getActionCommand());
                }
            });
            layout.putConstraint(SpringLayout.WEST, fId, 5, SpringLayout.EAST, dateAppliedHead);
            layout.putConstraint(SpringLayout.NORTH, fId, 5, SpringLayout.SOUTH, lastLeave);
            page.add(fId);

            JButton fromDate = new JButton(leaves.get(i).getString("from_date"));
            fromDate.setOpaque(false);
            fromDate.setContentAreaFilled(false);
            fromDate.setBorderPainted(false);
            fromDate.setForeground(Color.BLUE);
            fromDate.setActionCommand(leaves.get(i).getString("d_id"));
            fromDate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(page, e.getActionCommand());
                }
            });
            layout.putConstraint(SpringLayout.WEST, fromDate, 5, SpringLayout.EAST, fIdHead);
            layout.putConstraint(SpringLayout.NORTH, fromDate, 5, SpringLayout.SOUTH, lastLeave);
            page.add(fromDate);

            JButton toDate = new JButton(leaves.get(i).getString("to_date"));
            toDate.setOpaque(false);
            toDate.setContentAreaFilled(false);
            toDate.setBorderPainted(false);
            toDate.setForeground(Color.BLUE);
            toDate.setActionCommand(leaves.get(i).getString("d_id"));
            toDate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(page, e.getActionCommand());
                }
            });
            layout.putConstraint(SpringLayout.WEST, toDate, 5, SpringLayout.EAST, fromDateHead);
            layout.putConstraint(SpringLayout.NORTH, toDate, 5, SpringLayout.SOUTH, lastLeave);
            page.add(toDate);

            JButton reason = new JButton(leaves.get(i).getString("commentsfac"));
            reason.setOpaque(false);
            reason.setContentAreaFilled(false);
            reason.setBorderPainted(false);
            reason.setForeground(Color.BLUE);
            reason.setActionCommand(leaves.get(i).getString("d_id"));
            reason.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(page, e.getActionCommand());
                }
            });
            layout.putConstraint(SpringLayout.WEST, reason, 5, SpringLayout.EAST, toDateHead);
            layout.putConstraint(SpringLayout.NORTH, reason, 5, SpringLayout.SOUTH, lastLeave);
            page.add(reason);

            JButton status = new JButton();
            if (leaves.get(i).getString("status").equals("rejectedLeaves")) {
                status.setActionCommand("authcomments" + " " + i);
                status.setText("Rejected By " + leaves.get(i).getString("auth"));
            } else if (leaves.get(i).getString("status").equals("approvedLeaves")) {
                status.setActionCommand("commentsdean" + " " + i);
                status.setText("Approved");
            } else if (leaves.get(i).getString("status").equals("approved1Leaves")) {
                status.setActionCommand("commentshod" + " " + i);
                status.setText("Wating for Dean's Approval");
            } else if (leaves.get(i).getString("status").equals("newLeaves")) {
                status.setActionCommand("commentsfac" + " " + i);
                status.setText("Wating for HOD Approval");
            }
            status.setOpaque(false);
            status.setContentAreaFilled(false);
            status.setBorderPainted(false);
            status.setForeground(Color.BLUE);
            status.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String[] info = e.getActionCommand().split(" ");
                    JOptionPane.showMessageDialog(page, leaves.get(Integer.parseInt(info[1])).getString(info[0]),
                            "Comments", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            layout.putConstraint(SpringLayout.WEST, status, 5, SpringLayout.EAST, reasonHead);
            layout.putConstraint(SpringLayout.NORTH, status, 5, SpringLayout.SOUTH, lastLeave);
            page.add(status);

            lastLeave = dateApplied;
        }
        page.revalidate();
    }

    void createJOptionPane() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField newFromDate = new JFormattedTextField(df);
        JFormattedTextField newToDate = new JFormattedTextField(df);
        JTextArea newReason = new JTextArea(15, 50);
        JScrollPane scrollPane = new JScrollPane(newReason);

        JPanel newFac = new JPanel();
        newFac.setLayout(new BoxLayout(newFac, BoxLayout.Y_AXIS));
        newFac.add(new JLabel("From: *", JLabel.LEFT));
        newFac.add(newFromDate);
        newFac.add(new JLabel("To: *", JLabel.LEFT));
        newFac.add(newToDate);
        newFac.add(new JLabel("Reason: ", JLabel.LEFT));
        newFac.add(scrollPane);

        int result = JOptionPane.showConfirmDialog(page, newFac, "Please enter the followings",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (newFromDate.getText().isEmpty() || newToDate.getText().isEmpty())
                JOptionPane.showMessageDialog(page, "* fields must be Filled. Try Again");
            else {
                try {
                    leavesDb.createNewLeaveEntry(Integer.parseInt(facDoc.getString("f_id")), facDoc.getString("d_id"),
                            Date.valueOf(newFromDate.getText()), Date.valueOf(newFromDate.getText()),
                            newReason.getText());
                    leaves = leavesDb.getAllLeaves(Integer.parseInt(facDoc.getString("f_id")));
                    setUpLeaves(leaves.size() - 1);
                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}