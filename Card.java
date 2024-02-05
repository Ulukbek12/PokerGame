package Collections.challenge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Card(Suit suit, String face, int rank) {
    enum Suit{
        CLUB, DIAMOND, HEART, SPADE;

        public char getImage(){
            return new char[]{9827, 9830, 9829, 9824}[this.ordinal()];
        }
    }
    public static Comparator<Card> compareByRankAndSuit(){
        return Comparator.comparing(Card::rank).thenComparing(Card::suit);
    }
    @Override
    public String toString() {
        int index = face.equals("10")?2:1;
        String faceString = face.substring(0,index);
        return "%s%c(%d)".formatted(faceString,suit.getImage(),rank);
    }
    public static Card getNumberCard(Suit suit, int cardNumber){
        if(cardNumber > 1 && cardNumber < 11){
            return new Card(suit,String.valueOf(cardNumber),cardNumber - 2);
        }else{
            System.out.println("Exception");
            return null;
        }
    }
    public static Card getFaceCard(Suit suit, char abbrev){
        int index = "JQKA".indexOf(abbrev);
        if(index > -1){
            return new Card(suit,"" + abbrev, index + 9);
        }
        else{
            System.out.println("Exception");
            return null;
        }
    }
    public static List<Card> getStandardDeck(){
        List<Card> deck = new ArrayList<>(52);
        for(Suit suit : Suit.values()){
            for(int i = 2; i <= 10; i++){
                deck.add(getNumberCard(suit,i));
            }
            for(var c : new char[]{'J','Q','K','A'}){
                deck.add(getFaceCard(suit,c));
            }
        }
        deck.sort(compareByRankAndSuit());
        return deck;
    }
    public static void printDeck(List<Card> deck){
        printDeck(deck,"Standard deck",13);
    }
    public static void printDeck(List<Card> deck, String description, int rows){
        System.out.println("-".repeat(15));
        if(description != null){
            System.out.println(description);
        }
        int cardInRow = deck.size() / rows;
        for(int i = 0; i < rows; i++){
            int start = i * cardInRow;
            int end = start + cardInRow;
            deck.subList(start,end).forEach(c -> System.out.print(" " + c));
            System.out.println();
        }
    }
}
