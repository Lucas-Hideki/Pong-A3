package A3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.Timer;

public class Pong extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener 
{
	
	public static Pong pong;

	public int width, height;

	public Renderer renderer;

	public Paddle player1;

	public Ball ball;

	public boolean a, d, left, right;

	public int gameStatus = 0, scoreLimit = 0, playerWon; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over

	public Random random;

	public JFrame jframe;

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Atualiza a posição da barra quando o mouse é arrastado (movido enquanto um botão está pressionado)
		int mouseX = e.getX();
		player1.setX(mouseX - player1.getWidth() / 2); // Centraliza a barra na posição do mouse
		repaint(); // Redesenha o JFrame para refletir a nova posição da barra
	}

	private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

	public Pong()
	{
		Timer timer = new Timer(20, this);
		random = new Random();

		jframe = new JFrame("Pong");
		

		renderer = new Renderer();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height - 55; //para não atravessar a taskbar do windows
		
		jframe.setSize(width, height);
		jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		jframe.addMouseListener(this);
		jframe.addMouseMotionListener(this);
		jframe.addKeyListener(this);

		timer.start();

		jframe.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Atualiza o tamanho preferencial do Renderer para corresponder ao tamanho do JFrame
                renderer.setPreferredSize(new Dimension(jframe.getWidth(), jframe.getHeight()));
                renderer.revalidate(); // Força a atualização do layout
            }
        });
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Invoked when the mouse button has been clicked (pressed and released) on a component
		//System.out.println("You clicked the mouse");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Invoked when a mouse button has been pressed on a component

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Invoked when a mouse button has been released on a component

	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Invoked when the mouse enters a component

		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Invoked when the mouse exits a component

	}


	public void start()
	{
		gameStatus = 2;
		player1 = new Paddle(this, 1);
		ball = new Ball(this);
	}

	public void update()
	{
		if (player1.lifes <= scoreLimit)
		{
			playerWon = 1;
			gameStatus = 3;
		}


		if (a || left)
		{
			player1.move(true);
		}
		if (d || right)
		{
			player1.move(false);
		}

		ball.update(player1);
	}
	
	public void render(Graphics2D g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (gameStatus == 0)
		{
			// g.setColor(Color.WHITE);
			// g.setFont(new Font("Arial", 1, 50));

			// g.drawString("PONG", width / 2 - 75, 50);


			// g.setFont(new Font("Arial", 1, 30));

			// g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
			// // g.drawString("Press Shift to Play with Bot", width / 2 - 200, height / 2 + 25);
			// g.drawString("<< Vidas: 5 >>", width / 2 - 150, height / 2 + 75);,
			BufferedImage imageHome = loadImage("C:\\Users\\lucas\\OneDrive\\Documentos\\Vs projects\\A3\\home.png");

            // Desenhar a imagem na posição (x, y)

				int imgWidth = width -10; // Ajuste conforme necessário
				int imgHeight = height - 10; // Ajuste conforme necessário
                g.drawImage(imageHome,5, 5, imgWidth, imgHeight, null); // Ajuste as coordenadas (x, y) conforme necessário₢
			
		}

		if (gameStatus == 1)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PAUSED", width / 2 - 103, height / 2);
		}

		if (gameStatus == 1 || gameStatus == 2)
		{
			g.setColor(Color.WHITE);

			g.setStroke(new BasicStroke(5f));

			g.drawLine(width, height / 2, 	0, height / 2);

			g.setStroke(new BasicStroke(2f));

			g.drawOval(width / 2 - 150, height / 2 - 150, 300, 300);

			g.setFont(new Font("Arial", 1, 50));

			g.drawString(String.valueOf(player1.lifes), width / 2 - 90, 50);

			player1.render(g);
			ball.render(g);
		}

		if (gameStatus == 3)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PONG", width / 2 - 75, 50);

			if (playerWon != 2)
			{
				g.drawString("GAME OVER", width / 2 - 165, 200);
			}

			g.setFont(new Font("Arial", 1, 30));

			g.drawString("Press Space to Play Again", width / 2 - 185, height / 2 - 25);
			g.drawString("Press ESC for Menu", width / 2 - 140, height / 2 + 25);
		}
		if (gameStatus == 4)
		{
			// g.setColor(Color.WHITE);
			// g.setFont(new Font("Arial", 1, 50));

			// g.drawString("Bem vindo ao pong", width / 2 - 250, 50);

			// g.setFont(new Font("Arial", 1, 30));

			// g.drawString("Regras:", width / 2 - 75, 250);
			// g.drawString("- Faça 200 pontos para passar para a próxima fase", width / 2 - 550, 325);
			// g.drawString("- Na segunda fase, você terá um obstaculo no centro da tela", width / 2 - 550, 375);
			// g.drawString("- Controle a barra e evite deixar a bola passar, caso passe, você perderá 1 vida", width / 2 - 550, 425);
			// g.drawString("- Se você ficar com 0 vidas, você perde", width / 2 - 550, 475);

			
			BufferedImage imageRules = loadImage("C:\\Users\\lucas\\OneDrive\\Documentos\\Vs projects\\A3\\regras.png");
            // Desenhar a imagem na posição (x, y)
            
			int imgWidth = width -10; // Ajuste conforme necessário
			int imgHeight = height - 10; // Ajuste conforme necessário
			g.drawImage(imageRules,5, 5, imgWidth, imgHeight, null); // Ajuste as coordenadas (x, y) conforme necessário₢
			
			}
			
		} 
		
	
     
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (gameStatus == 2)
		{
			update();
		}

		renderer.repaint();
	}

	public static void main(String[] args)
	{
		pong = new Pong();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_A)
		{
			a = true;
		}
		else if (id == KeyEvent.VK_D)
		{
			d = true;
		}
		else if (id == KeyEvent.VK_LEFT)
		{
			left = true;
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			right = true;
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			if (gameStatus == 0)
			{
				scoreLimit--;
			}
		}
		else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3))
		{
			gameStatus = 0;
		}

		else if (id == KeyEvent.VK_SPACE)
		{
			if (gameStatus == 0 || gameStatus == 3)
			{
				gameStatus = 4;
			}
			else if (gameStatus == 1)
			{
				gameStatus = 2;
			}
			else if (gameStatus == 2)
			{
				gameStatus = 1;
			}
			else if (gameStatus == 4)
			{
				gameStatus = 0;
				start();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_A)
		{
			a = false;
		}
		else if (id == KeyEvent.VK_D)
		{
			d = false;
		}
		else if (id == KeyEvent.VK_LEFT)
		{
			left = false;
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
}