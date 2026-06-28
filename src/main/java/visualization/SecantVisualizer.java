/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package visualization;

import algebra.Term;
import optimization.SecantMethod;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class SecantVisualizer extends JFrame {

    private final List<Term> function;

    private final FunctionPanel functionPanel;

    private final JTextField fromField =
            new JTextField("0.1", 8);

    private final JTextField toField =
            new JTextField("1.0", 8);

    private final JTextField epsilonField =
            new JTextField("1e-10", 8);

    private final JTextField deltaField =
            new JTextField("1e-9", 8);

    private final JTextField iterationField =
            new JTextField("100", 8);

    private final JButton startButton =
            new JButton("Start");

    private final JButton resetButton =
            new JButton("Reset");

    private final DefaultListModel<String> iterationModel =
            new DefaultListModel<>();

    private final JList<String> iterationList =
            new JList<>(iterationModel);

    private final JLabel statusLabel =
            new JLabel("Ready");

    private Timer animationTimer;

    public SecantVisualizer(List<Term> function) {

        super("Secant Method Visualizer");

        this.function = List.copyOf(function);

        this.functionPanel = new FunctionPanel(function);

        buildUI();

        registerListeners();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(1400, 900);

        setLocationRelativeTo(null);
    }

    private void buildUI() {

        setLayout(new BorderLayout());

        add(functionPanel, BorderLayout.CENTER);

        JPanel controls = buildControlPanel();

        add(controls, BorderLayout.EAST);
    }

    private JPanel buildControlPanel() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(340, 900));
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setLayout(new BorderLayout(10,10));

        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        inputPanel.add(new JLabel("From x"), gbc);

        gbc.gridx = 1;
        inputPanel.add(fromField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        inputPanel.add(new JLabel("To x"), gbc);

        gbc.gridx = 1;
        inputPanel.add(toField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        inputPanel.add(new JLabel("ε"), gbc);

        gbc.gridx = 1;
        inputPanel.add(epsilonField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        inputPanel.add(new JLabel("δ"), gbc);

        gbc.gridx = 1;
        inputPanel.add(deltaField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        inputPanel.add(new JLabel("Max iterations"), gbc);

        gbc.gridx = 1;
        inputPanel.add(iterationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        inputPanel.add(startButton, gbc);

        gbc.gridx = 1;

        inputPanel.add(resetButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        iterationList.setFont(new Font(Font.MONOSPACED,
                Font.PLAIN,
                12));

        JScrollPane scrollPane =
                new JScrollPane(iterationList);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Iterations"));

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.setBorder(new EmptyBorder(5,0,0,0));
        south.add(statusLabel, BorderLayout.CENTER);

        panel.add(south, BorderLayout.SOUTH);
        return panel;
    }

    private void registerListeners() {
        startButton.addActionListener(e -> startAnimation());
        resetButton.addActionListener(e -> resetVisualization());
    }

    private void startAnimation() {

        try {

            double from = Double.parseDouble(fromField.getText());
            double to = Double.parseDouble(toField.getText());
            double epsilon = Double.parseDouble(epsilonField.getText());
            double delta = Double.parseDouble(deltaField.getText());
            int maxIterations = Integer.parseInt(iterationField.getText());

            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }

            iterationModel.clear();
            functionPanel.reset();

            SecantMethod solver = SecantMethod.of(function);

            double extremum = solver.getExtremumX(
                    from,
                    to,
                    epsilon,
                    delta,
                    maxIterations);

            List<Iteration> iterations = solver.getIterations();

            functionPanel.setIterations(iterations);

            for (Iteration iteration : iterations) {
                iterationModel.addElement(iteration.toString());
            }

            if (iterations.isEmpty()) {

                functionPanel.setExtremum(extremum);

                statusLabel.setText(
                        String.format(
                                "Finished. x = %.10f",
                                extremum));

                return;
            }

            statusLabel.setText("Animating...");

            final int[] current = {0};

            animationTimer = new Timer(900, e -> {

                functionPanel.setCurrentIteration(current[0]);

                iterationList.setSelectedIndex(current[0]);
                iterationList.ensureIndexIsVisible(current[0]);

                current[0]++;

                if (current[0] >= iterations.size()) {

                    ((Timer) e.getSource()).stop();

                    functionPanel.setExtremum(extremum);

                    statusLabel.setText(
                            String.format(
                                    "Finished. x = %.10f",
                                    extremum));
                }
            });

            animationTimer.setInitialDelay(300);

            animationTimer.start();

        } catch (Exception ex) {

            if (animationTimer != null) {
                animationTimer.stop();
            }

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            statusLabel.setText("Error");
        }
    }

    private void resetVisualization() {

        if (animationTimer != null) {
            animationTimer.stop();
        }

        iterationModel.clear();
        functionPanel.reset();
        statusLabel.setText("Ready");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            List<Term> function = List.of(
                    Term.builder().power(4).coefficient(3).build(),
                    Term.builder().power(2).coefficient(1).build(),
                    Term.builder().power(1).coefficient(-2).build(),
                    Term.asRealConstant(1)
            );

            List<Term> derivative = List.of(
                    Term.builder().power(3).coefficient(12).build(),
                    Term.builder().power(1).coefficient(2).build(),
                    Term.asRealConstant(-2)
            );

            new SecantVisualizer(derivative).setVisible(true);
        });
    }
}
