package com.company;

import java.util.HashMap;

/**
 * permet d'�valuer des expressions arithm�tiques. Gere que les op�rations de base (plus,moins, multiplier,
 * diviser,parentheses). Gere aussi les variables, pour ajouter une variable faire addVar<br>
 * Gere les fonctions a 1 parametre, fonctions possibles : sin,cos<br>
 * EXEMPLE :<br>
 * sin(x)+2.2*5+cos(2*x)
 * <br>
 * Se base sur la th�orie des langages! utilise un analyseur syntaxique qui permet de calculer une expression a partir d'une grammaire.<br>
 * Pour �valuer l'expression, on utilise une pile.<br>
 * Voici la grammaire :<br>
 *  S' -> E<br>
 *  E -> TE'<br>
 *  E' -> +TE' <br>
 *  E' -> -TE' <br>
 *  E' -> $<br>
 *  T -> FT'<br>
 *  T' -> *FT'<br>
 *  T' -> /FT'<br>
 *  T' -> $<br>
 *  F -> id<br> 
 *  F -> id(param)<br>
 *  F -> (E)<br>
 *<br>
 *<br>
 * <br>
 * La grammaire et la source ont �t� inspir� du cours de M. Krajecki disponible a l'adresse suivante :<br>
 * <a href="http://cosy.univ-reims.fr/~mkrajecki/enseignement/info62/info62Chap3.pdf">http://cosy.univ-reims.fr/~mkrajecki/enseignement/info62/info62Chap3.pdf</a><br>
 *
 * @author Guillaume Bouchon (bouchon_guillaume@yahoo.fr)
 */
public class Evalueur
{
    public static final byte ERROR_NO=0; /** pas d'erreur*/
    public static final byte ERROR_BAD_SYNTAX=1; /*** erreur de syntaxe*/
    private String  expr; /*** la chaine en cours*/
    private int pos=-1; /*** position en cours*/
    private char    lu;/*** caractere en cours dans l'expression*/
    private HashMap<String,Double>  vars=new    HashMap(); /*** les variables*/
    private byte     erreur=ERROR_NO; /*** erreur*/
    private double stack[]=new double[100]; /*** pile (permet de calculer)*/
    private Fonction    stackF[]=new    Fonction[100]; /*** pile des fonctions a appliquer*/
    private int stack_pos=stack.length-1; /*** position dans la pile*/
    private int stackF_pos=stackF.length-1; /*** position dans la pile des fonctions*/


    public Evalueur() /*** Constructeur de Evalueur*/
    {
        
    }
    
    private void    empileF(Fonction v) /*** empile une fonction (empile au sommet)*/
    {
        if (stackF_pos==0)   {erreur=ERROR_BAD_SYNTAX; return;}
        stackF[stackF_pos]=v;
        stackF_pos--;
    }
    
    private Fonction     depileF() /*** depile une valeur de la pile (donne la valeur du sommet de la pile)*/
    {
        if (stackF_pos==stackF.length-1)   {erreur=ERROR_BAD_SYNTAX; return null;}
        stackF_pos++;
        return  stackF[stackF_pos];
    }
    
    private void    empile(double v) /*** empile une valeur (empile au sommet)*/
    {
        if (stack_pos==0)   {erreur=ERROR_BAD_SYNTAX; return;}
        stack[stack_pos]=v;
        stack_pos--;
    }
    
    private double     depile() /*** depile une valeur de la pile (donne la valeur du sommet de la pile)*/
    {
        if (stack_pos==stack.length-1)   {erreur=ERROR_BAD_SYNTAX; return 0;}
        stack_pos++;
        return  stack[stack_pos];
    }
    
    /**
     * evalue une expression
     * @param expr l'expression a �valuer (sans espaces)
     * @return le resultat de l'expression (v�rifier que getError() ne renvoi pas une erreur)
     */
    public double  evalue(String expr) throws ErreurSyntaxeException, VariableInconnueException, FonctionInconnueException, ErreurException, ParenthèseOuvranteManquanteException, ParenthèseFermanteManquanteException {
        erreur=ERROR_NO;
        this.expr=expr;
        //se remplace en haut de la pile
        stack_pos=stack.length-1;
        stackF_pos=stackF.length-1;
        stack[stack.length-1]=0;
        stackF[stack.length-1]=null;
        
        //au debut de l'expression
        pos=-1;
        avance();
        
        //lance l'automate
        Sprime();

        //execute sur la pile de fonctions
        //System.out.println("expr="+expr);
        for(int i=stackF.length-1;i>stackF_pos && stackF[i]!=null;i--)
        {
            //System.out.println(stackF[i]);
            stackF[i].evalue();
        }
        
        //le sommet de la pile contient le resultat (si pas d'erreur!)
        return  stack[stack.length-1];  
    }
    
