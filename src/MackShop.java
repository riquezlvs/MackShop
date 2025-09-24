import java.util.Scanner;
import java.util.Random;

public class MackShop {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // CÁTALOGOS DE PRODUTOS

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
        int historicoItensVendidos[][] = new int[50][3];

        // ESTADO DA BASE
        boolean baseInicializada = false;

        Menu(scanner, idsProdutos, nomesProdutos, precosProdutos, estoqueProdutos,
                vendaAtualIds, vendaAtualQuantidades,
                historicoIdsPedidos, historicoValoresPedidos, historicoItensVendidos,
                baseInicializada);
        scanner.close();
    }

    // INICIA O MENU DO PROGRAMA

    public static void Menu(Scanner scanner, int[] ids, String[] nomes, double[] precos, int[] estoque,
                            int[] vendaAtualIds, int[] vendaAtualQuantidades,
                            int[] historicoIdsPedidos, double[] historicoValoresPedidos, int[][] historicoItensVendidos,
                            boolean baseInicializada) {
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

            if (opcaoMenu < 0 || opcaoMenu > 9) {
                System.out.printf("Digite um caractere válido. Opção %d não aceita!\n", opcaoMenu);
            } else {
                if (opcaoMenu != 0 && opcaoMenu != 1 && !baseInicializada) {
                    System.out.println("Obrigatório iniciar a base de dados antes!");
                } else {
                    switch (opcaoMenu) {
                        case 1:
                            if (!baseInicializada) {
                                baseInicializada = iniciarBase(ids, nomes, precos, estoque,
                                vendaAtualIds, vendaAtualQuantidades,
                                historicoIdsPedidos, historicoValoresPedidos, historicoItensVendidos);
                            }else{
                                System.out.println("A base de dados já foi inicializada!");
                            }
                            break;
                        case 2:
                            exibeProdutos(estoque, nomes, ids, precos);
                            break;
                        case 3:
                            adicionarItemVenda(scanner, ids, estoque, vendaAtualIds, vendaAtualQuantidades);
                            break;
                        case 4:
                            VerResumoVendaAtual(ids, nomes, precos, vendaAtualIds,  vendaAtualQuantidades);
                            break;
                        case 5:
                            finalizarVenda(historicoItensVendidos, historicoIdsPedidos, historicoValoresPedidos, vendaAtualIds, ids, nomes, precos, vendaAtualQuantidades, estoque);                            break;
                        case 6:
                            verHistoricoVendas(historicoIdsPedidos, historicoValoresPedidos);
                            break;
                        case 7:
                            buscarVendaEspecifica(scanner, historicoIdsPedidos, historicoValoresPedidos, historicoItensVendidos, ids, nomes, precos);
                            break;
                        case 8:
                            reporEstoque(scanner, ids, estoque);
                            break;
                        case 9:
                            relatorioEstoqueBaixo(ids, nomes, estoque);
                            break;
                        default:
                            break;
                    }
                }
            }
        } while (opcaoMenu != 0);
    }

    // INICIA A BASE DE DADOS PADRÃO DO SISTEMA

    public static boolean iniciarBase(int[] ids, String[] nomes, double[] precos, int[] estoque,
                                      int[] vendaAtualIds, int[] vendaAtualQuantidades,
                                      int[] historicoIdsPedidos, double[] historicoValoresPedidos, int[][] historicoItensVendidos) {

        ids[0] = 0;
        nomes[0] = "Camiseta";
        precos[0] = 49.90;
        estoque[0] = 20;


        ids[1] = 1;
        nomes[1] = "Calça Jeans";
        precos[1] = 120.00;
        estoque[1] = 15;


        ids[2] = 2;
        nomes[2] = "Tênis Esportivo";
        precos[2] = 250.00;
        estoque[2] = 10;


        ids[3] = 3;
        nomes[3] = "Moletom com Capuz";
        precos[3] = 95.50;
        estoque[3] = 8;


        ids[4] = 4;
        nomes[4] = "Meia Esportiva (par)";
        precos[4] = 15.00;
        estoque[4] = 50;

        // ZERA O HISTORICO DE VENDAS

        for (int i = 0; i < historicoIdsPedidos.length; i++) {
            vendaAtualIds[i] = 0;
            vendaAtualQuantidades[i] = 0;
            historicoIdsPedidos[i] = 0;
            historicoValoresPedidos[i] = 0;
        }
        for(int i = 0; i < historicoItensVendidos.length; i++){
            for (int j = 0; j < historicoItensVendidos[i].length; j++) {
                historicoItensVendidos[i][j] = 0;
            }
        }

        System.out.println("Base de dados inicializada com sucesso!");
        return true;
    }

    //EXIBE CATALOGOS DE PRODUTOS
    public static void exibeProdutos(int[] estoqueProdutos, String[] nomesProdutos, int[] idsProdutos, double[] precosProdutos) {
        System.out.println("--- CATALOGO DE PRODUTOS ---");
        System.out.println("----------------------------------------");
        for (int i = 0; i < estoqueProdutos.length; i++) {
            if (estoqueProdutos[i] != 0) {
                System.out.println("ID: " + idsProdutos[i] + " | Nome:" + nomesProdutos[i] + " | R$" + precosProdutos[i] + " | Estoque: " + estoqueProdutos[i]);

            }
        }
    }


    // ADICICIONAR ITEM A VENDA
    public static void adicionarItemVenda(Scanner scanner, int[] id, int[] estoque, int[] vendaAtualIds, int[] vendaAtualQuantidades) {
        System.out.println("Digite o ID do item que deseja vender: ");
        int IdVenda = scanner.nextInt();
        System.out.println("Digite a quantidade de itens que deseja vender: ");
        int quantidadeVenda = scanner.nextInt();
        boolean itemEncontrado = false;

        for (int i = 0; i < id.length; i++) {
            if(id[i] == IdVenda){
                itemEncontrado = true;
                if(estoque[i] >= quantidadeVenda){
                    for (int j = 0; j < vendaAtualIds.length; j++) {
                        if (vendaAtualIds[j] == 0){
                            vendaAtualIds[j] = IdVenda;
                            vendaAtualQuantidades[j] = quantidadeVenda;
                            System.out.println("Item adicionado com sucesso!");
                            return;
                        }
                    }
                }
                else {
                    System.out.printf("Quantidade solicitada: %d, excede o estoque disponivel: %d\n", quantidadeVenda, estoque[i]);
                }
                break;
            }

        }
        if(!itemEncontrado){
            System.out.println("Id do produto não encontrado.");
        }

    }

    // VER RESUMO DA VENDA ATUAL
    public static double VerResumoVendaAtual(int[] id, String[] nome, double[] preco, int[] vendaAtualIds, int[] vendaAtualQuantidades) {
        double totalVenda = 0;
        boolean vendaVazia = true;

        System.out.println("--- RESUMO DA VENDA ATUAL ---");
        System.out.println("----------------------------------------");
        for(int i = 0; i < vendaAtualIds.length; i++ ){
            if(vendaAtualIds[i] != 0){
                vendaVazia = false;

                for(int j = 0; j < id.length; j++ ) {
                    if(id[j] == vendaAtualIds[i]){
                        double subtotal = preco[j] * vendaAtualQuantidades[i];
                        System.out.printf("ID: %d | Nome: %s | Quantidade: %d | Subtotal: R$ %.2f\n", id[j], nome[j], vendaAtualQuantidades[i], subtotal);
                        totalVenda += subtotal;
                    }
                }
            }
        }
        if(vendaVazia){
            System.out.println("Não há itens na venda atual.");
        } else {
            System.out.println("----------------------------------------");
            System.out.printf("TOTAL DA VENDA: R$ %.2f\n", totalVenda);
            System.out.println("----------------------------------------");
        }
        return totalVenda;
    }

    //FINALIZAR VENDA
    public static void finalizarVenda(int [][] historicoItensVendidos, int[] historicoIdsPedidos, double[] historicoValoresPedidos, int[] vendaAtualIds, int[] id, String[] nome, double[] preco, int[] vendaAtualQuantidades, int[] estoque) {
        double totalVenda = 0;
        boolean vendaVazia = true;

        for(int i = 0; i < vendaAtualIds.length; i++ ){
            if(vendaAtualIds[i] != 0){
                vendaVazia = false;
                for(int j = 0; j < id.length; j++ ) {
                    if(id[j] == vendaAtualIds[i]){
                        totalVenda += preco[j] * vendaAtualQuantidades[i];
                        estoque[j] -= vendaAtualQuantidades[i];
                        break;
                    }
                }
            }
        }

        if (vendaVazia) {
            System.out.println("Não é possível finalizar uma venda vazia.");
            return;
        }

        int proximaPosicaoHistoricoPedido = -1;
        for (int i = 0; i < historicoIdsPedidos.length; i++) {
            if (historicoIdsPedidos[i] == 0) {
                proximaPosicaoHistoricoPedido = i;
                break;
            }
        }
        int novoIdPedido = proximaPosicaoHistoricoPedido + 1;

        historicoIdsPedidos[proximaPosicaoHistoricoPedido] = novoIdPedido;
        historicoValoresPedidos[proximaPosicaoHistoricoPedido] = totalVenda;

        int proximaPosicaoHistoricoItem = -1;
        for (int i = 0; i < vendaAtualIds.length; i++) {
            if (vendaAtualIds[i] != 0) {
                for (int j = 0; j < historicoItensVendidos.length; j++) {
                    if (historicoItensVendidos[j][0] == 0) {
                        proximaPosicaoHistoricoItem = j;
                        break;
                    }
                }

                if (proximaPosicaoHistoricoItem != -1) {
                    historicoItensVendidos[proximaPosicaoHistoricoItem][0] = novoIdPedido;
                    historicoItensVendidos[proximaPosicaoHistoricoItem][1] = vendaAtualIds[i];
                    historicoItensVendidos[proximaPosicaoHistoricoItem][2] = vendaAtualQuantidades[i];
                }
            }
        }
        imprimirNotaFiscal(novoIdPedido, totalVenda, vendaAtualIds, vendaAtualQuantidades, id, nome, preco);

        for (int i = 0; i < vendaAtualIds.length; i++) {
            vendaAtualIds[i] = 0;
            vendaAtualQuantidades[i] = 0;
        }
    }

    //VER HISTÓRICO DE VENDAS

    public static void verHistoricoVendas(int[] historicoIdsPedidos, double[] historicoValoresPedidos) {
        for (int i = 0; i < historicoIdsPedidos.length; i++) {
            if (historicoIdsPedidos[i] != 0) {
                System.out.println("Pedido ID: " + historicoIdsPedidos[i] + " - Valor Total: R$ " + historicoValoresPedidos[i]);
            }
        }
    }

    // Busca venda especifica do historico
    public static void buscarVendaEspecifica(Scanner scanner, int[] historicoIdsPedidos, double[] historicoValoresPedidos, int[][] historicoItensVendidos, int[] idsCatalogo, String[] nomesCatalogo, double[] precosCatalogo){
        System.out.print("Digite o ID do pedido que deseja buscar: ");
        int idPedidoBusca = scanner.nextInt();
        boolean pedidoEncontrado = false;

        for (int i = 0; i < historicoIdsPedidos.length; i++) {
            if (historicoIdsPedidos[i] == idPedidoBusca) {
                pedidoEncontrado = true;
                System.out.println("--- DETALHES DO PEDIDO ID: " + idPedidoBusca + " ---");
                System.out.println("Valor Total: R$ " + historicoValoresPedidos[i]);
                System.out.println("Itens:");

                for (int j = 0; j < historicoItensVendidos.length; j++) {
                    if (historicoItensVendidos[j][0] == idPedidoBusca) {
                        int idProdutoVendido = historicoItensVendidos[j][1];
                        int quantidadeVendida = historicoItensVendidos[j][2];

                        for (int k = 0; k < idsCatalogo.length; k++) {
                            if (idsCatalogo[k] == idProdutoVendido) {
                                double precoItem = precosCatalogo[k];
                                System.out.printf("  - %s (ID: %d) | Qtd: %d | Subtotal: R$ %.2f\n", nomesCatalogo[k], idProdutoVendido, quantidadeVendida, precoItem * quantidadeVendida);
                                break;
                            }
                        }
                    }
                }
                System.out.println("----------------------------------------");
                break;
            }
        }
        if (!pedidoEncontrado) {
            System.out.println("Pedido com ID " + idPedidoBusca + " não encontrado no histórico.");
        }
    }

    //(admin) Repor Estoque
    public static void reporEstoque(Scanner scanner, int[] id, int[] estoque){
        System.out.println("Digite o ID do item que deseja repor: ");
        int IdVenda = scanner.nextInt();
        System.out.println("Digite a quantidade que deseja repor no estoque: ");
        int quantidadeVenda = scanner.nextInt();
        boolean itemEncontrado = false;

        for(int i = 0; i < id.length; i++ ){
            if(id[i] == IdVenda){
                estoque[i] += quantidadeVenda;
                System.out.println("Estoque reposto com sucesso!");
                itemEncontrado = true;
                break;
            }
        }
        if (!itemEncontrado) {
            System.out.println("ID do produto não encontrado. Nenhum item foi reposto.");
        }
    }

    public static void relatorioEstoqueBaixo(int[] ids, String[] nomes, int[] estoque) {
        System.out.println("--- PRODUTOS COM BAIXO ESTOQUE ---");
        System.out.println("----------------------------------------");
        boolean itemEncontrado = false;
        for (int j = 0; j < ids.length; j++) {
            if (estoque[j] < 10 && estoque[j] > 0) {
                itemEncontrado = true;
                System.out.printf("ID: %d | Nome: %s | Quantidade: %d\n", ids[j], nomes[j], estoque[j]);
            }
        }
        if (!itemEncontrado) {
            System.out.println("Nenhum item está com baixo estoque.");
        }
    }

    //(admin)  Relatório de estoque baixo
    public static void imprimirNotaFiscal(int pedidoId, double total, int[] vendaAtualIds, int[] vendaAtualQuantidades, int[] idsCatalogo, String[] nomesCatalogo, double[] precosCatalogo){
        System.out.println("\n*********************************************************************************************");
        System.out.printf("* %-90s*\n", "MACKSHOP");
        System.out.printf("* %-90s*\n", "CNPJ: 12.345.678/0001-99" );
        System.out.println("*********************************************************************************************");
        System.out.printf("* %-90s*\n", "NOTA FISCAL - VENDA AO CONSUMIDOR");
        System.out.printf("* %-90s*\n", "Pedido ID: " + pedidoId);

        System.out.printf("* %-90s*\n", "Data de Emissão: 01/09/2025 15:15:30");

        System.out.println("*********************************************************************************************");
        System.out.printf("* %-2s| %-5s| %-20s| %-5s| %-12s| %-12s*\n", "#", "ID", "DESCRIÇÃO", "QTD", "VL. UNIT.", "VL. TOTAL");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        int numeroItem = 1;
        for(int i = 0; i < vendaAtualIds.length; i++){
            if(vendaAtualIds[i] != 0){
                int produtoId = vendaAtualIds[i];
                int quantidade = vendaAtualQuantidades[i];

                for(int j = 0; j < idsCatalogo.length; j++){
                    if(idsCatalogo[j] == produtoId){
                        String nomeProduto = nomesCatalogo[j];
                        double precoUnitario = precosCatalogo[j];
                        double subtotalItem = precoUnitario * quantidade;

                        System.out.printf("* %-2d| %-5d| %-20s| %-5d| R$ %-8.2f| R$ %-8.2f*\n", numeroItem, produtoId, nomeProduto, quantidade, precoUnitario, subtotalItem);
                        numeroItem++;
                        break;
                    }
                }
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.printf("* %-76sR$ %-8.2f *\n", "TOTAL", total);
        System.out.println("*********************************************************************************************");
    }
}

