package minipaint;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class MiniPaint extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    private ButtonGroup modos;
    private JPanel area;
    private JLabel status;
    private Image buffer;
    private Image temporal;
    
    private final int PUNTOS = 1;
    private final int LINEAS = 2;
    private final int RECTANGULOS = 3;
    private final int CIRCULOS = 4;
    
    private int modo;
    private int x, y;
    
    public MiniPaint(){
        super("MiniPaint 1.0");
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem opcionNuevo = new JMenuItem("Nuevo",'N');
        opcionNuevo.addActionListener(this);
        opcionNuevo.setActionCommand("Nuevo");
        menuArchivo.add(opcionNuevo);
        menuBar.add(menuArchivo);
        
        JMenuItem opcionSalir = new JMenuItem("Salir");
        opcionSalir.addActionListener(this);
        opcionSalir.setActionCommand("Salir");
        menuArchivo.add(opcionSalir);
        menuBar.add(menuArchivo);
        
        modos = new ButtonGroup();
        JMenu menuModo = new JMenu("Modo");
        JRadioButtonMenuItem opcionPuntos = new JRadioButtonMenuItem("Puntos", true);
        opcionPuntos.addActionListener(this);
        opcionPuntos.setActionCommand("Puntos");
        menuModo.add(opcionPuntos);
        modos.add(opcionPuntos);
        
        JRadioButtonMenuItem opcionLineas = new JRadioButtonMenuItem("Lineas");
        opcionLineas.addActionListener(this);
        opcionLineas.setActionCommand("Lineas");
        menuModo.add(opcionLineas);
        modos.add(opcionLineas);
        
        JRadioButtonMenuItem opcionRectangulos = new JRadioButtonMenuItem("Rectangulos");
        opcionRectangulos.addActionListener(this);
        opcionRectangulos.setActionCommand("Rectangulos");
        menuModo.add(opcionRectangulos);
        modos.add(opcionRectangulos);
        
        JRadioButtonMenuItem opcionCirculos = new JRadioButtonMenuItem("Circulos");
        opcionCirculos.addActionListener(this);
        opcionCirculos.setActionCommand("Circulos");
        menuModo.add(opcionCirculos);
        modos.add(opcionCirculos);
        
        menuBar.add(menuModo);
        
        area = new JPanel();
        area.addMouseListener(this);
        area.addMouseMotionListener(this);
        
        status = new JLabel("Status", JLabel.LEFT);
        setJMenuBar(menuBar);
        
        getContentPane().add(area, BorderLayout.CENTER);
        getContentPane().add(status, BorderLayout.SOUTH);
        
        modo = PUNTOS;
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(900, 700);
        
        buffer = area.createImage(area.getWidth(), area.getHeight());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if(comando.equals("Nuevo")){
            buffer =  null;
            area.getGraphics().clearRect(0,0, area.getWidth(), area.getHeight());
            buffer = area.createImage(area.getWidth(), area.getHeight());
        } else if(comando.equals("Salir")){
            if(JOptionPane.showConfirmDialog(this,"En verdad desea salir?", "Confirmacion",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                dispose();
                System.exit(0);
            }
        } else if(comando.equals("Puntos")){
            modo = PUNTOS;  
        } else if(comando.equals("Lineas")){
            modo = LINEAS;
        } else if(comando.equals("Rectangulos")){
            modo = RECTANGULOS;  
        } else if(comando.equals("Circulos")){
            modo = CIRCULOS;
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {}

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        x = e.getX();
        y = e.getY();
        temporal = area.createImage(area.getWidth(), area.getHeight());
        temporal.getGraphics().drawImage(buffer, 0, 0, this);
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        buffer.getGraphics().drawImage(temporal, 0, 0, this);
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        Graphics g = temporal.getGraphics();
        switch(modo){
            case PUNTOS:
                g.fillOval(e.getX(), e.getY(), 2, 2);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case LINEAS:
                g.drawImage(buffer, 0, 0, area);
                g.drawLine(x, y, e.getX(), e.getY());
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case RECTANGULOS:
                g.drawImage(buffer, 0, 0, area);
                int rectWidth = Math.abs(e.getX() - x);
                int rectHeight = Math.abs(e.getY() - y);
                g.drawRect(x, y, rectWidth, rectHeight);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case CIRCULOS:
                g.drawImage(buffer, 0, 0, area);
                g.drawOval(x, y, e.getX()-x, e.getY()-y);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        status.setText("x=" + e.getX() + ",y=" + e.getY());
    }
    
    public static void main(String[] args) {
        MiniPaint miniPaint = new MiniPaint();
        miniPaint.setVisible(true);
    }
    
}
