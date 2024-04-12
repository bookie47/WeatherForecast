import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AirQualityGui extends JFrame {
    private JTextField searchTextField;
    private JButton searchButton;
    private JLabel airQualityImage;
    private JLabel airQualityDesc;
    private JLabel airQualityValue;
    private JLabel aqiText;
    private JSONObject airQualityData;

    public AirQualityGui(){
        super("Air Quality App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450,700);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setBackground(new Color(217,217,217));
        addGuiComponents();
    }

    private void addGuiComponents(){

        searchTextField = new JTextField("Search Location");
        searchTextField.setForeground(new Color(153,153,153));
        searchTextField.setBounds(70,15,300,40);
        searchTextField.setFont(new Font("Dialog",Font.PLAIN,24));
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchTextField.getText().equals("Search Location")) {
                    searchTextField.setText("");
                }
                searchTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText("Search Location");
                    searchTextField.setForeground(new Color(153, 153, 153, 128));
                }
            }
        });
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        add(searchTextField);

        airQualityImage = new JLabel(loadImage("src/ImgAirQuality/Good.png"));
        airQualityImage.setBounds(115,80,193,424);
        add(airQualityImage);

        airQualityDesc = new JLabel("GOOD");
        airQualityDesc.setBounds(0,510,450,36);
        airQualityDesc.setFont(new Font("Dialog", Font.BOLD,36));
        airQualityDesc.setForeground(new Color(199,0,0));
        airQualityDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(airQualityDesc);

        airQualityValue = new JLabel("50");
        airQualityValue.setBounds(0,560,450,36);
        airQualityValue.setFont(new Font("Dialog",Font.BOLD,44));
        airQualityValue.setHorizontalAlignment(SwingConstants.CENTER);
        add(airQualityValue);

        aqiText = new JLabel("US AQI");
        aqiText.setBounds(0,600,450,36);
        aqiText.setFont(new Font("Dialog",Font.BOLD,20));
        aqiText.setForeground(new Color(88,88,88));
        aqiText.setHorizontalAlignment(SwingConstants.CENTER);
        add(aqiText);

        searchButton = new JButton(loadImage("src/ImgUi/search_2.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,12,45,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        add(searchButton);

        JButton pm25Button = new JButton(loadImage("src/ImgAirQuality/weathericon_1.png"));
        pm25Button.setBounds(10,10,50,50);
        pm25Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WeatherAppGui().setVisible(true);
                dispose();
            }
        });
        add(pm25Button);

    }

    private void performSearch(){
        String userInput = searchTextField.getText();
        if(userInput.replaceAll("\\s", "").length() <= 0){
            return;
        }
        airQualityData = AirQualityApp.getAirQualityData(userInput);
        String airQualityCondition = (String) airQualityData.get("aqi_condition");
        switch (airQualityCondition){
            case "GOOD":
                airQualityImage.setIcon(loadImage("src/ImgAirQuality/Good.png"));
                break;
            case "MODERATE":
                airQualityImage.setIcon(loadImage("src/ImgAirQuality/MODERATE.png"));
                break;
            case "UNHEALTHY":
                airQualityImage.setIcon(loadImage("src/ImgAirQuality/Unhealthy.png"));
                break;
            case "HAZARDOUS":
                airQualityImage.setIcon(loadImage("src/ImgAirQuality/Hazardous.png"));
                break;
        }

        int aqiValue = Integer.parseInt(airQualityData.get("aqi").toString());
        airQualityValue.setText(String.valueOf(aqiValue));

        String airQualityConditionn = airQualityData.get("aqi_condition").toString();
        airQualityDesc.setText(airQualityConditionn);
    }
    private ImageIcon loadImage(String resourcePath){
        try{
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon((image));
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;

    }

}
