package ub.edu.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Excursio {
    private String nom;
    private LocalDate data;
    private Set<Especie> llistaEspecies;
    private Map<String, Activitat> activitatsMap;

    public Excursio(String titol, String dataText) {
        this.nom = titol;
        setData(dataText);
        llistaEspecies = new HashSet<>();
        this.activitatsMap = new HashMap<>();
    }

    public Excursio(String titol, String dataText, List<Especie> especies) {
        this.nom = titol;
        setData(dataText);
        llistaEspecies = new HashSet<>(especies);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(String dataText) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.parse(dataText, formatter);
    }

    public void addEspecie(Especie especie) {
        llistaEspecies.add(especie);
    }

    public boolean containsEspecie(Especie especie) {
        return llistaEspecies.contains(especie);
    }

    public void addActivitat(String nom, float preu) {
        activitatsMap.put(nom, new Activitat(this.nom, nom, preu));
    }

    public Activitat getActivitat(String nomActivitat) {
        return activitatsMap.getOrDefault(nomActivitat, null);
    }

    public Map<String, Activitat> getActivitatsMap() {
        return activitatsMap;
    }


}