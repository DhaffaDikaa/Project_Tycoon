import javax.swing.*;
import java.awt.*;

public class HUDPanel extends JPanel {

    private JLabel uangText;
    private JLabel hariText;
    private JLabel levelText;

    private Restoran restoran;

    public HUDPanel(Restoran r){
        this.restoran = r;

        setLayout(null);
        setOpaque(false);

        //FONT

        Font fontGame;
        try {
            fontGame = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/font/PressStart2P-Regular.tff"));
            fontGame = fontGame.deriveFont(28f);
        }catch(Exception e){
            System.out.println(e.getMessage());

            fontGame = new Font("Arial", Font.PLAIN, 20);
        }

        //UANG

        ImageIcon uang = new ImageIcon(getClass().getResource("/assets/coin.png"));
        Image img = uang.getImage();
        Image resize = img.getScaledInstance(190,60,Image.SCALE_SMOOTH);

        JLabel iconUang = new JLabel(new ImageIcon(resize));
        iconUang.setBounds(20,10,190,60);
        iconUang.setLayout(null);

        uangText = new JLabel();
        uangText.setBounds(70,10,120,40);
        uangText.setForeground(Color.WHITE);
        uangText.setFont(fontGame);

        iconUang.add(uangText);
        add(iconUang);

        //HARI

        ImageIcon hari = new ImageIcon(getClass().getResource("/assets/hari.png"));

        Image img2 = hari.getImage();
        Image resize2 = img2.getScaledInstance(190,60,Image.SCALE_SMOOTH);

        JLabel iconHari = new JLabel(new ImageIcon(resize2));

        iconHari.setBounds(230,10,190,60);
        iconHari.setLayout(null);

        hariText = new JLabel();
        hariText.setBounds(70,10,120,40);
        hariText.setForeground(Color.WHITE);
        hariText.setFont(fontGame);

        iconHari.add(hariText);

        add(iconHari);

        //LEVEL

        ImageIcon level = new ImageIcon(
                getClass().getResource("/assets/level.png")
        );

        Image img3 = level.getImage();
        Image resize3 = img3.getScaledInstance(215,60,Image.SCALE_SMOOTH);
        JLabel iconLevel = new JLabel(new ImageIcon(resize3));

        iconLevel.setBounds(1235,10,215,60);
        iconLevel.setLayout(null);
        levelText = new JLabel();
        levelText.setBounds(110,10,150,40);
        levelText.setForeground(Color.WHITE);
        levelText.setFont(fontGame);

        iconLevel.add(levelText);

        add(iconLevel);

        updateHUD();
    }

    public void updateHUD() {

        uangText.setText(
                String.valueOf(restoran.getUang())
        );

        levelText.setText(
                "Lv " + restoran.getLevel()
        );

        hariText.setText(
                "Hari " + restoran.getHari()
        );
    }
}