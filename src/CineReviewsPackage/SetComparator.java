package CineReviewsPackage;

import CineReviewsPackage.Shows.Artist;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

public class SetComparator implements Comparator<SortedSet<Artist>> {
    @Override
    public int compare(SortedSet<Artist> o1, SortedSet<Artist> o2) {
        int comp = o2.size() - o1.size();
        if (comp == 0) {
            Iterator<Artist> it1 = o1.iterator();
            Iterator<Artist> it2 = o2.iterator();
            while (it1.hasNext() && it2.hasNext() && comp == 0)
                comp = it1.next().getName().compareTo(it2.next().getName());
        }
        return comp;
    }
}