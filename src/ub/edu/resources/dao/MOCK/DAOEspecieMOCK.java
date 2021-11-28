package ub.edu.resources.dao.MOCK;

import ub.edu.model.Especie;
import ub.edu.resources.dao.DAOEspecie;

import java.util.*;

public class DAOEspecieMOCK implements DAOEspecie {

    private final Map<Especie, ArrayList<String>> llistaEspecies = new HashMap<>();

    public DAOEspecieMOCK() {
        //lludriga-----------------------------------------------
        ArrayList<String> espEx1 = new ArrayList<>();
        espEx1.add("Museu Miro");
        espEx1.add("la Foradada");
        espEx1.add("El camí des Correu");
        llistaEspecies.put(new Especie("Llúdriga"), espEx1);

        //mila reial-----------------------------------------------
        ArrayList<String> espEx2 = new ArrayList<>();
        llistaEspecies.put(new Especie("Milà Reial"), espEx2);

        //turo europeu-----------------------------------------------
        ArrayList<String> espEx3 = new ArrayList<>();
        espEx3.add("Museu Miro");
        espEx3.add("la Foradada");
        llistaEspecies.put(new Especie("Turó europeu"), espEx3);

    }

    @Override
    public Map<Especie, ArrayList<String>> getLlistaEspecies() {
        return llistaEspecies;
    }

    @Override
    public List<Especie> getAll() {
        return new ArrayList<>(llistaEspecies.keySet());
    }

    @Override
    public Optional<Especie> getById(String id) {
        for (Map.Entry<Especie, ArrayList<String>> entry : llistaEspecies.entrySet()) {
            Especie key = entry.getKey();
            if (key.getNom().equals(id)) return Optional.of(key);
        }
        return Optional.empty();
    }

    @Override
    public boolean add(final Especie especie) {
        return false;
    }

    @Override
    public boolean update(final Especie especie, String[] params) {
        especie.setNom(Objects.requireNonNull(
                params[0], "Title cannot be null"));
        return llistaEspecies.replace(especie, null ) != null;
    }

    @Override
    public boolean delete(final Especie especie) {
        return llistaEspecies.remove(especie) != null;
    }

    @Override
    public List<String> getExcursionsForEspecie(Especie especie) {
        if (especie == null) {
            throw new ClassCastException();
        }
        if (llistaEspecies.containsKey(especie)) {
            return (llistaEspecies.get(especie));
        }
        return null;
    }

    public void setValue (Especie e,String s){
        for (Especie esp : llistaEspecies.keySet()){
            if (e.getNom().equals(esp.getNom())){
                llistaEspecies.get(esp).add(s);
            }
        }
    }

    public void afegirEspecie(Especie e, String s){
        ArrayList<String> espEx = new ArrayList<>();
        if (!s.equals("") && !s.isEmpty()) {
            espEx.add(s);
        }
        llistaEspecies.put(e, espEx);
    }

    public boolean inEspecies (Especie e){
        boolean trobat = false;
        for (Especie esp : llistaEspecies.keySet()){
            if (e.getNom().equals(esp.getNom())) {
                trobat = true;
                break;
            }
        }
        return trobat;
    }
}
