/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalha;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author joaov
 */
public class Batalha {    
    private static String[][] matrizEspecie = new String[151][8];
    private static String[][] matrizAtaque = new String[165][7];
    private static Jogador jogador1;
    private static Jogador jogador2;  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        matrizEspecie = carregaEspecies();
        matrizAtaque  = carregaAtaques();
        jogador1 = inicializarJogador(1);
        jogador2 = inicializarJogador(2);        
        batalha();
    }
    
    public static String[][] carregaEspecies(){
        String[][] retorno = new String[151][8];
        String nome = "C:\\TabelaEspecies.txt";
        FileReader arq;
        BufferedReader lerArq;
        String linha;
        Integer i = 0;
        
        try{
            arq = new FileReader(nome);            
            lerArq = new BufferedReader(arq);
            linha = lerArq.readLine();
            linha = lerArq.readLine();
            while (linha != null){                
                retorno[i++] = linha.split("\t");
                linha = lerArq.readLine();
            }
            
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
            System.err.printf("\nO arquivo deve estar na pasta: C:\\TabelaEspecies.txt");
            fechaJogo();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
            fechaJogo();
        }
        
        return retorno;
    }
    
    public static String[][] carregaAtaques(){
        String[][] retorno = new String[165][7];
        String nome = "C:\\TabelaAtaques.txt";
        FileReader arq;
        BufferedReader lerArq;
        String linha;
        Integer i = 0;
        
        try{
            arq = new FileReader(nome);            
            lerArq = new BufferedReader(arq);
            linha = lerArq.readLine();
            linha = lerArq.readLine();
            while (linha != null){                
                retorno[i++] = linha.split("\t");
                linha = lerArq.readLine();
            }
            
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
            System.err.printf("\nO arquivo deve estar na pasta: C:\\TabelaAtaques.txt");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
            System.err.printf("\nO arquivo esta alterado!");
        }
        return retorno;
    }
    
    public static Jogador inicializarJogador(int idJogador){
        Jogador jogador = null;
        Object[] opJogador = { "Humano", "Maquina"};
        Object[] opNumPokemon = {1,2,3,4,5,6};
        int escolha = 0;
        int contPkm = 0;
        String[] parametros = new String[6];
        
        try{
            escolha = JOptionPane.showOptionDialog(null, "O jogador " + idJogador + " será:", "",
                   JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                  null, opJogador, opJogador[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.printf("Erro na leitura dos parametros: %s.\n",e.getMessage());  
            fechaJogo();
        }
        
        switch (escolha) {
            case 0:
                jogador = new Humano(idJogador);
                break;
                
            case 1:
                jogador = new Maquina(idJogador);
                break;
                
            default:
                fechaJogo();
                break;
        }
        
        escolha = 1;
        
        escolha += JOptionPane.showOptionDialog(null, "O jogador " + idJogador + " terá quantos pokemons?", "",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opNumPokemon, opNumPokemon[0]);
        if (escolha<1 || escolha>6) fechaJogo(); //Clicou para fechar a janela                
    
        do{
            try {
                parametros = JOptionPane.showInputDialog("Entre com os parametros do pokemon Nº" + ++contPkm + "\n" + 
                                                         "Formato: (idPokemon) (Level) (IdAtaque1) (IdAtaque2) (IdAtaque3) (IdAtaque4)","0 0 0 0 0 0").split(" ");
            } catch (NullPointerException e){
                fechaJogo();
            }
            
            if (parametros[0].equals("0")) {
                System.err.printf("Erro na leitura dos parametros, argumentos zerados\n");
                fechaJogo();
            }
            
            if (parametros[1].equals("0")) {
                System.err.printf("Erro na leitura dos parametros, argumentos zerados\n");
                fechaJogo();
            }
            
            if (parametros[2].equals("0") && parametros[3].equals("0") && parametros[4].equals("0") && parametros[5].equals("0")){
                System.err.printf("Erro na leitura dos parametros, nenhum ataque foi informado!\n");
                fechaJogo();
            }

            List<Ataque> listaAtaque = new ArrayList();  
            int i = 2;
            
            while (i<=5){              
                try{
                    int idAtaque = parseInt(parametros[i])-1;
                    String[] linhaMatrizAtaque = {};
                    if (idAtaque>0) linhaMatrizAtaque = matrizAtaque[idAtaque];                        
                    Ataque ataque = null;   
                    
                    if (idAtaque>0){
                        switch (matrizAtaque[parseInt(parametros[i])-1][6]){
                            case "comum":
                                ataque = new Ataque(linhaMatrizAtaque);
                                break;
                            
                            case "modifier":
                                ataque = new AtaqueModifier(linhaMatrizAtaque);
                                break;
                            
                            case "status":
                                ataque = new AtaqueStatus(linhaMatrizAtaque);
                                break;
                            
                          case "multihit":
                                ataque = new AtaqueMultihit(linhaMatrizAtaque);
                                break;
                            
                            case "hp":
                                ataque = new AtaqueHP(linhaMatrizAtaque);
                                break;
                            
                            case "fixo":
                                ataque = new AtaqueFixo(linhaMatrizAtaque);
                                break;
                            
                            case "charge":
                                ataque = new AtaqueCharge(linhaMatrizAtaque);
                                break;
                        }   
                        listaAtaque.add(ataque);
                    }
                    i++;
                } catch (NumberFormatException e){
                    System.err.printf("Erro na leitura dos parametros, formato invalido: %s.\n",e.getMessage());
                    fechaJogo();
                } catch (ArrayIndexOutOfBoundsException e){
                    System.err.printf("Erro na leitura dos parametros, falta argumentos: %s.\n",e.getMessage());
                    fechaJogo();
                }         
            }
            
            Especie especie = new Especie(matrizEspecie[parseInt(parametros[0])-1]);
            Pokemon pokemon = new Pokemon(especie,parametros[1],listaAtaque);
            jogador.getListaPokemon().add(contPkm-1,pokemon);
            escolha--;
        } while(escolha > 0); 
        
        return jogador;
    }
    
    public static void batalha(){
        int comandoJogador1;
        int comandoJogador2;
        
        do{
            List<Jogador> listaPrioridadeJogador = new ArrayList();                        
            
            //0=Trocar Pokemon  1=Realizar Ataque
            comandoJogador1 = jogador1.escolherComando(jogador2.getPrimeiroPokemon());
            comandoJogador2 = jogador2.escolherComando(jogador1.getPrimeiroPokemon());           
            
            listaPrioridadeJogador.add(jogador1);
            
            if (comandoJogador1==comandoJogador2){                                
                //Compara velocidade dos pokemons
                if (jogador1.getPrimeiroPokemon().getSpd() > jogador2.getPrimeiroPokemon().getSpd()){
                    listaPrioridadeJogador.add(1,jogador2);
                }
                else listaPrioridadeJogador.add(0,jogador2);
            }
            else {
                //Veja quem escolheu a troca de pokemons e coloca em primeiro na lista
                if (comandoJogador1==0) listaPrioridadeJogador.add(1,jogador2);
                else listaPrioridadeJogador.add(0,jogador2);
            }
    
            //Quando os comandos sao iguais, apenas ver se é troca ou ataque e executar na lista de priopridade
            if (comandoJogador1==comandoJogador2){
                if (comandoJogador1==0){
                    listaPrioridadeJogador.get(0).trocarPokemon();
                    listaPrioridadeJogador.get(1).trocarPokemon();
                }
                else {
                    listaPrioridadeJogador.get(0).usarAtaque(listaPrioridadeJogador.get(1).getPrimeiroPokemon());
                    
                    if (temVencedor()) break;
                    if (pokemonEstaFainted(listaPrioridadeJogador.get(0))) listaPrioridadeJogador.get(0).forcaTroca();
                    if (pokemonEstaFainted(listaPrioridadeJogador.get(1))){
                        listaPrioridadeJogador.get(1).forcaTroca();
                        //continue; //Caso o pokemon morrer no ultimo ataque, o jogador nao pode atacar com o pokemon novo
                    }
                    else listaPrioridadeJogador.get(1).usarAtaque(listaPrioridadeJogador.get(0).getPrimeiroPokemon());
                    
                    if (temVencedor()) break;
                    if (pokemonEstaFainted(listaPrioridadeJogador.get(0))) listaPrioridadeJogador.get(0).forcaTroca();
                    if (pokemonEstaFainted(listaPrioridadeJogador.get(1))) listaPrioridadeJogador.get(1).forcaTroca();
                }                                                     
            }
            //Se não, ver quem vai executar a troca e fazer primeiro
            else {
                listaPrioridadeJogador.get(0).trocarPokemon();
                listaPrioridadeJogador.get(1).usarAtaque(listaPrioridadeJogador.get(0).getPrimeiroPokemon());
                
                if (temVencedor()) break;
                if (pokemonEstaFainted(listaPrioridadeJogador.get(0))) listaPrioridadeJogador.get(0).forcaTroca();
                if (pokemonEstaFainted(listaPrioridadeJogador.get(1))) listaPrioridadeJogador.get(1).forcaTroca();
            }
            executaEfeitoStatus(jogador1);
            executaEfeitoStatus(jogador2);
        } while (!temVencedor());
        
        if (jogador1.temPokemonVivo()) JOptionPane.showMessageDialog(null,"O jogador 1 ganhou!","", JOptionPane.PLAIN_MESSAGE);
        else
        if (jogador2.temPokemonVivo()) JOptionPane.showMessageDialog(null,"O jogador 2 ganhou!","", JOptionPane.PLAIN_MESSAGE);            
        else JOptionPane.showMessageDialog(null,"Deu empate","", JOptionPane.PLAIN_MESSAGE);            
              
        fechaJogo();
    }
    
    public static void fechaJogo(){
        System.exit(0);       
    }
    
    public static boolean pokemonEstaFainted(Jogador jogador){
        return jogador.getPrimeiroPokemon().getStatus()==Status.FAINTED;
    }
    
    public static boolean temVencedor(){
        return !jogador1.temPokemonVivo() || !jogador2.temPokemonVivo();
    }
    
    public static void executaEfeitoStatus(Jogador jogador){
        Pokemon pokemon = jogador.getPrimeiroPokemon();
        
        
        if (Status.OK.equals(pokemon.getStatus())) return;
        
        System.out.println("teste");
        
        if (Status.BURN.equals(pokemon.getStatus())){
            double dano = pokemon.getHpMax() * 6.25;
            double hpAtual = pokemon.getHpAtual() - dano;
            pokemon.setHpAtual(hpAtual);
            JOptionPane.showMessageDialog(null,pokemon.getEspecie().getNome() + " recebeu " + dano + " de dano do status Burn!\n" + 
                                               pokemon.getEspecie().getNome() + " HP: " + pokemon.getHpAtual(),"", JOptionPane.PLAIN_MESSAGE); 
            if (pokemonEstaFainted(jogador)) jogador.forcaTroca();
        }
        if (Status.POISON.equals(pokemon.getStatus())){
            double dano = pokemon.getHpMax() * 6.25;
            double hpAtual = pokemon.getHpAtual() - dano;
            pokemon.setHpAtual(hpAtual);
            JOptionPane.showMessageDialog(null,pokemon.getEspecie().getNome() + " recebeu " + dano + " de dano do status Poison!\n" + 
                                               pokemon.getEspecie().getNome() + " HP: " + pokemon.getHpAtual(),"", JOptionPane.PLAIN_MESSAGE); 
            if (pokemonEstaFainted(jogador)) jogador.forcaTroca();
        }
        if (Status.FROZEN.equals(pokemon.getStatus())){
            double rand = Math.random()*100;
 
            if (10>rand) {
                pokemon.setStatus(Status.OK);
                JOptionPane.showMessageDialog(null,pokemon.getEspecie().getNome() + " saiu do status Frozen!","", JOptionPane.PLAIN_MESSAGE);
            }
        }
        if (Status.SLEEP.equals(pokemon.getStatus())){
            double rand = Math.random()*100;
 
            if (20>rand) {
                pokemon.setStatus(Status.OK);            
                JOptionPane.showMessageDialog(null,pokemon.getEspecie().getNome() + " saiu do status Frozen!","", JOptionPane.PLAIN_MESSAGE);
            }
        }
        if (pokemon.isFlinch()){
            pokemon.setFlinch(false);
            JOptionPane.showMessageDialog(null,pokemon.getEspecie().getNome() + " saiu do status Flinch!","", JOptionPane.PLAIN_MESSAGE);
        }
        if (pokemon.isConfusion()){
            double rand = Math.random()*100;
 
            if (20 > rand) pokemon.setConfusion(false);
            JOptionPane.showMessageDialog(null,pokemon.getEspecie().getNome() + " saiu do status Confusion!","", JOptionPane.PLAIN_MESSAGE);
        }
        
    }
}