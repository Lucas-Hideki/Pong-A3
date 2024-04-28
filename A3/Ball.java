package A3;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball
{

	public int x, y, width = 25, height = 25;

	public int motionX, motionY;

	public Random random;

	private Pong pong;

	public int amountOfHits;

	public Ball(Pong pong)
	{
		this.pong = pong;

		this.random = new Random();

		spawn();
	}

	public void update(Paddle paddle1)
	{
		if (this.y + this.height >= paddle1.y && this.y + this.height <= paddle1.y + motionY && this.x >= paddle1.x && this.x <= paddle1.x + paddle1.width) {
            // Bola quica
            this.motionY *= -1;
            // Reposiciona a bola logo acima da barra
            this.y = paddle1.y - this.height;
        }

        int speed = 5;

        this.x += motionX * speed;
        this.y += motionY * speed;

        // Verifica colisão com as paredes verticais (extremidades da tela)
        if (this.x + width >= pong.width || this.x <= 0) {
            motionX *= -1; // Inverte a direção horizontal
        }

        // Verifica colisão com as paredes horizontais (topo e fundo da tela)
        if (this.y <= 0 || this.y + height >= pong.height) {
            motionY *= -1; // Inverte a direção vertical
        }

		if (checkCollision(paddle1) == 1)
		{
			this.motionX = 1 + (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);
			
			if (motionY == 0)
			{
				motionY = 1;
			}
			
			amountOfHits++;
		}
		
		if (checkCollision(paddle1) == 2)
		{
			paddle1.lifes--;
			spawn();
		}
	}

	public void spawn()
	{
		this.amountOfHits = 0;
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height / 2 - this.height / 2;

		this.motionY = -2 + random.nextInt(4);

		if (motionY == 0)
		{
			motionY = 1;
		}

		if (random.nextBoolean())
		{
			motionX = -1;
		}
		else
		{
			motionX = 1;
		}
	}
	
	public int checkCollision(Paddle paddle)
	{
		if (this.x <= paddle.x + paddle.width && this.x + width >= paddle.x && this.y <= paddle.y + paddle.height && this.y + height >= paddle.y)
		{
			return 1; //bounce
		}
		else if ((paddle.y < y && paddle.paddleNumber == 1) || (paddle.y > y - width && paddle.paddleNumber == 2))
		{
			return 2; //score
		}
		
		return 0; //nothing
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}

}
