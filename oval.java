import java.awt.Color;

public class oval{
		int x;
		int y;
		int width;
		int height;
		Color c;
		public oval(int x, int y, int width, int height, Color c){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.c = c;
		}
		
		public Color getColor(){
			return c;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public int getWidth(){
			return width;
		}
		
		public int getHeight(){
			return height;
		}
		
	}
