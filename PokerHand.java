package Collections.challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerHand {
    private List<Card> cards;
    private List<Card> discards;
    private List<Card> keepers;
    private int playerNo;
    private Ranking score = Ranking.NONE;

    @Override
    public String toString() {
        return "%d. %-16s Rank:%d%-40s Best:%-7s Worst: %-6s %s".formatted(
                playerNo,score,score.ordinal(),cards,
                Collections.max(cards, Comparator.comparing(Card::rank)),
                Collections.min(cards,Comparator.comparing(Card::rank)),
                (!discards.isEmpty()? "Discards: " + discards: "")
        );
    }

    public PokerHand(int playerNo,List<Card> cards)  {
        this.cards = cards;
        this.playerNo = playerNo;
        discards = new ArrayList<>();
        keepers = new ArrayList<>();
    }
    public void setRank(int faceCount){
        switch (faceCount){
            case 4 ->{
               score = Ranking.FOUR_OF_KIND;
            }
            case 3 ->{
                if(score == Ranking.NONE) score = Ranking.THREE_OF_KIND;
                else score = Ranking.FULL_HOUSE;
            }
            case 2 ->{
                if(score == Ranking.NONE) score = Ranking.ONE_PAIR;
                else if(score == Ranking.THREE_OF_KIND) score = Ranking.FULL_HOUSE;
                else score = Ranking.TWO_PAIR;
            }
        }
    }
    public void evalHand(){
        List<String> faceList = new ArrayList<>();
        cards.forEach(card -> faceList.add(card.face()));
        List<String> duplicatedFaces = new ArrayList<>();
        faceList.forEach(face ->{
                if(!duplicatedFaces.contains(face) && Collections.frequency(faceList,face) > 1){
                duplicatedFaces.add(face);
            }
        });
        for( String duplicateFace : duplicatedFaces){
            int start = faceList.indexOf(duplicateFace);
            int end = faceList.lastIndexOf(duplicateFace);
            setRank(end - start + 1);
            List<Card> sub = cards.subList(start, end + 1);
            keepers.addAll(sub);
        }
        pickDiscard();
    }
    public void pickDiscard(){
        List<Card> temp = new ArrayList<>(cards);
        temp.removeAll(keepers);
        int rankedCards = keepers.size();
        Collections.reverse(temp);
        int index = 0;
        for(Card c : temp) {
            if (index++ < 3 && (rankedCards > 2 || c.rank() < 9)){
                discards.add(c);
            }else{
                keepers.add(c);
            }
        }
    }
}
