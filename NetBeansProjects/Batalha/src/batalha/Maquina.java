/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalha;

import java.util.Random;

/**
 *
 * @author joaov
 */
public class Maquina extends Jogador{
    
    @Override
    public int escolherComando(int nroJogador){
        Random rand = new Random();
        return rand.nextInt(2);
    }
    public int escolherAtaque(){
        return 0;
    }
    public int escolhePokemon(){
        Random rand = new Random();
        return rand.nextInt(4)+1;
    }
}
