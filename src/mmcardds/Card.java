package mmcardds;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private String lol;
	private int index;
	private Image backcards; // PHÍA SAU THẺ 

	public Card(String lol, int i) {
		this.width =100;
		this.height = 150;
		this.lol = lol;	
		this.index = i;
		
		setPicture("BG_cards");
	}
	
	  // PHƯƠNG HƯỚNG (TRƯỚC KHI LẬT)
	int direction = 1;
	public boolean flip() {
		
		width -= 30*direction;
			x += 15*direction;
			
			if(width<=0) {
				direction *= -1;
			}
			if(direction == -1 && width ==100) {
				direction =1;
				return false;
			}
			return true;
	}	
				//  VA CHẠM 	//CON TRỎ
	public boolean collision(int x_cursor, int y_cursor) {
	  // THẺ HÌNH CHỮ NHẬT	
		Rectangle cardRect = new Rectangle(x, y, width, height);
		
		return cardRect.contains(x_cursor, y_cursor);
	}	
	
	
	
	public void setPicture(String lol) {
		try {
			backcards = ImageIO.read(new File("./src/images/"+ lol +".jpg"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	
	public Image getPicture() {
		return backcards;
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getLol() {
		return lol;
	}
	public void setLol(String lol) {
		this.lol = lol;
	}
	public int getIndex() {
		return index;
	}

	public boolean isFlipped() {
		
		return false;
	}	
}
