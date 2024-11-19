import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class Pong extends KeyAdapter implements ActionListener{
	JFrame window;
	Rectangle pad1, pad2;
	DrawPanel pan;
	Ball b;
	Timer t;
	int tSpeed = 10;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Pong();
			}
		});
	}
	
	Pong(){
		window = new JFrame();
		window.setTitle("Pong");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
	
		pan = new DrawPanel();
		pad1 = new Rectangle(450, 10, 100, 20);
		pad2 = new Rectangle(450, 500, 100, 20);
		
		b = new Ball();
		
		window.addKeyListener(this);
		
		window.add(pan);
		window.pack();
		window.setVisible(true);
		
		t = new Timer(tSpeed, this);
		t.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		b.move();
		if (b.x == 0) {
			t.stop();
			JOptionPane.showMessageDialog(null, "Player 2 Wins!");
			window.dispose();
		} else if (b.x == pan.panW - b.width ) {
			t.stop();
			JOptionPane.showMessageDialog(null, "Player 1 Wins!");
			window.dispose();
		}
		pan.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			pad2.y -= 30;
			if (pad2.y < 0) pad2.y = 0;
		} else if (key == KeyEvent.VK_DOWN) {
			pad2.y += 30;
			if (pad2.y > pan.panH - pad2.height) pad2.y = pan.panH - pad2.height;
		} 
		pan.repaint();
		if (key == KeyEvent.VK_W) {
			pad1.y -= 30;
			if (pad1.y < 0) pad1.y = 0;
		} else if (key == KeyEvent.VK_S) {
			pad1.y += 30;
			if (pad1.y > pan.panH - pad1.height) pad1.y = pan.panH - pad1.height;
		}
		pan.repaint();
	}
	
	class DrawPanel extends JPanel{
		int panW, panH;
		
		DrawPanel() {
			panW = 1000;
			panH = 600;
			this.setPreferredSize(new Dimension(panW, panH));
			this.setBackground(Color.black);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			//draw the ball
			g2.setPaint(Color.white);
			g2.fillOval(b.x,  b.y,  b.width,  b.height);
			
			//draw the paddles
			g2.fillRect(pad1.x,  pad1.y,  pad1.width,  pad1.height);
			g2.fillRect(pad2.x,  pad2.y,  pad2.width,  pad2.height);
		}
	}
	
	class Ball extends Rectangle {
		double xx, yy;
		double vx = 3.0, vy = 3.0;
		
		Ball() {
			x = pan.panW/2;
			y = pan.panH/2;
			width = 50;
			height = 50;
			xx = (double)x;
			yy = (double)y;
			
		}
		
		public void move() {
			xx += vx;
			yy += vy;
			checkCollision();
			if (xx > pan.panW - width) {
				xx = (double)(pan.panW - width);
				vx = - vx;
			} else if (xx < 0.0) {
				xx = 0.0;
				vx = -vx;
			}
			if (yy > pan.panH - height) {
				yy = (double)(pan.panH - height);
				vy = - vy;
			} else if (yy < 0.0) {
				yy = 0.0;
				vy = -vy;
			}
			x = (int)xx;
			y = (int)yy;
		}
		
		public void checkCollision() {
			double leftX = xx;
			double rightX = xx + (double)width;
			double mid = yy + (double)(height/2);
			if (pad1.contains(leftX, mid)) {
				xx = (double)(pad1.x + pad1.width);
				vx = -vx;
			} else if (pad2.contains(rightX, mid)) {
				xx = (double)pad2.x;
				vx = -vx;
			}
		}
	}

}
