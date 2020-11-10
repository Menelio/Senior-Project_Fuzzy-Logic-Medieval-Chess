package Scratch;

import java.util.ArrayList;
import java.util.Collections;

import sp.AI.Move;
import sp.Utils.MoveValueSorter;
import sp.pieces.Piece.PieceType;

public class Testing {

	public static void main(String[] args) {
		String s= "twe";
		String s1= "twe";
		if(s.equals(s1)) {
			System.out.println("Flag");
		}
		
		Move m1 = new Move(0,0,0,0,false, sp.pieces.Piece.PieceType.BISHOP, 3, null, " 3");
		Move m2 = new Move(0,0,0,0,false, sp.pieces.Piece.PieceType.BISHOP, 0, null, " 0");
		Move m3 = new Move(0,0,0,0,false, sp.pieces.Piece.PieceType.BISHOP, 5, null, " 5");
		
		ArrayList<Move> mL = new ArrayList<Move>();
		
		mL.add(m1);
		mL.add(m2);
		mL.add(m3);
		
		
		for(int i=0; i < mL.size();i++) {
			System.out.println(mL.get(i).getPieceID());
			
		}
		mL.sort(new MoveValueSorter());

		System.out.println("----------------------------------------");
		
		for(int i=0; i < mL.size();i++) {
			System.out.println(mL.get(i).getPieceID());
			
		}
		Collections.reverse(mL);
		System.out.println("----------------------------------------");
		
		for(int i=0; i < mL.size();i++) {
			System.out.println(mL.get(i).getPieceID());
			
		}
	}

}
