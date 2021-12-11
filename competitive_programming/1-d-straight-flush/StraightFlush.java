import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

public class StraightFlush {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String[] cards = new String[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = in.next();
        }
        boolean result = checkFlush(cards);
        if (result)
            out.println("YES");
        else
            out.println("NO");
        in.close();
        out.close();
    }

    private static boolean checkFlush(String[] cards) {
        List<Integer> cardNumbers = new ArrayList<>();
        char kind = cards[0].charAt(1);
        for (String card : cards) {
            if (card.charAt(1) != kind) {
                return false; // not a same kind
            }
            if (card.charAt(0) == 'A') {
                cardNumbers.add(14);
                cardNumbers.add(1);
            } else {
                cardNumbers.add(toNumber(card.charAt(0)));
            }
        }
        cardNumbers.sort(Integer::compareTo);
        boolean check1 = true;
        for (int i = 0; i < 4; i++) {
            if (cardNumbers.get(i+1) - cardNumbers.get(i) != 1) {
                check1 = false;
                break;
            }
        }
        if (check1 || cardNumbers.size() == 5)
            return check1;

        for (int i = 1; i < 5; i++) {
            if (cardNumbers.get(i+1) - cardNumbers.get(i) != 1) {
                return false;
            }
        }
        return true;
    }

    private static int toNumber(char c) {
        switch (c) {
            case 'K':
                return 13;
            case 'Q':
                return 12;
            case 'J':
                return 11;
            case 'T':
                return 10;
            default:
                return c - '0';
        }
    }
}
