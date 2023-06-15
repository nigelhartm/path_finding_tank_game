import java.util.*;

import javax.swing.JOptionPane;
public class AStar {
	public  final int V_H_COST = 10;

	class Zelle{  
		int heuristicCost = 0; //Heuristic cost
		int finalCost = 0; //G+H
		int y, x;
		Zelle parent; 

		Zelle(int ZelleY, int ZelleX){
			this.y = ZelleY;
			this.x = ZelleX; 
		}

		@Override
		public String toString(){
			return "["+this.y+", "+this.x+"]";
		}
	}

	//Blocked cells are just null Cell values in grid
	Zelle [][] feld = new Zelle[5][5];

	PriorityQueue<Zelle> open;

	boolean closed[][];
	int yStart, xStart;
	int yZiel, xZiel;

	public  void setMauer(int MauerY, int MauerX){
		feld[MauerY][MauerX] = null;
	}

	public  void setStartPunkt(int StartPunktY, int StartPunktX){
		yStart = StartPunktY;
		xStart = StartPunktX;
	}

	public  void setZielPunkt(int ZielPunktY, int ZielPunktX){
		yZiel = ZielPunktY;
		xZiel = ZielPunktX; 
	}

	void checkAndUpdateCost(Zelle current, Zelle t, int cost){
		if(t == null || closed[t.y][t.x])return;
		int t_final_cost = t.heuristicCost+cost;

		boolean inOpen = open.contains(t);
		if(!inOpen || t_final_cost<t.finalCost){
			t.finalCost = t_final_cost;
			t.parent = current;
			if(!inOpen)open.add(t);
		}
	}

	public  void suchen(){ 

		//add the start location to open list.
		open.add(feld[yStart][xStart]);

		Zelle current;

		while(true){ 
			current = open.poll();
			if(current==null)break;
			closed[current.y][current.x]=true; 

			if(current.equals(feld[yZiel][xZiel])){
				return; 
			} 

			Zelle t;  
			if(current.y-1>=0){
				t = feld[current.y-1][current.x];
				checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
			} 

			if(current.x-1>=0){
				t = feld[current.y][current.x-1];
				checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
			}

			if(current.x+1<feld[0].length){
				t = feld[current.y][current.x+1];
				checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
			}

			if(current.y+1<feld.length){
				t = feld[current.y+1][current.x];
				checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
			}
		} 
	}

	/*
    Params :
    feldY, feldX = Feldgröße
    startY, startX = Koordinaten des Startpunktes
    zielY, zielX = Koordinaten des Zielpunktes
    int[][] mauer = array mit den Koordinaten der Mauern
	 */
	public  Object[] test(int feldY, int feldX, int startY, int startX, int zielY, int zielX, int[][] mauer){
		//Reset
		feld = new Zelle[feldY][feldX];
		closed = new boolean[feldY][feldX];
		open = new PriorityQueue<>((Object o1, Object o2) -> {
			Zelle c1 = (Zelle)o1;
			Zelle c2 = (Zelle)o2;

			return c1.finalCost<c2.finalCost?-1:
				c1.finalCost>c2.finalCost?1:0;
		});
		//Set start position
		setStartPunkt(startY, startX);  //Setting to 0,0 by default. Will be useful for the UI part

		//Set End Location
		setZielPunkt(zielY, zielX); 

		for(int y=0;y<feldY;++y){
			for(int x=0;x<feldX;++x){
				feld[y][x] = new Zelle(y, x);
				feld[y][x].heuristicCost = Math.abs(y-yZiel)+Math.abs(x-xZiel);
				//                  System.out.print(grid[i][j].heuristicCost+" ");
			}
			//              System.out.println();
		}
		feld[startY][startX].finalCost = 0;

		/*
             Set blocked cells. Simply set the cell values to null
             for blocked cells.
		 */
		for(int m=0;m<mauer.length;++m){
			setMauer(mauer[m][0], mauer[m][1]);
		}


		suchen(); 

		if(closed[yZiel][xZiel]){
			//Trace back the path 
			Zelle current = feld[yZiel][xZiel];
			ArrayList<String> bewegung = new ArrayList<>();
			while(current.parent!=null){
				String dir = null;
				switch(current.parent.x-current.x)
				{
				case -1: dir = "2";
				break;
				case  1: dir = "4";
				break;
				}
				switch (current.parent.y-current.y) {
				case -1: dir = "3";
				break;
				case  1: dir = "1";
				break;
				}

				bewegung.add(dir);
				//System.out.println(dir);
				current = current.parent;
			} 
			return  bewegung.toArray();
		}
		else {
			JOptionPane.showMessageDialog(null, "Es gibt keinen Weg.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

}