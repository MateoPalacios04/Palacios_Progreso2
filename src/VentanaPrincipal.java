import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class VentanaPrincipal extends JFrame {
    private GestorHeroes gestor = new GestorHeroes();

    public VentanaPrincipal() {
        setTitle("Sistema de Héroes del Spiderverse");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnRegistrar = new JButton("Registrar Héroe");
        JButton btnBuscar = new JButton("Buscar Héroe");
        JButton btnListar = new JButton("Listar Héroes");
        JButton btnFiltrar = new JButton("Filtrar por Poder");

        btnRegistrar.addActionListener(e -> registrarHeroe());
        btnBuscar.addActionListener(e -> buscarHeroe());
        btnListar.addActionListener(e -> listarHeroes());
        btnFiltrar.addActionListener(e -> filtrarHeroes());

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(btnRegistrar);
        panel.add(btnBuscar);
        panel.add(btnListar);
        panel.add(btnFiltrar);

        add(panel);
    }

    private void registrarHeroe() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Código (único):"));
            if (gestor.buscarPorCodigo(codigo) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un héroe con ese código.");
                return;
            }

            String nombre = JOptionPane.showInputDialog("Nombre:");
            String[] poderes = {"Sentido Arácnido", "Trepa Muros", "Fuerza Sobrehumana", "Agilidad Mejorada", "Tejido de Telaraña"};
            String poder = (String) JOptionPane.showInputDialog(this, "Poder Especial:", "Selecciona", JOptionPane.PLAIN_MESSAGE, null, poderes, poderes[0]);

            String[] universos = {"Tierra-616", "Tierra-1610", "Tierra-12041", "Tierra-90214", "Tierra-138"};
            String universo = (String) JOptionPane.showInputDialog(this, "Universo:", "Selecciona", JOptionPane.PLAIN_MESSAGE, null, universos, universos[0]);

            int experiencia = Integer.parseInt(JOptionPane.showInputDialog("Nivel de experiencia (1-5):"));
            if (experiencia < 1 || experiencia > 5) throw new NumberFormatException("Nivel fuera de rango");

            SpiderverseHero h = new SpiderverseHero(codigo, nombre, poder, universo, experiencia);
            gestor.registrarHeroe(h);
            JOptionPane.showMessageDialog(this, "Héroe registrado con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void buscarHeroe() {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Código del héroe a buscar:"));
            SpiderverseHero h = gestor.buscarPorCodigo(codigo);
            if (h != null) {
                JOptionPane.showMessageDialog(this, h.toString(), "Héroe encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Héroe no encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Código inválido.");
        }
    }

    private void listarHeroes() {
        StringBuilder sb = new StringBuilder();
        for (SpiderverseHero h : gestor.getLista()) {
            sb.append(h.toString()).append("\n------------------\n");
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 300));
        JOptionPane.showMessageDialog(this, scroll, "Lista de Héroes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void filtrarHeroes() {
        String[] poderes = {"Sentido Arácnido", "Trepa Muros", "Fuerza Sobrehumana", "Agilidad Mejorada", "Tejido de Telaraña"};
        String poder = (String) JOptionPane.showInputDialog(this, "Filtrar héroes que NO tienen:", "Filtro de Poder", JOptionPane.PLAIN_MESSAGE, null, poderes, poderes[0]);

        LinkedList<SpiderverseHero> filtrados = gestor.filtrarSinPoder(poder);
        if (filtrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay héroes sin ese poder.");
            return;
        }

        StringBuilder sb = new StringBuilder("Héroes sin " + poder + " ordenados por experiencia:\n");
        for (SpiderverseHero h : filtrados) {
            sb.append(h.toString()).append("\n------------------\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 300));
        JOptionPane.showMessageDialog(this, scroll, "Héroes filtrados", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
