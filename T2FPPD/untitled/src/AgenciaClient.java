import java.rmi.Naming;
import java.util.Scanner;

public class AgenciaClient {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso : java AgenciaClient <servidor> <nomeCliente>");
            System.exit(1);
        }

        try{
            ContaInterface banco = (ContaInterface) Naming.lookup("//" + args[0] + "/Banco");
            Scanner scanner = new Scanner(System.in);
            String nomeCliente = args[1];


            System.out.println("Bem-vindo ao sistema bancário!");
            System.out.print("Digite o nome do cliente: ");
            nomeCliente = scanner.nextLine();

            boolean exit = false;
            while (!exit) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1. Abrir Conta");
                System.out.println("2. Consultar Saldo");
                System.out.println("3. Depositar");
                System.out.println("4. Sacar");
                System.out.println("5. Fechar Conta");
                System.out.println("0. Sair");
                System.out.print("Opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine();  // Limpar o buffer de entrada

                switch (opcao) {
                    case 1:
                        if (banco.abrirConta(nomeCliente)) {
                            System.out.println("Conta aberta com sucesso para: " + nomeCliente);
                        } else {
                            System.out.println("Conta já existe para: " + nomeCliente);
                        }
                        break;

                    case 2:
                        double saldo = banco.consultarSaldo(nomeCliente);
                        if (saldo >= 0) {
                            System.out.println("Saldo atual: " + saldo);
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                        break;

                    case 3:
                        System.out.print("Digite o valor para depósito: ");
                        double valorDeposito = scanner.nextDouble();
                        if (banco.depositar(nomeCliente, valorDeposito)) {
                            System.out.println("Depósito de " + valorDeposito + " realizado com sucesso.");
                        } else {
                            System.out.println("Falha ao depositar. Verifique se a conta existe.");
                        }
                        break;

                    case 4:
                        System.out.print("Digite o valor para saque: ");
                        double valorSaque = scanner.nextDouble();
                        if (banco.sacar(nomeCliente, valorSaque)) {
                            System.out.println("Saque de " + valorSaque + " realizado com sucesso.");
                        } else {
                            System.out.println("Falha ao sacar. Verifique o saldo ou se a conta existe.");
                        }
                        break;

                    case 5:
                        if (banco.fecharConta(nomeCliente)) {
                            System.out.println("Conta fechada com sucesso para: " + nomeCliente);
                            exit = true;  // Sair após fechar a conta
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                        break;

                    case 0:
                        exit = true;
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

            System.out.println("Obrigado por usar o sistema bancário!");
            scanner.close();
        }catch (Exception e){
            System.err.println("Erro no cliente de agência: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
