import javax.swing.*;
import java.awt.*;

public class JimatGUI extends JFrame  {
    JimatGUI (){
        setTitle("Toko Jimat");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);



        ImageIcon bg = new ImageIcon(getClass().getResource("/assets/openingmenu.png"));
        JLabel background = new JLabel(bg);
        background.setLayout(null);
        setContentPane(background);

        Restoran r = new Restoran();
        HUDPanel hud = new HUDPanel(r);
        hud.setBounds(0,0,1600,100);
        background.add(hud);

        //Terminal
        ImageIcon terminalBg = new ImageIcon(getClass().getResource("/assets/terminaljimat.png"));
        JLabel terminalBox = new JLabel(terminalBg);
        terminalBox.setBounds(850,80,600,700);
        terminalBox.setLayout(null);
        background.add(terminalBox);

        JTextArea output = new JTextArea();
        output.setBounds(40,70,540,620);
        output.setEditable(false);
        output.setOpaque(false);
        output.setForeground(Color.WHITE);
        output.append("Selamat datang di toko jimat !\nSilahkan piliha jimat yang ingin di beli dengan memasukkan angka 1-3\n");



        terminalBox.add(output);

        //Etalase
        ImageIcon etalase = new ImageIcon(getClass().getResource("/assets/etalaseJimat.png"));
        Image etalaseIcon = etalase.getImage();

        Image resizeEtalase =etalaseIcon.getScaledInstance(780,670, Image.SCALE_SMOOTH);
        ImageIcon etalaseFix = new ImageIcon(resizeEtalase);

        JLabel iconEtalase = new JLabel(etalaseFix);
        iconEtalase.setBounds(30,80,780,670);

        background.add(iconEtalase);



        //Input
        ImageIcon inputJimat = new ImageIcon(getClass().getResource("/assets/inputJimat.png"));
        JLabel inputBg = new JLabel(inputJimat);
        inputBg.setBounds(850,800, 600,100);
        inputBg.setLayout(null);
        background.add(inputBg);

        JTextField input = new JTextField();
        input.setBounds(30,20,540,60);
        input.setOpaque(false);
        input.setForeground(Color.WHITE);
        //input.setCaretColor(Color.WHITE);
        input.setBorder(null);
        inputBg.add(input);


        //back
        ImageIcon back = new ImageIcon(getClass().getResource("/assets/back.png"));
        Image res = back.getImage();
        Image resize = res.getScaledInstance(70,70,Image.SCALE_SMOOTH);
        ImageIcon resizeIcon = new ImageIcon(resize);
        JButton iconBack = new JButton(resizeIcon);
        iconBack.setBounds(30,820,70,70);
        background.add(iconBack);

        iconBack.addActionListener(e ->  {
            new PersiapanGUI();
            dispose();
        });


        input.addActionListener(e -> {
            String teks = input.getText();
            output.append(">" + teks + "\n");
            input.setText("");

        });

        setVisible(true);
    }
}
