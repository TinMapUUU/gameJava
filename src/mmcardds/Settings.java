package mmcardds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Settings extends JPanel implements ActionListener, MouseListener {
	
	Image background; 
	Timer timer;
	boolean flip = false;
	boolean check = false;
	int [] selected = new int[2];
	int count = 0;
	int index;
	Card [] card;
	int cardSize = 26;
	String [] lol= {"ahri", "akali", "darius", "ezreal", "jinx", "kaisa", "lux",
				    "missfortune", "rakan", "seraphine", "xayah", "zyra", 
				    "boom"};  

	long complTime = 0;	
	int dem = 0;
	
	private JLabel timerLabel; // label hiện thời gian

	public Settings() {
		card = new Card[cardSize];
		
		List<String> shuffled_list = Arrays.asList(lol);		
		Collections.shuffle(shuffled_list);


		for (int i=0; i<cardSize; i++) {			
			card[i] = new Card(lol[i%13], i);			
			card[i].setX(52 + i *120);
			if (i>=10 && i<20) {
				card[i].setX(52+(i-10)*120);
				card[i].setY(225);
			}
			if (i>=20) {
				card[i].setX(52+(i-20)*120);
				card[i].setY(2*225);
			}
		}
		
		addMouseListener(this);
		timer = new Timer(10, this);
        timer.start();

		try {
			background = ImageIO.read(new File("./src/images/background02.jpg"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
  // TẠO VÀ CẤU HÌNH JLABEL THỜI GIAN  	
	timerLabel = new JLabel();	
	timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
	timerLabel.setForeground(Color.WHITE);
    timerLabel.setHorizontalAlignment(SwingConstants.RIGHT); //import THƯ VIỆN
    timerLabel.setBounds(800, 20, 200, 30);
    add(timerLabel);
    
    // THIẾT LẬP THỜI GIAN START 00:00
    MMCards.startTimeMillis = System.currentTimeMillis();
	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, MMCards.width, MMCards.height, null);

		for (int i=0; i<cardSize; i++) {
			g.drawImage(card[i].getPicture(), card[i].getX(), card[i].getY(), 
						card[i].getWidth(), card[i].getHeight(), null);

		}		
		Font kiten = new Font("Forte", Font.BOLD, 40);
		g.setFont(kiten);
		g.setColor(Color.MAGENTA);
		g.drawString("DMinh_MDuy_Hung", 100, 750);
		Font playerFont = new Font("Cambria", Font.BOLD, 24);
	    g.setFont(playerFont);
	    g.setColor(Color.WHITE);
	    g.drawString("Player: "+ MMCards.PLAYER, 950, 750);    
	}	

	private String formatTime(long timeMillis) {
        long elapsedSeconds = (int) timeMillis / 1000;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
		
	  // PHƯƠNG HƯỚNG ( SAU KHI ĐC LẬT)
	int direction = 1;
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (flip) {
            flip = card[index].flip();
            if (card[index].getWidth() <= 0) {
                card[index].setPicture(card[index].getLol());
            }
        }

        if (check) {
            if (!card[selected[0]].getLol().equals(card[selected[1]].getLol())) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                card[selected[0]].setPicture("BG_cards");
                card[selected[1]].setPicture("BG_cards");
                dem = dem +0;
            }
            
            check = false;
        }
        ///////////////////////////////
        if (count == 2 && !flip) {
            if (card[selected[0]].getLol().equals(card[selected[1]].getLol())) {
                dem = dem +1;
            } else {
            	try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                card[selected[0]].setPicture("BG_cards");
                card[selected[1]].setPicture("BG_cards");              
            }
            count = 0;            
        }
///////////////////////////
        repaint();

        if (count == 2 && !flip) {
            check = true;
            count = 0;
        }

        if (dem == 13) {
            showCompletionDialog();
        }
        updateTimer();
        
    }

	private void updateTimer() {
		try {
		long currentTimeMillis = System.currentTimeMillis();
		long elapsedSeconds = (currentTimeMillis - MMCards.startTimeMillis) / 1000;
		complTime = currentTimeMillis - MMCards.startTimeMillis; // Cập nhật complTime
		long minutes = elapsedSeconds / 60;
		long seconds = elapsedSeconds % 60; 
		String timeString = String.format("%02d:%02d", minutes, seconds);
		timerLabel.setText(timeString);
		 } catch (Exception e) {
			 System.err.println("Error updating timer: " + e.getMessage());
		    }
	}
	
	private void showCompletionDialog() {
	    String message = "Player: " + MMCards.PLAYER + "!\n"
	            + "Finish Time: " + formatTime(complTime);
	   
	    JOptionPane.showMessageDialog(this, message, "Finish Game", JOptionPane.INFORMATION_MESSAGE);
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		 flip = true;
	        direction = 1;

	        for (Card the : card) {
	            if (the.collision(e.getX(), e.getY())) {
	                selected[count] = the.getIndex();

	                if (count == 0 || (count == 1 && selected[0] != selected[1])) {
	                    index = the.getIndex();
	                    count++;
	                }
	            }
	        }
	    }
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
