package com.sid;

import static com.sid.ActivityMain.db;
import static com.sid.ActivityMain.depts;
import static com.sid.ActivityMain.setActivity;
import static com.sid.LoginPage.loginButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.bson.Document;
import org.jdesktop.swingx.JXDatePicker;

public class FacultyPage {
    public JPanel page = new JPanel() {
        private static final long serialVersionUID = 1L;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(5000, 5000);
        }
    };
    SpringLayout layout;
    public static Document facDoc;
    public static JButton leaveButton, searchButton, logoutButton, refreshButton;
    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    JTextArea lastArea;

    public FacultyPage(Document doc) {
        layout = new SpringLayout();
        page.setLayout(layout);
        facDoc = doc;

        leaveButton = new JButton("Leave");
        layout.putConstraint(SpringLayout.WEST, leaveButton, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, leaveButton, 5, SpringLayout.NORTH, page);
        page.add(leaveButton);

        searchButton = new JButton();
        try {
            searchButton.setIcon(new ImageIcon(ImageIO.read(new File("src/R/drawable/search.png")).getScaledInstance(15,
                    15, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        searchButton.setText("Search");
        layout.putConstraint(SpringLayout.WEST, searchButton, 5, SpringLayout.EAST, leaveButton);
        layout.putConstraint(SpringLayout.NORTH, searchButton, 5, SpringLayout.NORTH, page);
        page.add(searchButton);

        logoutButton = new JButton("Logout");
        layout.putConstraint(SpringLayout.WEST, logoutButton, 5, SpringLayout.EAST, searchButton);
        layout.putConstraint(SpringLayout.NORTH, logoutButton, 5, SpringLayout.NORTH, page);
        page.add(logoutButton);
        
        refreshButton = new JButton("Refresh");
        layout.putConstraint(SpringLayout.WEST, refreshButton, 5, SpringLayout.EAST, logoutButton);
        layout.putConstraint(SpringLayout.NORTH, refreshButton, 5, SpringLayout.NORTH, page);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginButton.setVisible(false);
                logoutButton.setVisible(false);
                logoutButton.doClick();
                loginButton.doClick();
                loginButton.setVisible(true);
                logoutButton.setVisible(true);
            }
        });
        // page.add(refreshButton);

        setUpFacultyProfile();

        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LeavePage leavePage = new LeavePage(facDoc);
                setActivity(leavePage.page);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                QueryPage queryPage = new QueryPage();
                setActivity(queryPage.page);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                db.facultyDoc = null;
                setActivity(null);
            }
        });

    }

    void setUpFacultyProfile() {
        JLabel name = new JLabel(facDoc.getString("name"));
        name.setFont(new Font(name.getFont().getName(), Font.BOLD, name.getFont().getSize() + 10));
        layout.putConstraint(SpringLayout.WEST, name, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, name, 5, SpringLayout.SOUTH, leaveButton);
        page.add(name);

        JButton editButton = null;
        try {
            editButton = new JButton(new ImageIcon(ImageIO.read(new File("src/R/drawable/edit.png"))
                    .getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        editButton.setOpaque(false);
        editButton.setContentAreaFilled(false);
        editButton.setBorderPainted(false);
        editButton.setForeground(Color.BLUE);
        layout.putConstraint(SpringLayout.WEST, editButton, 5, SpringLayout.EAST, name);
        layout.putConstraint(SpringLayout.NORTH, editButton, 5, SpringLayout.SOUTH, leaveButton);
        page.add(editButton);

        JButton addFieldButton = null;
        try {
            addFieldButton = new JButton(new ImageIcon(ImageIO.read(new File("src/R/drawable/add.png"))
                    .getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        addFieldButton.setOpaque(false);
        addFieldButton.setContentAreaFilled(false);
        addFieldButton.setBorderPainted(false);
        addFieldButton.setForeground(Color.BLUE);
        layout.putConstraint(SpringLayout.WEST, addFieldButton, 5, SpringLayout.EAST, editButton);
        layout.putConstraint(SpringLayout.NORTH, addFieldButton, 5, SpringLayout.SOUTH, leaveButton);
        page.add(addFieldButton);

        JTextArea department = new JTextArea(
                facDoc.getString("position") + ", " + facDoc.getString("d_id") + " department");
        department.setEditable(false);
        department.setCursor(null);
        department.setOpaque(false);
        department.setFocusable(false);
        department.setFont(new Font(department.getFont().getName(), Font.BOLD, department.getFont().getSize()));
        layout.putConstraint(SpringLayout.WEST, department, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, department, 5, SpringLayout.SOUTH, editButton);
        page.add(department);

        Document extras = (Document) facDoc.get("extra");
        JLabel headingAboutMe = new JLabel("About Me");
        headingAboutMe.setFont(
                new Font(headingAboutMe.getFont().getName(), Font.BOLD, headingAboutMe.getFont().getSize() + 5));
        layout.putConstraint(SpringLayout.WEST, headingAboutMe, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, headingAboutMe, 5, SpringLayout.SOUTH, department);
        page.add(headingAboutMe);

        JTextArea detailsAboutMe = new JTextArea(extras.getString("About Me"));
        detailsAboutMe.setEditable(false);
        detailsAboutMe.setCursor(null);
        detailsAboutMe.setOpaque(false);
        detailsAboutMe.setFocusable(false);
        detailsAboutMe.setLineWrap(true);
        detailsAboutMe.setWrapStyleWord(true);
        detailsAboutMe.setColumns(150);
        layout.putConstraint(SpringLayout.WEST, detailsAboutMe, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, detailsAboutMe, 5, SpringLayout.SOUTH, headingAboutMe);
        page.add(detailsAboutMe);

        JLabel headingSkillSet = new JLabel("Skill Set");
        headingSkillSet.setFont(
                new Font(headingSkillSet.getFont().getName(), Font.BOLD, headingSkillSet.getFont().getSize() + 5));
        layout.putConstraint(SpringLayout.WEST, headingSkillSet, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, headingSkillSet, 5, SpringLayout.SOUTH, detailsAboutMe);
        page.add(headingSkillSet);

        JTextArea detailsSkillSet = new JTextArea(extras.getString("Skill Set"));
        detailsSkillSet.setEditable(false);
        detailsSkillSet.setCursor(null);
        detailsSkillSet.setOpaque(false);
        detailsSkillSet.setFocusable(false);
        detailsSkillSet.setLineWrap(true);
        detailsSkillSet.setWrapStyleWord(true);
        detailsSkillSet.setColumns(150);
        layout.putConstraint(SpringLayout.WEST, detailsSkillSet, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, detailsSkillSet, 5, SpringLayout.SOUTH, headingSkillSet);
        page.add(detailsSkillSet);

        JTextArea headingWork = new JTextArea("Work");
        headingWork.setEditable(false);
        headingWork.setCursor(null);
        headingWork.setOpaque(false);
        headingWork.setFocusable(false);
        headingWork.setLineWrap(true);
        headingWork.setWrapStyleWord(true);
        headingWork.setColumns(150);
        headingWork.setFont(new Font(headingWork.getFont().getName(), Font.BOLD, headingWork.getFont().getSize() + 5));
        layout.putConstraint(SpringLayout.WEST, headingWork, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, headingWork, 5, SpringLayout.SOUTH, detailsSkillSet);
        page.add(headingWork);

        lastArea = headingWork;
        Document workDoc = (Document) extras.get("Work");
        List<String> workKeys = new ArrayList<>(workDoc.keySet());
        for (String key : workKeys) {
            JLabel work1 = new JLabel(key, JLabel.CENTER);
            work1.setForeground(Color.LIGHT_GRAY);
            layout.putConstraint(SpringLayout.WEST, work1, 5, SpringLayout.WEST, page);
            layout.putConstraint(SpringLayout.NORTH, work1, 5, SpringLayout.SOUTH, lastArea);
            page.add(work1);

            JTextArea work2 = new JTextArea();
            if (key.equals("Date of joining"))
                work2.setText(df.format((Date) workDoc.get("Date of joining")));
            else if (key.equals("Department"))
                work2.setText(facDoc.getString("d_id"));
            else
                work2.setText(workDoc.getString(key));
            work2.setEditable(false);
            work2.setCursor(null);
            work2.setOpaque(false);
            work2.setFocusable(false);
            work2.setLineWrap(true);
            work2.setWrapStyleWord(true);
            work2.setColumns(150);
            layout.putConstraint(SpringLayout.WEST, work2, 5, SpringLayout.EAST, work1);
            layout.putConstraint(SpringLayout.NORTH, work2, 5, SpringLayout.SOUTH, lastArea);

            if (!facDoc.getString("d_id").equals("CCF"))
                if (key.equals("HOD ID")) {
                    for (Document doc : depts) {
                        if (doc.getString("d_id").equals(facDoc.getString("d_id"))) {
                            Document allhod = (Document) doc.get("hod");
                            work2.setText(allhod.getString(String.valueOf(allhod.size() - 1)));
                        }
                    }
                }

            page.add(work2);
            lastArea = work2;
        }

        JTextArea headingPersonal = new JTextArea("Personal");
        headingPersonal.setEditable(false);
        headingPersonal.setCursor(null);
        headingPersonal.setOpaque(false);
        headingPersonal.setFocusable(false);
        headingPersonal.setLineWrap(true);
        headingPersonal.setWrapStyleWord(true);
        headingPersonal.setColumns(150);
        headingPersonal.setFont(
                new Font(headingPersonal.getFont().getName(), Font.BOLD, headingPersonal.getFont().getSize() + 5));
        layout.putConstraint(SpringLayout.WEST, headingPersonal, 5, SpringLayout.WEST, page);
        layout.putConstraint(SpringLayout.NORTH, headingPersonal, 5, SpringLayout.SOUTH, lastArea);
        page.add(headingPersonal);
        lastArea = headingPersonal;

        Document personalDoc = (Document) extras.get("Personal");
        List<String> personalKeys = new ArrayList<>(personalDoc.keySet());
        for (String key : personalKeys) {
            JLabel personal1 = new JLabel(key, JLabel.CENTER);
            personal1.setForeground(Color.LIGHT_GRAY);
            layout.putConstraint(SpringLayout.WEST, personal1, 5, SpringLayout.WEST, page);
            layout.putConstraint(SpringLayout.NORTH, personal1, 5, SpringLayout.SOUTH, lastArea);
            page.add(personal1);

            JTextArea personal2 = new JTextArea(personalDoc.getString(key));
            personal2.setEditable(false);
            personal2.setCursor(null);
            personal2.setOpaque(false);
            personal2.setFocusable(false);
            personal2.setLineWrap(true);
            personal2.setWrapStyleWord(true);
            personal2.setColumns(150);
            layout.putConstraint(SpringLayout.WEST, personal2, 5, SpringLayout.EAST, personal1);
            layout.putConstraint(SpringLayout.NORTH, personal2, 5, SpringLayout.SOUTH, lastArea);
            page.add(personal2);
            lastArea = personal2;

            if(key.equals("Age"))
                personal2.setText(((Document) extras.get("Personal")).getString("Age"));
        }

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createJOptionPane();
            }
        });

        addFieldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createJOptionPane1("", "");
            }
        });
        setExtras(0);
    }

    void setExtras(int index) {
        Document extras = (Document) facDoc.get("extra");
        List<String> keys = new ArrayList<>(extras.keySet());
        keys.remove("About Me");
        keys.remove("Skill Set");
        keys.remove("Work");
        keys.remove("Personal");
        for (String key : keys) {
            JLabel heading = new JLabel(key);
            heading.setCursor(null);
            heading.setOpaque(false);
            heading.setFocusable(false);
            heading.setFont(new Font(heading.getFont().getName(), Font.BOLD, heading.getFont().getSize() + 5));
            layout.putConstraint(SpringLayout.WEST, heading, 5, SpringLayout.WEST, page);
            layout.putConstraint(SpringLayout.NORTH, heading, 5, SpringLayout.SOUTH, lastArea);
            page.add(heading);

            JButton editButton = null;
            try {
                editButton = new JButton(new ImageIcon(ImageIO.read(new File("src/R/drawable/edit.png"))
                        .getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            editButton.setOpaque(false);
            editButton.setContentAreaFilled(false);
            editButton.setBorderPainted(false);
            editButton.setForeground(Color.BLUE);
            editButton.setActionCommand(key + "~" + extras.getString(key));
            layout.putConstraint(SpringLayout.WEST, editButton, 5, SpringLayout.EAST, heading);
            layout.putConstraint(SpringLayout.NORTH, editButton, 5, SpringLayout.SOUTH, lastArea);
            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String s[] = e.getActionCommand().split("~", 2);
                    createJOptionPane1(s[0], s[1]);
                }
            });
            page.add(editButton);

            JButton deleteButton = null;
            try {
                deleteButton = new JButton(new ImageIcon(ImageIO.read(new File("src/R/drawable/delete.png"))
                        .getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            deleteButton.setOpaque(false);
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);
            deleteButton.setForeground(Color.BLUE);
            deleteButton.setActionCommand(key);
            layout.putConstraint(SpringLayout.WEST, deleteButton, 5, SpringLayout.EAST, editButton);
            layout.putConstraint(SpringLayout.NORTH, deleteButton, 5, SpringLayout.SOUTH, lastArea);
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(ActivityMain.mainFrame,
                            "Are you sure you want to delete this field?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        Document doc = ((Document) facDoc.get("extra"));
                        doc.remove(e.getActionCommand());
                        facDoc.put("extra", doc);
                        db.upsertFaculty(facDoc);
                        logoutButton.doClick();
                        loginButton.doClick();
                    }
                }
            });
            page.add(deleteButton);

            JTextArea details = new JTextArea(extras.getString(key));
            details.setEditable(false);
            details.setCursor(null);
            details.setOpaque(false);
            details.setFocusable(false);
            details.setLineWrap(true);
            details.setWrapStyleWord(true);
            details.setColumns(150);
            layout.putConstraint(SpringLayout.WEST, details, 5, SpringLayout.WEST, page);
            layout.putConstraint(SpringLayout.NORTH, details, 5, SpringLayout.SOUTH, heading);
            page.add(details);
            lastArea = details;
        }
    }

    void createJOptionPane1(String s1, String s2) {
        JTextField newHeading = new JTextField(30);
        JTextArea newDetails = new JTextArea(8, 30);
        JScrollPane aboutMe = new JScrollPane(newDetails);

        newHeading.setText(s1);
        newDetails.setText(s2);

        JPanel addDetailFac = new JPanel();
        addDetailFac.setLayout(new BoxLayout(addDetailFac, BoxLayout.PAGE_AXIS));
        addDetailFac.add(new JLabel("Heading: *", JLabel.LEFT));
        addDetailFac.add(newHeading);
        addDetailFac.add(new JLabel("Details: *", JLabel.LEFT));
        addDetailFac.add(aboutMe);

        int result = JOptionPane.showConfirmDialog(ActivityMain.mainFrame, addDetailFac, "Please enter the followings",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (newHeading.getText().isEmpty() || newDetails.getText().isEmpty())
                JOptionPane.showMessageDialog(ActivityMain.mainFrame, "All the fields must be Filled. Try Again");
            else {
                Document extras = (Document) facDoc.get("extra");
                extras.put(newHeading.getText(), newDetails.getText());
                facDoc.put("extra", extras);
                db.upsertFaculty(facDoc);
                logoutButton.doClick();
                loginButton.doClick();
            }
        }
    }

    void createJOptionPane() {
        JTextArea textAboutMe = new JTextArea(5, 50);
        JScrollPane aboutMe = new JScrollPane(textAboutMe);
        JTextArea textSkillset = new JTextArea(5, 50);
        JScrollPane skillset = new JScrollPane(textSkillset);
        JTextArea textOffice = new JTextArea(1, 50);
        JScrollPane office = new JScrollPane(textOffice);
        JTextField phone = new JTextField(5);
        JTextField email = new JTextField(5);
        JXDatePicker birth = new JXDatePicker();
        birth.setDate(Calendar.getInstance().getTime());
        birth.setFormats(df);
        JTextArea textAdd = new JTextArea(1, 50);
        JScrollPane address = new JScrollPane(textAdd);
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);
        JPanel editFac = new JPanel();

        Document extrasDoc = (Document) facDoc.get("extra");
        textAboutMe.setText(extrasDoc.getString("About Me"));
        textSkillset.setText(extrasDoc.getString("Skill Set"));
        textOffice.setText(((Document) extrasDoc.get("Work")).getString("Office"));
        phone.setText(((Document) extrasDoc.get("Personal")).getString("Mobile Phone"));
        email.setText(((Document) extrasDoc.get("Personal")).getString("Email"));
        try {
            if (!((Document) extrasDoc.get("Personal")).getString("Birth Date").equals(""))
                birth.setDate(df.parse(((Document) extrasDoc.get("Personal")).getString("Birth Date")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textAdd.setText(((Document) extrasDoc.get("Personal")).getString("Address"));
        if (((Document) extrasDoc.get("Personal")).getString("Gender").equals("Male"))
            male.setSelected(true);
        else if (((Document) extrasDoc.get("Personal")).getString("Gender").equals("Female"))
            female.setSelected(true);

        editFac.setLayout(new BoxLayout(editFac, BoxLayout.PAGE_AXIS));
        editFac.add(new JLabel("About Me: *", JLabel.LEFT));
        editFac.add(aboutMe);
        editFac.add(new JLabel("Skill Set: *", JLabel.LEFT));
        editFac.add(skillset);
        editFac.add(new JLabel("Office address: *", JLabel.LEFT));
        editFac.add(office);
        editFac.add(new JLabel("Moblie Phone: *", JLabel.LEFT));
        editFac.add(phone);
        editFac.add(new JLabel("Email: *", JLabel.LEFT));
        editFac.add(email);
        editFac.add(new JLabel("Birthday: *", JLabel.LEFT));
        editFac.add(birth);
        editFac.add(new JLabel("Home Address: *", JLabel.LEFT));
        editFac.add(address);
        editFac.add(new JLabel("Gender: *", JLabel.LEFT));
        editFac.add(male);
        editFac.add(female);

        int result = JOptionPane.showConfirmDialog(ActivityMain.mainFrame, editFac, "Please enter the followings",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (textAboutMe.getText().isEmpty() || textSkillset.getText().isEmpty() || textOffice.getText().isEmpty()
                    || phone.getText().isEmpty() || email.getText().isEmpty()
                    || birth.getDate().after(new java.util.Date()) || textAdd.getText().isEmpty()
                    || (!male.isSelected() && !female.isSelected()))
                JOptionPane.showMessageDialog(ActivityMain.mainFrame, "All the fields must be Filled. Try Again");
            else {
                Document extras = (Document) facDoc.get("extra");
                extras.put("About Me", textAboutMe.getText());
                extras.put("Skill Set", textSkillset.getText());
                ((Document) extras.get("Work")).put("Office", textOffice.getText());
                ((Document) extras.get("Personal")).put("Mobile Phone", phone.getText());
                ((Document) extras.get("Personal")).put("Email", email.getText());
                ((Document) extras.get("Personal")).put("Birth Date", df.format(birth.getDate()));
                ((Document) extras.get("Personal")).put("Address", textAdd.getText());
                LocalDate now = LocalDate.now();
                LocalDate l = LocalDate.parse(((Document) extras.get("Personal")).getString("Birth Date"));
                Period age = Period.between(l, now);
                ((Document) extras.get("Personal")).put("Age",age.getYears() + " Years");
                if (male.isSelected())
                    ((Document) extras.get("Personal")).put("Gender", "Male");
                else
                    ((Document) extras.get("Personal")).put("Gender", "Female");
                facDoc.put("extra", extras);
                db.upsertFaculty(facDoc);
                logoutButton.doClick();
                loginButton.doClick();
            }
        }
    }
}
