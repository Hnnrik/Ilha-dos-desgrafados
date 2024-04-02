import java.util.Scanner;
public class Play {
    public static void main(String[] args) {
        Mapa mapa = new Mapa();
        mapa.inicializa();
        Jogador jogador = new Jogador("desgrafador", 100, 0);

        Scanner scanEscolha = new Scanner(System.in);
        Scanner scanEscolhaPrincipal = new Scanner(System.in);

        System.out.println("Bem-vindo a ilha dos desgrafados.\nNesta ilha, temos um tesouro escondido \nem meio a uma névoa dença e inumeras criaturas,\na plilhagem do tesouro é baseada na sua vida\ntente não tomar dano para não perder\numa parte do que foi ganho\nDeseja iniciar a aventura?\n");
        System.out.println(" 1 - Sim\n 2 - Não\n");
        int principal = scanEscolhaPrincipal.nextInt();
        if (principal == 2) {
            System.out.println("Até a próxima");
        } else {
            System.out.println("Você começa na praia no vertice [00]");
            while (true) {
                System.out.println("O que deseja fazer?\n 1 - andar; \n 2 - usar poção; \n 3 - soltar arma;\n 4 - Ver Mapa;\n 5 - Ver ficha; \n 0 - sair;");
                int res = scanEscolha.nextInt();
                switch (res) {
                    case 1:
                        jogador.anda(mapa);
                        break;
                    case 2:
                        jogador.usa_pocao();
                        break;
                    case 3:
                        jogador.solta_arma(mapa);
                        break;
                    case 4:
                        mapa.grafo();
                        break;
                    case 5:
                        jogador.ficha();
                        break;
                    case 0:
                        return;
                    default:
                        return;
                }
            }
        }
        scanEscolha.close();
        scanEscolhaPrincipal.close();
    }
}