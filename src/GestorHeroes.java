import java.util.LinkedList;

public class GestorHeroes {
    private LinkedList<SpiderverseHero> lista = new LinkedList<>();

    public boolean registrarHeroe(SpiderverseHero h) {
        for (SpiderverseHero hero : lista) {
            if (hero.getCodigo() == h.getCodigo()) {
                return false; // Código duplicado
            }
        }
        lista.addFirst(h);
        return true;
    }

    public SpiderverseHero buscarPorCodigo(int codigo) {
        for (SpiderverseHero h : lista) {
            if (h.getCodigo() == codigo) return h;
        }
        return null;
    }

    public LinkedList<SpiderverseHero> getLista() {
        return lista;
    }

    public LinkedList<SpiderverseHero> filtrarSinPoder(String poder) {
        LinkedList<SpiderverseHero> nueva = new LinkedList<>();
        for (SpiderverseHero h : lista) {
            if (!h.getPoderEspecial().equalsIgnoreCase(poder)) {
                nueva.add(h);
            }
        }

        // Burbuja por nivel de experiencia (ascendente)
        for (int i = 0; i < nueva.size(); i++) {
            for (int j = 0; j < nueva.size() - i - 1; j++) {
                if (nueva.get(j).getNivelExperiencia() > nueva.get(j + 1).getNivelExperiencia()) {
                    SpiderverseHero temp = nueva.get(j);
                    nueva.set(j, nueva.get(j + 1));
                    nueva.set(j + 1, temp);
                }
            }
        }
        return nueva;
    }
}
