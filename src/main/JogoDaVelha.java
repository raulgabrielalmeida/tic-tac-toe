package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JogoDaVelha extends JFrame implements ActionListener {
    // variaveis
    private int xWin = 0, oWin = 0; //conta as vitorias

    private int jogador = 1; // define 'jogador' -> verificador de qual jogador é
    private int disable[] = new int[9]; // define  'disable' -> variavel que desabilita a posição ja jogada para que não seja possivel repetir
    private int jogadas = 0; //define a variavel 'jogadas' -> conta as jogadas para calcular o fim do jogo
    

    //icones
    ImageIcon xIcon = new ImageIcon(getClass().getResource("../resources/xIcon.png")); //icone X
    ImageIcon oIcon = new ImageIcon(getClass().getResource("../resources/oIcon.png")); //icone O 
    ImageIcon vIcon = new ImageIcon(getClass().getResource("../resources/vIcon.png")); //icone #

    // criando elementos
    private JButton tecla[] = new JButton[9]; //define 'tecla' -> variavel das casinhas
    private JButton reset = new JButton(); //cria o jbutton de resetar o jogo
    private JLabel placar = new JLabel();
    
    
    public JogoDaVelha() {
        super();

        setTitle("Jogo da velha");
        setVisible(true);
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);        

        // declarando
        for (int i = 0; i <= 8; i++) {
            tecla[i] = new JButton();
            tecla[i].setIcon(null);
            tecla[i].setVisible(true);
        }

        reset.setBounds(150, 425, 200, 50);
        reset.setText("Reiniciar");
        placar.setBounds(150,0,300,100);
        placar.setText(String.format("X %d  •   O %d",xWin, oWin));
        placar.setFont(new Font("Arial", Font.PLAIN, 45));
        add(placar);
        // posiçâo
        
        

        for (int i = 0; i <= 2; i++) {
            tecla[i].setBounds((i+1) * 100, 100, 100, 100);
        }
        for (int i = 0; i <=2; i++) {
            tecla[i + 3].setBounds((i+1) * 100, 200, 100, 100);
        }
        for (int i = 0; i <= 2; i++) {
            tecla[i + 6].setBounds((i+1) * 100, 300, 100, 100);
        }

        // action

        for (int i = 0; i <= 8; i++) {
            final int index = i; //index por que é lambda
            tecla[i].addActionListener((e) -> { jogada(index); });
        }
        reset.addActionListener((ActionEvent e) -> { resetYes();} );

        // add
        for (int i = 0; i <=8 ; i++) {
            add(tecla[i]);
        }
        add(reset);


        revalidate();
        repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void reset(String msg, String tit, ImageIcon icon) {
        

        Object[] op = {"Reiniciar","Fechar"};
        int close = JOptionPane.showOptionDialog(null, "Fim de jogo! " + msg, tit,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon , op, op[0]);

                
        if(close == 1){
            System.exit(0);
        }
        resetYes();
    }

    public void resetYes() {
        jogadas = 0;

        for (int i = 0; i <= 8 ; i++) {
            disable[i] = 0;
            tecla[i].setIcon(null);
        }
    }

    public void jogada(int x) {

        if (disable[x] == 0) {
            jogadas++;
            if (jogador == 1) {
                tecla[x].setIcon(xIcon);
                disable[x] = 1;
                checkWin();
                jogador = 2;
            } else {
                tecla[x].setIcon(oIcon);
                disable[x] = 2;
                checkWin();
                jogador = 1;
            }

        }

    }

    public void checkWin() {
        // lista de possiveis vitorias:
        int vitorias[][] = {  
        {0, 1, 2}, 
        {3, 4, 5}, 
        {6, 7, 8}, 
        {0, 3, 6}, 
        {1, 4, 7}, 
        {2, 5, 8}, 
        {0, 4, 8}, 
        {2, 4, 6}  
        };
        
        for(int linha[] : vitorias){
            if (disable[linha[0]] == jogador && 
                disable[linha[1]] == jogador && 
                disable[linha[2]] == jogador) {

                    ImageIcon wicon = (jogador == 1) ? xIcon: oIcon ;

                    if (jogador == 1) xWin ++; else oWin++;
                    placar.setText(String.format("X %d  •   O %d",xWin, oWin));

                    reset("Jogador " + (jogador) + " Venceu!", "Fim de jogo", wicon); 
                }
            }
            if (jogadas == 9) {
                    reset("Empate!", "Empatou",vIcon);
        }
    }
    
}
