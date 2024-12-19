
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.text.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Server implements ActionListener {

    JTextField text;
    JPanel a;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server() {
        f.setLayout(null);

        JPanel p = new JPanel();
        p.setBackground(new Color(7, 94, 84));
        p.setBounds(0, 0, 450, 70);
        p.setLayout(null);
        f.add(p);

        ImageIcon i = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(5, 20, 25, 25);
        p.add(label);

        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel label2 = new JLabel(i6);
        label2.setBounds(40, 10, 50, 50);
        p.add(label2);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel label3 = new JLabel(i9);
        label3.setBounds(300, 20, 30, 30);
        p.add(label3);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel label4 = new JLabel(i12);
        label4.setBounds(360, 20, 35, 30);
        p.add(label4);

        ImageIcon i15 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i16 = i15.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i17 = new ImageIcon(i16);
        JLabel label5 = new JLabel(i17);
        label5.setBounds(420, 20, 10, 25);
        p.add(label5);

        JLabel name = new JLabel("Gaitonde");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p.add(name);

        JLabel status = new JLabel("Active now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        p.add(status);

        a = new JPanel();
        a.setBounds(5, 75, 430, 570);
        f.add(a);

        text = new JTextField();
        text.setBounds(5, 610, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320, 610, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        send.addActionListener(this);
        f.add(send);

        f.setUndecorated(true);
        f.setSize(452, 700);
        f.setVisible(true);
        f.setLocation(200, 50);
        f.getContentPane().setBackground(Color.WHITE);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
            JLabel output = new JLabel(out);
            JPanel p2 = formatLabel(out);
            // p2.add(output);
            a.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }

    public static void main(String args[]) {
        new Server();

        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream((s.getOutputStream()));
                while (true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
