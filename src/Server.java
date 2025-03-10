//package chatapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.border.Border;

public class Server extends JFrame implements ActionListener {

    JTextField text1;
    JPanel p2;
    Box vertical = Box.createVerticalBox();
    static JPanel messageArea;
    static JScrollPane scrollPane;
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    static boolean connected = false;
    static JLabel statusConnection;
    
    Server(){
        setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(193, 53, 132));
        setSize(500,700);
        p1.setLayout(null);
        p1.setBounds(0,0,500,70);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        JLabel name = new JLabel("Jitesh");
        name.setBounds(110, 25, 100, 18);
        name.setFont(new Font("Serif", Font.BOLD, 20));
        name.setForeground(Color.WHITE);
        p1.add(name);

        statusConnection = new JLabel("Waiting for connection...");
        statusConnection.setBounds(110, 45, 200, 18);
        statusConnection.setFont(new Font("Serif", Font.BOLD, 12));
        statusConnection.setForeground(Color.black);
        p1.add(statusConnection);


        p2 = new JPanel();
        p2.setLayout(null);
        p2.setBounds(7,75,485, 615);
        add(p2);

        text1 = new JTextField();
        text1.setBounds(7, 570, 390, 40);
        text1.setFont(new Font("Serif", Font.BOLD, 16));
        p2.add(text1);

        JButton send = new JButton("Send");
        send.setBounds(400, 570, 80, 40);
        send.setBackground(new Color(193, 53, 132));
        // send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("Serif", Font.BOLD, 16));
        p2.add(send);

