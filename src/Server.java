//package chatapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server extends JFrame implements ActionListener {

    JTextField text1;
    JPanel p2;
    Box vertical = Box.createVerticalBox();
    static JPanel messageArea;
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    
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

        JLabel Status = new JLabel("Active Now");
        Status.setBounds(110, 45, 100, 18);
        Status.setFont(new Font("Serif", Font.BOLD, 12));
        Status.setForeground(Color.black);
        p1.add(Status);


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
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("Serif", Font.BOLD, 16));
        p2.add(send);

        // Add a panel for messages with a different layout
        messageArea = new JPanel(new BorderLayout());
        messageArea.setBounds(7, 0, 470, 560);  // Area above the text field
        messageArea.add(vertical, BorderLayout.PAGE_START);
        p2.add(messageArea);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Server");
        setLocation(100,50);
        setUndecorated(true); // this gives more space by removing the features
        getContentPane().setBackground(Color.black);

        setVisible(true);
        
        // Start server in separate thread
        new Thread(() -> {
            try {
                skt = new ServerSocket(6001);
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true) {
                    String msginput = din.readUTF();
                    JPanel p3 = formatLabel(msginput, false);
                    
                    SwingUtilities.invokeLater(() -> {
                        JPanel left = new JPanel(new BorderLayout());
                        left.add(p3, BorderLayout.LINE_START);
                        vertical.add(left);
                        vertical.add(Box.createVerticalStrut(15));
                        p2.validate();
                        p2.repaint();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void actionPerformed(ActionEvent ae){
        try {
            String out = text1.getText();
            
            if (!out.equals("")) {
                JPanel p3 = formatLabel(out, true);
                
                JPanel right = new JPanel(new BorderLayout());
                right.add(p3, BorderLayout.LINE_END);
                vertical.add(right);
                vertical.add(Box.createVerticalStrut(15));
                
                dout.writeUTF(out);
                text1.setText("");
                
                revalidate();
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out, boolean isSent){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 
        
        Color messageColor = isSent ? new Color(51, 144, 255) : new Color(39, 174, 96);
        panel.setBackground(messageColor);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        int width = Math.min(Math.max(out.length() * 8, 50), 300);
        
        JLabel output = new JLabel("<html><p style=\"width: " + width + "px\">" + out + "</p></html>");
        output.setFont(new Font("Serif", Font.PLAIN, 16));
        output.setForeground(Color.WHITE);
        output.setOpaque(true);
        output.setBackground(messageColor);
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(cal.getTime());

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timePanel.setBackground(messageColor);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        timeLabel.setForeground(Color.WHITE);
        
        timePanel.add(timeLabel);
        panel.add(timePanel);
        panel.add(Box.createVerticalStrut(5));

        return panel;
    }
    public static void main(String[] args){
        new Server();
    }
}