import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mapa {
    ArrayList<Vertice> mapa = new ArrayList<Vertice>();
    Random random = new Random(System.currentTimeMillis());
    int tempo = 81;
    String grafo = "";

    // criamos a estrutura do nosso grafo
    public void grafo() {
        System.out.println("MAPA -----------> ");
        System.out.println("                [12]--------------------[13]------------[14]");
        System.out.println("                  |                       |               | ");
        System.out.println("[27]--------------+-----------------------+-----[24]----[29]");
        System.out.println("  |               |                       |       |       | ");
        System.out.println("  |               |     [01]----[00]----[02]    [11]      | ");
        System.out.println("  |               |       |               |       |       | ");
        System.out.println("  |             [10]----[03]----[04]----[05]----[07]----[08]");
        System.out.println("  |               |       |       |                       | ");
        System.out.println("  |     [18]------+-----[28]------+-----[15]              | ");
        System.out.println("  |       |       |       |       |       |               | ");
        System.out.println("  |       |       |     [16]------+-----[17]              | ");
        System.out.println("  |       |       |               |                       | ");
        System.out.println("  |       |     [09]--------------+---------------------[06]");
        System.out.println("  |       |       |               |                         ");
        System.out.println("[25]----[23]----[20]------------[19]----[21]                ");
        System.out.println("  |                                       |                 ");
        System.out.println("[26]------------------------------------[22]                ");
    }

    // passamos um csv, e criamos nosso grafo, adicionandos os vertices
    public void criaGrafo() {
        String arquivoCSV = "grafo.csv";
        try {
            File arquivo = new File(arquivoCSV);
            Scanner scanner = new Scanner(arquivo);

            // Ignora a primeira linha (cabeçalho)
            scanner.nextLine();
            // int contador;
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] dados = linha.split(",");
                int i = 0;

                Vertice o = new Vertice();

                while (i != dados.length) {
                    o.adjacentes.add(Integer.parseInt(dados[i]));
                    i++;
                }
                this.mapa.add(o);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
    }

    // passamos um csv, adicionandos as criaturas em vertices aleatorios, que não
    // sejam a praia e o vertice do tesouro
    public void criaCriaturas() {
        String arquivoCSV = "criaturas.csv";
        try {
            File arquivo = new File(arquivoCSV);
            Scanner scanner = new Scanner(arquivo);

            // Ignora a primeira linha (cabeçalho)
            scanner.nextLine();
            // int contador;
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] dados = linha.split(",");

                String nome = dados[0];
                int vida = Integer.parseInt(dados[1]);
                int ataque = Integer.parseInt(dados[2]);

                int vertice;
                do {
                    vertice = random.nextInt(this.mapa.size());
                } while (vertice == 0 || vertice == this.mapa.size() || this.mapa.get(vertice).checkPoint == true);

                Criatura e = new Criatura(nome, vida, ataque, vertice);
                this.mapa.get(vertice).criaturas.add(e);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
    }
    // passamos um csv, adicionandos os obstaculos em
    // vertices aleatorios, que não sejam a praia e o vertice do tesouro

    public void criaObstaculos() {
        String arquivoCSV = "obstaculos.csv";
        try {
            File arquivo = new File(arquivoCSV);
            Scanner scanner = new Scanner(arquivo);

            // Ignora a primeira linha (cabeçalho)
            scanner.nextLine();
            // int contador;
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] dados = linha.split(",");

                String nome = dados[0];
                int dano = Integer.parseInt(dados[1]);

                int vertice;
                do {
                    vertice = random.nextInt(this.mapa.size());
                } while (vertice == 0 || vertice == 29 || this.mapa.get(vertice).checkPoint==true || this.mapa.get(vertice).obstaculo != null);

                this.mapa.get(vertice).obstaculo = new Obstaculo(nome, dano);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
    }

    // passamos um csv, adicionandos os itens em
    // vertices aleatorios, que não sejam a praia e o vertice do tesouro
    public void criaItens() {
        String arquivoCSV = "armas.csv";
        try {
            File arquivo = new File(arquivoCSV);
            Scanner scanner = new Scanner(arquivo);

            // Ignora a primeira linha (cabeçalho)
            scanner.nextLine();
            // int contador;
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] dados = linha.split(",");

                String nome = dados[0];
                int dano = Integer.parseInt(dados[1]);
                String descricao = dados[2];
                int vertice;
                do {
                    vertice = random.nextInt(this.mapa.size());
                } while (vertice == 0 || vertice == this.mapa.size() || this.mapa.get(vertice).arma != null);

                Arma a = new Arma(nome, dano, descricao);

                this.mapa.get(vertice).arma = a;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
        int cont = 3;
        while (cont > 0) {
            int c = random.nextInt(100);
            int vertice;
            do {
                vertice = random.nextInt(this.mapa.size());
            } while (vertice == 0 || vertice == this.mapa.size() || this.mapa.get(vertice).cura != 0);
            this.mapa.get(vertice).cura = c;

            do {
                vertice = random.nextInt(this.mapa.size());
            } while (vertice == 0 || vertice == this.mapa.size() || this.mapa.get(vertice).checkPoint == true);
            this.mapa.get(vertice).checkPoint = true;
            cont -= 1;
        }

    }

    // chamo as funções que juntas criam meu mapa
    public void inicializa() {
        criaGrafo();
        criaCriaturas();
        this.mapa.get(29).tesouro = 10000;
        criaItens();
        criaObstaculos();
    }
}
