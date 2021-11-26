package ub.edu.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Soci {
    CompteBancari compteBancari;
    private String pwd;
    private String nom;
    private Map<String, Pagament> llistaPagaments;


    public Soci(String nom, String pwd) {
        this.pwd = pwd;
        this.nom = nom;
        this.compteBancari = null;
        this.llistaPagaments = new HashMap<>();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return nom;
    }

    public void setName(String nom) {
        this.nom = nom;
    }

    public CompteBancari getCompteBancari() {
        return compteBancari;
    }

    public void setCompteBancari(String soci, String iban) {
        this.compteBancari = new CompteBancari(soci, iban);
    }

    public void addPagament(String str, Pagament pagament) {
        llistaPagaments.put(str, pagament);
    }

    public Pagament findPagament(String id) {
        for (Pagament c : llistaPagaments.values()) {
            if (c.getIdTrans().equals(id)) return c;
        }
        return null;
    }

    public boolean containsPagament(Pagament pagament) {
        return llistaPagaments.containsValue(pagament);
    }

    public String pagarCompteBancari(Pagament p) {
        if (llistaPagaments.containsValue(p)) {
            try {
                return this.getCompteBancari().pagar(p);
            } catch (Exception e) {
                return "L'usuari no te compte bancari asociat";
            }
        } else {
            return "No s'ha torbat el pagament";
        }
    }

    public String pagar(Pagament p, MetodePagament mp) {
        if (llistaPagaments.containsValue(p)) {
            try {
                return mp.pagar(p);
            } catch (Exception e) {
                return "El Pagament ha fallat";
            }
        } else {
            return "No s'ha torbat el pagament";
        }
    }

    public String llistarPagaments() {
        //--ordenem el hash set primer--
        String s = "";
        TreeMap<String, Pagament> ts = (new TreeMap<String, Pagament>() {
            public int compare(Pagament a1, Pagament a2) {
                return a1.getData().compareTo(a2.getData());
            }
        });
        ts.putAll(llistaPagaments);
        //-----------------------------
        if (!ts.isEmpty()) {
            for (Pagament x : ts.values()) {
                if (x.esta_pagat) {
                    s += ("Pagament: " + x.id_transaccio
                            + " | Concepte: " + x.concepte
                            + " | Valor: " + x.valor
                            + " | Estat: Pagat" + "\n");
                } else {
                    s += ("Pagament: " + x.id_transaccio
                            + " | Concepte: " + x.concepte
                            + " | Valor: " + x.valor
                            + " | Estat: Pendent de pagament" + "\n");
                }
            }
            return s;
        } else {
            return "No hi ha cap pagament";
        }
    }

    public Pagament findPagamentByAct(Activitat a) {
        for (Pagament p : llistaPagaments.values()) {
            if (p.getActivitat().equals(a)) {
                return p;
            }
        }
        return null;
    }


}