package edu.northeastern.ccs.cs5500.homework4;

import java.util.Comparator;

public class KaComparator implements Comparator<Ka> {

	/* 
	 * Comparator for Ka
	 * 
	 * @see java.util.Comparator#compare(T, T)
	 */
	
	public int compare(Ka dìYīZhangKa, Ka diErZhangKa) {
		
        if (dìYīZhangKa.getRank() < diErZhangKa.getRank())
            return -1; 
        
        if (dìYīZhangKa.getRank() == diErZhangKa.getRank())
            return 0;
        
        return 1;
    }

}
