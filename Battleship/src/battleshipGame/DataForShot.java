package battleshipGame;

import java.util.ArrayList;

public class DataForShot {
	private ArrayList<Cell> cellsAroundShip;
	private Cell shootedCell;
	private boolean isHitShip;
	
	public void setShootedCell(Cell shootedCell) {
		this.shootedCell = shootedCell;
	}
	
	public Cell getShootedCell() {
		return this.shootedCell;
	}
	
	public void setIsHitShip(Boolean isHitShip) {
		this.isHitShip = isHitShip;
	}
	
	public boolean getIsHitShip() {
		return this.isHitShip;
	}
	
	public void setCellsAroundShip(ArrayList<Cell> cellsOpened) {
		this.cellsAroundShip = cellsOpened;
	}
	
	public ArrayList<Cell> getCellsAroundShip() {
		return this.cellsAroundShip;
	}
}
