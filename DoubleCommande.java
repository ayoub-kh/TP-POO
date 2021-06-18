public class DoubleCommande extends Exception{
    public DoubleCommande(){
        super("Interdit dutiliser deux commandes dans une seule ligne");
    }
}
