package com.aspire.util;

public class FileUtils {

	public static int getTeamRanking(String oldRanking){
		
		String ranking = oldRanking.replace("[", "").replace("]", "");
		if("".equals(ranking)||"-".equals(ranking)){
			return -1;
		}
		return Integer.valueOf(ranking);
	}
	
	public static String parseNewOdds(String odds){
		
		String new_odds = odds.replace("↓", "").replace("↑", "");
		if("".equals(new_odds)){
			return "-1.00";
		}
		return new_odds;
	}
}
