package A3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Paddle
{

	public int paddleNumber;

	public int x, y, width = 400, height = 25;
	
	public int getWidth() {
		return width;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int lifes = 5;

	public Paddle(Pong pong, int paddleNumber)
	{
		this.paddleNumber = paddleNumber;

		if (paddleNumber == 1)
		{
			this.x = pong.width - this.width;
		}

		this.y = pong.height - (height + 10);
	
		
	}

	public void render(Graphics2D g)
	{
		g.setColor(Color.white);
		RoundRectangle2D.Double paddleShape = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
        g.fill(paddleShape);
	}

	public void move(boolean left)
	{
		int speed = 15;

		if (left) {
            if (x - speed > 0) {
                x -= speed;
            } else {
                x = 0;
            }
        } else {
			if (x + width + speed < Pong.pong.width)
			{
				x += speed;
			}
			else
			{
				x = Pong.pong.width - width;
			}
		}
	}

}