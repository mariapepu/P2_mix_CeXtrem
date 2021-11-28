package ub.edu.model;

import java.time.LocalDate;

public class Pagament {
    //atributs
    String id_transaccio;
    Soci soci;
    float valor;
    boolean esta_pagat;
    Excursio e;
    Activitat a;
    String concepte;
    private LocalDate data;
    private  MetodePagament mp;

    public Pagament(Excursio e, Activitat a, String id_transaccio, Soci soci, boolean esta_pagat) {
        this.e = e;
        this.a = a;
        this.id_transaccio = id_transaccio;
        this.soci = soci;
        this.valor = a.getPreu();
        this.esta_pagat = esta_pagat;
        this.concepte = a.getNom() + " a " + a.getNomExcursio();
        setData(LocalDate.now());
    }

    public String getIdTrans() {
        return id_transaccio;
    }

    public String getNomSoci() {
        return soci.getName();
    }

    public float getValor() {
        return valor;
    }
    public Excursio getExcursio() {
        return e;
    }


    public Activitat getActivitat() {
        return a;
    }

    public boolean getEstaPagat() {
        return esta_pagat;
    }

    public void setEstaPagat(boolean esta_pagat) {
        this.esta_pagat = esta_pagat;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public MetodePagament getMetodePagament() {
        return this.mp;
    }

    public void setMetodePagament(MetodePagament mp) {
        this.mp = mp;
    }

    public void printInfo(){
        System.out.println("Soci: " + getNomSoci());
        System.out.println("Pagament: " + id_transaccio);
        System.out.println("Concepte: " + concepte);
        System.out.println("Valor: " + valor);
        System.out.println("Data " + data);
        if (esta_pagat){
            System.out.println("Estat: " + "Pagat" );
        }else{
            System.out.println("Estat: " + "Pendent de pagament");
        }

    }

}