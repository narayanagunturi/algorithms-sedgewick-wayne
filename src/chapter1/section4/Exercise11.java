package chapter1.section4;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by rene on 9/28/16.
 */
public class Exercise11 {

    public static void main(String[] args){
        int[] array = {2, 4, 8, 16, 16, 16, 32, 64, 128, 128};
        Exercise11 exercise11 = new Exercise11(array);

        StdOut.println("How many 2: "  + exercise11.howMany(2)); //Expected: 1
        StdOut.println("How many 16: "  + exercise11.howMany(16)); //Expected: 3
        StdOut.println("How many 128: "  + exercise11.howMany(128)); //Expected: 2
        StdOut.println("How many -99: "  + exercise11.howMany(-99)); //Expected: 0
    }

    private int[] a;

    public Exercise11(int[] keys) {
        a = new int[keys.length];
        for(int i=0; i<keys.length; i++) {
            a[i] = keys[i]; //defensive copy
        }
        Arrays.sort(a);
    }

    public boolean contains(int key) {
        return rank(key) != -1;
    }

    private int rank(int key) {
        //Binary search
        int lo = 0;
        int hi = a.length - 1;

        while(lo <= hi) {
            //Key is in a[lo..hi] or not present
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    private int recursiveRank(int key, int low, int high) {
        int middle = low + (high - low) / 2;

        if(low > high) {
            return -1;
        }

        if(a[middle] > key) {
            return recursiveRank(key, low, middle-1);
        } else if(a[middle] < key) {
            return recursiveRank(key, middle+1, high);
        } else {
            return middle;
        }
    }

    //O(log n)
    private int howMany(int key) {
        int indexFromRank = rank(key);

        if(indexFromRank == -1) {
            return 0;
        }

        int count;
        int previousIndex = indexFromRank;
        int currentPreviousIndex = previousIndex;
        int nextIndex = indexFromRank;
        int currentNextIndex = nextIndex;

        //Find the smallest index of an element
        while(currentPreviousIndex != -1) {
            currentPreviousIndex = recursiveRank(key, 0, currentPreviousIndex-1);

            if(currentPreviousIndex != -1) {
                previousIndex = currentPreviousIndex;
            }
        }

        //Find the highest index of an element
        while(currentNextIndex != -1) {
            currentNextIndex = recursiveRank(key, currentNextIndex+1, a.length-1);

            if(currentNextIndex != -1) {
                nextIndex = currentNextIndex;
            }
        }

        count = nextIndex - previousIndex + 1;

        //O(n)
        //count would have been initialized to 1
//        while(previousIndex - 1 >= 0 && a[previousIndex - 1] == key) {
//            count++;
//            previousIndex--;
//        }
//
//        while(nextIndex + 1 < a.length && a[nextIndex + 1] == key) {
//            count++;
//            nextIndex++;
//        }

        return count;
    }
}
