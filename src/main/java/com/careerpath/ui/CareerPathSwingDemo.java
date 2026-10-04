package com.careerpath.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CareerPathSwingDemo - A Swing demo showing collection framework usage,
 * string processing, and a small career recommendation interface.
 */
public class CareerPathSwingDemo extends JFrame {

    private final JTextArea resumeArea;
    private final JTextArea resultArea;
    private final Map<String, List<String>> careerSkills;

    public CareerPathSwingDemo() {
        super("Career Path Swing Demo");

        careerSkills = createCareerSkills();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Career Path Resume Analyzer (Swing)", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        resumeArea = new JTextArea();
        resumeArea.setLineWrap(true);
        resumeArea.setWrapStyleWord(true);
        resumeArea.setText("Paste your resume text here and press Analyze...");

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(resumeArea), new JScrollPane(resultArea));
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        JButton analyzeButton = new JButton("Analyze Resume");
        analyzeButton.addActionListener(new AnalyzeListener());
        add(analyzeButton, BorderLayout.SOUTH);
    }

    private Map<String, List<String>> createCareerSkills() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("Java Developer", new ArrayList<>(List.of("Java", "Servlets", "Collections", "OOP", "SQL")));
        map.put("Frontend Developer", new ArrayList<>(List.of("HTML", "CSS", "JavaScript", "React", "UI/UX")));
        map.put("Data Analyst", new ArrayList<>(List.of("Python", "SQL", "Data Visualization", "Statistics", "Excel")));
        return map;
    }

    private class AnalyzeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String resumeText = resumeArea.getText();
            if (resumeText == null || resumeText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(CareerPathSwingDemo.this,
                        "Please enter resume text before analyzing.",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String normalized = resumeText.toLowerCase();
            StringBuilder message = new StringBuilder();
            message.append("Resume Analysis Results:\n\n");

            for (Map.Entry<String, List<String>> entry : careerSkills.entrySet()) {
                String job = entry.getKey();
                List<String> skills = entry.getValue();
                int matchCount = 0;

                for (String skill : skills) {
                    if (normalized.contains(skill.toLowerCase())) {
                        matchCount++;
                    }
                }

                message.append(job)
                       .append(" - ")
                       .append(matchCount)
                       .append("/")
                       .append(skills.size())
                       .append(" skills matched\n");

                message.append("Required skills: ")
                       .append(String.join(", ", skills))
                       .append("\n\n");
            }

            resultArea.setText(message.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CareerPathSwingDemo demo = new CareerPathSwingDemo();
            demo.setVisible(true);
        });
    }
}
