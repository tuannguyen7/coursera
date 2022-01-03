import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BinaryKnapsack {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int w = in.nextInt();

        // your code
        int maxValue = 0;
        List<Thing> things = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            things.add(new Thing(in.nextInt(), in.nextInt()));
        }

        List<Integer> slots = new ArrayList<>();
        String binaryW = new StringBuilder(Integer.toBinaryString(w)).reverse().toString();
        for (int i = 0; i < binaryW.length(); i++) {
            if (binaryW.charAt(i) == '1') {
                slots.add(i);
            }
        }
        things.sort(new Comparator<Thing>() {
            @Override
            public int compare(Thing t1, Thing t2) {
                if (t1.w > t2.w) {
                    return 1;
                } else if (t1.w < t2.w) {
                    return -1;
                } else {
                    return t2.v - t1.v;
                }
            }
        });

        int thingI = 0;
        for (int slot : slots) {
            int curMax = 0;
            int smallerThingIndex = 0;
            int slotW = (int)Math.pow(2, slot);
            List<Thing> smallerThings = new ArrayList<>();
            for (Thing t : things) {
                if (t.w > slotW) {
                    break;
                }
                smallerThings.add(t);
            }
            List<Thing> mergedThings = new ArrayList<>();
            Thing merged = new Thing(0, 0);
            for (; smallerThingIndex < smallerThings.size(); smallerThingIndex++) {
                Thing cur = smallerThings.get(smallerThingIndex);
                if (cur.w >= merged.w && cur.w + merged.w <= slotW) {
                    merged = new Thing(cur.w + merged.w, cur.v + merged.v);
                } else {
                    mergedThings.add(cur);
                }
            }
            mergedThings.add(merged);

        }

        out.println(maxValue);
        in.close();
        out.close();
    }
}

class Thing {
    int w;
    int v;

    public Thing(int w, int v) {
        this.v = v;
        this.w = w;
    }
}
