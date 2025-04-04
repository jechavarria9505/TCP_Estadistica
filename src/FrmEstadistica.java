import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class FrmEstadistica extends JFrame {
    private JTextField txtDato;
    private JLabel lblMuestra;
    private JList lstMuestra;
    private JComboBox cmbEstadistica;
    private JTextField txtEstadistica;

    // Metodo constructor
    public FrmEstadistica() {
        setSize(500, 300);
        setTitle("Estadistica");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        // JLabel sirve para añadir etiqueta
        JLabel lblDato = new JLabel("Dato: ");
        lblDato.setBounds(10, 10, 100, 25);
        getContentPane().add(lblDato); // Para añadirlo a la ventana usar y se debe usar debajo de la declaracion

        // Creando Caja de texto
        txtDato = new JTextField();
        txtDato.setBounds(110, 10, 100, 25);
        getContentPane().add(txtDato);

        // Creadndo Botones
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(110, 40, 100, 25);
        getContentPane().add(btnAgregar);

        JButton btnQuitar = new JButton("Quitar");
        btnQuitar.setBounds(110, 70, 100, 25);
        getContentPane().add(btnQuitar);

        // Creando Cuadro de lista
        lblMuestra = new JLabel("Muestra: ");
        lblMuestra.setBounds(220, 10, 100, 25);
        lblMuestra.setHorizontalAlignment(SwingConstants.CENTER); // Para Centrar
        getContentPane().add(lblMuestra);

        lstMuestra = new JList();
        JScrollPane spMuestra = new JScrollPane(lstMuestra);
        spMuestra.setBounds(220, 40, 100, 150);
        getContentPane().add(spMuestra);

        JButton btnEstadistica = new JButton("Estadistica");
        btnEstadistica.setBounds(10, 200, 100, 25);
        getContentPane().add(btnEstadistica);

        // Creando lista desplegable
        cmbEstadistica = new JComboBox();
        String[] opciones = new String[] { "Sumatoria", "Promedio", "Desviacion Estandar", "Maximo", "Minimo", "Moda" }; // Opciones
                                                                                                                         // para
                                                                                                                         // lista
                                                                                                                         // desplegable
        DefaultComboBoxModel mdlEstadistica = new DefaultComboBoxModel(opciones); // Genera la lista desplegable
        cmbEstadistica.setModel(mdlEstadistica);// Esta declaracion para asignar el modelo del ComboBox
        cmbEstadistica.setBounds(110, 200, 100, 25);
        getContentPane().add(cmbEstadistica);

        // Caja de texto para mostrar el resultado

        txtEstadistica = new JTextField();
        txtEstadistica.setBounds(220, 200, 100, 25);
        txtEstadistica.setEditable(false); // No permite que en la caja de texto se pueda escribir
        getContentPane().add(txtEstadistica);

        // Generando eventos en el programa (Acciones al dar click en los botones)
        btnAgregar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDato();
            }

        });

        btnQuitar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                quitarDato();
            }

        });

        btnEstadistica.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                calcularEstadistica();
            }

        });
    }

    private int MAXIMO_DATOS = 1000;
    private double[] muestra = new double[1000];
    private int totalDatos = 0;

    private void agregarDato() {
        if (totalDatos <= MAXIMO_DATOS) {
            muestra[totalDatos] = Double.parseDouble(txtDato.getText());
            totalDatos++;
            mostrar();
        } else {
            JOptionPane.showMessageDialog(null, "No se deben agregar mas datos");
        }
    }

    private void mostrar() {
        String[] strMuestra = new String[totalDatos];
        for (int i = 0; i < totalDatos; i++) {
            strMuestra[i] = String.valueOf(muestra[i]);
        }
        DefaultListModel dlm = new DefaultListModel();
        dlm.copyInto(strMuestra);
        lstMuestra.setListData(strMuestra);
    }

    private void quitarDato() {
        int posicion = lstMuestra.getSelectedIndex();
        if (posicion >= 0) {
            for (int i = posicion; i < totalDatos - 1; i++) {
                muestra[i] = muestra[i + 1];
            }
            totalDatos--;
            mostrar();

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar la posicion a eliminar");
        }

    }

    // Generando la sumatoria de los datos
    private double sumatoria() {
        double suma = 0;
        for (int i = 0; i < totalDatos; i++) {
            suma += muestra[i];
        }

        return suma;
    }

    private double promedio() {
        return totalDatos > 0 ? sumatoria() / totalDatos : 0;
    }

    private double desviacionEstandar() {
        if (totalDatos > 1) {
            double suma = 0;
            double promedioActual = promedio();
            for (int i = 0; i < totalDatos; i++) {
                suma += Math.abs(muestra[i] - promedioActual);
            }
            return (suma / (totalDatos - 1));

        } else {
            return 0;
        }
    }

    private double maximo() {
        if (totalDatos > 0) {
            double maximoActual = muestra[0];
            for (int i = 1; i < totalDatos; i++) {
                if (muestra[i] > maximoActual) {
                    maximoActual = muestra[i];
                }
            }
            return maximoActual;

        } else {
            return 0;
        }

    }

    private double minimo() {

    }

    private double moda() {

    }

    private void calcularEstadistica() {
        switch (cmbEstadistica.getSelectedIndex()) {
            case 0:
                txtEstadistica.setText(String.valueOf(sumatoria()));
                break;
            case 1:
                txtEstadistica.setText(String.valueOf(promedio()));
                break;
            case 2:
                txtEstadistica.setText(String.valueOf(desviacionEstandar()));
                break;
            case 3:
                txtEstadistica.setText(String.valueOf(maximo()));
                break;
            case 4:
                txtEstadistica.setText(String.valueOf(minimo()));
                break;
            case 5:
                txtEstadistica.setText(String.valueOf(moda()));
                break;

        }
    }

}
