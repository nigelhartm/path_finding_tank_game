import java.io.Serializable;

@SuppressWarnings("serial")
public class KI implements Runnable, Serializable {
	// Variablen deklarieren
	//
	private KIConnect kicon;

	// Konstruktor
	//
	// Aufgabe: Objekt initialisieren
	//
	public KI(final KIConnect kicon) {
		this.kicon = kicon;
	}

	// getWalls
	//
	// Aufgabe:
	//
	int[][] getWalls(){
		Feld[][] feld = kicon.getFeld();
		int k = 0;		
		for(int j=0;j<kicon.feldGetY();++j){
			for(int i=0;i<kicon.feldGetX();++i){

				if(feld[i][j]instanceof Hindernis)
				{
					k++;
				}
			}
		}
		int[][] mauern = new int [k][2];
		k = 0;
		for(int i=0;i<kicon.feldGetX();++i){
			for(int j=0;j<kicon.feldGetY();++j){

				if(feld[i][j]instanceof Hindernis)
				{
					mauern[k][0] = j;
					mauern[k][1] = i;
					k++;
				}
			}
		}

		return mauern;
	}

	void bewegen(Object[] bewegung)
	{
		for(int i = bewegung.length-1; i>=0; i--)
		{
			
			kicon.setDir(Integer.parseInt((String) bewegung[i]));
			kicon.move();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// run()
	// 
	// Aufgabe: Ausfuehren der KI
	//
	@Override
	public void run() {
		AStar runner = new AStar();
		System.err.println(kicon.feldGetX());
		getWalls();
		bewegen(runner.test(kicon.feldGetY(), kicon.feldGetX(), kicon.spielerYPos(), kicon.spielerXPos(), kicon.zielYPos(), kicon.zielXPos(), getWalls()));
		kicon.reset();
	}
}