    /**
     * re�valu l'expression en cours (permet d'optimiser car ne regenere pas toute l'expression)<br>
     * prends en compte les changements de valeurs des variables<br>
     * Utilisation :<br>
     * on genere l'expression avec evalue la 1ere fois<br>
     * ensuite on peut utiliser reevalue si l'expression n'a pas chang� (les variables peuvent avoir chang�s)
     */
    public  double  reevalue()
    {
        //se remplace en haut de la pile
        stack_pos=stack.length-1;
        stack[stack.length-1]=0;
        
         //execute sur la pile de fonctions
        //System.out.println("expr="+expr);
        for(int i=stackF.length-1;i>stackF_pos && stackF[i]!=null;i--)
        {
            //System.out.println(stackF[i]);
            stackF[i].evalue();
        }
        
        //le sommet de la pile contient le resultat (si pas d'erreur!)
        return  stack[stack.length-1];  
    }

    private void    avance() throws ErreurException /*** avance dans l'expression*/
    {
        if (erreur!=ERROR_NO)   throw new ErreurException();
        lu=' ';
        while(lu==' ')  //enleve tous les espaces
        {
            pos++;
            if (pos>=expr.length()) 
                lu=0;
            else
                lu=expr.charAt(pos);
        }
    }
  
    private void Sprime() throws ErreurSyntaxeException, VariableInconnueException, FonctionInconnueException, ErreurException, ParenthèseOuvranteManquanteException, ParenthèseFermanteManquanteException /*** S'->E*/
    {
        if (erreur!=ERROR_NO)   throw new ErreurException();
        E();
        //if (lu!=0)  //pas bon
            //erreur=ERROR_BAD_SYNTAX;
    }
    
    private void    E() throws ErreurSyntaxeException, VariableInconnueException, FonctionInconnueException, ErreurException, ParenthèseOuvranteManquanteException, ParenthèseFermanteManquanteException /*** E->TE'*/
    {
        if (erreur!=ERROR_NO)   throw new ErreurException();
        
        if (lu=='(' || (lu>='0' && lu<='9') || (lu>='a' && lu<='z') || (lu>='A' && lu<='Z') )
        {
            T(); Eprime();
        }else   throw new ParenthèseOuvranteManquanteException();
    }

    private void Eprime() throws ErreurSyntaxeException, VariableInconnueException, FonctionInconnueException, ErreurException, ParenthèseOuvranteManquanteException, ParenthèseFermanteManquanteException /*** E'->+TE' | -TE' | $*/
    {
        if (erreur!=ERROR_NO)   throw new ErreurException();
        
        if (lu=='+')
        {
            avance(); T(); empileF(new FonctionPlus()); Eprime();
            
        }else if (lu=='-')
        {
            avance();  T();  empileF(new FonctionMoins()); Eprime();
            
        }
    }
    
    private void T() throws ErreurSyntaxeException, VariableInconnueException, FonctionInconnueException, ErreurException, ParenthèseOuvranteManquanteException, ParenthèseFermanteManquanteException /*** T->FT'*/
    {
        if (erreur!=ERROR_NO)   throw new ErreurException();
        
        if (lu=='(' || (lu>='0' && lu<='9') || (lu>='a' && lu<='z') || (lu>='A' && lu<='Z'))
        {
            F(); Tprime();
        }else   throw new ErreurSyntaxeException();
    }

    private void Tprime() throws ErreurSyntaxeException, VariableInconnueException, FonctionInconnueException, ErreurException, ParenthèseOuvranteManquanteException, ParenthèseFermanteManquanteException /*** T'->*FT' | /FT' | $*/
    {
        if (erreur!=ERROR_NO)   throw new ErreurException();
        if (lu=='*')
        {
            avance(); F(); empileF(new FonctionMulu()); Tprime();
            
        }else if (lu=='/')
        {
            avance(); F(); empileF(new FonctionDiv()); Tprime();
            
        }
                
    }

