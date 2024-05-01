package br.com.alura.Service;

import br.com.alura.Domain.Pet;
import br.com.alura.Client.ClientHttpConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PetService {

    private ClientHttpConfiguration clientHttpConfiguration;

    public PetService(ClientHttpConfiguration clientHttpConfiguration) {
        this.clientHttpConfiguration = clientHttpConfiguration;
    }

    public  void listarPetsDoAbrigo() throws IOException, InterruptedException {
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = new Scanner(System.in).nextLine();


        String uri = "http://localhost:8080/abrigos/" + idOuNome + "/pets";
        var response = clientHttpConfiguration.dispararRequisicaoGet(uri);
        int statusCode = response.statusCode();
        if (statusCode == 404 || statusCode == 500) {
            System.out.println("ID ou nome não cadastrado!");
        }
        String responseBody = response.body();
        Pet[] pets = new ObjectMapper().readValue(responseBody, Pet[].class);
        List<Pet> petsList = Arrays.stream(pets).toList();
        System.out.println("Pets cadastrados:");
        for (Pet pet : petsList) {
            long id = pet.getId();
            String tipo = pet.getTipo();
            String nome =  pet.getNome();
            String raca = pet.getRaca();
            int idade = pet.getIdade();
            System.out.println(id + " - " + tipo + " - " + nome + " - " + raca + " - " + idade + " ano(s)");
        }
    }

    public  void importarPetsDoAbrigo() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o id ou nome do abrigo:");
        String idOuNome = scanner.nextLine();

        System.out.println("Digite o nome do arquivo CSV:");
        String nomeArquivo = scanner.nextLine();

        scanner.close();


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nomeArquivo));
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: " + nomeArquivo);
        }
        String line;
        while ((line = reader.readLine()) != null) {
            String[] campos = line.split(",");
            String tipo = campos[0];
            String nome = campos[1];
            String raca = campos[2];
            int idade = Integer.parseInt(campos[3]);
            String cor = campos[4];
            Float peso = Float.parseFloat(campos[5]);

           Pet pet = new Pet(null, tipo.toUpperCase(), nome, raca, cor, peso, idade);
            String uri = "http://localhost:8080/abrigos/" + idOuNome + "/pets";
            final HttpResponse<String> response =  clientHttpConfiguration.requisicaoPost(uri, pet);
            int statusCode = response.statusCode();
            String responseBody = response.body();
            if (statusCode == 200) {
                System.out.println("Pet cadastrado com sucesso: " + nome);
            } else if (statusCode == 404) {
                System.out.println("Id ou nome do abrigo não encontado!");
                break;
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o pet: " + nome);
                System.out.println(responseBody);
                break;
            }
        }
        reader.close();
    }
}