        // Add a panel for messages with a different layout
        messageArea = new JPanel();
        messageArea.setLayout(new BoxLayout(messageArea, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(messageArea);
        scrollPane.setBounds(7, 0, 470, 560);  // Area above the text field
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        p2.add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Server");
        setLocation(100,50);
        setUndecorated(true); // this gives more space by removing the features
        getContentPane().setBackground(Color.black);

        setVisible(true);
        
        // Start server in separate thread
        new Thread(() -> {
            try {
                JPanel serverInfoPanel = new JPanel(new BorderLayout());
                JLabel infoLabel = new JLabel("<html><div style='color:gray;font-size:12px;text-align:center;'>Server started on port 6001.<br>Waiting for client connection...</div></html>");
                serverInfoPanel.add(infoLabel, BorderLayout.CENTER);
                serverInfoPanel.setBackground(new Color(240, 240, 240));
                serverInfoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                SwingUtilities.invokeLater(() -> {
                    messageArea.add(serverInfoPanel);
                    messageArea.add(Box.createVerticalStrut(15));
                    scrollToBottom();
                });
                
                skt = new ServerSocket(6001);
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                connected = true;
                
                SwingUtilities.invokeLater(() -> {
                    statusConnection.setText("Active Now");
                    JPanel connectionPanel = new JPanel(new BorderLayout());
                    JLabel connectedLabel = new JLabel("<html><div style='color:green;font-size:12px;text-align:center;'>Client connected successfully!</div></html>");
                    connectionPanel.add(connectedLabel, BorderLayout.CENTER);
                    connectionPanel.setBackground(new Color(240, 240, 240));
                    connectionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    messageArea.add(connectionPanel);
                    messageArea.add(Box.createVerticalStrut(15));
                    scrollToBottom();
                });
                
                while(true) {
                    String msginput = din.readUTF();
                    JPanel p3 = formatLabel(msginput, false);
                    
                    SwingUtilities.invokeLater(() -> {
                        JPanel left = new JPanel(new BorderLayout());
                        left.add(p3, BorderLayout.LINE_START);
                        left.setOpaque(false);
                        messageArea.add(left);
                        messageArea.add(Box.createVerticalStrut(15));
                        scrollToBottom();
                    });
                }
            } catch (Exception e) {
                connected = false;
                SwingUtilities.invokeLater(() -> {
                    statusConnection.setText("Disconnected");
                    JPanel errorPanel = new JPanel(new BorderLayout());
                    JLabel errorLabel = new JLabel("<html><div style='color:red;font-size:12px;'>Connection lost. Restart the application.</div></html>");
                    errorPanel.add(errorLabel, BorderLayout.CENTER);
                    errorPanel.setBackground(new Color(240, 240, 240));
                    errorPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    messageArea.add(errorPanel);
                    messageArea.add(Box.createVerticalStrut(15));
                    scrollToBottom();
                });
                e.printStackTrace();
            }
        }).start();
        
        // Add key listener for Enter key
        text1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });
    }
    
    private void scrollToBottom() {
        p2.validate();
        p2.repaint();
        // Use SwingUtilities.invokeLater to ensure scrolling happens after layout is complete
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
    
    private void sendMessage() {
        try {
            String out = text1.getText();
            
            if (!out.equals("")) {
                if (!connected) {
                    JPanel warningPanel = new JPanel(new BorderLayout());
                    JLabel warningLabel = new JLabel("<html><div style='color:red;font-size:12px;'>Cannot send message: No client connected</div></html>");
                    warningPanel.add(warningLabel, BorderLayout.CENTER);
                    warningPanel.setBackground(new Color(255, 240, 240));
                    warningPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    messageArea.add(warningPanel);
                    messageArea.add(Box.createVerticalStrut(15));
                    scrollToBottom();
                    text1.setText("");
                    return;
                }
                
                JPanel p3 = formatLabel(out, true);
                
                JPanel right = new JPanel(new BorderLayout());
                right.add(p3, BorderLayout.LINE_END);
                right.setOpaque(false);
                messageArea.add(right);
                messageArea.add(Box.createVerticalStrut(15));
                
                dout.writeUTF(out);
                text1.setText("");
                
                scrollToBottom();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        sendMessage();
    }

    // Replace the formatLabel method and remove all other border implementations:

// Replace the formatLabel method with this optimized version:

// Replace the formatLabel method with this optimized version:

public static JPanel formatLabel(String out, boolean isSent) {
    Color messageColor = isSent ? new Color(51, 144, 255) : new Color(39, 174, 96);
    
    // Calculate width based on text length
    int textLength = out.length();
    int width;
    
    if (textLength <= 10) {
        width = 80;
    } else if (textLength <= 20) {
        width = 150;
    } else if (textLength <= 50) {
        width = 220;
    } else {
        width = 300;
    }
    
    // Use absolute positioning for precise control
    JPanel panel = new JPanel();
    panel.setLayout(null); // No layout manager for precise control
    panel.setBackground(messageColor);
    
    int totalHeight;
    
    if (textLength <= 5) {
        // For very short messages
        JLabel textLabel = new JLabel(out);
        textLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        textLabel.setForeground(Color.WHITE);
        
        // Create time label
        JLabel timeLabel = new JLabel(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 9));
        timeLabel.setForeground(new Color(240, 240, 240));
        
        // Measure text dimensions
        FontMetrics fm = textLabel.getFontMetrics(textLabel.getFont());
        int textWidth = fm.stringWidth(out) + 2;
        int textHeight = fm.getHeight();
        
        // Position text
        textLabel.setBounds(5, 2, textWidth, textHeight);
        panel.add(textLabel);
        
        // Position time next to text
        timeLabel.setBounds(textWidth + 10, 4, 40, 12);
        panel.add(timeLabel);
        
        // Set total height - just enough for one line
        totalHeight = Math.max(textHeight, timeLabel.getPreferredSize().height) ;
        
    } else {
        // For longer messages
        JTextArea textArea = new JTextArea(out);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(messageColor);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Remove all border
        textArea.setMargin(new Insets(15, 0, 0, 15)); // Remove all margin
        
        // Calculate exact text height
        textArea.setSize(width - 10, Short.MAX_VALUE); // Set width constraint, large height temporarily
        int lineCount = textArea.getLineCount();
        FontMetrics fm = textArea.getFontMetrics(textArea.getFont());
        int textHeight = lineCount * fm.getHeight();
        
        // Position text
        textArea.setBounds(5, 2, width - 10, textHeight);
        panel.add(textArea);
        
        // Create time label
        JLabel timeLabel = new JLabel(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 9));
        timeLabel.setForeground(new Color(240, 240, 240));
        
        // Position time at bottom right
        FontMetrics timeFm = timeLabel.getFontMetrics(timeLabel.getFont());
        timeLabel.setBounds(width - timeFm.stringWidth(timeLabel.getText()) - 7, textHeight + 1, 40, 10);
        panel.add(timeLabel);
        
        // Set total height - text + time
        totalHeight = textHeight + 14;
    }
    
    // Apply the calculated height to the panel
    panel.setPreferredSize(new Dimension(width, totalHeight));
    
    // Use minimal border
    panel.setBorder(new Border() {
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }
        
        public boolean isBorderOpaque() {
            return true;
        }
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(messageColor);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.fillRoundRect(x, y, width-1, height-1, 8, 8);
        }
    });
    
    // Wrapper panel
    JPanel wrapperPanel = new JPanel(new BorderLayout(0, 0));
    wrapperPanel.setOpaque(false);
    wrapperPanel.add(panel, isSent ? BorderLayout.EAST : BorderLayout.WEST);
    
    return wrapperPanel;
}

    public static void main(String[] args){
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Server();
    }
}