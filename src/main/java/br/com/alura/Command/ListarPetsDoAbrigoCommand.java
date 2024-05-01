package br.com.alura.Command;

import br.com.alura.Client.ClientHttpConfiguration;
import br.com.alura.Service.AbrigoService;
import br.com.alura.Service.PetService;

import java.io.IOException;

public class ListarPetsDoAbrigoCommand implements Command{
    @Override
    public void execute() {
        try {
            ClientHttpConfiguration clientHttpConfiguration = new ClientHttpConfiguration();
            PetService petService = new PetService(clientHttpConfiguration);
            petService.listarPetsDoAbrigo();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
