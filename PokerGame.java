package Collections.challenge;

import java.util.*;
import java.util.function.Consumer;

public class PokerGame {
    private List<Card> deck = Card.getStandardDeck();
    private List<PokerHand> pokerHands;
    private int cardsInHand;
    private int playerCount;
    private List<Card> remainingCards;

    public PokerGame(int cardsInHand, int playerCount) {
        this.cardsInHand = cardsInHand;
        this.playerCount = playerCount;
        pokerHands = new ArrayList<>(playerCount);
    }
    public void startTheGame(){
        Card.printDeck(deck);
        Collections.shuffle(deck);
        int randomMiddle = new Random().nextInt(15,35);
        Collections.rotate(deck,randomMiddle);
        Card.printDeck(deck);

        deal();
        Consumer<PokerHand> checkHand = PokerHand::evalHand;
        pokerHands.forEach(checkHand.andThen(System.out::println));

        int cardsDealt = playerCount * cardsInHand;
        int cardsRemaining = deck.size() - cardsDealt;
        remainingCards = new ArrayList<>(Collections.nCopies(cardsRemaining,null));
        remainingCards.replaceAll(c -> deck.get(cardsDealt + remainingCards.indexOf(c)));
        Card.printDeck(remainingCards,"Remaining cards: ",2);
    }


    public void deal(){
        Card[][] cards = new Card[playerCount][cardsInHand];
        for(int i = 0, deckIndex = 0; i <cardsInHand ; i++){
            for(int j = 0; j < playerCount; j++){
                cards[j][i] = deck.get(deckIndex++);
            }
        }
        int playerNo = 1;
        for(Card[] c : cards){
            pokerHands.add(new PokerHand(playerNo++, Arrays.asList(c)));
        }
    }
}
