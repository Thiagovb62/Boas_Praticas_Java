package br.com.alura.Service;

import br.com.alura.Domain.Abrigo;
import br.com.alura.Client.ClientHttpConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class AbrigoService {

    private ClientHttpConfiguration clientHttpConfiguration;

    public AbrigoService(ClientHttpConfiguration clientHttpConfiguration) {
        this.clientHttpConfiguration = clientHttpConfiguration;
    }

    public  void listarAbrigos() throws IOException, InterruptedException {
        String uri = "http://localhost:8080/abrigos";
        var response = clientHttpConfiguration.dispararRequisicaoGet(uri);
        String responseBody = response.body();
        Abrigo[] abrigos = new ObjectMapper().readValue(responseBody, Abrigo[].class);
        List<Abrigo> abrigosList = Arrays.stream(abrigos).toList();
        if (abrigosList.isEmpty()) {
            System.out.println("Nenhum abrigo cadastrado!");
        }else {
            showAbrigos(abrigosList);
        }
    }

    private static void showAbrigos(List<Abrigo> abrigosList) {
        System.out.println("Abrigos cadastrados:");
        for (Abrigo abrigo : abrigosList) {
            long id = abrigo.getId();
            String nome = abrigo.getNome();
            System.out.println(id + " - " + nome);
        }
    }

    public  void cadastrarAbrigo() throws IOException, InterruptedException {
        System.out.println("Digite o nome do abrigo:");
        String nome = new Scanner(System.in).nextLine();
        System.out.println("Digite o telefone do abrigo:");
        String telefone = new Scanner(System.in).nextLine();
        System.out.println("Digite o email do abrigo:");
        String email = new Scanner(System.in).nextLine();

        Abrigo abrigo = new Abrigo( nome, telefone, email);

        String uri = "http://localhost:8080/abrigos";

        var response = clientHttpConfiguration.requisicaoPost(uri, abrigo);
        int statusCode = response.statusCode();
        String responseBody = response.body();
        if (statusCode == 200) {
            System.out.println("Abrigo cadastrado com sucesso!");
            System.out.println(responseBody);
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o abrigo:");
            System.out.println(responseBody);
        }
    }

}
