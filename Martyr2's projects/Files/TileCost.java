import java.util.Scanner;
import java.text.NumberFormat;

public class TileCost
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		System.out.print("Floor width: ");
		int floorWidth = in.nextInt();
		
		System.out.print("Floor height: ");
		int floorHeight = in.nextInt();
		
		System.out.print("Tile width: ");
		double pileWidth = in.nextDouble();

		System.out.print("Tile height: ");
		double pileHeight = in.nextDouble();
		
		System.out.print("Tile cost: ");
		double tileCost = in.nextDouble();
		
		double cost = (((double)floorWidth * (double)floorHeight) / (pileWidth * pileHeight)) * tileCost;
		System.out.println("That would cost you " + NumberFormat.getCurrencyInstance().format(cost));
		in.close();
	}
}
