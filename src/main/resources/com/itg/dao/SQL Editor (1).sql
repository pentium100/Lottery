select * 
from Matches m, MatchMouth mm where M.id2 = mm.MatchId 
and m.date>='2009 and m.date<=:toDate
and mm.company = 6261
and (mm.euro_final_win =2.1 or mm.euro_final_loss =2.1          
or mm.euro_early_win=2.1 or mm.euro_early_loss = 2.1)  
and mm.asia_final_bs_mouth=6299
