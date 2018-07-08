package game.classes;

import javafx.scene.control.Button;

public class ExtendedButton extends Button{
	private Integer value = 0;
	private Integer X = -1, Y = -1;
	private String color;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getX() {
		return X;
	}

	public void setX(Integer x) {
		X = x;
	}

	public Integer getY() {
		return Y;
	}

	public void setY(Integer y) {
		Y = y;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getCords() {
		return "[" + getX() + "; " + getY() + "]";
	}

}
