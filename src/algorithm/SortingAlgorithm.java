/***********************************************************************
 * Module:  SortingAlgorithm.java
 * Author:  Milica Milosevic, Boris Boskovic
 * Purpose: Definise interfejs SortingAlgorithm. Potrebno je da ga
 * 			implementiraju konkretni algoritmi da bi mogli biti koristeni
 * 			u modelima testova
 ***********************************************************************/

package algorithm;

import java.util.ArrayList;

public interface SortingAlgorithm {
   
	/**
    * Funkcija vrsi sortiranje niza
    * @param args - Opcionalni argumenti
    * @return Trajanje sortiranja u milisekundama
    */
   public long sort(String[] args);
   
   /**
    * @return niz koji se sortira
    */
   public ArrayList<Integer> getArray();
   
   /**
    * Postavljanje niza
    * @param array - niz koji se prosljedjuje
    */
   public void setArray(ArrayList<Integer> array);

   /**
    * @return sortiranje jeste/nije zavrseno
    */
   public boolean isDone();
   
}