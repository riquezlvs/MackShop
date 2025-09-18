import java.util.Scanner;

public class MackShop {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // CÁTALOGOS DE PRODUTOS

        // static boolean baseInicializada = false; // Verifica se a base está iniciada.

        int idsProdutos[] = new int[50]; // Armazena os códigos numéricos únicos de cada produto.
        String nomesProdutos[] = new String[50]; // Armazena as descrições dos produtos.
        double precosProdutos[] = new double[50]; // Armazena os preços unitários de cada produto.
        int estoqueProdutos[] = new int[50]; // Armazena a quantidade disponível de cada produto.

        // VENDA ATUAL
        int vendaAtualIds[] = new int[50]; //Armazena os IDs dos produtos na venda.
        int vendaAtualQuantidades[] = new int[50]; //Armazena a quantidade de cada produto.

        // HISTÓRICO DE VENDAS
        int historicoIdsPedidos[] = new int[50];
        double historicoValoresPedidos[] = new double[50];
        int historicoItensVendidos[][] = new int[50][50];

        Menu(scanner, idsProdutos, nomesProdutos, precosProdutos, estoqueProdutos);
        scanner.close();
    }

    // INICIA O MENU DO PROGRAMA

    public static void Menu(Scanner scanner, int[] ids, String[] nomes, double[] precos, int[] estoque) {
        int opcaoMenu;

        do {
            System.out.println("--================== MENU ==================--");
            System.out.println("1. Inicializar base");
            System.out.println("2. Exibir catálogo de produtos");
            System.out.println("3. Adicionar item à venda");
            System.out.println("4. Ver resumo da venda atual");
            System.out.println("5. Finalizar venda (gerar histórico e Nota Fiscal)");
            System.out.println("6. Ver histórico de vendas");
            System.out.println("7. Buscar venda específica do histórico");
            System.out.println("8. (Admin) Repor estoque");
            System.out.println("9. (Admin) Relatório de estoque baixo");
            System.out.println("0. Sair");
            System.out.print("Digite opção que deseja selecionar (1-9): ");

            opcaoMenu = scanner.nextInt();

            if (opcaoMenu < 1 || opcaoMenu > 9) {
                System.out.printf("Digite um caractere inválido. Opção %d não aceita\n", opcaoMenu);
            } else {
                switch (opcaoMenu) {
                    case 1:
                        iniciarBase(ids, nomes, precos, estoque);
                        break;

                    case 2:
                        break;

                    default:
                        break;
                }
            }

        } while (opcaoMenu != 0);
    }

    // INICIA A BASE DE DADOS PADRÃO DO SISTEMA

    public static void iniciarBase(int[] ids, String[] nomes, double[] precos, int[] estoque) {


        ids[0] = 101;
        nomes[0] = "Camiseta";
        precos[0] = 49.90;
        estoque[0] = 20;


        ids[1] = 102;
        nomes[1] = "Calça Jeans";
        precos[1] = 120.00;
        estoque[1] = 15;


        ids[2] = 103;
        nomes[2] = "Tênis Esportivo";
        precos[2] = 250.00;
        estoque[2] = 10;


        ids[3] = 104;
        nomes[3] = "Moletom com Capuz";
        precos[3] = 95.50;
        estoque[3] = 8;


        ids[4] = 105;
        nomes[4] = "Meia Esportiva (par)";
        precos[4] = 15.00;
        estoque[4] = 50;

        System.out.println("Base de dados inicializada com sucesso!");
    }

}