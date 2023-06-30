import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Calculator {
    private static JFrame frame;
    private static Container container;
    private static JPanel panelButtonsStandart, panelButtonsMini;
    private final JLabel result;
    private final JTextArea text;
    private static JMenuBar jMenuBar;

    private static final JButton
            button1 = new JButton("%"), button2 = new JButton("CE"), button3 = new JButton("C"), button4 = new JButton("⮨"),
            button5 = new JButton("1/x"), button6 = new JButton("x²"), button7 = new JButton("√x"), button8 = new JButton("÷"),
            button9 = new JButton("7"), button10 = new JButton("8"), button11 = new JButton("9"), button12 = new JButton("×"),
            button13 = new JButton("4"), button14 = new JButton("5"), button15 = new JButton("6"), button16 = new JButton("-"),
            button17 = new JButton("1"), button18 = new JButton("2"), button19 = new JButton("3"), button20 = new JButton("+"),
            button21 = new JButton("+/-"), button22 = new JButton("0"), button23 = new JButton("."), button24 = new JButton("=");
    private static final JButton[] BUTTONS = {
            button1, button2, button3, button4,
            button5, button6, button7, button8,
            button9, button10, button11, button12,
            button13, button14, button15, button16,
            button17, button18, button19, button20,
            button21, button22, button23, button24};
    private double number1 = 0.0, number2 = 0.0;
    private String operation = null;
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    private static final Font FONT_MENU_ITEM = new Font(Font.SANS_SERIF, Font.BOLD, 12);
    private static final Color COLOR_GRAY = new Color(235, 235, 235);

    public Calculator() {
        frame = new JFrame();
        frame.setTitle("Калькулятор 1.0");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        createMenu();
        frame.setJMenuBar(jMenuBar);

        container = frame.getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));

        result = new JLabel();
        result.setPreferredSize(new Dimension(275, 25));
        result.setFont(FONT);
        result.setForeground(Color.GRAY);
        container.add(result);

        text = new JTextArea();
        text.setPreferredSize(new Dimension(275, 50));
        text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
        text.setLineWrap(true);
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String s = String.valueOf(e.getKeyChar());
                e.consume();
                if (s.matches("\\d|[+=.-]")) {
                    processingValues(s);
                } else if ("/".equals(s)) {
                    processingValues("÷");
                } else if ("*".equals(s)) {
                    processingValues("×");
                } else if (",".equals(s)) {
                    processingValues(".");
                } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    processingValues("=");
                }
            }
        });
        container.add(text);

        for (JButton button : BUTTONS) {
            button.setFont(FONT);
            button.setBackground(Color.WHITE);
            button.setBorderPainted(false);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(COLOR_GRAY);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(Color.WHITE);
                }
            });
            button.addActionListener(e -> processingValues(button.getText()));
        }
        createStandart();

        frame.setVisible(true);
    }

    public static void createMenu() {
        jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");
        menu.setFont(FONT);
        JMenuItem[] menuItems = {new JMenuItem("Стандартный"),
                new JMenuItem("Простой"),
                new JMenuItem("О программе"),
                new JMenuItem("Выход")};
        menu.add(menuItems[0]);
        menu.add(menuItems[1]);
        menu.addSeparator();
        menu.add(menuItems[2]);
        menu.addSeparator();
        menu.add(menuItems[3]);

        jMenuBar.add(menu);
        jMenuBar.setBackground(new Color(238, 238, 238));
        jMenuBar.setBorderPainted(false);

        for (JMenuItem menuItem : menuItems) {
            menuItem.setBackground(Color.WHITE);
            menuItem.setFont(FONT_MENU_ITEM);
            menuItem.addActionListener(e -> {
                switch (menuItem.getText()) {
                    case "Стандартный" -> createStandart();
                    case "Простой" -> createMini();
                    case "О программе" -> createInfo();
                    case "Выход" -> System.exit(0);
                }
            });
        }
    }

    public static void createInfo(){
        JFrame frame = new JFrame("О программе");
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        JLabel label1 = new JLabel("<HTML>Калькулятор<br>Версия 1.0</HTML>");
        container.add(label1, BorderLayout.CENTER);
        label1.setFont(FONT);
        frame.setSize(320, 120);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    public static void createStandart() {
        if (container.isAncestorOf(panelButtonsStandart)) {
            return;
        }
        frame.setSize(320, 450);
        panelButtonsStandart = new JPanel(new GridLayout(6, 4, 5, 5));
        panelButtonsStandart.setPreferredSize(new Dimension(275, 280));
        for (JButton button : BUTTONS) {
            panelButtonsStandart.add(button);
        }
        if (container.isAncestorOf(panelButtonsMini)) {
            container.remove(panelButtonsMini);
        }
        container.add(panelButtonsStandart);
    }

    public static void createMini() {
        if (container.isAncestorOf(panelButtonsMini)) {
            return;
        }
        frame.setSize(320, 410);
        panelButtonsMini = new JPanel(new GridLayout(5, 4, 5, 5));
        panelButtonsMini.setPreferredSize(new Dimension(275, 235));
        JButton[] BUTTONS_MINI = {button2, button3, button4, button8,
                button9, button10, button11, button12,
                button13, button14, button15, button16,
                button17, button18, button19, button20,
                button21, button22, button23, button24};
        for (JButton button : BUTTONS_MINI) {
            panelButtonsMini.add(button);
        }
        if (container.isAncestorOf(panelButtonsStandart)) {
            container.remove(panelButtonsStandart);
        }
        container.add(panelButtonsMini);
    }

    public void processingValues(String str) {
        if (str.matches("\\d") && text.getText().length() < 17) {
            text.append(str);
        }
        try {
            switch (str) {
                case "." -> {
                    if (text.getText().isEmpty() || text.getText().isBlank()) {
                        text.setText("0.");
                    } else if (!(text.getText().contains("."))) {
                        text.append(str);
                    }
                }
                case "CE" -> text.setText("");
                case "C" -> {
                    number1 = 0.0;
                    number2 = 0.0;
                    text.setText("");
                    result.setText("");
                }
                case "⮨" -> {
                    if (text.getText().isEmpty() || text.getText().isBlank()) {
                        text.setText("");
                    } else {
                        text.setText(text.getText().substring(0, text.getText().length() - 1));
                    }
                }
                case "+/-" -> {
                    if (text.getText().isEmpty() || text.getText().isBlank()) {
                        text.setText("-");
                    } else if (text.getText().charAt(0) == '-') {
                        text.setText(text.getText().substring(1));
                    } else {
                        text.setText("-" + text.getText());
                    }
                }
                case "1/x" -> {
                    number1 = Double.parseDouble(text.getText());
                    result.setText("1 / " + number1 + " = ");
                    text.setText(String.valueOf(1 / number1));
                }
                case "x²" -> {
                    number1 = Double.parseDouble(text.getText());
                    result.setText(number1 + "²" + " = ");
                    text.setText(String.valueOf(Math.pow(number1, 2)));
                }
                case "√x" -> {
                    number1 = Double.parseDouble(text.getText());
                    result.setText("√" + number1 + " = ");
                    text.setText(String.valueOf(Math.sqrt(number1)));
                }
                case "%" -> {
                    number2 = (number1 / 100) * Double.parseDouble(text.getText());
                    text.setText(String.valueOf(number2));
                }
                case "÷" -> calcWithZero("÷");
                case "×" -> calcWithZero("×");
                case "-" -> calcWithZero("-");
                case "+" -> calcWithZero("+");
                case "=" -> {
                    if (operation != null) {
                        number2 = Double.parseDouble(text.getText());
                        result.setText(result.getText().concat(number2 + " = "));
                        calc(operation);
                        operation = null;
                    }
                }
            }
        } catch (NumberFormatException | NullPointerException ignored) {

        }
    }

    public void calc(String operation) {
        switch (operation) {
            case "+" -> text.setText(String.valueOf(number1 + number2));
            case "-" -> text.setText(String.valueOf(number1 - number2));
            case "÷" -> {
                if (number2 == 0) {
                    text.setText("На 0 делить нельзя!");
                } else {
                    text.setText(String.valueOf(number1 / number2));
                }
            }
            case "×" -> text.setText(String.valueOf(number1 * number2));
        }
    }

    public void calcWithZero(String oper) {
        if (number2 == 0) {
            operation = oper;
            result.setText(number1 + " " + oper + " ");
        }
        if (number1 != 0 && number2 == 0) {
            number2 = Double.parseDouble(text.getText());
            calc(oper);
        }
        operation = oper;
        number1 = Double.parseDouble(text.getText());
        result.setText(number1 + " " + oper + " ");
        text.setText("");
        number2 = 0.0;
    }

    public static void main(String[] args) {
        new Calculator();
    }
}