    private void F() throws ErreurSyntaxeException, FonctionInconnueException, VariableInconnueException, ErreurException, ParenthèseOuvranteManquanteException, ParenthèseFermanteManquanteException /*** F->id | (E)*/
    {
        if (erreur!=ERROR_NO)   throw new ErreurException();
        if (lu>='0' && lu<='9')
        {
            double valeur=0;
            while(lu>='0' && lu<='9')   //lit une valeur num�rique 
            {
                valeur=valeur*10+(lu-'0');
                avance();
            }
            if (lu=='.')    //reel
            {
                avance();
                double av=0.1;
                while(lu>='0' && lu<='9')   //lit une valeur num�rique 
                {
                    valeur=valeur+(lu-'0')*av;
                    av=av*0.1;
                    avance();
                }
            }
            empileF(new FonctionCst(valeur));
        }else if ((lu>='a' && lu<='z') || (lu>='A' && lu<='Z'))  //une variable
        {
            String nomv=""+lu;
            avance();
            while((lu>='a' && lu<='z') || (lu>='A' && lu<='Z') || (lu>='0' && lu<='9'))
            {
                nomv=nomv+lu;
                avance();
            }
            if (lu!='(')    //c'est une variable
            {
                //cherche la variable
                Double var=vars.get(nomv);
                if (var==null)
                    throw new VariableInconnueException();
                else
                    empileF(new FonctionVar(nomv));
            }else
            {   //une fonction
                avance();
                E();
                if (lu==')')
                    avance();
                else throw new ParenthèseFermanteManquanteException();
                    
                if (nomv.equals("sin")) //sinus   
                    empileF(new FonctionSin());
                else if (nomv.equals("cos")) //cos   
                    empileF(new FonctionCos());
                else if (nomv.equals("log")) //log
                    empileF(new FonctionLog());
                else if (nomv.equals("tan")) //tan
                    empileF(new FonctionTan());
                else if (nomv.equals("abs")) //abs
                    empileF(new FonctionAbs());
                else if (nomv.equals("sqrt")) //sqrt
                    empileF(new FonctionSqrt());
                else
                    throw new FonctionInconnueException();
            }
        }
        else if (lu=='(')
        {
            avance();
            E(); 
            if (lu==')')
                avance();
            else throw new ParenthèseFermanteManquanteException();
        }else throw new ErreurSyntaxeException();
    }

    public  void    addVar(String nom,double value) /*** ajout d'une variable ou modification de sa valeur*/
    {
        vars.put(nom,value);
    }

    public HashMap<String, Double> getVars() /*** donne les variables*/
    {
        return vars;
    }

    public void setVars(HashMap<String, Double> vars) /*** defini les variables*/
    {
        this.vars = vars;
    }

    private abstract class Fonction /*** une fonction dans la pile*/
    {
        public abstract void evalue();
    }

    private class FonctionCst extends Fonction /*** constante*/
    {
        private double  cst;

        public  FonctionCst(double cst)
        {
            this.cst=cst;
        }
        public void evalue()
        {
            empile(cst);
        }

        public String toString() { return ""+cst;}
    }

    private class FonctionVar extends Fonction /*** variable*/
    {
        private String  var;

        public  FonctionVar(String var)
        {
            this.var=var;
        }
        public void evalue()
        {
            empile(vars.get(var));  //valeur de la variable
        }

        public String toString() { return var;}
    }

    private class FonctionPlus extends Fonction /*** plus*/
    {
         public void evalue()
        {
            empile(depile()+depile());
        }
         public String toString() { return "+";}
    }

    private class FonctionMoins extends Fonction /*** moins*/
    {
         public void evalue()
        {
            double a=depile(),b=depile();
            empile(b-a);
        }

         public String toString() { return "-";}
    }

    private class FonctionMulu extends Fonction /*** mulu*/
    {
         public void evalue()
        {
            double a=depile(),b=depile();
            empile(b*a);
        }

         public String toString() { return "*";}
    }

    private class FonctionDiv extends Fonction /*** div*/
    {
         public void evalue()
        {
            double a=depile(),b=depile();
            empile(b/a);
        }

         public String toString() { return "/";}
    }

    private class FonctionSin extends Fonction /*** sinus*/
    {
         public void evalue()
        {
            double a=depile();
            empile(Math.sin(a));
        }

         public String toString() { return "sin";}
    }

    private class FonctionCos extends Fonction /*** cosnus*/
    {
         public void evalue()
        {
            double a=depile();
            empile(Math.cos(a));
        }

         public String toString() { return "cos";}
    }

    private class FonctionTan extends Fonction /*** tan*/
    {
        public void evalue()
        {
            double a=depile();
            empile(Math.tan(a));
        }

        public String toString() { return "log";}
    }

    private class FonctionAbs extends Fonction /*** abs*/
    {
        public void evalue()
        {
            double a=depile();
            empile(Math.abs(a));
        }

        public String toString() { return "abs";}
    }

    private class FonctionSqrt extends Fonction /*** log*/
    {
        public void evalue()
        {
            double a=depile();
            empile(Math.sqrt(a));
        }

        public String toString() { return "log";}
    }

    private class FonctionLog extends Fonction /*** log*/
    {
        public void evalue()
        {
            double a=depile();
            empile(Math.log(a));
        }

        public String toString() { return "log";}
    }
}

class ErreurException extends Exception{
    @Override
    public String getMessage() {
        return "Erreur !";
    }
}

class ErreurSyntaxeException extends Exception{
    @Override
    public String getMessage() {
        return "Expression erronée";
    }
}

class VariableInconnueException extends Exception{
    @Override
    public String getMessage() {
        return "Variable inconnue";
    }
}

class FonctionInconnueException extends Exception{
    @Override
    public String getMessage() {
        return "Fonction inconnue";
    }
}

class ParenthèseFermanteManquanteException extends Exception{
    @Override
    public String getMessage() {
        return "parenthèse fermante manquante";
    }
}

class ParenthèseOuvranteManquanteException extends Exception{
    @Override
    public String getMessage() {
        return "parenthèse ouvrante manquante";
    }
}
