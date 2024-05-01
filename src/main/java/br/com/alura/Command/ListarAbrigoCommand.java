package br.com.alura.Command;

import br.com.alura.Client.ClientHttpConfiguration;
import br.com.alura.Service.AbrigoService;

import java.io.IOException;

public class ListarAbrigoCommand implements Command {
    @Override
    public void execute() {
        try {
            ClientHttpConfiguration clientHttpConfiguration = new ClientHttpConfiguration();
            AbrigoService abrigoService = new AbrigoService(clientHttpConfiguration);
            abrigoService.listarAbrigos();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
