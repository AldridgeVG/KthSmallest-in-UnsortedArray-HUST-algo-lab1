package WorstCase_On;

import java.util.*;

public class GFG {

    //find mid elem in unsorted array
    static int findMid(int arr[], int i, int n) {
        Arrays.sort(arr, i, i + n);   //quick sort from i to i+n
        return arr[(n + i + i) / 2];
    }


    //swap 2 elems in an array
    static int[] swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    //search int x in arr[l..r] and construct partition around x
    static int partition(int[] arr, int l, int r, int x) {
        int i;
        //find x in array
        for (i = l; i < r; i++) {
            if (arr[i] == x) break;
        }
        //swap x to the right end
        swap(arr, i, r);
        //swap all elems smaller than x to the left, bigger to the right
        //at last i means the number of the smaller and pos of x(start with 0)
        i = l;
        for (int j = l; j < r; j++) {
            if (arr[j] <= x) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, r);
        return i;   //return position of x
    }

    //ASSUME ALL ELEMS ARE UNIQUE
    //worst case:linear time, return kth smallest in arr[l...r]
    //division size : 5
    static int kthSmallest(int arr[], int l, int r, int k) {
        //when k legal do:
        if (k > 0 && k <= r - l + 1) {
            int n = r - l + 1;  //number of elems in arr

            int i;

            //calculate median of each 5 size group and store in median[]
            //all (n+4)/5 groups has 5 elems expect last group
            //separately calculate mid of last group
            int[] median = new int[(n + 4) / 5];
            for (i = 0; i < n / 5; i++) {
                median[i] = findMid(arr, l + i * 5, 5);
            }
            //last group contains less than 5 elems
            if (i * 5 < n) {
                median[i] = findMid(arr, l + i * 5, n % 5);
                i++;
            }
            int tmp = (n + 4) / 5;

            //Arrays.sort(median);
            //int midOfMedians = (i == 1) ? median[i - 1] : median[tmp / 2];

            int midOfMedians = (i == 1)? median[i - 1]:
                    kthSmallest(median, 0, i - 1, i / 2);

            int pos = partition(arr, l, r, midOfMedians);
            if (pos - l == k - 1)
                return arr[pos];    //find the kth smallest
            else if (pos - l > k - 1)
                return kthSmallest(arr, l, pos - 1, k);                    //find in the left half, kth
            else
                return kthSmallest(arr, pos + 1, r, k - 1 - pos + l);   //find in the right half, k-1-(pos-l) th
        }
        return Integer.MAX_VALUE;   //k illegal, return 0x7fffffff
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] arrIn;
        int[] arrPre;
        System.out.println("Welcome to GFG");
        System.out.println("press 1 to input array, press 2 to use internal array, press 0 to quit");
        System.out.println("op: ");
        int op;
        int res, n, k;
        op = in.nextInt();
        while (op > 0) {
            switch (op) {
                case 1:
                    System.out.println("Input array size: ");
                    n = in.nextInt();
                    arrIn = new int[n];
                    for (int i1 = 0; i1 < n; i1++) {
                        System.out.println("Input element " + i1 + ": ");
                        arrIn[i1] = in.nextInt();
                    }
                    System.out.println("array initiated");
                    do {
                        System.out.println("input k: ");
                        k = in.nextInt();
                    } while (k <= 0 || k > n);  //demand correct input
                    res = kthSmallest(arrIn, 0, n - 1, k);
                    System.out.println("the " + k + "th smallest element is " + res);
                    break;
                case 2:
                    System.out.println("Internal Array: {3,4,2,6,7,1,8,9,5,0}");
                    arrPre = new int[]{3, 4, 2, 6, 7, 1, 8, 9, 5, 0};
                    n = arrPre.length;
                    do {
                        System.out.println("input k: ");
                        k = in.nextInt();
                    } while (k <= 0 || k > n);  //demand correct input
                    res = kthSmallest(arrPre, 0, n - 1, k);
                    System.out.println("the " + k + "th smallest element is " + res);
                    break;
                default:
                    break;
            }
            do {
                System.out.println("press 1 to reinput array, press 2 to use internal array, press 0 to quit");
                System.out.println("op: ");
                op = in.nextInt();
            } while (op != 0 && op != 1 && op != 2);   //demand correct input
        }
        System.out.println("System quit");
    }
}
