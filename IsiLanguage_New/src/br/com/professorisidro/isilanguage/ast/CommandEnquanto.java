package br.com.professorisidro.isilanguage.ast;

import java.util.ArrayList;

public class CommandEnquanto extends AbstractCommand {

    private String condition;
    private ArrayList<AbstractCommand> enquantoCommands;

    public CommandEnquanto(String condition, ArrayList<AbstractCommand> enquantoCommands) {
        this.condition = condition;
        this.enquantoCommands = enquantoCommands;
    }

    @Override
    public String generateJavaCode() {
        StringBuilder str = new StringBuilder();
        str.append("while (").append(condition).append("){\n");
        for(AbstractCommand cmd: this.enquantoCommands){
            str.append(cmd.generateJavaCode());
        }
        str.append("}\n");
        return str.toString();
    }

    @Override
    public String toString() {
    	StringBuilder bdr = new StringBuilder();
    	for(AbstractCommand c: enquantoCommands)
    	{
    		bdr.append("\t");
    		bdr.append(c);
       	}
        return "ComandoEnquanto [enquanto " + condition + " + " +  bdr + " ]" ;
}
}
