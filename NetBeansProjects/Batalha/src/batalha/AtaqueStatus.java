/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batalha;

import static java.lang.Integer.parseInt;

/**
 *
 * @author joaov
 */
public class AtaqueStatus extends Ataque{
    private Status status;
    private int chance;

    public AtaqueStatus(String[] parametros){
        super(parametros);
        
        this.status = Status.valueOf(parametros[7].split(",")[0]);
        this.chance = parseInt(parametros[7].split(",")[1]);
    }
    
    @Override
    public void efeito(){
        
    }
}
