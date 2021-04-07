package problema;

public class Solution {
    String s;

    // numarul de adaugari care sunt necesare
    int numaradaugari;

    // numarul de stergeri care sunt necesare
    int numarStergeri;

    // secventele pe care le contorizam
    int[] seq;


    public  int strongPasswordChecker(String s) {
        if (s == null || s.equals("")) return 6;
        this.s = s;

        // Initializam variabilele
        numaradaugari = 0;
        numarStergeri = 0;
        seq = new int[s.length() + 1];

        // Contorizam numarul de  "adaugari" si frecventa secventelor intalnite
        readString();

        // Folosirea stergerilor pentru a minimiza pauzele necesare
        if (s.length() > 20) spendDeletions();

        // numarul de pauze de secventa necesare
        int numBreaks = 0;
        for (int i = 3; i < seq.length; i++) {
            numBreaks += seq[i] * (i / 3);
        }

        // consolidarea pauzelor ,adaugarilor si modificarilor
        int numChanges = Math.max(numBreaks, numaradaugari);

        // pentru secvente scurte ,este necesara consolidarea prin insertii si modificari.
        if (s.length() < 6) {
            int numInsertions = 6 - s.length();
            numChanges = Math.max(numInsertions, numChanges);
        }

        // Pentru secvente prea lungi, adaugam numarul de stergeri si adaugari necesare
        // in variabila numChanges.
        if (s.length() > 20) {
            numChanges = numarStergeri + numChanges;
        }

        return numChanges;
    }

    /**
     Se proceseaza sirul dat stocand daca sirul indeplineste cerintele si anume daca se repeta anumite caractere si stocheaza
     secventele de caractere repetate si daca lungimea secventei are mai mult de 3 caractere
     */
    private void readString() {
        boolean needsNumber = true;
        boolean needsUpper = true;
        boolean needsLower = true;

        // Lungimea secventei
        int c = 1;
        char tmp = s.charAt(0);
        for (int i = 0; i < s.length(); i++) {
            if (i > 0) {
                // Secventa continua
                if (s.charAt(i) == tmp) c++;

                    // Sfarsitul secventei
                else {
                    if (c > 2) seq[c]++;
                    c = 1;
                    tmp = s.charAt(i);
                }
            }
            if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') needsLower = false;
            else if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') needsUpper = false;
            else if (s.charAt(i) >= '0' && s.charAt(i) <= '9') needsNumber = false;
        }

        // gestionarea secventelor lungi care continua pana la sfarsitul sirului dat.
        if (c > 2) seq[c]++;

        if (needsLower) numaradaugari++;
        if (needsUpper) numaradaugari++;
        if (needsNumber) numaradaugari++;
    }

   //Stergerile
    private void spendDeletions() {
        numarStergeri = s.length() - 20;
        int ndtemp = numarStergeri;
        int lastThreeMult = 3 * ((seq.length - 1) / 3);
        for (int i = lastThreeMult; i < lastThreeMult + 3; i++) {

            int j = (i >= seq.length) ? i - 3: i;
            while (j > 2 && ndtemp > 0) {
                if (seq[j] > 0) {

                    // Avem o secventa mai mica de lungime j
                    seq[j]--;

                    /*
                     * Stabilim daca mai avem suficiente stergeri
                     * pentru a reduce numarul de secvente necesare

                     * daca nu mai avem stergeri, le folosim doar cele pe care le aveam.

                     */


                    int d = Math.min((i % 3) + 1, ndtemp);

                    // avem inca o secventa de lungime (j-d)
                    seq[j-d]++;

                    // analizam daca mai avem stergeri ramase
                    ndtemp -= d;
                }
                else j -= 3;
            }
        }
    }


    public static void main(String[] args) {
        Solution sol = new Solution();

        System.out.println(sol.strongPasswordChecker(""));
        // 6 insertions

        System.out.println(sol.strongPasswordChecker("0123456789"));
        // 2 changes or additions; needsLower = true; needsUpper = true;

        System.out.println(sol.strongPasswordChecker("abcdefghijklmnopqrstuvwxyz"));
        // 8; 6 deletions and 2 changes; needsUpper = true; needsNumber = true;

        System.out.println(sol.strongPasswordChecker("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        // 8; 6 deletions and 2 changes; needsNumber = true; needsLower = true;

        System.out.println(sol.strongPasswordChecker("aaaaA"));
        // 1 e.g. add a number at 2

        System.out.println(sol.strongPasswordChecker("aaaaa"));
        // 2 e.g. add a number at 2, change 4 to capital

        System.out.println(sol.strongPasswordChecker("aaaaaaaaaaaaaaaaaaaaa"));
        // 7 e.g. one deletion, one change to capital, one change to number, and 5 changes from 'a'

        System.out.println(sol.strongPasswordChecker("$$$"));
        // 3 e.g. three insertions (which can satisfy requirements, and break sequence)

        System.out.println(sol.strongPasswordChecker("Aa1Aa1Aa1Aa1Aa1Aa1zzAa1Aa1Aa1Aa1Aa1Aa1zzAa1Aa1Aa1Aa1Aa1Aa1zz"));
        // 40 (e.g. 40 deletions)

        System.out.println(sol.strongPasswordChecker("ABABABABABABABABABAB1"));
        // 2

        System.out.println(sol.strongPasswordChecker("aaaaaaaaaaaaaaaaaaaaa"));
        // 7

        System.out.println(sol.strongPasswordChecker("1010101010aaaB10101010"));
        // 2

        System.out.println(sol.strongPasswordChecker("aaaabbaaabbaaa123456A"));
        // 3

        System.out.println(sol.strongPasswordChecker("aaa111"));
        // 2

        System.out.println(sol.strongPasswordChecker("AAAAAABBBBBB123456789a"));
        // 4

        System.out.println(sol.strongPasswordChecker("aaaaabbbbbccccccddddddA1"));
        // 8

        System.out.println(sol.strongPasswordChecker("aaaaaa1234567890123Ubefg"));
        // 4




    }

}