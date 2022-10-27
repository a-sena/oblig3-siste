package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {

        //KOPIERER KODE 5.2.3
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi,q);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;// høyre barn til q

        //-----OPPGAVE-1 START-----
        if (q != null)
            p.forelder = q;

        else p.forelder = null;
        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging

        //-------------OPPGAVE1 FERDIG-------------
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    //---------OPPGAVE2 STARTER-------------------
    public int antall(T verdi) {
        //Hvis verdi ikke  er i treet, skal metoden returnere 0

        int antallForekomst =0; //teller

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else {  p = p.høyre;
                antallForekomst++;

            }
        }
        return antallForekomst;
    }
    //---------OPPGAVE2 FERDIG-------------------
    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }
    //-----------OPPGAVE3 STARTER----------------------
    private static <T> Node<T> førstePostorden(Node<T> p) {
        while (true) {
            if (p.venstre != null) {p = p.venstre;} //hvis venstrebarn finnes
            else if (p.høyre != null) {p = p.høyre;}    //hvis høyre barn finnes
            else {return p;}    //har nå kommet til første node i post-orden
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        if(p.forelder==null){
            return null;
        }

        else if(p==p.forelder.høyre){
            return p.forelder;
        }

        else if(p==p.forelder.venstre){

            if(p.forelder.høyre==null){ return p.forelder;}


            else {
                return førstePostorden(p.forelder.høyre);

            }
        }
        return null;
    }
//-----------OPPGAVE 3 FERDIG------------------------------------------------


    //-----------OPPGAVE 4 START-----------------------------------------------
    public void postorden(Oppgave<? super T> oppgave) {
        //FIKK HJELP FRA KILDEKODE 1.5.7 h
        Node <T> p = førstePostorden(rot);
        while (p!=null){
            oppgave.utførOppgave(p.verdi);
            p=nestePostorden(p);
        }
    }
    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        //JEG FIKK HJELP FRA OPPGAVE 4 I AVSNITT 5.1.7
        oppgave.utførOppgave(p.verdi);
        if (p.venstre!=null){   //går inn i venstre barn
            postordenRecursive(p.venstre,oppgave);
        }
        if(p.høyre!=null){  //Går inn i høyre barn
            postordenRecursive(p.høyre,oppgave);
        }



    }
    //-----------OPPGAVE 4 FERDIG------------------------------------------------
    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
