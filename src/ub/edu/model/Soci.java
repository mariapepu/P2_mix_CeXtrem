package ub.edu.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Soci {
    CompteBancari compteBancari;
    private final String pwd;
    private final String nom;
    private final Map<String, Pagament> llistaPagaments;


    public Soci(String nom, String pwd) {
        this.pwd = pwd;
        this.nom = nom;
        this.compteBancari = null;
        this.llistaPagaments = new HashMap<>();
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return nom;
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
        StringBuilder s = new StringBuilder();
        TreeMap<String, Pagament> ts = (new TreeMap<>() {
        });
        ts.putAll(llistaPagaments);
        //-----------------------------
        if (!ts.isEmpty()) {
            for (Pagament x : ts.values()) {
                if (x.esta_pagat) {
                    s.append("Pagament: ").append(x.id_transaccio).append(" | Concepte: ").append(x.concepte).append(" | Valor: ").append(x.valor).append(" | Estat: Pagat").append("\n");
                } else {
                    s.append("Pagament: ").append(x.id_transaccio).append(" | Concepte: ").append(x.concepte).append(" | Valor: ").append(x.valor).append(" | Estat: Pendent de pagament").append("\n");
                }
            }
            return s.toString();
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